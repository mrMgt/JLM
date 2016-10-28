package vn.hackathon.likeme.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by bangnl on 3/9/2016.
 */

@Document
public class BuddyTemp extends Model {
    private String token;//google token
    private String name;//name of man
    private Location[] location;//array of x,y

    private List<String> hashtags;//list of hash tags

    /*@Transient
    private PokeType pokeType;*/

    /*@Transient
    private Double radius;*/

    private Double distance;//distance for scan anyone

    private String logout;//0: login, 1: logout

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location[] getLocation() {
        return location;
    }

    public void setLocation(Location[] location) {
        this.location = location;
    }

    /*public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    public PokeType getPokeType() {
        return pokeType;
    }

    public void setPokeType(PokeType pokeType) {
        this.pokeType = pokeType;
    }*/

    public List<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<String> hashtags) {
        this.hashtags = hashtags;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getLogout() {
        return logout;
    }

    public void setLogout(String logout) {
        this.logout = logout;
    }
}
