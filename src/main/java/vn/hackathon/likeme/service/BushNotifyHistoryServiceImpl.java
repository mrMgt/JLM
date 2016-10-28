package vn.hackathon.likeme.service;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.hackathon.likeme.entity.BushNotifyHistory;

/**
 * Created by linhnd on 2016/10/28.
 */
@Service
@Transactional
public class BushNotifyHistoryServiceImpl extends ModelServiceImpl<BushNotifyHistory>  implements BushNotifyHistoryService{
    public BushNotifyHistoryServiceImpl(MongoRepository<BushNotifyHistory, String> repository) {
        super(repository);
    }
}
