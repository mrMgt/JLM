package vn.hackathon.likeme.entity;

import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by linhnd on 2016/10/19.
 */
public class UserLocale implements Serializable {
    private String name;
    private double[] location;//array of x,y
    private String status; // 0:poked, 1: ok, 2: reject

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

}
