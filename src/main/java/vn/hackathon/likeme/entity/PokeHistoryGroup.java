package vn.hackathon.likeme.entity;

/**
 * Created by linhnd on 2016/10/19.
 */
public class PokeHistoryGroup extends Model {
    private UserLocale poker;//a man is poking
    private UserLocaleGroup[] receivePoke;//a man is receive poking

    private String complete;// 0/1: flag check is completed

    public UserLocale getPoker() {
        return poker;
    }

    public void setPoker(UserLocale poker) {
        this.poker = poker;
    }

    public UserLocaleGroup[] getReceivePoke() {
        return receivePoke;
    }

    public void setReceivePoke(UserLocaleGroup[] receivePoke) {
        this.receivePoke = receivePoke;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }
}
