package Connoisseur;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
		
		// first time loading, i.e. playlist-data.json doesn't exist yet
		if (!playlist_file.exists()) {
			try {
				playlist_file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.playlist_data = new JSONObject();
			
			FileWriter file;
			try {
				file = new FileWriter(playlist_path);
				file.write(playlist_data.toString());
				file.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("[Connoisseur] Created playlist data file");
		} else {
			// TODO line 102 in FileManager
		}
		
		
	}
	
	
	// dummy main for testing
	public static void main(String[] args) {
		System.out.println("PlaylistManager Created");
	}
}
