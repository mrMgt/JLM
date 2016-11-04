package vn.hackathon.likeme.service;

import com.google.common.collect.Lists;
//import com.mysema.query.BooleanBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.hackathon.likeme.constant.SystemConstant;
import vn.hackathon.likeme.entity.Buddy;
import vn.hackathon.likeme.entity.Hashtag;
import vn.hackathon.likeme.enums.PokeType;
import vn.hackathon.likeme.repository.BuddyRepository;
import vn.hackathon.likeme.repository.HashtagRepository;
import vn.hackathon.likeme.until.MeasureUntil;

import java.util.Date;
import java.util.List;

import static vn.hackathon.likeme.constant.SystemConstant.BUDDY_LIST_EMPTY;
import static vn.hackathon.likeme.constant.SystemConstant.DISTANCE_DEFAULT;
import static vn.hackathon.likeme.until.MeasureUntil.getDistance;


/**
 * Created by bangnl on 3/9/2016.
 */
@Service
@Transactional
public class BuddyServiceImpl  extends ModelServiceImpl<Buddy>  implements BuddyService{

    private final BuddyRepository buddyRepository;

    private final NotificationService notifiSv;

    @Autowired
    private final HashtagRepository hashtagRepository;


    @Autowired
    public BuddyServiceImpl( BuddyRepository buddyRepository,
                             NotificationService notifiSv, HashtagRepository hashtagRepository){
        super(buddyRepository);
        this.buddyRepository = buddyRepository;
        this.notifiSv = notifiSv;
        this.hashtagRepository = hashtagRepository;
    }




    // ko dung
    /*@Override
    public Buddy findAndRegisterBuddy(Buddy buddy) {

        if(StringUtils.isEmpty(buddy.getToken())) {
            throw new IllegalArgumentException("token of buddy can not empty");
        }

        Buddy buddyResult = this.buddyRepository.findByToken(buddy.getToken());

        if(buddyResult == null){
           buddyResult = this.buddyRepository.save(buddy);
        }
        return buddyResult;
    }*/

    @Override
    public List<Buddy> findByArrayHashtag(Buddy buddy) {
        if(buddy == null){
            throw new IllegalArgumentException("buddy must not null");
        }

        if(CollectionUtils.isEmpty(buddy.getHashtags())){
            return BUDDY_LIST_EMPTY;
        }
        /*BooleanBuilder builder = new BooleanBuilder();
        List<Hashtag> hashtags = buddy.getHashtags();

        for(Hashtag has : hashtags){
            builder.or(QBuddy.buddy.hashtags.any().hash.contains(has.getHash()));
        }
        return Lists.newArrayList(this.buddyRepository.findAll(builder));*/
        return null;
    }

    @Override
    public Buddy updateLocation(Buddy buddy) {

        Buddy buddyUpdate = this.findById(buddy.getId());
        if (buddyUpdate == null) {
            return null;
        } else {
            buddyUpdate.setLocation(buddy.getLocation());
            return this.buddyRepository.save(buddyUpdate);
        }
    }



//    @Override
//    public boolean pokeOrAccept(String tokenSend, String tokenReceive, PokeType pokeType) {
//        Buddy sender = this.buddyRepository.findByToken(tokenSend);
////        sender.setPokeType(pokeType);//linhnd
//        this.notifiSv.notificationToOneBuddy(sender, tokenReceive);
//        return false;
//
//    }

  /*  *//**
     *
     * @param
     * @return
     * author linhnd
     *//*
    @Override
    public Buddy findByToken(String token) {
        if(StringUtils.isBlank(token)){
            throw new IllegalArgumentException("token must not empty");
        }
        return this.buddyRepository.findByToken(token);

    }*/

    /*@Override
    public List<Buddy> serverSendBuddy(Buddy buddy) {
        List<Buddy> buddies = this.findByLocationWithin(buddy);
        if(CollectionUtils.isEmpty(buddies)){
            return  buddies;
        }
        this.notifiSv.notificationListBuddy(buddy, buddies);
        //buddies.stream().forEach(bd -> bd.setDistance(getDistance(buddy.getLocation(),bd.getLocation())));//linhnd comment
        buddies.sort((bd1, bd2) -> {
            return (int)(bd1.getDistance() - bd2.getDistance());
        });
        return buddies;
    }*/

    @Override
    @Transactional(readOnly = true)
    public List<Buddy> findByLocationWithin(Buddy buddy) {
        if(buddy == null){
            throw new IllegalArgumentException("buddy must not empty");
        }

        Point point = new Point(buddy.getLocation().getCoordinates()[0], buddy.getLocation().getCoordinates()[1]);
        Distance distance = MeasureUntil.getDistanceByMet(DISTANCE_DEFAULT);
        return this.buddyRepository.findByLocationNear(point, distance);
    }

    // linhnd
    @Override
    public List<Buddy> getListBuddySameHashtag(Buddy buddy) {
        StringBuffer hasBf = new StringBuffer();
        for(String has : buddy.getHashtags()){
            hasBf.append(has);
        }

        List<Buddy> buddies = this.findByLocationWithin(buddy);

        if (buddies == null) {
            return null;
        }

        buddies.stream().filter(bda -> {
            return compareHashtag(hasBf.toString(), bda);
        });

        return buddies;
    }



    //==================================================================================================================
    //linhnd
    @Override
    public Buddy registerBuddy(Buddy buddy) {
        /*Buddy buddydb = this.findByToken(buddy.getToken());
        if(buddydb != null){
            return buddydb;
        }*/
        this.buddyRepository.save(buddy);
        return buddy;
    }
    //linhnd
    @Override
    @Transactional(readOnly = true)
    public List<Buddy> findNearbyBuddy(Buddy buddy) {

//        Point point = new Point(buddy.getLocation().getCoordinates()[0], buddy.getLocation().getCoordinates()[1]);
//        Distance distance = MeasureUntil.getDistanceByMet(DISTANCE_DEFAULT);

        return this.buddyRepository.findByLocationNearBy(buddy.getHashtags(),new ObjectId(buddy.getId()), DISTANCE_DEFAULT, buddy.getLocation().getCoordinates());
    }



    private boolean compareHashtag(String hash,Buddy bd){
        boolean check = false;
        for(String h : bd.getHashtags()){
            if(hash.matches("/.*"+h+".*/i")){
                check = true;
                break;
            }
        }
        return check;
    }
}
