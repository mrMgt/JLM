package vn.hackathon.likeme.entity;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by linhnd on 2016/10/28.
 */
public class BushNotifyHistory extends Model {
    private  String name;//push notify name
    private String status;//0:wait,1:sent,2:ignore
    private JSONObject data;//content of data json
    private String typeSend;//single,multi
    private JSONArray jsonArrayFailed;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public String getTypeSend() {
        return typeSend;
    }

    public void setTypeSend(String typeSend) {
        this.typeSend = typeSend;
    }

    public JSONArray getJsonArrayFailed() {
        return jsonArrayFailed;
    }

    public void setJsonArrayFailed(JSONArray jsonArrayFailed) {
        this.jsonArrayFailed = jsonArrayFailed;
    }
}
