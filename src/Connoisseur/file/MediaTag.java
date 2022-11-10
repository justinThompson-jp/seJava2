package Connoisseur.file;

/**
 * Class used to represent a tag that can be added to a MediaFile
 * @author Jonathan Vallejo
 *
 */
public class MediaTag {
	
	private String name;
	
	public MediaTag(String name) {
		this.name = name.toUpperCase();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name.toUpperCase();
	}

}
