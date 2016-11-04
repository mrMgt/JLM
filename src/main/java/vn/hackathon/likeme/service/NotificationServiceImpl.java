package vn.hackathon.likeme.service;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import vn.hackathon.likeme.constant.SystemConstant;
import vn.hackathon.likeme.entity.Buddy;
import vn.hackathon.likeme.entity.BushNotifyHistory;
import vn.hackathon.likeme.entity.PushNotification;
import vn.hackathon.likeme.entity.UserLocale;
import vn.hackathon.likeme.until.DateUntil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import static vn.hackathon.likeme.constant.SystemConstant.API_KEY;
import static vn.hackathon.likeme.constant.SystemConstant.SENDER_ID;

/**
 * Created by bangnl on 3/9/16.
 */

@Service
public class NotificationServiceImpl implements NotificationService{
    @Autowired
    private BushNotifyHistoryService bushNotifyHistoryService;

    @Override
    public boolean notificationToOneBuddyByPokeHistory(String historyId, UserLocale poker, UserLocale receivePoke) {

        URL url = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        JSONObject jsonObjectResult = null;
        PushNotification pushNotification = null;
        List<UserLocale> userLocaleList = null;
        boolean isError = false;
        JSONObject jFCMData = null;
        JSONObject jData = null;

        try {
            // Prepare JSON containing the FCM message content. What to send and where to send.
            jFCMData = new JSONObject();
            jData = new JSONObject();
            pushNotification = new PushNotification();
//            pushNotification.setType("poking");
            pushNotification.setPokeHistoryId(historyId);
            userLocaleList = new ArrayList<>();
            userLocaleList.add(poker);
            pushNotification.setUserLocaleList(userLocaleList);
            jData.put("pushNotification", pushNotification);

            // Where to send FCM message.
            if (receivePoke != null) {
                jFCMData.put("to", receivePoke.getToken().trim());
            } else {
                jFCMData.put("to", "/topics/global");
            }
            // What to send in FCM message.
            jFCMData.put("data", jData);

            // Create connection to send FCM Message request.
            url = new URL("https://fcm.googleapis.com/fcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", "key=" + API_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // Send FCM message content.
            outputStream = conn.getOutputStream();
            outputStream.write(jFCMData.toString().getBytes());

            // Read FCM response.
            inputStream = conn.getInputStream();

            String resp = IOUtils.toString(inputStream);

            jsonObjectResult = new JSONObject(resp);

            //check result
            if (jsonObjectResult.isNull("error")) {
                //ok
                System.out.println(resp);
                System.out.println("Check your device/emulator for notification or logcat for " +
                        "confirmation of the receipt of the FCM message.");
                isError = true;
            }/* else {
                //store to db
                BushNotifyHistory bushNotifyHistory = new BushNotifyHistory();
                bushNotifyHistory.setName("acceptPoking");
                bushNotifyHistory.setStatus("0");//wating
                bushNotifyHistory.setTypeSend("single");
                bushNotifyHistory.setData(jFCMData);
                bushNotifyHistory.setCreatedTime(DateUntil.getDateByPattern(new Date()));

                bushNotifyHistoryService.save(bushNotifyHistory);
            }*/
        } catch (ConnectException ce) {
            System.out.println("Unable to send the message. Connection timed out!");
            ce.printStackTrace();
            isError = true;
        } catch (IOException e) {
            System.out.println("Unable to send the message.");
            System.out.println("Please ensure that API_KEY has been replaced by the server " +
                    "API key, and that the device's registration token is correct (if specified).");
            e.printStackTrace();
            isError = true;
        } finally {
            try {
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //store to resend later
        if (!isError) {
            try {
                BushNotifyHistory bushNotifyHistory = new BushNotifyHistory();
                bushNotifyHistory.setName("acceptPoking");
                bushNotifyHistory.setStatus("0");//wating
                bushNotifyHistory.setTypeSend("single");
                bushNotifyHistory.setData(jFCMData);
                bushNotifyHistory.setCreatedTime(DateUntil.getDateByPattern(new Date()));

                bushNotifyHistoryService.save(bushNotifyHistory);
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        }

        return true;
    }

    /**
     * push notification to many devices
     *
     * @param tokenList
     * @param userLocale
     * @return
     */
    @Override
    public boolean notificationToManyBuddy(UserLocale userLocale, List<String> tokenList) {

        String notificationKey = null;
        JSONObject jFCMData = null;
        JSONObject jData = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        JSONObject jsonObjectResult = null;
        PushNotification pushNotification = null;
        List<UserLocale> userLocaleList = null;
        SimpleDateFormat formatter = null;
        String pattern = "yyyy.MM.dd.hh:mm:ss.sss";
        Date today = new Date();
        boolean isError = false;

        formatter = new SimpleDateFormat(pattern);
        String notificationKeyName = "notificationKeyName_near_man_"
                + formatter.format(today);

        try {
            notificationKey = createNotificationKey(
                    SENDER_ID,//senderId
                    notificationKeyName,//notificationKeyName
                    tokenList//registrationId
            );
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }


        try {
            jFCMData = new JSONObject();
            jData = new JSONObject();
            pushNotification = new PushNotification();
            userLocaleList = new ArrayList<>();
            userLocaleList.add(userLocale);
            pushNotification.setUserLocaleList(userLocaleList);
            jData.put("pushNotification", pushNotification);


            // Where to send FCM message.
            if (notificationKey != null) {
                jFCMData.put("to", notificationKey);
            } else {
                jFCMData.put("to", "/topics/global");
            }
            // What to send in FCM message.
            jFCMData.put("data", jData);

            // Create connection to send FCM Message request.
            URL url = new URL("https://fcm.googleapis.com/fcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", "key=" + API_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // Send FCM message content.
            outputStream = conn.getOutputStream();
            outputStream.write(jFCMData.toString().getBytes());

            // Read FCM response.
            inputStream = conn.getInputStream();
            String resp = IOUtils.toString(inputStream);

            jsonObjectResult = new JSONObject(resp);

            //check result
            if (((Integer) jsonObjectResult.get("success")).equals(tokenList.size())) {
                //ok
                System.out.println(resp);
                System.out.println("Check your device/emulator for notification or logcat for " +
                        "confirmation of the receipt of the FCM message.");
                isError = true;
            }/* else {
                JSONArray jsonArrayFailed =  jsonObjectResult.getJSONArray("failed_registration_ids");
                //store to db
                BushNotifyHistory bushNotifyHistory = new BushNotifyHistory();
                bushNotifyHistory.setName("acceptPoking");
                bushNotifyHistory.setStatus("0");//wating
                bushNotifyHistory.setData(jFCMData);
                bushNotifyHistory.setTypeSend("multi");
                bushNotifyHistory.setJsonArrayFailed(jsonArrayFailed);
                bushNotifyHistory.setCreatedTime(DateUntil.getDateByPattern(new Date()));

                bushNotifyHistoryService.save(bushNotifyHistory);
            }*/
        } catch (ConnectException ce) {
            System.out.println("Unable to send the message. Connection timed out!");
            ce.printStackTrace();
            isError = true;
        } catch (IOException e) {
            System.out.println("Unable to send the message.");
            System.out.println("Please ensure that API_KEY has been replaced by the server " +
                    "API key, and that the device's registration token is correct (if specified).");
            e.printStackTrace();
            isError = true;
        } finally {
            try {
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!isError) {
            try {
                JSONArray jsonArrayFailed =  jsonObjectResult.getJSONArray("failed_registration_ids");
                //store to db
                BushNotifyHistory bushNotifyHistory = new BushNotifyHistory();
                bushNotifyHistory.setName("acceptPoking");
                bushNotifyHistory.setStatus("0");//wating
                bushNotifyHistory.setData(jFCMData);
                bushNotifyHistory.setTypeSend("multi");
                bushNotifyHistory.setJsonArrayFailed(jsonArrayFailed);
                bushNotifyHistory.setCreatedTime(DateUntil.getDateByPattern(new Date()));

                bushNotifyHistoryService.save(bushNotifyHistory);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return true;
    }

    public boolean notificationToManyBuddyToAccept(String pokeHistoryId, UserLocale poker, UserLocale receivePoke) {
        String notificationKey = null;
        JSONObject jFCMData = null;
        JSONObject jData = null;
        SimpleDateFormat formatter;
        String pattern = "yyyy.MM.dd.hh:mm:ss.sss";
        Date today = new Date();
        URL url = null;
        HttpURLConnection conn = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        String resp = null;
        JSONObject jsonObjectResult = null;
        List<String> tokenList = null;
        List<UserLocale> userLocaleList = null;
        PushNotification pushNotification = null;
        boolean isError = false;

        formatter = new SimpleDateFormat(pattern);
        String notificationKeyName = "notificationKeyName_acceptPoking_"
                + pokeHistoryId
                + formatter.format(today);
        tokenList = new ArrayList<>();
        tokenList.add(poker.getToken());
        tokenList.add(receivePoke.getToken());

        try {
            notificationKey = createNotificationKey(
                    SENDER_ID,//senderId
                    notificationKeyName,//notificationKeyName
                    tokenList//registrationId
            );
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }

        try {
            jFCMData = new JSONObject();
            jData = new JSONObject();

            pushNotification = new PushNotification();
            pushNotification.setPokeHistoryId(pokeHistoryId);
            userLocaleList = new ArrayList<>();
            userLocaleList.add(poker);
            userLocaleList.add(receivePoke);
            pushNotification.setUserLocaleList(userLocaleList);
            pushNotification.setSoundId(String.valueOf(( new Random()).nextInt(10)));
            jData.put("pushNotification", pushNotification);

            // Where to send FCM message.
            if (notificationKey != null) {
                jFCMData.put("to", notificationKey);
            } else {
                jFCMData.put("to", "/topics/global");
            }
            // What to send in FCM message.
            jFCMData.put("data", jData);

            // Create connection to send FCM Message request.
            url = new URL("https://fcm.googleapis.com/fcm/send");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", "key=" + API_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // Send FCM message content.
            outputStream = conn.getOutputStream();
            outputStream.write(jFCMData.toString().getBytes());

            // Read FCM response.
            inputStream = conn.getInputStream();
            resp = IOUtils.toString(inputStream);

            jsonObjectResult = new JSONObject(resp);

            //check result
            if (((Integer) jsonObjectResult.get("success")).equals(tokenList.size())) {
                //ok
                System.out.println(resp);
                System.out.println("Check your device/emulator for notification or logcat for " +
                        "confirmation of the receipt of the FCM message.");
                isError = true;
            } /*else {
                JSONArray jsonArrayFailed =  jsonObjectResult.getJSONArray("failed_registration_ids");
                //store to db
                BushNotifyHistory bushNotifyHistory = new BushNotifyHistory();
                bushNotifyHistory.setName("acceptPoking");
                bushNotifyHistory.setStatus("0");//wating
                bushNotifyHistory.setTypeSend("multi");
                bushNotifyHistory.setJsonArrayFailed(jsonArrayFailed);
                bushNotifyHistory.setData(jFCMData);
                bushNotifyHistory.setCreatedTime(DateUntil.getDateByPattern(new Date()));

                bushNotifyHistoryService.save(bushNotifyHistory);
            }*/


        } catch (ConnectException ce) {
            System.out.println("Unable to send the message. Connection timed out!");
            ce.printStackTrace();
            isError = true;
        } catch (IOException e) {
            System.out.println("Unable to send the message.");
            System.out.println("Please ensure that API_KEY has been replaced by the server " +
                    "API key, and that the device's registration token is correct (if specified).");
            e.printStackTrace();
            isError = true;
        }  catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!isError) {
            try {
                JSONArray jsonArrayFailed =  jsonObjectResult.getJSONArray("failed_registration_ids");
                //store to db
                BushNotifyHistory bushNotifyHistory = new BushNotifyHistory();
                bushNotifyHistory.setName("acceptPoking");
                bushNotifyHistory.setStatus("0");//wating
                bushNotifyHistory.setTypeSend("multi");
                bushNotifyHistory.setJsonArrayFailed(jsonArrayFailed);
                bushNotifyHistory.setData(jFCMData);
                bushNotifyHistory.setCreatedTime(DateUntil.getDateByPattern(new Date()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return true;
    }

    public String createNotificationKey(
            String senderId, String notificationKeyName, List<String> registrationId)
            throws IOException, JSONException {
        URL url = new URL("https://android.googleapis.com/gcm/notification");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoOutput(true);
        con.setConnectTimeout(5*60*1000);

        // HTTP request header
        con.setRequestProperty("project_id", senderId);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", "key=" + API_KEY);
        con.setRequestMethod("POST");
        con.connect();

        // HTTP request
        JSONObject data = new JSONObject();
        data.put("operation", "create");
        data.put("notification_key_name", notificationKeyName);
        data.put("registration_ids", new JSONArray(registrationId));

        OutputStream os = con.getOutputStream();
        os.write(data.toString().getBytes("UTF-8"));
        os.close();

        // Read the response into a string
        InputStream is = con.getInputStream();
        String responseString = new Scanner(is, "UTF-8").useDelimiter("\\A").next();
        is.close();

        // Parse the JSON string and return the notification key
        JSONObject response = new JSONObject(responseString);
        return response.getString("notification_key");
    }

    public String addNotificationKey(
            String senderId, String notificationKeyName, List<String> registrationId, String idToken)
            throws IOException, JSONException {
        URL url = new URL("https://android.googleapis.com/gcm/notification");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoOutput(true);

        // HTTP request header
        con.setRequestProperty("project_id", senderId);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestMethod("POST");
        con.connect();

        // HTTP request
        JSONObject data = new JSONObject();
        data.put("operation", "create");
        data.put("notification_key_name", notificationKeyName);
        //data.put("registration_ids", new JSONArray(Arrays.asList(registrationId)));
        data.put("registration_ids", new JSONArray(registrationId));
        data.put("id_token", idToken);

        OutputStream os = con.getOutputStream();
        os.write(data.toString().getBytes("UTF-8"));
        os.close();

        // Read the response into a string
        InputStream is = con.getInputStream();
        String responseString = new Scanner(is, "UTF-8").useDelimiter("\\A").next();
        is.close();

        // Parse the JSON string and return the notification key
        JSONObject response = new JSONObject(responseString);
        return response.getString("notification_key");

    }


   /* @Value("${"+ SystemConstant.AUTHORIZATION+"}")
    private String authorizationKey;

    @Value("${"+SystemConstant.SERVER_GCM+"}")
    private String serverGCM;

    @Override
    public boolean notificationListBuddy(Buddy sender, List<Buddy> receivers) {

        if(sender == null){
            throw new IllegalArgumentException("sender can not null");
        }

        if(CollectionUtils.isEmpty(receivers)){
            return true;
        }

        RestTemplate template = new RestTemplate();
        HttpHeaders headers = getHeaders();
        receivers.stream().forEach(buddy -> {
           sendDataToserverGCM(sender, headers, template, buddy.getToken());
        });
        return true;
    }

    @Override
    public boolean notificationToOneBuddy(Buddy sender, String tokenReceive) {
        sendDataToserverGCM(sender,tokenReceive);
        return true;
    }


    private HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization","key="+this.authorizationKey);
        return headers;
    }

    private void sendDataToserverGCM(Buddy data, String tokenReceiver){
        HttpHeaders headers =getHeaders();
        RestTemplate template = new RestTemplate();
        this.sendDataToserverGCM(data, headers,template,tokenReceiver);
    }

    private void sendDataToserverGCM(Buddy data, HttpHeaders headers, RestTemplate template, String tokenReceiver){
        Message<Buddy> message = new Message<>(tokenReceiver, data);
        HttpEntity<Message> entity = new HttpEntity<>(message, headers);
        template.exchange(this.serverGCM, HttpMethod.POST,entity, Object.class);
    }

    *//**
     * Class DTO for send message
     * @param <T>
     *//*
    private static class Message<T>{
        private String to;
        private T data;
        Message(String to, T data){
            this.to = to;
            this. data = data;
        }

        public T getData() {
            return data;
        }

        public String getTo() {
            return to;
        }
    }*/
}
