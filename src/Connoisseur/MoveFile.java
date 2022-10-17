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
	// TODO this could be refactored to take File objects as inputs
	private MoveFile(String _curr_file_path, String _target_dir_path) {
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

	/*
	 *	Setter Method(s)
	 *		Not needed, object should only exist long enough
	 *		to move the file or to error out, the private
	 *		variables should not be altered/alterable
	 */

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
			// check if file to be moved exists
			if (Files.exists(curr_start_path)) {
				// check if destination folder exists
				if (Files.exists(Paths.get(target_dir))) {
					// check for duplicate file name in destination folder
					if (Files.notExists(target_end_path)) {
						Files.move(curr_start_path, target_end_path);
						// if a duplicate fileName exists, then print an error message(for now)
					} else {
						System.out.println("ERR: Duplicate file name in target directory, move aborted");
					}
					// if destination folder doesn't exist, then print an error message(for now)
				} else {
					System.out.println("ERR: Target directory not found, move aborted");
				}
			// if file to be moved doesn't exist, then print an error message
			} else {
				System.out.println("ERR: Selected file not found, move aborted");
			}
		} catch (IOException e) {
			System.out.println("ERR: MoveFile failed");
			e.printStackTrace();
		}
	}

	// dummy main for testing file movement
	public static void main(String[] args) {
		//MoveFile test = new MoveFile("bin/testfolder1/test.txt","bin/testfolder2/");
		//test = null;
	}
}
