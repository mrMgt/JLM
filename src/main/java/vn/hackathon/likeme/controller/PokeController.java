package vn.hackathon.likeme.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.hackathon.likeme.entity.Buddy;
import vn.hackathon.likeme.entity.PokeHistory;
import vn.hackathon.likeme.entity.UserLocale;
import vn.hackathon.likeme.enums.PokeType;
import vn.hackathon.likeme.output.PokeOutput;
import vn.hackathon.likeme.service.BuddyService;

/**
 * Created by linhnd on 2016/10/18.
 */
@RestController
public class PokeController {
    @Autowired
    private BuddyService buddySv;

    private PokeOutput pokeOutput;

    private boolean isError;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(path = "poke-accept", method = RequestMethod.POST)
    public boolean pokeOrAccept(@RequestParam String tokenSend,
                                @RequestParam String tokenReceive,
                                @RequestParam PokeType pokeType){
        System.out.print(pokeType);
        return this.buddySv.pokeOrAccept(tokenSend, tokenReceive, pokeType);
    }

    @RequestMapping(path = "poke-action", method = RequestMethod.POST)
    public PokeOutput actionPoke(UserLocale poker, UserLocale receivePoke) {
        pokeOutput = new PokeOutput();
        isError = false;

        //insert in to PokeHistory

        return pokeOutput;
    }

    /**
     * 0: ok, 1: reject
     *
     * @param pokeHistory
     * @return PokeOutput
     */
    @RequestMapping(path = "poke-reply", method = RequestMethod.POST)
    public PokeOutput pokeReply(PokeHistory pokeHistory) {
        pokeOutput = new PokeOutput();
        isError = false;
        PokeHistory pokeHistoryDb = null;



        //insert in to PokeHistory
        if (isError) {
            pokeOutput.setResultCode("1");
        } else {
            pokeOutput.setResultCode("0");
            pokeOutput.setPokeHistory(pokeHistoryDb);
        }

        return pokeOutput;
    }

    @RequestMapping(path = "get-poke-by-buddy", method = RequestMethod.POST)
    public PokeOutput getPokeByBuddy(Buddy buddy) {
        pokeOutput = new PokeOutput();
        isError = false;

        //insert in to PokeHistory

        return pokeOutput;
    }
}
