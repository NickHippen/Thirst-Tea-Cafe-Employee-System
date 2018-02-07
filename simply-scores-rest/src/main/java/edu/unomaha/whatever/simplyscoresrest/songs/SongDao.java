package edu.unomaha.whatever.simplyscoresrest.songs;

import java.util.List;

import edu.unomaha.whatever.simplyscoresrest.exceptions.AuthenticationException;
import edu.unomaha.whatever.simplyscoresrest.exceptions.ValidationException;

public interface SongDao {

	List<SongData> getSongSearchResult(SongRequestData request);

	SongData getSongByID(int request);	
}
