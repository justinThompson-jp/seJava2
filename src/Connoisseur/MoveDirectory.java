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

public class MoveDirectory {
	private Path curr_dir_path, target_end_path;

	// Constructor(s)
	/**
	 * The object will take String parameters to create directory<br>
	 * paths of the specified directory and the target directory.
	 * <br><br>
	 * This checks if the target directory exists and if the target directory
	 * already has a<br>
	 * file with the same name/extension.
	 * <br><br>
	 * Currently prints error message if target directory doesn't<br>
	 * exist or if duplicate file name is detected
	 * 
	 * @param String _curr_dir_path - The directory's current path
	 * @param String _target_dir_path - The path of the destination directory
	 */
	public MoveDirectory(String _curr_dir_path, String _target_dir_path) {
		this.curr_dir_path = Paths.get(toAbsolute(_curr_dir_path));
		/*
		 * appends the final directory from _old_path onto
		 * _new_path in order to move the old directory into
		 * the new one
		 */ 
		this.target_end_path = Paths.get(toAbsolute(_target_dir_path + "\\" + curr_dir_path.getFileName()));
		move();
	}
	// empty constructor
	// use if you want to make multiple moves with the same object
	/**
	 * Empty constructor for MoveDirectory object.
	 * <p>
	 * This object will not move anything on initial creation.<br>
	 * MoveDirectory.setStartPath(String _new_curr_dir_path) and MoveDirectory.setEndPath(String _new_target_end_path) must be ran before running MoveDirectory.move()<br>
	 * curr_dir_path must be set before target_end_path can be assigned
	 * </p>
	 */
	public MoveDirectory() {
		this.curr_dir_path = null;
		this.target_end_path = null;
		// doesn't automatically run move() after assigning variables
	}

	// Getter method(s)
	public String getCurrPath() {
		return this.curr_dir_path.toString();
	}
	public String getTargetPath() {
		return this.target_end_path.toString();
	}
	
	// Setter method(s)
	public void setCurrPath(String _new_curr_dir_path) {
		this.curr_dir_path = Paths.get(toAbsolute(_new_curr_dir_path));
	}
	public void setTargetPath(String _new_target_end_path) {
		if (this.curr_dir_path == null) {
			System.out.println("ERR: curr_start_path must be assigned before target_end_path");
			return;
		}
		this.target_end_path = Paths.get(toAbsolute(_new_target_end_path + "\\" + curr_dir_path.getFileName()));
	}


	/**
	  * Moves the directory specified by the MoveDirectory object into the destination directory
	  * <p>
	  * Method performs several guard clauses checking for edge cases before attempting to move<br>
	  * If a duplicate directory exists in the destination directory, then this will call the promptMergeDirectory() method
	  * </p>
	  * 
	  * @exception ErrorMessage if target directory is not found
	  * @exception ErrorMessage if destination directory is not found
	  */
	public void move() {
		if (target_end_path == null) {
			if (curr_dir_path == null) {
				System.out.println("ERR: Need to assign curr_dir_path and target_end_path");
				return;
			}
			System.out.println("ERR: Need to assign target_end_path");
			return;
		}
		try {
			System.out.println("Attempt to move directory " + curr_dir_path + " to " + target_end_path);
			// if directory to be moved doesn't exist, then print an error message
			if (Files.notExists(curr_dir_path)) {
				System.out.println("ERR: " + curr_dir_path + "  not found");
				return;
			}
			// if destination folder doesn't exist, then print an error message(for now)
			if (Files.notExists(target_end_path.getParent())) {
				System.out.println("ERR: " + target_end_path.getParent() + "  not found, move aborted");
				return;
			}
			// if a duplicate directory name exists, then print an error message(for now)
			if (Files.exists(target_end_path)) {
				promptMergeDirectory(curr_dir_path);
				return;
			}
			Files.move(curr_dir_path, target_end_path);
			System.out.println("Successfully moved directory " + curr_dir_path + " to " + target_end_path);
			
		} catch (IOException e) {
			System.out.println("ERR: MoveDirectory failed");
			e.printStackTrace();
		}
	}

	/**	
	  * This will send a call to the GUI and depending on the User's response will either merge the directories or cancel the move
	  * <p>
	  * This first calls the GUI layer to ask the User if they want to merge duplicate directories<br>
	  * If yes, then this will create a list of the contents of the current directory,
	  * then begin moving those listed files and directories to the duplicate directory using a MoveFile and MoveDirectory object.<br>
	  * After moving all the contents of the target directory to the duplicated directory, the method will delete the target directory using a Delete object.<br>
	  * If no, then the method will cancel the move and send a message to the console.<br><br>
	  * All exceptions are handled with the MoveFile and MoveDirectory objects.
	  * </p>
	  */
	private void promptMergeDirectory(Path _old_dir) {
		
		// TODO change "true" to a call to GUI layer, for now it's assumed yes
		boolean merge = true;
		
		if (merge) {
			System.out.println("Merge directories " + curr_dir_path + " and " + target_end_path);

			// create a list of all the contents of the directory
			ViewDirectory dir = new ViewDirectory();
			dir.Directory(_old_dir.toString());
			String[] dir_contents = dir.pathnames;
	        
			// iterate through the list, creating Path objects using the target_end_path appended with the current item on the list
	        MoveDirectory temp_dir = new MoveDirectory();
	        MoveFile temp_file = new MoveFile();
			for (int i = 0; i < dir_contents.length; i++) {
				String temp_curr = curr_dir_path.toString() + "\\" + dir_contents[i];
				String temp_dest = target_end_path.toString();
				
				// check if temp_curr points to a directory
				if (Files.isDirectory(Paths.get(temp_curr))) {
					temp_dir.setCurrPath(temp_curr);
					temp_dir.setTargetPath(temp_dest);
					temp_dir.move();
				
				// else temp_curr must be pointing at a file
				} else {
					temp_file.setCurrPath(temp_curr);
					temp_file.setTargetPath(temp_dest);
					temp_file.move();
				}
			}
			// after loop is done moving all contents of curr_start_path to target_end_path, delete curr_start_path directory
			Delete del = new Delete(_old_dir.toString());
			
			// object cleanup
			temp_dir = null;
			temp_file = null;
			del = null;
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

	// dummy main for testing directory movement
	public static void main(String[] args) throws IOException {
		//MoveDirectory test = new MoveDirectory("bin/testfolder1","bin/testfolder2/");
		//test = null;
		
		//Path test = Paths.get("D:/Users/jdcra/Documents/School/FALL2022/CS4800/seJava2/bin/testfolder1");
		//Path test = Paths.get(toAbsolute("bin/testfolder1"));
		//while (test != null) {
		//	System.out.println(test.getFileName());
		//	test = test.getParent();
		//}
	}
}
