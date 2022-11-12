package Connoisseur.file;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import Connoisseur.ConnoisseurGUI;

/**
 * Class used to represent a type of media file within Connoisseur
 * @author Jonathan Vallejo
 *
 */
public class MediaFile {
	
	private File file;
	private MediaFileType fileType = MediaFileType.UNSPECIFIED; 
	
	private ArrayList<MediaTag> tagsApplied;
	
	/**
	 * Constructor used to initialize a new MediaFile
	 * @param File file
	 */
	public MediaFile(File file) {
		this.file = file;
		this.tagsApplied = new ArrayList<MediaTag>();
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
		this.setFileType(fileType);
		this.tagsApplied = new ArrayList<MediaTag>();
	}
	
	/**
	 * Constructor used to initialize a new MediaFile and add tags to it
	 * @param file
	 * @param tags
	 */
	public MediaFile(File file, String[] tags) {
		this.file = file;
		this.tagsApplied = new ArrayList<MediaTag>();
		this.addTags(tags);
		this.detectFileType();
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
	
	public String getPath() {
		return file.getPath();
	}
	
	public ArrayList<MediaTag> getTags() {
		return tagsApplied;
	}
	
	public String getTagsString() {
		String result = "[";
		int size = tagsApplied.size();
		int count = 0;
		for (MediaTag tag : tagsApplied) {
			result = result + tag.getName();
			if (count < (size-1)) {
				result = result + ",";
			}
			count++;
		}
		return result + "]";
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
	
	public void setTags(String[] tags) {
		ArrayList<MediaTag> newTags = new ArrayList<MediaTag>();
		for (String tag : tags) {
			MediaTag mTag = new MediaTag(tag);
			newTags.add(mTag);
		}
		this.tagsApplied = newTags;
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
	
	@SuppressWarnings("unchecked")
	public void setFileType(MediaFileType fileType) {
		Map<String, Object> fileData = (Map<String, Object>) ConnoisseurGUI.getFileManager().getDirectoryData().get(file.getPath());
		if (fileData == null) {
			fileData = new LinkedHashMap<String, Object>();
		}
		
		// add file-type to directory-data.json
		fileData.put("file-type", fileType.getName());
		ConnoisseurGUI.getFileManager().getDirectoryData().put(file.getPath(), fileData);
		
		// update fileType locally
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
