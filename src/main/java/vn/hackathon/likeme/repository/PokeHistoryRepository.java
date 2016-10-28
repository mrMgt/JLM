package vn.hackathon.likeme.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import vn.hackathon.likeme.entity.Buddy;
import vn.hackathon.likeme.entity.PokeHistory;

/**
 * Created by linhnd on 2016/10/20.
 */
public interface PokeHistoryRepository extends MongoRepository<PokeHistory,String> {

}
