package vn.hackathon.likeme.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.hackathon.likeme.service.HelloService;

import java.util.concurrent.Callable;

/**
 * Created by linhnd on 2016/10/28.
 */
@RestController
public class HelloController {
    Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    HelloService helloService;

    @RequestMapping("/helloAsync")
    public Callable<String> sayHelloAsync() {
        logger.info("Entering controller");

        Callable<String> asyncTask = new Callable<String>() {

            @Override
            public String call() throws Exception {
                return helloService.doSlowWork();
            }
        };

        logger.info("Leaving  controller");
        System.out.println("Leaving  controller");
        return asyncTask;
    }
}
