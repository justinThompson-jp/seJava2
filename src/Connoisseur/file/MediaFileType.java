package Connoisseur.file;

import java.io.File;

/**
 * Enum used to differentiate between different types of media files
 * 
 * @author Jonathan Vallejo
 *
 */
public enum MediaFileType {

	DOCUMENT("Document"), AUDIO("Audio"), IMAGE("Image"), VIDEO("Video"), MISCELLANEOUS("Miscellaneous"),
	UNSPECIFIED("Unspecified"), UNKNOWN("Unknown");

	private String name;

	private MediaFileType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static MediaFileType detectType(File f) {
		int index = f.getName().lastIndexOf('.');
		String ext = "";
		if (index > 0) {
			ext = f.getName().substring(index + 1);
		}
		if (ext.equalsIgnoreCase("png") || ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg")) {
			return MediaFileType.IMAGE;
		} else if (ext.equalsIgnoreCase("mp3") || ext.equalsIgnoreCase("wav")) {
			return MediaFileType.AUDIO;
		} else if (ext.equalsIgnoreCase("mp4") || ext.equalsIgnoreCase("mov")) {
			return MediaFileType.VIDEO;
		} else if (ext.equalsIgnoreCase("docx") || ext.equalsIgnoreCase("pdf")) {
			return MediaFileType.DOCUMENT;
		} else {
			return MediaFileType.UNKNOWN;
		}
	}

}
