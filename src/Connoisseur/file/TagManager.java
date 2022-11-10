package Connoisseur.file;

import java.util.ArrayList;

/**
 * Class to manage all the media tags
 * @author Jonathan Vallejo
 * TODO: This class will also manage all the searching by tag functions, etc.
 */
public class TagManager {
	
	//this arraylist will hold all the directories managed by the tag manager
	private ArrayList<MediaDirectory> directories = new ArrayList<MediaDirectory>();
	
	public TagManager() {
		
	}
	
	private void init() {
		//TODO: Identify which directories are managed by Connoisseur, load data for directories
	}
	
	public ArrayList<MediaDirectory> getDirectories() {
		return directories;
	}
	
}
