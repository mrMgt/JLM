package vn.hackathon.likeme.output;

import vn.hackathon.likeme.entity.PokeHistory;

/**
 * Created by linhnd on 2016/10/19.
 */
public class PokeOutput extends ModelOutput{
    private PokeHistory pokeHistory;
    private PokeHistory[] pokeHistoryList;

    public PokeHistory getPokeHistory() {
        return pokeHistory;
    }

    public void setPokeHistory(PokeHistory pokeHistory) {
        this.pokeHistory = pokeHistory;
    }

    public PokeHistory[] getPokeHistoryList() {
        return pokeHistoryList;
    }

    public void setPokeHistoryList(PokeHistory[] pokeHistoryList) {
        this.pokeHistoryList = pokeHistoryList;
    }
}
