package Connoisseur.file;

/**
 * Enum used to differentiate between different types of media files
 * @author Jonathan Vallejo
 *
 */
public enum MediaFileType {
	
	DOCUMENT("Document"), AUDIO("Audio"), IMAGE("Image"), VIDEO("Video"), 
	MISCELLANEOUS("Miscellaneous"), UNSPECIFIED("Unspecified"), UNKNOWN("Unknown");
	
	private String name;
	
	private MediaFileType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
}
