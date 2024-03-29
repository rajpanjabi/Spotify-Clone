package com.eecs3311.songmicroservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping
public class SongController {

	@Autowired
	private final SongDal songDal;

	private OkHttpClient client = new OkHttpClient();

	
	public SongController(SongDal songDal) {
		this.songDal = songDal;
	}

	/**
	 * This method is partially implemented for you to follow as an example of
	 * how to complete the implementations of methods in the controller classes.
	 * @param songId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getSongById/{songId}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getSongById(@PathVariable("songId") String songId,
			HttpServletRequest request) {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("path", String.format("GET %s", Utils.getUrl(request)));

		DbQueryStatus dbQueryStatus = songDal.findSongById(songId);
		
		// TODO: uncomment these two lines when you have completed the implementation of findSongById in SongDal
		 response.put("message", dbQueryStatus.getMessage());
	    return Utils.setResponseStatus(response, dbQueryStatus.getdbQueryExecResult(), dbQueryStatus.getData());
		//return null;
	
	}

	
	@RequestMapping(value = "/getSongTitleById/{songId}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getSongTitleById(@PathVariable("songId") String songId,
			HttpServletRequest request) {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("path", String.format("GET %s", Utils.getUrl(request)));
		
		
		DbQueryStatus dbQueryStatus = songDal.getSongTitleById(songId);
		
		// TODO: add any other values to the map following the example in getSongById
		
		    response.put("message", dbQueryStatus.getMessage());
		    return Utils.setResponseStatus(response, dbQueryStatus.getdbQueryExecResult(), dbQueryStatus.getData());

//		return ResponseEntity.status(HttpStatus.OK).body(response); // TODO: replace with return statement similar to in getSongById
	}

	
	@RequestMapping(value = "/deleteSongById/{songId}", method = RequestMethod.DELETE)
	public ResponseEntity<Map<String, Object>> deleteSongById(@PathVariable("songId") String songId,
			HttpServletRequest request) {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("path", String.format("DELETE %s", Utils.getUrl(request)));
		// TODO: add any other values to the map following the example in getSongById
		
		DbQueryStatus dbQueryStatus = songDal.deleteSongById(songId);
		
		 response.put("message", dbQueryStatus.getMessage());
		 return Utils.setResponseStatus(response, dbQueryStatus.getdbQueryExecResult(), dbQueryStatus.getData());
		
		
		
		

		//return ResponseEntity.status(HttpStatus.OK).body(response); // TODO: replace with return statement similar to in getSongById
	}

	
	@RequestMapping(value = "/addSong", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> addSong(@RequestBody Map<String, String> params,
			HttpServletRequest request) {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("path", String.format("POST %s", Utils.getUrl(request)));
		// TODO: add any other values to the map following the example in getSongById'
		
				
		System.out.println(params.get("songName"));
		
		
			String sName = (params.get("songName"));
			String sAl = (params.get("songAlbum"));
			String sAFN = (params.get("songArtistFullName"));
			//String sAntFavourites = 0;
		
		
		Song songToAdd= new Song(sName, sAFN, sAl);
		DbQueryStatus dbQueryStatus = songDal.addSong(songToAdd );

		//return ResponseEntity.status(HttpStatus.OK).body(response); // TODO: replace with return statement similar to in getSongById
		// TODO: uncomment these two lines when you have completed the implementation of findSongById in SongDal
		 response.put("message", dbQueryStatus.getMessage());
		 
		 return Utils.setResponseStatus(response, dbQueryStatus.getdbQueryExecResult(), dbQueryStatus.getData());
//		return null;
	
		
		
		
	}

	
	@RequestMapping(value = "/updateSongFavouritesCount", method = RequestMethod.PUT)
	public ResponseEntity<Map<String, Object>> updateFavouritesCount(@RequestBody Map<String, String> params, HttpServletRequest request) {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("data", String.format("PUT %s", Utils.getUrl(request)));
		// TODO: add any other values to the map following the example in getSongById

		//return ResponseEntity.status(HttpStatus.OK).body(response); // TODO: replace with return statement similar to in getSongById
		System.out.println(params);
		String id= params.get("songId");
		String dtr= params.get("shouldDecrement");
		Boolean bool;
		
		if (dtr.equals("true")) {
		 bool=true;
		 
		 }else {
			 bool=false;
		}
		
		System.out.println(id);
		//System.out.println(bool);
		
		
		DbQueryStatus dbQueryStatus = songDal.updateSongFavouritesCount(id, bool);

          response.put("message", dbQueryStatus.getMessage());
		 
		 return Utils.setResponseStatus(response, dbQueryStatus.getdbQueryExecResult(), dbQueryStatus.getData());
		
	//	return null;
		
		
	}
	
	
	@RequestMapping(value = "/composer", method = RequestMethod.PUT)
	public ResponseEntity<Map<String, Object>> updateComposerName(@RequestBody Map<String, String> params, HttpServletRequest request) {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("data", String.format("PUT %s", Utils.getUrl(request)));
		System.out.println(params);
		String id= params.get("songId");
		String composerName= params.get("composerName");
		
		
		
		
		System.out.println(id);
		System.out.println(composerName);
		
		
		DbQueryStatus dbQueryStatus = songDal.updateSongComposerName(id, composerName);

          response.put("message", dbQueryStatus.getMessage());
		 
		 return Utils.setResponseStatus(response, dbQueryStatus.getdbQueryExecResult(), dbQueryStatus.getData());
		
	//	return null;
		
		
	}
	@RequestMapping(value = "/year", method = RequestMethod.PUT)
	public ResponseEntity<Map<String, Object>> updateReleaseYear(@RequestBody Map<String, String> params, HttpServletRequest request) {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("data", String.format("PUT %s", Utils.getUrl(request)));
		System.out.println(params);
		String id= params.get("songId");
		Integer releaseYear= Integer.parseInt(params.get("releaseYear")) ;
		
		
		
		
		System.out.println(id);
		System.out.println(releaseYear);
		
		
		DbQueryStatus dbQueryStatus = songDal.updateReleaseYear(id, releaseYear);

          response.put("message", dbQueryStatus.getMessage());
		 
		 return Utils.setResponseStatus(response, dbQueryStatus.getdbQueryExecResult(), dbQueryStatus.getData());
		
	//	return null;
		
		
	}
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
