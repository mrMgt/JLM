package vn.hackathon.likeme.output;

import vn.hackathon.likeme.entity.Hashtag;
import vn.hackathon.likeme.output.ModelOutput;

import java.util.List;

/**
 * Created by linhnd on 2016/10/18.
 */
public class HashtagOutput extends ModelOutput {
    private List<String> hashtagList;

    public List<String> getHashtagList() {
        return hashtagList;
    }

    public void setHashtagList(List<String> hashtagList) {
        this.hashtagList = hashtagList;
    }
}
