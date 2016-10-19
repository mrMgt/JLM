package vn.hackathon.likeme.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.hackathon.likeme.output.HashtagOutput;
import vn.hackathon.likeme.service.HashtagService;

import java.util.List;

/**
 * Created by bangnl on 3/11/2016.
 */

@RestController
public class HashtagController {

    @Autowired
    private HashtagService hashtagService;

    private HashtagOutput hashtagOutput;

    @RequestMapping("get-list-hash")
    public HashtagOutput getListHash(@RequestParam(required = false) String keyword){
        hashtagOutput = new HashtagOutput();

        hashtagOutput.setResultCode("0");
        hashtagOutput.setHashtagList(hashtagService.findByHash(keyword));

        return hashtagOutput;
    }

}
