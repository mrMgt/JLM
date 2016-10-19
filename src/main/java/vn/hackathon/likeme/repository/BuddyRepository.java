package vn.hackathon.likeme.repository;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
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
}
