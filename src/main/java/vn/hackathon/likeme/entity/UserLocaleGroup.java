package vn.hackathon.likeme.entity;

import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by linhnd on 2016/10/19.
 */
public class UserLocaleGroup extends Model implements Serializable {
    private String name;
    private double[] location;//array of x,y
    private String status; // 0:poked, 1: ok, 2: reject
    private String distance;
    private String[] hashtags; // the same hashtag for people
    private String token;//token for push notification


    /*private Date createdTime;

    @LastModifiedDate
    private Date lastUpTime;*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double[] getLocation() {
        return location;
    }

    public void setLocation(double[] location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String[] getHashtags() {
        return hashtags;
    }

    public void setHashtags(String[] hashtags) {
        this.hashtags = hashtags;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
