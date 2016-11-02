package vn.hackathon.likeme.constant;



import vn.hackathon.likeme.entity.Buddy;
import vn.hackathon.likeme.entity.Hashtag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bangnl on 3/9/16.
 */
public class SystemConstant {
    public final static String AUTHORIZATION = "com.vn.ntt.authorization.key";
    public final static String SERVER_GCM = "com.vn.ntt.server.gcm";
    public final static List<Buddy> BUDDY_LIST_EMPTY = new ArrayList<>();
    public final static List<Hashtag> HASHTAG_LIST_EMPTY = new ArrayList<>();
    public final static int LIMIT_DEFAULT = 10;
    public final static double DISTANCE_DEFAULT = 100;
    //public final static String API_KEY = "AIzaSyDXkoDPgKwitrdUybiKpnxDMkWcT4aqso4";
    public final static String API_KEY = "AIzaSyCirQqn9Fpvv0xC0lDN1ahUQ35E5vQh44o";
    //public final static String SENDER_ID = "197858972728";
    public final static String SENDER_ID = "797002799597";
    public static String NOTIFY_TYPE_POKING = "poking";
    public static String NOTIFY_TYPE_ACCEPT_POKED = "accept_poked";
    public static String NOTIFY_TYPE_NEAR_MAN = "near_man";

}
