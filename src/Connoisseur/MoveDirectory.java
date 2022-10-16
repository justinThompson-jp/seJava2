package Connoisseur;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
 *	Connoisseur / seJava2
 *	CS4800 Software Engineering
 *	Author: Jacob Crawford
 */
public class MoveDirectory {
	private Path curr_path, target_path;
	private String target_dir;

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
	 * @param String _old_dir - The file's current directory path
	 * @param String _target_dir - The file's target directory path
	 * @author Jacob Crawford
	 */
	private MoveDirectory(String curr_dir, String _target_dir) {
		this.target_dir = _target_dir;
		this.curr_path = Paths.get(curr_dir);
		/*
		 * appends the final directory from _old_path onto
		 * _new_path in order to move the old directory into
		 * the new one
		 */ 
		this.target_path = Paths.get(_target_dir + pluckDirectory(curr_dir));
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
	 *	Moves DIRECTORY from old location into the specified directory
	 * 		Outputs error text if:
	 * 			directory to be moved does not exist
	 * 			destination directory does not exist
	 * 			duplicate directory name exists
	 * 
	 *	Later features/ideas:
	 * 		If destination folder doesn't exist,
	 * 			Then prompt user if they want to create new folder or cancel move
	 *		If duplicate folder name exist in destination folder
	 *			Then prompt user to either
	 *				Overwrite existing directory
	 *				Rename Directory being moved
	 *				Merge Directories, possibly overwriting/renaming duplicate files within
	 *				Cancel move
	 */
	private void move() {
		try {
			// check if directory to be moved exists
			if (Files.exists(curr_path)) {
				// check if destination folder exists
				if (Files.exists(Paths.get(target_dir))) {
					// check for duplicate directory name in destination folder
					if (Files.notExists(target_path)) {
						Files.move(curr_path, target_path);
					// if a duplicate filename exists, then print an error message(for now)
					} else {
						
					}
				// if destination folder doesn't exist, then print an error message(for now)
				} else {
					
				}
			// if directory to be moved doesn't exist, then print an error message
			} else {
				
			}
		} catch (IOException e) {
			System.out.println("ERR: MoveDirectory failed");
			e.printStackTrace();
		}
	}

	/*
	 * Parses through _path String to pluck last directory name Used with
	 * MoveDirectory method
	 */
	private String pluckDirectory(String _dir) {
		String result = "";
		/*
		 * iterates backwards through the string starting just before the last character
		 * upon finding the index of the next instance of '/' creates a substring
		 * starting there and saves it to result
		 */
		for (int index = _dir.length() - 1; index >= 0; index--) {
			if (_dir.charAt(index) == '/') {
				result = _dir.substring(index + 1);
			}
		}

		return result;
	}

	// dummy main for testing directory movement
	public static void main(String[] args) {
		// MoveDirectory test = new MoveDirectory("bin/testfolder1/","bin/testfolder2/");
		// test = null;
	}
}
