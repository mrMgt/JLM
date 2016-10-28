package vn.hackathon.likeme.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import vn.hackathon.likeme.entity.Hashtag;
import vn.hackathon.likeme.repository.HashtagRepository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * Created by bangnl on 3/11/16.
 */

@Service
public class HashtagServiceImpl implements HashtagService{

    @Autowired
    private HashtagRepository hashtagRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<String> findByHash(String hash) {

        if(StringUtils.isBlank(hash)) {
            return converListHashtagToListString(this.hashtagRepository.findAll());
        }
        return converListHashtagToListString(this.hashtagRepository.findByNameContaining(hash, getPageDefault("hash")));
    }

    private Pageable getPageDefault(String hash) {
        return null;
    }

    private List<String> converListHashtagToListString(List<Hashtag> hashtags){
        List<String> strs = new ArrayList<>();
        hashtags.forEach(hashtag -> strs.add(hashtag.getName()));
        return strs;
    }

}
