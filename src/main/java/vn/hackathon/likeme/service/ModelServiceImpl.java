package vn.hackathon.likeme.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import vn.hackathon.likeme.entity.Model;
import java.util.Date;

/**
 * Created by bangl on 2/25/16.
 */
public class ModelServiceImpl<T extends Model> implements ModelService<T>{

    MongoRepository<T, String> repository;

    public ModelServiceImpl(MongoRepository<T, String> repository){
        this.repository = repository;
    }

    @Override
    public T save(T t) {
        if(t == null){
            throw new IllegalArgumentException("model must not empty");
        }
        t.setLastUpTime(new Date());
        return repository.save(t);
    }

    @Override
    public T update(T t) {
        if(t == null) throw new IllegalArgumentException("model must not empty");
        return null;
    }

    @Override
    public boolean delete(String id) {
        if(StringUtils.isBlank(id)) throw new IllegalArgumentException("Id must not empty");
        return false;
    }

    @Override
    public T findById(String id) {
        if (StringUtils.isBlank(id)) throw new IllegalArgumentException("Id must not empty");
        return repository.findOne(id);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
