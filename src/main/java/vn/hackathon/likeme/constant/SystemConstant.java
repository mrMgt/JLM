package vn.hackathon.likeme.constant;



import vn.hackathon.likeme.entity.Buddy;
import vn.hackathon.likeme.entity.Hashtag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bangnl on 3/9/16.
 */
public class SystemConstant {
    public final static String AUTHORIZATION = "com.vn.ntt.authorization.key";
    public final static String SERVER_GCM = "com.vn.ntt.server.gcm";
    public final static List<Buddy> BUDDY_LIST_EMPTY = new ArrayList<>();
    public final static List<Hashtag> HASHTAG_LIST_EMPTY = new ArrayList<>();
    public final static int LIMIT_DEFAULT = 10;
    public final static double DISTANCE_DEFAULT = 20;
}
