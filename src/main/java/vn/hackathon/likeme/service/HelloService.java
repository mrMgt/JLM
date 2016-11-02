package vn.hackathon.likeme.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by linhnd on 2016/10/28.
 */
@Service
public class HelloService {
    Logger logger = LoggerFactory.getLogger(HelloService.class);

    public String doSlowWork() {
        logger.info("Start  slow work");
        System.out.println("start the work");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("finish slow work");
        System.out.println("end the work");
        return "index";       // return view's name
    }
}
