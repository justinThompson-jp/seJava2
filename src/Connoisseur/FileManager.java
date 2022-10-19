package Connoisseur;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileManager {

	private static final String SP = File.separator;
	
	public FileManager() {
		// once more stuff is done we will probably have stuff to do in the constructor
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
	 * Delete's a directory or file located at the given path
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
	 * Delete's the given directory or file
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
	
	
//  Example of how to use FileManager class to create and delete files

//	public static void main(String[] args) { 
//		FileManager fileManager = new FileManager();
//		File test = fileManager.newFile("desktop" + File.separator + "test folder", "THISISATEST.txt");
//		System.out.println("file " + test.getName() + " created at " + test.getPath());
//		fileManager.delete(test);
//	}
	
}