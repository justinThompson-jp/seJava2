package Connoisseur;

import java.awt.Component;

/*
 * @author: Jonathan Vallejo
 * Edited by: Justin Thompson
 */

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import Connoisseur.file.MediaFile;
import Connoisseur.gui.CSearchFrame;

public class FileManager {

	private static final String SP = File.separator;
	private static final String PROGRAM_NAME = "Connoisseur";

	private String appDataDir;

	private File systemDataFile;
	private File directoryDataFile;

	private JSONObject systemData;
	private JSONObject directoryData;
	
	private ArrayList<File> lastSearchResults = new ArrayList<File>();
	private double similaritySensitivity = 0.77;
	private int filesScanned = 0;
	
	private ImageIcon tagsIcon = new ImageIcon("resources/gui/menubar/icons8-pencil-16.png");
	private ImageIcon fileIcon = new ImageIcon("resources/gui/view/file.png");
	private ImageIcon folderIcon = new ImageIcon("resources/gui/view/folder.png");

	public FileManager() {
		this.init();
	}

	/**
	 * Initialize the FileManager + data files
	 */
	@SuppressWarnings("unchecked")
	private void init() {
		this.appDataDir = System.getenv("APPDATA") + SP + PROGRAM_NAME;

		// create the system data file
		String systemDataPath = appDataDir + SP + "system-data.json";
		systemDataFile = new File(systemDataPath);

		// system is loading for the first time
		if (!systemDataFile.exists()) {
			this.createFileDirectly(systemDataPath);
			systemData = new JSONObject();
			systemData.put("booted-once", "true");
			systemData.put("default-directory", ""); // Added for user-specified directory - Justin Thompson

			try {
				FileWriter file = new FileWriter(systemDataPath);
				file.write(systemData.toJSONString());
				file.close();

				this.log("Booted for the first time, created system data file.");
			} catch (Exception ex) {
				ex.printStackTrace();
				this.log("Something went wrong while creating system data.");
			}

		} else {
			// load data from system data file and store in JSONObject
			try {

				this.systemData = this.parseJSON(systemDataPath);
				this.log("Loaded system data.");
			} catch (Exception ex) {
				ex.printStackTrace();
				this.log("Something went wrong while loading system data.");
			}
		}

		// create the directory data file
		String directoryDataPath = appDataDir + SP + "directory-data.json";
		directoryDataFile = new File(directoryDataPath);

		// directory data being created for the first time
		if (!directoryDataFile.exists()) {
			this.createFileDirectly(directoryDataPath);
			this.directoryData = new JSONObject();

			FileWriter file;
			try {
				file = new FileWriter(directoryDataPath);
				file.write(directoryData.toJSONString());
				file.close();
			} catch (Exception ex) {
				ex.printStackTrace();
				this.log("Something went wrong while creating directory data.");
			}

			this.log("Created directory data file.");
		} else {
			// load data from directory data file and store in JSONObject
			try {
				this.directoryData = this.parseJSON(directoryDataPath);
				this.log("Loaded directory data.");
			} catch (Exception ex) {
				ex.printStackTrace();
				this.log("Something went wrong while loading directory data.");
			}
		}
	}

	/**
	 * Creates a file with the given name located at the given path
	 * 
	 * @param path     - path where file will be created
	 * @param fileName - name of file to be created
	 * @author Jonathan Vallejo
	 */
	public void createFile(String path, String fileName) {
		// gets the user's home path regardless of the OS (e.g. windows =
		// "C:\Users\<user>")
		String home = System.getProperty("user.home");

		String dir = home + SP + path;
		File f = new File(dir + SP + fileName);

		// if path does not exist, create path
		if (!Files.exists(Paths.get(path))) {
			new File(dir).mkdirs();
		}

		// if file doesn't exist, create file
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// if file exists, maybe throw exception and catch it -> display file already
			// exists warning to user?
		} else {
			System.out.println("File already exists, so not doing anything for now");
		}
	}

	/**
	 * Creates a file with the given name located at the given path, without
	 * figuring out the home directory, used by the CMenuBar
	 * 
	 * @param path     - path where file will be created
	 * @param fileName - name of file to be created
	 * @author Jonathan Vallejo
	 */
	public void createFileDirectly(String path, String fileName) {
		File f = new File(path + SP + fileName);

		// if path does not exist, create path
		if (!Files.exists(Paths.get(path))) {
			new File(path).mkdirs();
		}

		// if file doesn't exist, create file
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// if file exists, maybe throw exception and catch it -> display file already
			// exists warning to user?
		} else {
			System.out.println("File already exists, so not doing anything for now");
		}
	}

	/**
	 * Creates and returns a file with the given name located at the given path,
	 * without figuring out the home directory, used by the CMenuBar
	 * 
	 * @param path     - path where file will be created
	 * @param fileName - name of file to be created
	 * @author Jonathan Vallejo
	 */
	public File newFileDirectly(String path, String fileName) {
		File f = new File(path + SP + fileName);

		// if path does not exist, create path
		if (!Files.exists(Paths.get(path))) {
			new File(path).mkdirs();
		}

		// if file doesn't exist, create file
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// if file exists, maybe throw exception and catch it -> display file already
			// exists warning to user?
		} else {
			System.out.println("File already exists, so not doing anything for now");
		}
		return f;
	}

	/**
	 * Creates a file at the given path, without figuring out the home directory,
	 * used by the CMenuBar
	 * 
	 * @param path     - path where file will be created
	 * @param fileName - name of file to be created
	 * @author Jonathan Vallejo
	 */
	public void createFileDirectly(String path) {
		File f = new File(path);

		// if path does not exist, create path
		if (!Files.exists(Paths.get(path))) {
			new File(f.getParent()).mkdirs();
		}

		// if file doesn't exist, create file
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// if file exists, maybe throw exception and catch it -> display file already
			// exists warning to user?
		} else {
			System.out.println("File already exists, so not doing anything for now");
		}
	}

	/**
	 * Creates and returns a file at the given path, without figuring out the home
	 * directory, used by the CMenuBar
	 * 
	 * @param path     - path where file will be created
	 * @param fileName - name of file to be created
	 * @author Jonathan Vallejo
	 */
	public File newFileDirectly(String path) {
		File f = new File(path);

		// if path does not exist, create path
		if (!Files.exists(Paths.get(path))) {
			new File(f.getParent()).mkdirs();
		}

		// if file doesn't exist, create file
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// if file exists, maybe throw exception and catch it -> display file already
			// exists warning to user?
		} else {
			System.out.println("File already exists, so not doing anything for now");
		}
		return f;
	}

	/**
	 * Creates a file with the given name located at the given path and returns the
	 * file
	 * 
	 * @param path     - path where file will be created
	 * @param fileName - name of file to be created
	 * @author Jonathan Vallejo
	 */
	public File newFile(String path, String fileName) {
		// gets the user's home path regardless of the OS (e.g. windows =
		// "C:\Users\<user>")
		String home = System.getProperty("user.home");

		String dir = home + SP + path;
		File f = new File(dir, fileName);

		// if path does not exist, create path
		if (!Files.exists(Paths.get(path))) {
			new File(dir).mkdirs();
		}

		// if file doesn't exist, create file
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// if file exists, maybe throw exception and catch it -> display file already
			// exists warning to user?
		} else {
			System.out.println("File already exists, so not doing anything for now");
		}
		return f;
	}

	/**
	 * Deletes a directory or file located at the given path
	 * 
	 * @param path - path to file/directory
	 * @author Jonathan Vallejo
	 */
	public void delete(String path) {
		// gets the user's home path regardless of the OS (e.g. windows =
		// "C:\Users\<user>")
		String home = System.getProperty("user.home");
		String dir = home + SP + path;

		File f = new File(dir);

		// there is no file to delete
		if (!f.exists()) {
			// maybe throw an exception: file not found
			return;
		}

		// file is a directory, delete all contents and then delete directory
		if (f.list() != null) {
			for (String s : f.list()) {
				File currentFile = new File(f.getPath(), s);
				currentFile.delete();
			}
			f.delete();
			return;
		}

		if (f.delete()) {
			// file was deleted
			System.out.println("file at " + f.getPath() + " was deleted");
		} else {
			// maybe throw an exception: file was not deleted successfully
			System.out.println("file at " + f.getPath() + " was not deleted");
		}
	}

	/**
	 * Deletes the given directory or file
	 * 
	 * @param file - file to delete
	 * @author Jonathan Vallejo
	 */
	public void delete(File file) {

		// there is no file to delete
		if (!file.exists()) {
			// maybe throw an exception: file not found
			return;
		}

		// file is a directory, delete all contents and then delete directory
		if (file.list() != null) {
			for (String s : file.list()) {
				File currentFile = new File(file.getPath(), s);
				currentFile.delete();
			}
			file.delete();
			return;
		}

		if (file.delete()) {
			// file was deleted
			System.out.println("file at " + file.getPath() + " was deleted");
		} else {
			// maybe throw an exception: file was not deleted successfully
			System.out.println("file at " + file.getPath() + " was not deleted");
		}
	}

	/**
	 * This method will request the user's input and will keep re-asking for input
	 * until the user enters a valid directory
	 * 
	 * @param parentComponent
	 * @param message
	 * @param title
	 * @param msgType
	 * @param icon
	 * @param selectionValues
	 * @param initialSelectionValue
	 * @return String user input
	 */
	public String requestUserInput(Component parentComponent, Object message, String title, int msgType, Icon icon,
			Object[] selectionValues, Object initialSelectionValue) {
		String userInput = (String) JOptionPane.showInputDialog(parentComponent, message, title, msgType, icon, null,
				initialSelectionValue);
		if (userInput == null) {
			return requestUserInput(parentComponent, "[Error: Invalid Directory!] " + message, title, msgType, icon,
					selectionValues, initialSelectionValue);
		}
		File file = new File(userInput);
		if (file == null || !file.exists() || !Files.isDirectory(Paths.get(userInput))) {
			return requestUserInput(parentComponent, "[Error: Invalid Directory!] " + message, title, msgType, icon,
					selectionValues, initialSelectionValue);
		}
		return userInput;
	}

	/**
	 * Saves all the data files
	 */
	public void saveData() {
		this.saveSystemData();
		this.saveDirectoryData();
	}

	/**
	 * Save data to the system-data.json
	 */
	public void saveSystemData() {
		// save system data file
		try {
			FileWriter file = new FileWriter(this.getSystemDataFile().getPath());
			file.write(this.getSystemData().toJSONString());
			file.close();
			this.log("Saved system data file.");
		} catch (Exception ex) {
			ex.printStackTrace();
			this.log("Something went wrong while saving system data.");
		}
	}

	/**
	 * Calculates the similarity (a number within 0 and 1) between two strings.
	 * Code taken from: https://stackoverflow.com/questions/955110/similarity-string-comparison-in-java
	 */
	public double similarity(String s1, String s2) {
		String longer = s1, shorter = s2;
		if (s1.length() < s2.length()) { // longer should always have greater length
			longer = s2;
			shorter = s1;
		}
		int longerLength = longer.length();
		if (longerLength == 0) {
			return 1.0;
			/* both strings are zero length */ }
		/*
		 * // If you have Apache Commons Text, you can use it to calculate the edit
		 * distance: LevenshteinDistance levenshteinDistance = new
		 * LevenshteinDistance(); return (longerLength -
		 * levenshteinDistance.apply(longer, shorter)) / (double) longerLength;
		 */
		return (longerLength - editDistance(longer, shorter)) / (double) longerLength;

	}

	/**
	 * Returns the "edit distance" of two strings, used to calculate string similarity %
	 * Code taken from: https://stackoverflow.com/questions/955110/similarity-string-comparison-in-java
	 * @param s1
	 * @param s2
	 * @return 
	 */
	// Example implementation of the Levenshtein Edit Distance
	// See http://rosettacode.org/wiki/Levenshtein_distance#Java
	public int editDistance(String s1, String s2) {
		s1 = s1.toLowerCase();
		s2 = s2.toLowerCase();

		int[] costs = new int[s2.length() + 1];
		for (int i = 0; i <= s1.length(); i++) {
			int lastValue = i;
			for (int j = 0; j <= s2.length(); j++) {
				if (i == 0)
					costs[j] = j;
				else {
					if (j > 0) {
						int newValue = costs[j - 1];
						if (s1.charAt(i - 1) != s2.charAt(j - 1))
							newValue = Math.min(Math.min(newValue, lastValue), costs[j]) + 1;
						costs[j - 1] = lastValue;
						lastValue = newValue;
					}
				}
			}
			if (i > 0)
				costs[s2.length()] = lastValue;
		}
		return costs[s2.length()];
	}

	/**
	 * Save data to the directory-data.json
	 */
	public void saveDirectoryData() {
		// save directory data file
		try {
			FileWriter file = new FileWriter(this.getDirectoryDataFile().getPath());
			file.write(this.getDirectoryData().toJSONString());
			file.close();
			this.log("Saved directory data file.");
		} catch (Exception ex) {
			ex.printStackTrace();
			this.log("Something went wrong while saving directory data.");
		}
	}

	/**
	 * Logs a message to the console
	 * 
	 * @param message
	 */
	public void log(String message) {
		System.out.println("[" + PROGRAM_NAME + "] " + message);
	}

	/**
	 * Parses a JSONObject from the file at the given path
	 * 
	 * @param path path to JSON file
	 * @return JSONObject
	 */
	public JSONObject parseJSON(String path) {
		JSONParser parser = new JSONParser();
		Object obj = null;
		try {
			obj = parser.parse(new FileReader(path));
		} catch (Exception e) {
			e.printStackTrace();
			this.log("Something went wrong while parsing JSON file at: " + path);
		}
		return (JSONObject) obj;
	}
	
	/**
	 * Searches the files in the directory and returns anything that matches the given search query
	 * @param search frame
	 * @param directory
	 * @param query
	 * @return ArrayList<File> searchResults
	 */
	public ArrayList<File> searchDirectory(CSearchFrame searchFrame, String directory, String query) {
		this.resetSearchResults();
		this.filesScanned = 0;
		ArrayList<File> searchResults = new ArrayList<File>();
		
		// first we will look for files that have tags that match the search query
//		this.log("Searching for tags matching: " + query);
		ArrayList<MediaFile> mediaFileResults = ConnoisseurGUI.getTagManager().searchByTags(query.split(","));
//		this.log("Tag search complete.");
		
		for (MediaFile mf : mediaFileResults) {
			try {
				// if this file isn't in our current dir, skip it
				if (!mf.getJavaFile().getParent().toString().contains(directory)) {
					continue;
				}
				
				// if this file already exists in our search results, skip it. we don't want the same result twice					
				if (searchResults.contains(mf.getJavaFile())) {
					continue;
				}
				searchResults.add(mf.getJavaFile());
				filesScanned++;
				searchFrame.setBarProgress("Files scanned: " + filesScanned);
				searchFrame.addRow(new Object[]{tagsIcon, mf.getName(), mf.getPath()});
				searchFrame.getTable().getModel().getRowCount();
			} catch (Exception ex) {
				continue;
			}
		}
		
		// next we will also search for any files/directories that match the search query by at least 50%
		if (Files.isDirectory(Paths.get(directory))) {
			File currentDirectory = new File(directory);
			for (File f : this.traverseFiles(searchFrame, currentDirectory, query)) {
				searchResults.add(f);
			}
		}
		
		searchFrame.setBarProgress("Scanned " + filesScanned + " files");
		searchFrame.getProgressBar().setIndeterminate(false);
		searchFrame.getProgressBar().setValue(100);
		return searchResults;
	}

	/**
	 * Used to recursively traverse subfolders in a directory
	 * @param search frame
	 * @param start
	 * @param query
	 * @return
	 */
	public ArrayList<File> traverseFiles(CSearchFrame searchFrame, File start, String query) {
		if (start.listFiles() == null) return lastSearchResults;
//		this.log("Scanning: " + start.getName());
		
		if (this.similarity(query, start.getName()) >= similaritySensitivity || start.getName().equalsIgnoreCase(query) || start.getName().toLowerCase().contains(query.toLowerCase())) {
			if (!lastSearchResults.contains(start)) {
				searchFrame.addRow(new Object[] {folderIcon, start.getName(), start.getPath()});
				lastSearchResults.add(start);
			}
		}
		for (File child : start.listFiles()) {
			if (Files.isDirectory(Paths.get(child.getPath()))) {
				this.traverseFiles(searchFrame, child, query);
			}
			
//			ConnoisseurGUI.getFileManager().log("[" + query + " <-> " + child.getName() + "] " + similarity + "% similar");
			if (this.similarity(query, child.getName()) >= similaritySensitivity || child.getName().equalsIgnoreCase(query) || child.getName().toLowerCase().contains(query.toLowerCase())) {
				if (!lastSearchResults.contains(child)) {
					searchFrame.addRow(new Object[] {fileIcon, child.getName(), child.getPath()});
					lastSearchResults.add(child);
				}
			}
			
			if (child.getName().contains(" ") || child.getName().contains("-") || child.getName().contains("_")) {
				for (String s : child.getName().split("[ -_]")) {
					if (this.similarity(query, s) >= similaritySensitivity || s.equalsIgnoreCase(query) || s.toLowerCase().contains(query.toLowerCase())) {
						if (!lastSearchResults.contains(child)) {
							searchFrame.addRow(new Object[] {fileIcon, child.getName(), child.getPath()});
							lastSearchResults.add(child);
						}
					}
				}
			}
			
			filesScanned++;
			searchFrame.setBarProgress("Files scanned: " + filesScanned);
		}
		return lastSearchResults;
	}
	
	/**
	 * Return's the object representation of the system-data.json
	 * 
	 * @return JSONObject
	 */
	public JSONObject getSystemData() {
		return systemData;
	}

	/**
	 * Return's the object representation of the directory-data.json
	 * 
	 * @return
	 */
	public JSONObject getDirectoryData() {
		return directoryData;
	}

	/**
	 * Returns the Java file for the system-data.json
	 * 
	 * @return
	 */
	public File getSystemDataFile() {
		return systemDataFile;
	}

	/**
	 * Return's the Java file for the directory-data.json
	 * 
	 * @return
	 */
	public File getDirectoryDataFile() {
		return directoryDataFile;
	}

	/**
	 * Return's the latest search results
	 * @return
	 */
	public ArrayList<File> getLastSearchResults() {
		return lastSearchResults;
	}
	
	/**
	 * Reset the search results list for a new search
	 */
	public void resetSearchResults() {
		this.lastSearchResults = new ArrayList<File>();
	}
}