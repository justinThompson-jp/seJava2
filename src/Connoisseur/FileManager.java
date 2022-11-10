package Connoisseur;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class FileManager {

	private static final String SP = File.separator;
	private static final String PROGRAM_NAME = "Connoisseur";
	
	private String appDataDir;
	
	private File systemDataFile;
	private File directoryDataFile;
	
	private JSONObject systemData;
	private JSONObject directoryData;
	
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
			directoryData.put("directories", "");
			
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
	 * @param path - path where file will be created
	 * @param fileName - name of file to be created
	 * @author Jonathan Vallejo
	 */
	public void createFile(String path, String fileName) {
		// gets the user's home path regardless of the OS (e.g. windows = "C:\Users\<user>")
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
		// if file exists, maybe throw exception and catch it -> display file already exists warning to user?
		} else {
			System.out.println("File already exists, so not doing anything for now");
		}
	}
	
	/**
	 * Creates a file with the given name located at the given path, without figuring out the home directory, used by the CMenuBar
	 * @param path - path where file will be created
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
		// if file exists, maybe throw exception and catch it -> display file already exists warning to user?
		} else {
			System.out.println("File already exists, so not doing anything for now");
		}
	}
	
	/**
	 * Creates and returns a file with the given name located at the given path, without figuring out the home directory, used by the CMenuBar
	 * @param path - path where file will be created
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
		// if file exists, maybe throw exception and catch it -> display file already exists warning to user?
		} else {
			System.out.println("File already exists, so not doing anything for now");
		}
		return f;
	}
	
	/**
	 * Creates a file at the given path, without figuring out the home directory, used by the CMenuBar
	 * @param path - path where file will be created
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
		// if file exists, maybe throw exception and catch it -> display file already exists warning to user?
		} else {
			System.out.println("File already exists, so not doing anything for now");
		}
	}
	
	/**
	 * Creates and returns a file at the given path, without figuring out the home directory, used by the CMenuBar
	 * @param path - path where file will be created
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
		// if file exists, maybe throw exception and catch it -> display file already exists warning to user?
		} else {
			System.out.println("File already exists, so not doing anything for now");
		}
		return f;
	}
	
	/**
	 * Creates a file with the given name located at the given path and returns the file
	 * @param path - path where file will be created
	 * @param fileName - name of file to be created
	 * @author Jonathan Vallejo
	 */
	public File newFile(String path, String fileName) {
		// gets the user's home path regardless of the OS (e.g. windows = "C:\Users\<user>")
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
		// if file exists, maybe throw exception and catch it -> display file already exists warning to user?
		} else {
			System.out.println("File already exists, so not doing anything for now");
		}
		return f;
	}
	
	/**
	 * Deletes a directory or file located at the given path
	 * @param path - path to file/directory
	 * @author Jonathan Vallejo
	 */
	public void delete(String path) {
		// gets the user's home path regardless of the OS (e.g. windows = "C:\Users\<user>")
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
			for(String s: f.list()){
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
			for(String s: file.list()){
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
	 * Logs a message to the console
	 * @param message
	 */
	public void log(String message) {
		System.out.println("[" + PROGRAM_NAME + "] " + message);
	}

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
	
	public JSONObject getSystemData() {
		return systemData;
	}
	
	public JSONObject getDirectoryData() {
		return directoryData;
	}
	
	public File getSystemDataFile() {
		return systemDataFile;
	}
	
	public File getDirectoryDataFile() {
		return directoryDataFile;
	}
	
}