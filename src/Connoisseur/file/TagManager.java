package Connoisseur.file;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;

import Connoisseur.ConnoisseurGUI;
import Connoisseur.FileManager;

/**
 * Class to manage all the media tags
 * @author Jonathan Vallejo
 * TODO: This class will also manage all the searching by tag functions, etc.
 */
public class TagManager {
	
	//this arraylist will hold all the directories managed by the tag manager
	private ArrayList<MediaDirectory> directories = new ArrayList<MediaDirectory>();
	private FileManager fileManager = ConnoisseurGUI.getFileManager();
	
	/**
	 * TagManager constructor
	 */
	public TagManager() {
		this.init();
	}
	
	/**
	 * This function loads all the data from the directory-data.json and puts it into objects
	 */
	@SuppressWarnings({ "unchecked" })
	private void init() {
		//TODO: Identify which directories are managed by Connoisseur, load data for directories
		JSONObject directoryData = fileManager.getDirectoryData();
		Set<String> keys = directoryData.keySet();
		
		for (String filePath : keys) {
			File javaFile = new File(filePath);
			// TODO: If we have data on a file that no longer exists -> delete the data from our database.
			Map<String, Object> values = (Map<String, Object>) directoryData.get(filePath);
			String fileType = (String) values.get("file-type");
			String[] tags = values.get("tags").toString().replace("[", "").replace("]", "").split(",");
			
			
			MediaDirectory mDirectory = this.getDirectory(javaFile.getParent());
			if (mDirectory == null) {
				mDirectory = new MediaDirectory(javaFile.getParent());
				this.addDirectory(mDirectory);
			}
			
			MediaFile mFile = new MediaFile(javaFile, tags);
			mDirectory.addFile(mFile);
			if (fileType != null) {
				mFile.setFileType(MediaFileType.valueOf(fileType.toUpperCase()));
			} else {
				mFile.setFileType(MediaFileType.detectType(javaFile));
			}
		}
	}
	
	/**
	 * Get all the directories that our program has data on in the form of an ArrayList
	 * @return ArrayList<MediaDirectory>
	 */
	public ArrayList<MediaDirectory> getDirectories() {
		return directories;
	}
	
	/**
	 * Searches our data for the directory that matches the given path. Returns true if it exists in our data, false otherwise.
	 * @param path
	 * @return boolean
	 */
	public boolean hasDirectory(String path) {
		for (MediaDirectory dir : directories) {
			if (dir.getPath().equalsIgnoreCase(path)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Searches our data for the directory that matches the given path and returns a MediaDirectory object.
	 * @param path
	 * @return MediaDirectory
	 */
	public MediaDirectory getDirectory(String path) {
		for (MediaDirectory dir : directories) {
			if (dir.getPath().equalsIgnoreCase(path)) {
				return dir;
			}
		}
		return null;
	}
	
	/**
	 * Add's a Mediadirectory to the TagManager using the String path specified. 
	 * @param path
	 */
	public void addDirectory(String path) {
		if (!this.hasDirectory(path)) {
			directories.add(new MediaDirectory(path));
		}
	}
	
	/**
	 * Add's a MediaDirectory to the TagManager
	 * @param dir
	 */
	public void addDirectory(MediaDirectory dir) {
		directories.add(dir);
	}
	
	/**
	 * Searches our data for the given file and returns a MediaFile object of that file. Return's null if there is no data for the given file in our system.  
	 * @param file File to retrieve data on
	 * @return MediaFile
	 */
	public MediaFile findFile(File file) {
		for (MediaDirectory dirs : directories) {
			for (MediaFile mFile : dirs.getFiles()) {
				if (mFile.getJavaFile().getPath().equalsIgnoreCase(file.getPath())) {
					return mFile;
				}
			}
		}
		return null;
	}
	
	/**
	 * Adds the given MediaTag to the given file
	 * @param file File to add the tag to
	 * @param tag MediaTag to be added to the given file
	 */
	@SuppressWarnings("unchecked")
	public void addTag(File file, MediaTag tag) {
		MediaFile mFile = this.findFile(file);
		if (this.findFile(file) == null) {
			mFile = new MediaFile(file);
		}
		// fetch data from directory-data.json and update it
		Map<String, Object> fileData = (Map<String, Object>) fileManager.getDirectoryData().get(file.getPath());
		if (fileData == null) {
			fileData = new LinkedHashMap<String, Object>();
		}
		
		// add tag data to directory-data.json
		mFile.addTag(tag);
		fileData.put("tags", mFile.getTagsString());
		fileManager.getDirectoryData().put(file.getPath(), fileData);
		
		// update our system locally 
		MediaDirectory mDirectory = this.getDirectory(file.getParent());
		if (mDirectory == null) {
			mDirectory = new MediaDirectory(file.getParent());
			this.addDirectory(mDirectory);
		}
		
		mDirectory.addFile(mFile);
	}
	
	/**
	 * Sets the tags of the given file to the tags specified
	 * @param file File to add tags to
	 * @param tags Set of tags to apply to the given file
	 */
	@SuppressWarnings("unchecked")
	public void setTags(File file, String[] tags) {
		MediaFile mFile = this.findFile(file);
		if (this.findFile(file) == null) {
			mFile = new MediaFile(file);
		}
		// Create empty map object to set tags
		Map<String, Object> fileData = new LinkedHashMap<String, Object>();
		
		// add tag data to directory-data.json
		mFile.setTags(tags);
		
		fileData.put("tags", mFile.getTagsString());
		fileManager.getDirectoryData().put(file.getPath(), fileData);
		
		// update our system locally 
		MediaDirectory mDirectory = this.getDirectory(file.getParent());
		if (mDirectory == null) {
			mDirectory = new MediaDirectory(file.getParent());
			this.addDirectory(mDirectory);
		}
		
		mDirectory.addFile(mFile);
	}
	
	/**
	 * Searches the data in our data base and returns an ArrayList with MediaFiles that contain the specified tag
	 * @param tag
	 * @return ArrayList<MediaFile>
	 */
	public ArrayList<MediaFile> searchByTag(String tag) {
		ArrayList<MediaFile> results = new ArrayList<MediaFile>();
		for (MediaDirectory dir : directories) {
			for (MediaFile mFile : dir.getFiles()) {
				if (mFile.hasTag(tag.replace(" ", ""))) {
					results.add(mFile);
				}
			}
		}
		return results;
	}
	
	/**
	 * Searches the data in our data base and returns an ArrayList with MediaFiles that contain the specified tags
	 * @param tags
	 * @return ArrayList<MediaFile>
	 */
	public ArrayList<MediaFile> searchByTags(String[] tags) {
		ArrayList<MediaFile> results = new ArrayList<MediaFile>();
		for (MediaDirectory dir : directories) {
			for (MediaFile mFile : dir.getFiles()) {
				for (String tag : tags) {
					if (mFile.hasTag(tag.replace(" ", "")) && !results.contains(mFile)) {
						results.add(mFile);
					}
				}
			}
		}
		return results;
	}
	
}
