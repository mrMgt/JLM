package vn.hackathon.likeme.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import vn.hackathon.likeme.constant.SystemConstant;
import vn.hackathon.likeme.entity.Buddy;
import vn.hackathon.likeme.entity.UserLocale;
import vn.hackathon.likeme.enums.PokeType;
import vn.hackathon.likeme.output.BuddyOutput;
import vn.hackathon.likeme.service.BuddyService;
import vn.hackathon.likeme.service.HashtagService;
import vn.hackathon.likeme.service.NotificationService;

import java.util.ArrayList;
import java.util.Date;
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

	@Autowired
	private NotificationService notificationService;


	/**
	 * add new buddy
	 *
	 * @param buddy
	 * @return
	 */
	@RequestMapping(path = "buddy-register", method = RequestMethod.POST)
	public BuddyOutput registerBuddy(@RequestBody Buddy buddy) {
		Buddy buddyDb = null;
		buddyOutput = new BuddyOutput();
		if (buddy.getDistance() == 0) {
			buddy.setDistance(SystemConstant.DISTANCE_DEFAULT);
		}
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
	@RequestMapping(path = "buddy-update", method = RequestMethod.POST)
	public BuddyOutput buddyUpdate(@RequestBody Buddy buddy) {
		Buddy buddyDb = null;
		buddyOutput = new BuddyOutput();
		isError = false;

		if (buddy.getId() == null) {
			isError = true;
			buddyOutput.setErrorMessage("Information is invalid");
		}

		if (buddy.getLocation() == null ||
				buddy.getLocation().getCoordinates().length < 2) {
			isError = true;
			buddyOutput.setErrorMessage("Location is invalid");
		}

		if (buddy.getLocation() == null ||
				buddy.getLocation().getCoordinates().length < 2) {
			isError = true;
			buddyOutput.setErrorMessage("Location is invalid");
		}

		buddyDb = this.buddySv.findById(buddy.getId());

		if (buddyDb == null) {
			isError = true;
			buddyOutput.setErrorMessage("Buddy is not existed");
		}

		//do not update name
		/*if (!isError && !buddy.getName().equals(buddyDb.getName())) {
			isError = false;
			buddyOutput.setErrorMessage("Name is invalid");
		}*/

		if (!isError) {
			buddy.setLastUpTime(new Date());
			buddyDb = this.buddySv.save(buddy);
		}

		if (isError) {
			buddyOutput.setResultCode("1");
		} else {
			buddyOutput.setResultCode("0");
		}

		return buddyOutput;
	}

	/**
	 * buddy is moving, then update location
	 *
	 * @param buddy
	 * @return
	 */
	@RequestMapping(path = "buddy-update-location", method = RequestMethod.POST)
	public BuddyOutput updateLocation(@RequestBody Buddy buddy) {
		Buddy buddyDb = null;
		buddyOutput = new BuddyOutput();
		isError = false;

		if (buddy.getId() == null) {
			isError = true;
			buddyOutput.setErrorMessage("Information is invalid");
		}

		if (buddy.getLocation() == null ||
				buddy.getLocation().getCoordinates().length < 2) {
			isError = true;
			buddyOutput.setErrorMessage("Location is invalid");
		}

		buddyDb = this.buddySv.updateLocation(buddy);
		if (buddyDb == null) {
			isError = true;
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
	public BuddyOutput getUser(@RequestParam String buddyId) {
		Buddy buddyDb = null;
		buddyOutput = new BuddyOutput();
		isError = false;

		if (!StringUtils.isEmpty(buddyId)) {
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


	/*@RequestMapping(path = "buddy-update-token",method = RequestMethod.POST)
	public BuddyOutput updateToken(@RequestParam(value="buddyId", required=false) String buddyId,@RequestParam(value="token", required=false) String token){
		Buddy buddyDb = null;
		buddyOutput = new BuddyOutput();
		isError = false;

		buddyDb = this.buddySv.findById(buddyId);

		if (buddyDb==null) {
			isError = true;
			buddyOutput.setErrorMessage("Buddy is not existed");
			buddyOutput.setResultCode("1");
		}

		if (!isError) {
			buddyDb.setToken(token);
			buddyDb.setLastUpTime(new Date());
			this.buddySv.save(buddyDb);
			buddyOutput.setResultCode("0");
		}

		return buddyOutput;
	}*/


	@RequestMapping(path = "get-list-buddy", method = RequestMethod.POST)
	public BuddyOutput getListBuddy(@RequestBody Buddy buddy) {
		List<Buddy> buddyList = null;
		buddyOutput = new BuddyOutput();
		isError = false;

		if (buddy.getHashtags() == null) {
			isError = false;
			buddyOutput.setErrorMessage("Buddy has not any hash tag!");
		}

		if (!isError) {
			buddyList = this.buddySv.getListBuddySameHashtag(buddy);
		}

		if (isError) {
			buddyOutput.setResultCode("1");
		} else {
			buddyOutput.setResultCode("0");
			buddyOutput.setBuddyList(buddyList);
		}

		return buddyOutput;
	}

	/**
	 * @param id
	 * @return
	 */
	@RequestMapping(path = "user-logout", method = RequestMethod.POST)
	public BuddyOutput userLogout(@RequestBody String id) {
		buddyOutput = new BuddyOutput();
		isError = false;
		Buddy buddy = null;

		buddy = this.buddySv.findById(id);

		if (buddy != null) {
			buddy.setLogout("1");
			this.buddySv.save(buddy);
			buddyOutput.setResultCode("0");
		} else {
			buddyOutput.setResultCode("1");
			buddyOutput.setErrorMessage("Information invalid");
		}

		return buddyOutput;
	}

	/**
	 * @param id
	 * @return
	 */
	@RequestMapping(path = "nearby-buddy", method = RequestMethod.GET)
	public BuddyOutput nearbyBuddy(@RequestParam String id, @RequestParam double[] location) {
		buddyOutput = new BuddyOutput();
		isError = false;
		Buddy buddy = null;
		List<Buddy> buddyList = null;

		try {


			buddy = this.buddySv.findById(id);

			if (buddy == null) {
				buddyOutput.setErrorMessage("Buddy information invalid");
				isError = true;
			}

			if (!isError) {
				buddy.setLastUpTime(new Date());
				//update location
				buddy = this.buddySv.save(buddy);
				buddyList = this.buddySv.findNearbyBuddy(buddy);


			}

		} catch (Exception ex) {
			ex.printStackTrace();
			buddyOutput.setResultCode("1");
			buddyOutput.setErrorMessage("System error!");
			return buddyOutput;
		}

		if (isError) {
			buddyOutput.setResultCode("1");
		} else {
			buddyOutput.setResultCode("0");
			buddyOutput.setBuddyList(buddyList);
		}

		return buddyOutput;
	}

	@RequestMapping(path = "auto-request", method = RequestMethod.POST)
	public BuddyOutput autoRequest(@RequestBody UserLocale userLocale) {
		List<Buddy> buddyList = null;
		buddyOutput = new BuddyOutput();
		isError = false;
		Buddy buddy = null;
		List<String> tokenList = null;
		boolean notificationResult = false;

		try {

			if (StringUtils.isEmpty(buddy.getToken())) {
				isError = true;
				buddyOutput.setErrorMessage("Need to update token!");
			}

			if (userLocale == null) {
				isError = true;
				buddyOutput.setErrorMessage("Information invalid!");
			}

			//update location
			if (!isError) {
				buddy = this.buddySv.findById(userLocale.getId());
				buddy.setLocation(userLocale.getLocation());
				this.buddySv.save(buddy);//update location

				buddyList = this.buddySv.getListBuddySameHashtag(buddy);

				if (buddyList == null) {
					isError = true;
					buddyOutput.setErrorMessage("No one near here!");
				}
			}

			if (!isError) {
				//send notification: List<String> tokenList, String messageContent
				tokenList = getListToken(buddyList);
				if (tokenList == null) {
					isError = true;
					buddyOutput.setErrorMessage("No one near here!");
				}
			}

			//send notification
			if (!isError) {
				notificationResult = this.notificationService.notificationToManyBuddy(tokenList, buddy.getToken());

				if (!notificationResult) {
					System.out.println("Result notification: " + notificationResult);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			buddyOutput.setResultCode("1");
			buddyOutput.setErrorMessage("System error!");
		}

		if (isError) {
			buddyOutput.setResultCode("1");
		} else {
			buddyOutput.setResultCode("0");
			buddyOutput.setBuddyList(buddyList);
		}

		return buddyOutput;
	}

	//using in poke
	@RequestMapping(path = "server-send-buddy", method = RequestMethod.POST)
	public List<Buddy> serverSendBuddy(Buddy buddy) {
		return this.serverSendBuddy(buddy);
	}


	@RequestMapping(path = "test")
	public String test() {
		return "ok";
	}

	/**
	 * get list token from list buddy
	 *
	 * @param buddyList
	 * @return
     */
	private List<String> getListToken(List<Buddy> buddyList) {
		List<String> result = new ArrayList<>();

		String token=null;
		for (Buddy buddy : buddyList) {
			if (!StringUtils.isEmpty(buddy.getToken())) {
				result.add(  buddy.getToken());
			}
		}
		return result;
	}

}
