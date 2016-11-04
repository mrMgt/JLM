package vn.hackathon.likeme.entity;

import java.util.List;

/**
 * Created by linhnd on 2016/10/24.
 */
public class PushNotification {

    private String type;
    private String pokeHistoryId;
    private List<UserLocale> userLocaleList;

    private String soundId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPokeHistoryId() {
        return pokeHistoryId;
    }

    public void setPokeHistoryId(String pokeHistoryId) {
        this.pokeHistoryId = pokeHistoryId;
    }

    public List<UserLocale> getUserLocaleList() {
        return userLocaleList;
    }

    public void setUserLocaleList(List<UserLocale> userLocaleList) {
        this.userLocaleList = userLocaleList;
    }

    public String getSoundId() {
        return soundId;
    }

    public void setSoundId(String soundId) {
        this.soundId = soundId;
    }
}
