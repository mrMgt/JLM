package vn.hackathon.likeme.service;

import org.springframework.data.domain.Sort;
import vn.hackathon.likeme.entity.PokeHistory;

import java.util.List;

/**
 * Created by linhnd on 2016/10/20.
 */
public interface PokeHistoryService extends ModelService<PokeHistory>{
    PokeHistory registerPokeHistory(PokeHistory pokeHistory);

    PokeHistory updatePokeHistory(PokeHistory pokeHistory);

    List<PokeHistory> findAllPokeHistory();

    List<PokeHistory> findAllPokeHistory(Sort sort);

    List<PokeHistory> findPokeHistoryByBuddyId(String buddyId);

    List<PokeHistory> searchPokeHistory(String keyword);
}
