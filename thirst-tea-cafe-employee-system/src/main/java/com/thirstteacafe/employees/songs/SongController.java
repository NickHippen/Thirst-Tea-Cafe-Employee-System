package com.thirstteacafe.employees.songs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class SongController {

	@Autowired
	private SongService songService;
	
	@RequestMapping(value="/songSearch", method=RequestMethod.POST)
	public List<SongData> getSongs(@RequestBody SongRequestData request){
		return songService.getSongSearchResult(request);
	}
	
	@RequestMapping(value="/song/{songID}", method=RequestMethod.GET)
	public SongData getSongByID(@PathVariable Integer songID){
		return songService.getSongByID(songID);
	}
}
