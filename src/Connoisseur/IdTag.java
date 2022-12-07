package Connoisseur;

import java.io.Serializable;

// Code by: Aristan Galindo

public class IdTag implements Serializable {

	// Needed for serialization of the class
	private static final long serialVersionUID = 1L;
	
	private long tagId;
	
	// Tag fields we will mainly use
	private String title;
	private String album;
	private String artist;
	private String genre;
	private byte[] image;
	private long duration;
	private String year;
	
	// Setters and getters for the designated metadata variables
	// Only included certain characteristics as these are the main ones used
	// for identifying a song/audio file
	public IdTag() {
		
	}
	
	public long getTagId() {
		return tagId;
	}
	
	public void setTagId(long idTagId) {
		this.tagId = idTagId;
	}
	
	public String getAlbum() {
		return this.album;
	}
	
	public void setAlbum(String album) {
		this.album = album;
	}
	
	public String getArtist() {
		return this.artist;
	}
	
	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	public String getGenre() {
		return this.genre;
	}
	
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	// This will be for the artwork/thumbnail of the files, if they have them
	public byte [] getImage() {
		return this.image;
	}
	
	public void setImage(byte[] image) {
		this.image = image;
	}
	
	public long getDuration() {
		return this.duration;
	}
	
	public void setDuration(long duration) {
		this.duration = duration;
	}
	
	public String getTitle( ) {
		return this.title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getYear() {
		return this.year;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
}