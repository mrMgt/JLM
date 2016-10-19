package vn.hackathon.likeme.output;

import vn.hackathon.likeme.entity.Buddy;

import java.util.List;

/**
 * Created by linhnd on 2016/10/18.
 */
public class BuddyOutput extends ModelOutput {
    private Buddy buddy;

    private List<Buddy> buddyList;

    public Buddy getBuddy() {
        return buddy;
    }

    public void setBuddy(Buddy buddy) {
        this.buddy = buddy;
    }

    public List<Buddy> getBuddyList() {
        return buddyList;
    }

    public void setBuddyList(List<Buddy> buddyList) {
        this.buddyList = buddyList;
    }
}
