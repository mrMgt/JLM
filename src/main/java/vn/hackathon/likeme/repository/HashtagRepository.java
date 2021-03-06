package vn.hackathon.likeme.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import vn.hackathon.likeme.entity.Hashtag;

import java.util.List;

/**
 * Created by bangnl on 3/11/16.
 */
public interface HashtagRepository extends MongoRepository<Hashtag,String> {
    Hashtag findByName(String name);

    List<Hashtag> findByNameContaining(String keyword, Pageable pageable);
}
