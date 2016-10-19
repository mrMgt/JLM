package vn.hackathon.likeme.entity;

import org.springframework.data.annotation.Transient;
import vn.hackathon.likeme.enums.PokeType;

/**
 * Created by linhnd on 2016/10/18.
 */
public class PokeHistory extends Model {
    private UserLocale poker;//a man is poking
    private UserLocale receivePoke;//a man is receive poking

    private String status;// 0:poked, 1: ok, 2: reject

    public UserLocale getPoker() {
        return poker;
    }

    public void setPoker(UserLocale poker) {
        this.poker = poker;
    }

    public UserLocale getReceivePoke() {
        return receivePoke;
    }

    public void setReceivePoke(UserLocale receivePoke) {
        this.receivePoke = receivePoke;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
