package vn.hackathon.likeme.entity;

/**
 * Created by linhnd on 2016/10/24.
 */
public class PushNotification {
    private String historyId;//is of poke history
    private UserLocale userLocale;


    public String getHistoryId() {
        return historyId;
    }

    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }

    public UserLocale getUserLocale() {
        return userLocale;
    }

    public void setUserLocale(UserLocale userLocale) {
        this.userLocale = userLocale;
    }
}
