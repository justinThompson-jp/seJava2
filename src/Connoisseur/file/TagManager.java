package Connoisseur.file;

import java.io.File;
import java.util.ArrayList;
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
		
		for (String key : keys) {
			File javaFile = new File(key);
			MediaDirectory mDirectory = null;
			if (this.hasDirectory(javaFile.getParent())) {
				mDirectory = this.getDirectory(javaFile.getParent());
				fileManager.log("Already has directory: " + mDirectory.getPath());
			} else {
				mDirectory = new MediaDirectory(javaFile.getParent());
				this.addDirectory(mDirectory);
				fileManager.log("Added directory " + mDirectory.getPath());
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
	
}
