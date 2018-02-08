package com.thirstteacafe.employees.songs;

import java.util.List;

import com.thirstteacafe.employees.exceptions.AuthenticationException;
import com.thirstteacafe.employees.exceptions.ValidationException;

public interface SongDao {

	List<SongData> getSongSearchResult(SongRequestData request);

	SongData getSongByID(int request);	
}
