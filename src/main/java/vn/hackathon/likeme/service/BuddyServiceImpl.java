package vn.hackathon.likeme.service;

import com.google.common.collect.Lists;
//import com.mysema.query.BooleanBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
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
import static vn.hackathon.likeme.until.MeasureUntil.getDistance;


/**
 * Created by bangnl on 3/9/2016.
 */
@Service
@Transactional
public class BuddyServiceImpl  extends ModelServiceImpl<Buddy>  implements BuddyService{

    private final BuddyRepository buddyRepository;

    private final NotificationService notifiSv;

    private final HashtagRepository hashtagRepository;


    @Autowired
    public BuddyServiceImpl( BuddyRepository buddyRepository,
                             NotificationService notifiSv, HashtagRepository hashtagRepository){
        super(buddyRepository);
        this.buddyRepository = buddyRepository;
        this.notifiSv = notifiSv;
        this.hashtagRepository = hashtagRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Buddy> findByLocationWithin(Buddy buddy) {
        if(buddy == null){
            throw new IllegalArgumentException("buddy must not empty");
        }
        if(ArrayUtils.isEmpty(buddy.getLocation())){
            throw new IllegalArgumentException("lat or lon must not empty");
        }

        if(buddy.getRadius() == null){
            buddy.setRadius(SystemConstant.DISTANCE_DEFAULT);
        }

        Point point = new Point(buddy.getLocation()[0], buddy.getLocation()[1]);
        Distance distance = MeasureUntil.getDistanceByMet(buddy.getRadius());
        return this.buddyRepository.findByLocationNear(point, distance);
    }

    @Override
    public Buddy findAndRegisterBuddy(Buddy buddy) {

        if(StringUtils.isEmpty(buddy.getToken())) {
            throw new IllegalArgumentException("token of buddy can not empty");
        }

        Buddy buddyResult = this.buddyRepository.findByToken(buddy.getToken());

        if(buddyResult == null){
           buddyResult = this.buddyRepository.save(buddy);
        }
        return buddyResult;
    }

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



    @Override
    public boolean pokeOrAccept(String tokenSend, String tokenReceive, PokeType pokeType) {
        Buddy sender = this.buddyRepository.findByToken(tokenSend);
        sender.setPokeType(pokeType);
        this.notifiSv.notificationToOneBuddy(sender, tokenReceive);
        return false;

    }

    @Override
    public Buddy findByToken(String token) {
        if(StringUtils.isBlank(token)){
            throw new IllegalArgumentException("token must not empty");
        }
        return this.buddyRepository.findByToken(token);

    }

    @Override
    public List<Buddy> serverSendBuddy(Buddy buddy) {
        List<Buddy> buddies = this.findByLocationWithin(buddy);
        if(CollectionUtils.isEmpty(buddies)){
            return  buddies;
        }
        this.notifiSv.notificationListBuddy(buddy, buddies);
        buddies.stream().forEach(bd -> bd.setDistance(getDistance(buddy.getLocation(),bd.getLocation())));
        buddies.sort((bd1, bd2) -> {
            return (int)(bd1.getDistance() - bd2.getDistance());
        });
        return buddies;
    }

    @Override
    public List<Buddy> getListBuddySameHashtag(Buddy buddy) {
        StringBuffer hasBf = new StringBuffer();
        for(Hashtag has : buddy.getHashtags()){
            hasBf.append(has.getHash());
        }
        Buddy buddyData = this.findByToken(buddy.getToken());
        List<Buddy> buddies = this.findByLocationWithin(buddy);
        buddies.stream().filter(bd -> {
            return compareHashtag(hasBf.toString(), bd);
        });
        return buddies;
    }

    private boolean compareHashtag(String hash,Buddy bd){
        boolean check = false;
        for(Hashtag h : bd.getHashtags()){
            if(hash.matches("/.*"+h.getHash()+".*/i")){
                check = true;
                break;
            }
        }
        return check;
    }

        @Override
        public Buddy registerBuddy(Buddy buddy) {
            Buddy buddydb = this.findByToken(buddy.getToken());
            if(buddydb != null){
                return buddydb;
            }
            super.save(buddy);
            return buddy;
        }
}
