package edu.unomaha.whatever.simplyscoresrest.songs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SongService {

	@Autowired
	private SongDao songDao;
	
	public List<SongData> getSongSearchResult(SongRequestData request){
		return songDao.getSongSearchResult(request);
	}

	public SongData getSongByID(int request) {
		return songDao.getSongByID(request);
	}
}
