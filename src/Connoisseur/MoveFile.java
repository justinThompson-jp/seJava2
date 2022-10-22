package Connoisseur;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MoveFile {
	private Path curr_start_path, target_end_path;
	private String target_dir;
	
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
	 * @author Jacob Crawford
	 */
	// Idea this could be refactored to take File or Path objects as inputs
	public MoveFile(String _curr_file_path, String _target_dir_path) {
		// new_path variable used to check if folder exists
		this.target_dir = _target_dir_path;
		this.curr_start_path = Paths.get(_curr_file_path);
		this.target_end_path = Paths.get(_target_dir_path + curr_start_path.getFileName());
		move();
	}

	/*
	 *	Getter method(s)
	 *		This should be the only needed getter method
	 *		If/when we create the functionality to create a new folder
	 *		when the target folder doesn't exist this can be used to
	 *		get the new folder's name/path
	 */
	public String getTargetDirectory() {
		return this.target_dir;
	}
	public Path getStartPath() {
		return this.curr_start_path;
	}
	public Path getTargetPath() {
		return this.target_end_path;
	}
	
	/*
	 *	Setter Method(s)
	 *		Should not need to be used, added just in case
	 *		object should only exist long enough
	 *		to move the file or to error out, the private
	 *		variables should not be altered/alterable
	 */
	public void setTargetDirectory(String _target_dir) {
		this.target_dir = _target_dir;
	}
	public void setStartPath(String _curr_dir_path) {
		this.curr_start_path = Paths.get(_curr_dir_path);
	}
	public void setEndPath(String _target_end_path) {
		this.target_end_path = Paths.get(_target_end_path);
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
			// if file to be moved doesn't exist, then print an error message
			if (Files.notExists(curr_start_path)) {
				System.out.println("ERR: Selected file not found, move aborted");
				return;
			}
			// if destination folder doesn't exist, then print an error message(for now)
			if (Files.notExists(Paths.get(target_dir))) {
				System.out.println("ERR: Target directory not found, move aborted");
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
	*/
	public void promptOverwrite() {}

	// dummy main for testing file movement
	public static void main(String[] args) {
		// MoveFile test = new MoveFile("D:\\Users\\jdcra\\Documents\\School\\FALL2022\\CS4800\\seJava2\\bin\\testfolder1\\test.txt","bin/testfolder2/");
		// test = null;
	}
}
