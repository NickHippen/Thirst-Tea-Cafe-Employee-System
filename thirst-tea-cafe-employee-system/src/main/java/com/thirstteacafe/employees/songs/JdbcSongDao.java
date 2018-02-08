package com.thirstteacafe.employees.songs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.thirstteacafe.employees.exceptions.AuthenticationException;

@Component
public class JdbcSongDao implements SongDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<SongData> getSongSearchResult(SongRequestData request){
		List<SongData> songs = jdbcTemplate.query("SELECT S.SONG_ID, S.SONG_NAME, S.SONG_PACK, S.DIFFICULTY FROM songs S WHERE LOWER(S.SONG_NAME) LIKE LOWER(?)",
			new Object[] { "%"+request.getSongName()+"%"},
			new RowMapper<SongData>() {
				@Override
				public SongData mapRow(ResultSet rs, int rowNum) throws SQLException {
					SongData results = new SongData();
					results.setSongId(rs.getInt("SONG_ID"));
					results.setSongName(rs.getString("SONG_NAME"));
					results.setSongPack(rs.getString("SONG_PACK"));
					results.setDifficulty(rs.getInt("DIFFICULTY"));
					
					return results;
				}
			}
		);
		return songs;
	}
	
	@Override
	public SongData getSongByID(int request){
		List<SongData> songs = jdbcTemplate.query("SELECT S.SONG_ID, S.SONG_NAME, S.SONG_PACK, S.DIFFICULTY FROM songs S WHERE S.SONG_ID=?",
			new Object[] {request},
			new RowMapper<SongData>() {
				@Override
				public SongData mapRow(ResultSet rs, int rowNum) throws SQLException {
					SongData results = new SongData();
					results.setSongId(rs.getInt("SONG_ID"));
					results.setSongName(rs.getString("SONG_NAME"));
					results.setSongPack(rs.getString("SONG_PACK"));
					results.setDifficulty(rs.getInt("DIFFICULTY"));
					
					return results;
				}
			}
		);
		return songs.get(0);
	}
}
