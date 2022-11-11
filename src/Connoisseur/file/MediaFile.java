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
		this.detectFileType();
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
	 * Constructor used to initialize a new MediaFile and add tags to it
	 * @param file
	 * @param tags
	 */
	public MediaFile(File file, String[] tags) {
		this.file = file;
		this.addTags(tags);
	}
	
	/**
	 * Method used to get the Java version of this file
	 * @return File 
	 */
	public File getJavaFile() {
		return file;
	}
	
	public String getName() {
		return file.getName();
	}
	
	public ArrayList<MediaTag> getTags() {
		return tagsApplied;
	}
	
	public boolean hasTag(MediaTag tag) {
		return this.hasTag(tag.getName());
	}
	
	public boolean hasTag(String tag) {
		for (MediaTag mTag : tagsApplied) {
			if (mTag.getName().equalsIgnoreCase(tag)) {
				return true;
			}
		}
		return false;
	}
	
	public void addTag(MediaTag tag) {
		if (!this.hasTag(tag)) {
			tagsApplied.add(tag);
		}
	}
	
	public void addTags(String[] tags) {
		for (String tag : tags) {
			if (!this.hasTag(tag)) {
				this.addTag(new MediaTag(tag));
			}
		}
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
	
	private void detectFileType() {
		int index = file.getName().lastIndexOf('.');
		String ext = "";
		if (index > 0) {
			ext = file.getName().substring(index + 1);
		}
		if (ext.equalsIgnoreCase("png") || ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg")) {
			this.setFileType(MediaFileType.IMAGE);
		} else if (ext.equalsIgnoreCase("mp3") || ext.equalsIgnoreCase("wav")) {
			this.setFileType(MediaFileType.AUDIO);
		} else if (ext.equalsIgnoreCase("mp4") || ext.equalsIgnoreCase("mov")) {
			this.setFileType(MediaFileType.VIDEO);
		} else if (ext.equalsIgnoreCase("docx") || ext.equalsIgnoreCase("pdf")) {
			this.setFileType(MediaFileType.DOCUMENT);
		} else {
			this.setFileType(MediaFileType.UNKNOWN);
		}
	}
}
