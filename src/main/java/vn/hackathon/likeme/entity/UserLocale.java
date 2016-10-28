package vn.hackathon.likeme.entity;

import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by linhnd on 2016/10/19.
 */
public class UserLocale implements Serializable {
    private String id;
    private String name;
    private Location location;//array of x,y
    private double distance;
    private List<String> hashtags; // the same hashtag for people

    private String token;//token for push notification

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    /*public double[] getLocation() {
        return location;
    }

    public void setLocation(double[] location) {
        this.location = location;
    }*/

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<String> hashtags) {
        this.hashtags = hashtags;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
