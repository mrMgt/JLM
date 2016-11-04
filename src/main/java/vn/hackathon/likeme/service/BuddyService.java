package vn.hackathon.likeme.service;


import vn.hackathon.likeme.entity.Buddy;
import vn.hackathon.likeme.enums.PokeType;

import java.util.List;

/**
 * Created by bangnl on 3/9/2016.
 */
public interface BuddyService extends ModelService<Buddy>{

    /**
     * get list buddys near a user
     *
     * @param buddy
     * @return list buddy
     */
    List<Buddy> findByLocationWithin(Buddy buddy);


    /**
     * update location for
     * @param buddy
     * @return
     */
    Buddy updateLocation(Buddy buddy);

    /**
     * poke another buddy
     * @param tokenSend
     * @param tokenReceive
     * @param pokeType
     * @return
     */
//    boolean pokeOrAccept(String tokenSend, String tokenReceive, PokeType pokeType);


    /**
     * return a buddy if exist in database vice versa save and return a buddy
     *
     * @param buddy
     * @return buddy registered
     */
//    Buddy findAndRegisterBuddy(Buddy buddy);

    /**
     * find list buddy have same hashtag in a buddy
     *
     * @param buddy
     * @return
     */
    List<Buddy> findByArrayHashtag(Buddy buddy);

    /**
     * find Buddy by token
     * @param token
     * @return
     */
//    Buddy findByToken(String token);

    /**
     *
     *
     * @param buddy
     * @return
     */
//    List<Buddy> serverSendBuddy(Buddy buddy);

    /**
     *
     * @param buddy
     * @return
     */
    List<Buddy> getListBuddySameHashtag(Buddy buddy);

    /**
     *
     * @param buddy
     * @return
     */
    Buddy registerBuddy(Buddy buddy);

    /**
     * linhnd
     * @param buddy
     * @return
     */
    public List<Buddy> findNearbyBuddy(Buddy buddy);

}
