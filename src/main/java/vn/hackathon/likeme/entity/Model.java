package vn.hackathon.likeme.entity;



import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * Created by bangnl on 2/25/16.
 */
public class Model {

    @Id
    private String id;

    private String lastUpTime;

    private String createdTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastUpTime() {
        return lastUpTime;
    }

    public void setLastUpTime(String lastUpTime) {
        this.lastUpTime = lastUpTime;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }
}
