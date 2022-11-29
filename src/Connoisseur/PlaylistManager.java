package Connoisseur;

import java.io.File;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class PlaylistManager {

	private String appdata_dir = System.getenv("APPDATA") + "\\Connoisseur";
	
	private File playlist_file;
	private JSONObject playlist_data;
	
	
	// constructor(s)
	public PlaylistManager() {
		this.init();
	}
	
	// Setter method(s)
	
	
	// Getter method(s)
	
	
	// Other methods
	private void init() {
		
		// references the playlist json file
		String playlist_path = appdata_dir + "\\playlist-data.json";
		playlist_file = new File(playlist_path);
		
		// 
	}
	
	// dummy main for testing
	public static void main(String[] args) {
		System.out.println("PlaylistManager Created");
	}
}
