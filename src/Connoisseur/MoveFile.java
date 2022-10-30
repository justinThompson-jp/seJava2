/* 
 * Fall 2022
 * CS4800 - Software Engineering
 * Group - Java2
 * 
 * Class initially created by Jacob Crawford
 */

package Connoisseur;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class MoveFile {
	private Path curr_file_path, target_end_path;
	
	// Constructor(s)
	/**
	 * This will create a MoveFile object with input curr_file_path and target_end_path, then it will automatically run MoveFile.move()
	 * <p>
	 * The object will take String parameters to create directory paths of the specified file's current and the target directory.<br>
	 * These string inputs will be first run through the MoveFile.toAbsolute() method to ensure that they are absolute paths
	 * </p>
	 * 
	 * @param String _curr_file_path - The file's current path, including the name.ext
	 * @param String _target_dir_path - The path of the destination directory
	 */
	public MoveFile(String _curr_file_path, String _target_dir_path) {
		this.curr_file_path = Paths.get(toAbsolute(_curr_file_path));
		this.target_end_path = Paths.get(toAbsolute(_target_dir_path + curr_file_path.getFileName()));
		move();
	}
	// empty constructor
	// use if you want to make multiple moves with the same object
	/**
	 * Empty constructor for MoveFile object.
	 * <p>
	 * This object will not move anything on initial creation.<br>
	 * MoveFile.setStartPath(String _new_curr_file_path) and MoveFile.setEndPath(String _new_target_end_path) must be ran before running MoveFile.move()<br>
	 * curr_start_path must be set before target_end_path can be assigned
	 * </p>
	 */
	public MoveFile() {
		this.curr_file_path = null;
		this.target_end_path = null;
		// doesn't automatically run move() after assigning variables
	}

	// Getter method(s) 
	String getCurrPath() {
		return this.curr_file_path.toString();
	}
	public String getTargetPath() {
		return this.target_end_path.toString();
	}
	
	// Setter method(s)
	public void setCurrPath(String _new_curr_file_path) {
		this.curr_file_path = Paths.get(toAbsolute(_new_curr_file_path));
	}
	public void setTargetPath(String _new_target_end_path) {
		if (curr_file_path == null) {
			System.out.println("ERR: curr_file_path must be assigned before assigning target_end_path");
			return;
		}
		this.target_end_path = Paths.get(toAbsolute(_new_target_end_path + "\\" +curr_file_path.getFileName()));
	}

	/**
	  * Moves the file specified by the MoveFile object into the destination directory
	  * <p>
	  * Method performs several guard clauses checking for edge cases before attempting to move<br>
	  * If a duplicate file exists in the destination directory, then this will call the promptOverwriteFile() method
	  * </p>
	  * 
	  * @exception ErrorMessage if target file is not found
	  * @exception ErrorMessage if destination directory is not found
	  */
	public void move() {
		if (target_end_path == null) {
			if (curr_file_path == null) {
				System.out.println("ERR: Need to assign curr_file_path and target_end_path");
				return;
			}
			System.out.println("ERR: Need to assign target_end_path");
			return;
		}
		try {

			System.out.println("Attempt to move file " + curr_file_path + " to " + target_end_path);
			// if file to be moved doesn't exist, then print an error message
			if (Files.notExists(curr_file_path)) {
				System.out.println("ERR: " + curr_file_path + " not found, move aborted");
				return;
			}
			// if destination folder doesn't exist, then print an error message(for now)
			if (Files.notExists(target_end_path.getParent())) {
				System.out.println("ERR: " + target_end_path.getParent() + " not found, move aborted");
				return;
			}
			// if a duplicate fileName exists in directory, then print an error message(for now)
			if (Files.exists(target_end_path)) {
				promptOverwriteFile();
				return;
			}
			Files.move(curr_file_path, target_end_path);
			System.out.println("Successfully moved file " + curr_file_path + " to " + target_end_path);
			
		} catch (IOException e) {
			System.out.println("ERR: MoveFile failed");
			e.printStackTrace();
		}
	}

	/*
	 *	Send request to UI layer to display window asking if User want to overwrite duplicate file in target directory
	 *	if yes, then Files.move(curr_file_path, target_end_path, REPLACE_EXISTING);
	 *	if no, then cancel move
	*/
	/**	
	  * This will send a call to the GUI and depending on the User's response will either overwrite a duplicate or cancel the move
	  * <p>
	  * This first calls the GUI layer to ask the User if they want to overwrite duplicate files<br>
	  * If yes, then this will call the Files.move() method with the REPLACE_EXISTING StandardCopyOption
	  * If no, then the method will cancel the move and send a message to the console.
	  * </p>
	  */
	private void promptOverwriteFile() {
		// TODO change "true" to a call to GUI, for now lets assume yes overwrite
		boolean overwrite = true;
		
		if (overwrite) {
			try {
				Files.move(curr_file_path, target_end_path, REPLACE_EXISTING);
			} catch (IOException e) {
				System.out.println("ERR: Overwrite failed");
				e.printStackTrace();
			}
		} else {
			System.out.println("Move canceled");
		}
	}

	/** 
	  * Converts a relative path to an absolute path
	  * <p>
	  * This converts the input String _rel_path first to a File object, then back to a String object named abs_path.<br>
	  * This String abs_path is then returned to the caller.
	  * </p>
	  * 
	  * @param String _rel_path - relative path to specified file or directory
	  * @return String abs_path - absolute path to specified file or directory
	  */
	private static String toAbsolute(String _rel_path) {
		String abs_path = new File(_rel_path).getAbsolutePath();
		return abs_path;
	}

	// dummy main for testing file movement
	public static void main(String[] args) {
		// MoveFile test = new MoveFile("bin/testfolder1/test.txt","bin/testfolder2/");
		// test = null;
		
		//String test = "bin/test/testfolder1/test.txt";
		//File testfile = new File(test);
		//System.out.println(testfile.getPath());
		//System.out.println(testfile.getAbsolutePath());
		
	}
}
