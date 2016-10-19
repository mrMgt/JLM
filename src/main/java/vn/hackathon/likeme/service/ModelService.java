package vn.hackathon.likeme.service;

/**
 * Created by root on 2/25/16.
 */

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.hackathon.likeme.entity.Model;

public interface  ModelService <T extends Model> {
    /**
     * save model
     * @param t
     * @return
     */
    T save(T t);

    /**
     * update model
     * @param t
     * @return
     */
    T update(T t);

    /**
     * delete id
     * @param id
     * @return
     */
    boolean delete(String id);

    /**
     * find model by Id
     * @param id
     * @return
     */
    T findById(String id);

    /**
     * find all
     * @param pageable
     * @return
     */
    Page<T> findAll(Pageable pageable);
}
