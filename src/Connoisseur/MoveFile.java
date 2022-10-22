package Connoisseur;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MoveFile {
	private Path curr_start_path, target_end_path;
	
	// Constructor(s)
	/**
	 * The object will take String parameters to create directory<br>
	 * paths of the specified file's current and the target directory.
	 * <br><br>
	 * This checks if the target directory exists and if the target directory
	 * already has a<br>
	 * file with the same name/extension.
	 * <br> <br>
	 * Currently prints error message if target directory doesn't<br>
	 * exist or if duplicate file name is detected
	 * 
	 * @param String _curr_file_path - The file's current directory path, including the name.ext
	 * @param String _target_dir_path - The path of the destination directory
	 * @exception ErrorMessage if target file is not found
	 * @exception ErrorMessage if destination directory is not found
	 * @exception ErrorMessage if duplicate file is found in destination directory<br>Later to be changed to prompt to GUI
	 * @author Jacob Crawford
	 */
	public MoveFile(String _curr_file_path, String _target_dir_path) {
		// TODO convert input paths into absolute paths
		this.curr_start_path = Paths.get(_curr_file_path);
		this.target_end_path = Paths.get(_target_dir_path + curr_start_path.getFileName());
		move();
	}
	// empty constructor
	// use if you want to make multiple moves with the same object
	public MoveFile() {
		this.curr_start_path = null;
		this.target_end_path = null;
		// doesn't automatically run move() after assigning variables
	}
	/*
	 *	Getter method(s)
	 *		Should not need to be used, added just in case
	 */
	public String getStartPath() {
		return this.curr_start_path.toString();
	}
	public String getTargetPath() {
		return this.target_end_path.toString();
	}
	
	/*
	 *	Setter Method(s)
	 *		Should not need to be used, added just in case
	 */
	public void setStartPath(String _new_curr_dir_path) {
		this.curr_start_path = Paths.get(_new_curr_dir_path);
	}
	public void setEndPath(String _new_target_end_path) {
		this.target_end_path = Paths.get(_new_target_end_path);
	}

	/*
	 *	Moves FILE from old directory to new directory
	 * 		Outputs error text if:
	 * 			file to be moved does not exist
	 * 			destination directory does not exist
	 * 			duplicate file name exists in destination folder
	 * 
	 *	Later features/ideas:
	 * 		If destination folder doesn't exist,
	 * 			Then prompt user if they want to create new folder or cancel move
	 *		If duplicate file name exist in destination folder
	 *			Then prompt user to either
	 *				Overwrite existing file
	 *				Rename file being moved
	 *				Cancel move
	 */
	private void move() {
		try {

			System.out.println("Attempt Move " + curr_start_path + " to " + target_end_path);
			// if file to be moved doesn't exist, then print an error message
			if (Files.notExists(curr_start_path)) {
				System.out.println("ERR: " + curr_start_path + " not found, move aborted");
				return;
			}
			// if destination folder doesn't exist, then print an error message(for now)
			if (Files.notExists(target_end_path.getParent())) {
				System.out.println("ERR: " + target_end_path.getParent() + " not found, move aborted");
				return;
			}
			// if a duplicate fileName exists in directory, then print an error message(for now)
			if (Files.exists(target_end_path)) {
				//promptOverwrite();
				System.out.println("ERR: Duplicate file name in target directory, move aborted");
				return;
			}
			Files.move(curr_start_path, target_end_path);
			System.out.println("Successfully moved " + curr_start_path + " to " + target_end_path);
			
		} catch (IOException e) {
			System.out.println("ERR: MoveFile failed");
			e.printStackTrace();
		}
	}

	/*	
	 *	Send request to UI layer to display window asking if User want to overwrite duplicate file in target directory
	 *	if yes, then Files.move(curr_start_path, target_end_path, REPLACE_EXISTING);
	 *	if no, then cancel move
	 *	TODO make it send message to GUI/UI with a popup, enclose working code in if-else or guard clause
	 *	TODO write this method
	*/
	public void promptOverwrite() {}

	// dummy main for testing file movement
	public static void main(String[] args) {
		// MoveFile test = new MoveFile("D:\\Users\\jdcra\\Documents\\School\\FALL2022\\CS4800\\seJava2\\bin\\testfolder1\\test.txt","bin/testfolder2/");
		// test = null;
	}
}
