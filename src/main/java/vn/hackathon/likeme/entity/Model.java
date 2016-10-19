package vn.hackathon.likeme.entity;



import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * Created by bangnl on 2/25/16.
 */
public class Model {

    @Id
    private String id;

    private Date lastUpTime;

    private Date createdTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getLastUpTime() {
        return lastUpTime;
    }

    public void setLastUpTime(Date lastUpTime) {
        this.lastUpTime = lastUpTime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
