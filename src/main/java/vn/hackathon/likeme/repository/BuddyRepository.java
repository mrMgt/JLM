package vn.hackathon.likeme.repository;

import org.bson.types.ObjectId;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import vn.hackathon.likeme.entity.Buddy;

import java.util.List;

/**
 * Created by bangnl on 3/9/2016.
 */
//public interface BuddyRepository extends MongoRepository<Buddy,String>, QueryDslPredicateExecutor<Buddy> {
public interface BuddyRepository extends MongoRepository<Buddy,String> {
    /**
     * find location near a buddy
     *
     * @param point
     * @param distance
     * @return
     */
    List<Buddy> findByLocationNear(Point point, Distance distance);

    /**
     * find buddy by token
     *
     * @param token
     * @return
     */
    Buddy findByToken(String token);

//    GeoResults<Buddy> findByLocationNear(Point point, Distance distance);


    @Query("{"+
            "$and:[" +
            "{'_id': {$ne: ?1}},"+
            "{hashtags: { $in: ?0 }},"+
            "{"+
            "location:{"+
            "$near: {"+
            "$geometry: {"+
            "type: \"Point\" ,"+
            "coordinates: ?3"+
            "},"+
            "$maxDistance: ?2"+
            "}}}]"+
            "}"
    )
    List<Buddy> findByLocationNearBy(List<String> hashtags, ObjectId buddyId, double distance, double[] coordinates);
}
