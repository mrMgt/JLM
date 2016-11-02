package vn.hackathon.likeme.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import vn.hackathon.likeme.entity.BushNotifyHistory;

/**
 * Created by linhnd on 2016/10/28.
 */
public interface BushNotifyHistoryRepository extends MongoRepository<BushNotifyHistory,String> {
}
