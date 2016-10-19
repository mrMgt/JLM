package vn.hackathon.likeme.entity;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import vn.hackathon.likeme.constant.SystemConstant;
import vn.hackathon.likeme.enums.PokeType;
import vn.hackathon.likeme.until.MeasureUntil;

import java.util.Date;
import java.util.List;

import static sun.plugin.javascript.navig.JSType.Document;

/**
 * Created by bangnl on 3/9/2016.
 */

@Document
public class Buddy extends Model {
    private String token;//google token
    private String name;//name of man
    private double[] location;//array of x,y

    private List<String> hashtags;//list of hash tags

    /*@Transient
    private PokeType pokeType;*/

    /*@Transient
    private Double radius;*/

    @Transient
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

    public double[] getLocation() {
        return location;
    }

    public void setLocation(double[] location) {
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
