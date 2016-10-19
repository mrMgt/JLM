package vn.hackathon.likeme.service;


import vn.hackathon.likeme.entity.Buddy;

import java.util.List;

/**
 * Service have responsibility interactive with GMC(Google Messages Cloud)
 *
 * @Author by bangnl on 3/9/16.
 */
public interface NotificationService {

    /**
     *notification for many buddies
     *
     * @param receivers
     * @return true if success
     */
    boolean notificationListBuddy(Buddy sender, List<Buddy> receivers);

    /**
     * notification for one buddies
     *
     * @param sender
     * @param tokenReceive
     * @return
     */
    boolean notificationToOneBuddy(Buddy sender, String tokenReceive);
}
