package edu.unomaha.whatever.simplyscoresrest.songs;

public class SongData {

	private int songId;
	private String songName;
	private String songPack;
	private int difficulty;

	public String getSongName() {
		return songName;
	}
	
	public void setSongName(String songName) {
		this.songName = songName;
	}

	public int getSongId() {
		return songId;
	}

	public void setSongId(int songId) {
		this.songId = songId;
	}

	public String getSongPack() {
		return songPack;
	}

	public void setSongPack(String songPack) {
		this.songPack = songPack;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
}
