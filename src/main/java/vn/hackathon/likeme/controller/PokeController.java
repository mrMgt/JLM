package vn.hackathon.likeme.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import vn.hackathon.likeme.entity.Buddy;
import vn.hackathon.likeme.entity.PokeHistory;
import vn.hackathon.likeme.entity.UserLocale;
import vn.hackathon.likeme.enums.PokeType;
import vn.hackathon.likeme.output.PokeOutput;
import vn.hackathon.likeme.service.BuddyService;
import vn.hackathon.likeme.service.NotificationService;
import vn.hackathon.likeme.service.PokeHistoryService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by linhnd on 2016/10/18.
 */
@RestController
public class PokeController {
    @Autowired
    private BuddyService buddySv;

    @Autowired
    private PokeHistoryService pokeHistoryService;

    @Autowired
    private NotificationService notificationService;

    private PokeOutput pokeOutput;

    private boolean isError;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(path = "poke-accept", method = RequestMethod.POST)
    public boolean pokeOrAccept(@RequestParam String tokenSend,
                                @RequestParam String tokenReceive,
                                @RequestParam PokeType pokeType) {
        System.out.print(pokeType);
        return this.buddySv.pokeOrAccept(tokenSend, tokenReceive, pokeType);
    }

    @RequestMapping(path = "poke-action", method = RequestMethod.GET)
    public PokeOutput actionPoke(@RequestParam String pokerId, @RequestParam String receivePokeId) {
        pokeOutput = new PokeOutput();
        isError = false;
        Buddy buddy = null;
        PokeHistory pokeHistory = null;
        PokeHistory pokeHistorydb = null;
        Buddy buddyRequest = null;
        Buddy buddyReceive = null;
        UserLocale poker = null;
        UserLocale receivePoke = null;
        List<String> commonHashtag = null;

        try {

            buddyRequest = this.buddySv.findById(pokerId);
            buddyReceive = this.buddySv.findById(receivePokeId);

            if (buddyRequest == null || buddyReceive == null) {
                isError = true;
                pokeOutput.setErrorMessage("Information invalid!");
            }

            if (!isError) {
                poker = new UserLocale();
                poker.setId(buddyRequest.getId());
                poker.setLocation(buddyRequest.getLocation());
                poker.setToken(buddyRequest.getToken());
                poker.setDistance(buddyRequest.getDistance());

                receivePoke = new UserLocale();
                receivePoke.setId(buddyReceive.getId());
                receivePoke.setLocation(buddyReceive.getLocation());
                receivePoke.setToken(buddyReceive.getToken());
                receivePoke.setDistance(buddyReceive.getDistance());

                //set hashtag
                commonHashtag = getCommonItemsIn2List(buddyRequest.getHashtags(), buddyReceive.getHashtags());
                poker.setHashtags(commonHashtag);
                receivePoke.setHashtags(commonHashtag);

                pokeHistory = new PokeHistory();
                pokeHistory.setPoker(poker);
                pokeHistory.setReceivePoke(receivePoke);
                pokeHistory.setStatus("0");//0:poked
                pokeHistory.setCreatedTime(new Date());

                //insert in to PokeHistory
                pokeHistorydb = this.pokeHistoryService.registerPokeHistory(pokeHistory);

                //push notification
                this.notificationService.notificationToOneBuddyByPokeHistory(pokeHistorydb.getId(), poker, receivePoke, "Request to fuck!");
            }


        } catch (Exception ex) {
            ex.printStackTrace();
            pokeOutput.setResultCode("1");
            pokeOutput.setErrorMessage("Error occur");
        }

        if (isError) {
            pokeOutput.setResultCode("1");
        } else {
            pokeOutput.setResultCode("0");
        }

        return pokeOutput;
    }

    /**
     * 0: ok, 1: reject
     *
     * @param pokeHistoryId
     * @return PokeOutput
     */
    @RequestMapping(path = "poke-reply", method = RequestMethod.GET)
    public PokeOutput pokeReply(@RequestParam String pokeHistoryId, @RequestParam String status) {
        pokeOutput = new PokeOutput();
        isError = false;
        PokeHistory pokeHistoryDb = null;
        List<String> userLocaleList = null;
        Buddy poker = null;
        Buddy receivePoke = null;

        try {

            pokeHistoryDb = this.pokeHistoryService.findById(pokeHistoryId);

            if (pokeHistoryDb == null) {
                isError = true;
                pokeOutput.setErrorMessage("Information invalid");
            }

            if (StringUtils.isEmpty(status)) {
                isError = true;
                pokeOutput.setErrorMessage("Status invalid");
            }

            if (!isError) {
                poker = this.buddySv.findById(pokeHistoryDb.getPoker().getId());
                receivePoke = this.buddySv.findById(pokeHistoryDb.getReceivePoke().getId());

                //update status
                pokeHistoryDb.setStatus(status);
                pokeHistoryDb.getPoker().setToken(poker.getToken());
                pokeHistoryDb.getReceivePoke().setToken(receivePoke.getToken());
                this.pokeHistoryService.save(pokeHistoryDb);

                //push notification
                if (status.equals("1")) {//1: accept the request
                    userLocaleList = new ArrayList<>();
                    userLocaleList.add(pokeHistoryDb.getPoker().getToken());
                    userLocaleList.add(pokeHistoryDb.getReceivePoke().getToken());
                    this.notificationService.notificationToManyBuddy(userLocaleList, "Ok roi may ku!");
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            pokeOutput.setResultCode("1");
            pokeOutput.setErrorMessage("Error occur");
        }

        //insert in to PokeHistory
        if (isError) {
            pokeOutput.setResultCode("1");
        } else {
            pokeOutput.setResultCode("0");
        }

        return pokeOutput;
    }

    @RequestMapping(path = "get-poke-by-buddy", method = RequestMethod.POST)
    public PokeOutput getPokeByBuddy(Buddy buddy) {
        pokeOutput = new PokeOutput();
        isError = false;

        //insert in to PokeHistory

        return pokeOutput;
    }

    //get common items in 2 list
    private List<String> getCommonItemsIn2List(List<String> list1, List<String> list2) {
        List<String> result = null;
        if (list1 == null || list2 == null) {
            return  null;
        }

        result = new ArrayList<String>(list1);
        result.retainAll(list2);

        return result;
    }
}
