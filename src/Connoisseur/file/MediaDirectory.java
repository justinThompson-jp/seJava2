package Connoisseur.file;

import java.io.File;
import java.util.ArrayList;

/**
 * Class used to represent a directory
 * @author Jonathan Vallejo
 *
 */
public class MediaDirectory {
	
	// this is the path that corresponds to this directory's location
	private String path;
	
	// Java file representation of this directory
	private File file;
	
	//this arraylist will hold all the media files
	private ArrayList<MediaFile> mediaFiles = new ArrayList<MediaFile>();
	
	public MediaDirectory(String path) {
		this.path = path;
		this.file = new File(path);
		this.init();
	}
	
	/**
	 * Method to initialize a directory and its data. 
	 */
	private void init() {
		//TODO: Loop through files in directory, check if our database contains
		// If our data base contains it -> add it and its existing tags, otherwise just add it without any tags, 
		// maybe figure out possible tags automatically 
		
	}
	
	/**
	 * Method used to update a directory and its data
	 */
	private void reload(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.reload(path);
	}
	
	public ArrayList<MediaFile> getFiles() {
		return mediaFiles;
	}
	
	public void addFile(MediaFile file) {
		if (!this.hasFile(file)) {
			mediaFiles.add(file);
		}
	}
	
	public void removeFile(MediaFile file) {
		if (this.hasFile(file)) {
			mediaFiles.remove(file);
		}
	}
	
	public boolean hasFile(MediaFile file) {
		return mediaFiles.contains(file);
	}
	
	public File getJavaFile() {
		return file;
	}
}
