package Connoisseur;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import Connoisseur.file.MediaFile;

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
	public File getPlaylistFile() {
		return playlist_file;
	}
	
	public JSONObject getPlaylistData() {
		return playlist_data;
	}
	
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
			System.out.println("[Connoisseur] Created playlist data file.");
		
		// the playlist-data.json already exists
		} else {
			try {
				JSONParser parser = new JSONParser();
				this.playlist_data = (JSONObject) parser.parse(new FileReader(playlist_path));
			} catch(Exception e) {
				e.printStackTrace();
			}
			System.out.println("[Connoisseur] Loaded playlist data.");
		}
	}
	
	// 
	@SuppressWarnings("unchecked")
	public void buildPlaylist(String _tag) {
		JSONArray playlist_contents = new JSONArray();
		ArrayList<MediaFile> file_paths = ConnoisseurGUI.getTagManager().searchByTag(_tag);
		// loops through adding each found path to the playlist_contents JSONArray
		for (int i = 0; i < file_paths.size(); i++) {
			playlist_contents.add(file_paths.get(i).getPath());
		}
		// TODO have it 
		playlist_data.put(_tag, playlist_contents);
		savePlaylist(playlist_data);
	}
	
	public void savePlaylist(JSONObject _playlist) {
		try {
			FileWriter file = new FileWriter(playlist_file.getPath());
			file.write(_playlist.toJSONString());
			file.close();
    		System.out.println("[Connoisseur] Saved playlist data file.");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("[Connoisseur] Something went wront while saving playlist data.");
		}
	}
}
