package vn.hackathon.likeme.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import vn.hackathon.likeme.entity.Buddy;
import vn.hackathon.likeme.enums.PokeType;
import vn.hackathon.likeme.output.BuddyOutput;
import vn.hackathon.likeme.service.BuddyService;
import vn.hackathon.likeme.service.HashtagService;

import java.util.List;

@RestController
public class BuddyController {

	@Autowired
	private BuddyService buddySv;

	@Autowired
	private HashtagService hashtagService;

	private BuddyOutput buddyOutput;

	private boolean isError;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * add new buddy
	 * @param buddy
	 * @return
	 */
	@RequestMapping(path = "buddy-register",method = RequestMethod.POST)
	public BuddyOutput addBuddy(@RequestBody Buddy buddy){
		Buddy buddyDb = null;
		buddyOutput = new BuddyOutput();
		buddyDb = buddySv.registerBuddy(buddy);
		buddyOutput.setBuddy(buddyDb);
		buddyOutput.setResultCode("0");
		return buddyOutput;
	}

	/**
	 * buddy is moving, then update location
	 *
	 * @param buddy
	 * @return
     */
	@RequestMapping(path = "buddy-update-location",method = RequestMethod.PUT)
	public BuddyOutput updateLocation(@RequestBody Buddy buddy){
		Buddy buddyDb = null;
		buddyOutput = new BuddyOutput();
		isError = false;

		if (buddy.getId() == null) {
			isError = false;
			buddyOutput.setErrorMessage("Information is invalid");
		}

		if (buddy.getLocation() == null ||
				buddy.getLocation().length<2) {
			isError = false;
			buddyOutput.setErrorMessage("Location is invalid");
		}

		buddyDb = this.buddySv.updateLocation(buddy);
		if (buddyDb==null) {
			isError = false;
			buddyOutput.setErrorMessage("Buddy is not existed");
		}

		if (isError) {
			buddyOutput.setResultCode("1");
		} else {
			buddyOutput.setResultCode("0");
			buddyOutput.setBuddy(buddyDb);
		}

		return buddyOutput;
	}



	@RequestMapping(path = "get-buddy")
	public BuddyOutput getUser(@RequestParam String buddyId){
		Buddy buddyDb = null;
		buddyOutput = new BuddyOutput();
		isError = false;

		if (StringUtils.isEmpty(buddyId)) {
			buddyDb = this.buddySv.findById(buddyId);
		}

		if (buddyDb == null) {
			buddyOutput.setErrorMessage("Buddy is not existed!");
			buddyOutput.setResultCode("1");
		} else {
			buddyOutput.setBuddy(buddyDb);
			buddyOutput.setResultCode("0");
		}

		return buddyOutput;
	}


	@RequestMapping(path = "get-list-buddy", method = RequestMethod.POST)
	public BuddyOutput getListBuddy(@RequestBody Buddy buddy){
		List<Buddy> buddyList = null;
		buddyOutput = new BuddyOutput();
		isError = false;

		if (buddy.getHashtags() == null) {
			isError = false;
			buddyOutput.setErrorMessage("Buddy has not any hash tag!");
		}

		if (!isError) {
			buddyList =  this.buddySv.getListBuddySameHashtag(buddy);
		}

		if (isError) {
			buddyOutput.setResultCode("0");
		} else {
			buddyOutput.setResultCode("1");
			buddyOutput.setBuddyList(buddyList);
		}

		return buddyOutput;
	}


    @RequestMapping(path = "server-send-buddy", method = RequestMethod.POST)
    public List<Buddy> serverSendBuddy(Buddy buddy){
        return this.serverSendBuddy(buddy);
    }



	@RequestMapping(path = "test")
	public String test() {
		return "ok";
	}
}
