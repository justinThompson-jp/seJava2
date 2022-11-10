package Connoisseur.file;

import java.io.File;
import java.util.ArrayList;

/**
 * Class used to represent a type of media file within Connoisseur
 * @author Jonathan Vallejo
 *
 */
public class MediaFile {
	
	private File file;
	private MediaFileType fileType = MediaFileType.UNSPECIFIED; 
	
	private static ArrayList<MediaTag> tagsApplied = new ArrayList<MediaTag>();
	
	/**
	 * Constructor used to initialize a new MediaFile
	 * @param File file
	 */
	public MediaFile(File file) {
		this.file = file;
	}
	
	/**
	 * Constructor used to initialize a new MediaFile
	 * @param File file
	 * @param MediaFileType file type
	 */
	public MediaFile(File file, MediaFileType fileType) {
		this.file = file;
		this.fileType = fileType;
	}
	
	/**
	 * Method used to get the Java version of this file
	 * @return File 
	 */
	public File getJavaFile() {
		return file;
	}
	
	public ArrayList<MediaTag> getTags() {
		return tagsApplied;
	}
	
	public boolean hasTag(MediaTag tag) {
		return tagsApplied.contains(tag);
	}
	
	public void addTag(MediaTag tag) {
		tagsApplied.add(tag);
	}
	
	public void removeTag(MediaTag tag) {
		if (this.hasTag(tag)) tagsApplied.remove(tag);
	}
	
	public MediaFileType getFileType() {
		return fileType;
	}
	
	public void setFileType(MediaFileType fileType) {
		this.fileType = fileType;
	}
}
