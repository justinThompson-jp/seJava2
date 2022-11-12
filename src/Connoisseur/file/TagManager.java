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
	
	public TagManager() {
		this.init();
	}
	
	@SuppressWarnings({ "unchecked" })
	private void init() {
		//TODO: Identify which directories are managed by Connoisseur, load data for directories
		JSONObject directoryData = fileManager.getDirectoryData();
		Set<String> keys = directoryData.keySet();
		
		for (String filePath : keys) {
			File javaFile = new File(filePath);
			Map<String, Object> values = (Map<String, Object>) directoryData.get(filePath);
			String fileType = (String) values.get("file-type");
			String[] tags = values.get("tags").toString().replace("[", "").replace("]", "").split(",");
			
			
			MediaDirectory mDirectory = null;
			if (this.hasDirectory(javaFile.getParent())) {
				mDirectory = this.getDirectory(javaFile.getParent());
			} else {
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
	
	public ArrayList<MediaDirectory> getDirectories() {
		return directories;
	}
	
	public boolean hasDirectory(String path) {
		for (MediaDirectory dir : directories) {
			if (dir.getPath().equalsIgnoreCase(path)) {
				return true;
			}
		}
		return false;
	}
	
	public MediaDirectory getDirectory(String path) {
		for (MediaDirectory dir : directories) {
			if (dir.getPath().equalsIgnoreCase(path)) {
				return dir;
			}
		}
		return null;
	}
	
	public void addDirectory(String path) {
		if (!this.hasDirectory(path)) {
			directories.add(new MediaDirectory(path));
		}
	}
	
	public void addDirectory(MediaDirectory dir) {
		directories.add(dir);
	}
	
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
	 * @param file 
	 * @param tag
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
	
}
