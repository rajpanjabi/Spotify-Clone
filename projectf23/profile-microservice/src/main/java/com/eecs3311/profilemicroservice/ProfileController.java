package com.eecs3311.profilemicroservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eecs3311.profilemicroservice.Utils;
//import com.eecs3311.songmicroservice.DbQueryStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.Session;

@RestController
@RequestMapping
public class ProfileController {
	public static final String KEY_USER_NAME = "userName";
	public static final String KEY_USER_FULLNAME = "fullName";
	public static final String KEY_USER_PASSWORD = "password";
	public static final String KEY_FRIEND_USER_NAME = "friendUserName";
	public static final String KEY_SONG_ID = "songId";

	@Autowired
	private final ProfileDriverImpl profileDriver;

	@Autowired
	private final PlaylistDriverImpl playlistDriver;

	OkHttpClient client = new OkHttpClient();
	private final ObjectMapper mapper = new ObjectMapper();  // 

	public ProfileController(ProfileDriverImpl profileDriver, PlaylistDriverImpl playlistDriver) {
		this.profileDriver = profileDriver;
		this.playlistDriver = playlistDriver;
	}

	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> addProfile(@RequestBody Map<String, String> params, HttpServletRequest request) {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("path", String.format("POST %s", Utils.getUrl(request)));
		System.out.println(params);
		    String userName = params.get("userName");
	        String fullName = params.get("fullName");
	        String password = params.get("password");
		
		DbQueryStatus dbQueryStatus = profileDriver.createUserProfile( userName,  fullName,  password);
		response.put("message", dbQueryStatus.getMessage());
        return Utils.setResponseStatus(response, dbQueryStatus.getdbQueryExecResult(), dbQueryStatus.getData());
				
				
	}
	
	@RequestMapping(value = "/followFriend", method = RequestMethod.PUT)
	public ResponseEntity<Map<String, Object>> followFriend(@RequestBody Map<String, String> params, HttpServletRequest request) {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("path", String.format("PUT %s", Utils.getUrl(request)));
		System.out.println(params);
		
		    String userName = params.get("userName");
	        String frndUserName = params.get("friendUserName");
		
		
		DbQueryStatus dbQueryStatus = profileDriver.followFriend( userName, frndUserName);
		response.put("message", dbQueryStatus.getMessage());
        return Utils.setResponseStatus(response, dbQueryStatus.getdbQueryExecResult(), dbQueryStatus.getData());
				
		
		
		
		
		// TODO: add any other values to the map following the example in SongController.getSongById
		
//		return ResponseEntity.status(HttpStatus.OK).body(response); // TODO: replace with return statement similar to in getSongById
	}

	@RequestMapping(value = "/getAllFriendFavouriteSongTitles/{userName}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getAllFriendFavouriteSongTitles(@PathVariable("userName") String userName,
			HttpServletRequest request) {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("path", String.format("PUT %s", Utils.getUrl(request)));
		// TODO: add any other values to the map following the example in SongController.getSongById

		return ResponseEntity.status(HttpStatus.OK).body(response); // TODO: replace with return statement similar to in getSongById
	}


	@RequestMapping(value = "/unfollowFriend", method = RequestMethod.PUT)
	public ResponseEntity<Map<String, Object>> unfollowFriend(@RequestBody Map<String, String> params, HttpServletRequest request) {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("path", String.format("PUT %s", Utils.getUrl(request)));
		
	    String userName = params.get("userName");
        String frndUserName = params.get("friendUserName");
        
//		response.put("path", String.format("PUT %s", Utils.getUrl(request)));
		// TODO: add any other values to the map following the example in SongController.getSongById
		
		DbQueryStatus dbQueryStatus = profileDriver.unfollowFriend( userName, frndUserName);
     	response.put("message", dbQueryStatus.getMessage());
		
        return Utils.setResponseStatus(response, dbQueryStatus.getdbQueryExecResult(), dbQueryStatus.getData());
				
		
		
		

//		return ResponseEntity.status(HttpStatus.OK).body(response); // TODO: replace with return statement similar to in getSongById
	}

	@RequestMapping(value = "/likeSong", method = RequestMethod.PUT)
	public ResponseEntity<Map<String, Object>> likeSong(@RequestBody Map<String, String> params, HttpServletRequest request) {
		
		String userName = params.get("userName");
        String songId = params.get("songId");
        Map<String, Object> response = new HashMap<String, Object>();
		
        
        
        DbQueryStatus dbQueryStatus =playlistDriver.likeSong(userName, songId);

     	response.put("message", dbQueryStatus.getMessage());
		response.put("message", dbQueryStatus.getMessage());
        return Utils.setResponseStatus(response, dbQueryStatus.getdbQueryExecResult(), dbQueryStatus.getData());
				
        

	}
		
		
		
		
		
		
	@RequestMapping(value = "/unlikeSong", method = RequestMethod.PUT)
	public ResponseEntity<Map<String, Object>> unlikeSong(@RequestBody Map<String, String> params, HttpServletRequest request) {

		Map<String, Object> response = new HashMap<String, Object>();
//		response.put("path", String.format("PUT %s", Utils.getUrl(request)));
		
		    String userName = params.get("userName");
	        String songId = params.get("songId"); 
		
		

		DbQueryStatus dbQueryStatus = playlistDriver.unlikeSong(userName, songId);
		response.put("message", dbQueryStatus.getMessage());
        return Utils.setResponseStatus(response, dbQueryStatus.getdbQueryExecResult(), dbQueryStatus.getData());
				
		
		// TODO: add any other values to the map following the example in SongController.getSongById

//		return ResponseEntity.status(HttpStatus.OK).body(response); // TODO: replace with return statement similar to in getSongById
	}
}