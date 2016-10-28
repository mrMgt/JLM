package vn.hackathon.likeme.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import vn.hackathon.likeme.entity.PokeHistory;
import vn.hackathon.likeme.repository.PokeHistoryRepository;

import java.util.List;

/**
 * Created by linhnd on 2016/10/20.
 */
@Service
public class PokeHistoryServiceImpl extends ModelServiceImpl<PokeHistory> implements PokeHistoryService {

    private PokeHistoryRepository pokeHistoryRepository;

    public PokeHistoryServiceImpl(MongoRepository<PokeHistory, String> repository) {
        super(repository);
    }


    @Override
    public PokeHistory registerPokeHistory(PokeHistory pokeHistory) {
        return super.save(pokeHistory);
    }

    @Override
    public PokeHistory updatePokeHistory(PokeHistory pokeHistory) {
        return super.save(pokeHistory);
    }

    @Override
    public List<PokeHistory> findAllPokeHistory() {
        List<PokeHistory> pokeHistories;
        pokeHistories = this.pokeHistoryRepository.findAll();
        return pokeHistories;
    }

    @Override
    public List<PokeHistory> findAllPokeHistory(Sort sort) {
        List<PokeHistory> pokeHistories;
        pokeHistories = this.pokeHistoryRepository.findAll(sort);
        return pokeHistories;
    }

    @Override
    public List<PokeHistory> findPokeHistoryByBuddyId(String buddyId) {
        List<PokeHistory> pokeHistories = null;
        return pokeHistories;
    }

    @Override
    public List<PokeHistory> searchPokeHistory(String keyword) {
        List<PokeHistory> pokeHistories = null;
        return pokeHistories;
    }
}
