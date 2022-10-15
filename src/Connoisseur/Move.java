package Connoisseur;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
 *	Connoisseur / seJava2
 *	CS4800 Software Engineering
 *
 */
public class Move {
	private Path old_dir, target_dir;
	private String target_path;

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
	 * @param String _file_name - The name of the file to be moved
	 * @param String _old_dir - The file's current directory path
	 * @param String _target_dir - The file's target directory path
	 * @return none
	 */
	// move file
	private Move(String _old_dir, String _target_dir, String _file_name) {
		// new_path variable used to check if folder exists
		this.target_path = _target_dir;
		this.old_dir = Paths.get(_old_dir + _file_name);
		this.target_dir = Paths.get(target_path + _file_name);
		moveFile();
	}

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
	 * @return none
	 */
	// move directory
	private Move(String _old_dir, String _target_dir) {
		this.old_dir = Paths.get(_old_dir);

		// appends the final directory from _old_path onto _new_path in order to move
		// the
		// old directory into the new one
		this.target_dir = Paths.get(_target_dir + pluckDirectory(_old_dir));

		moveDirectory();
	}

	/*
	 * Getter method(s) this should be the only needed getter method if we create
	 * the function to create a new folder when the target folder doesn't exist this
	 * can be used to get the new folder's name/path
	 */
	public String getNewPath() {
		return this.target_path;
	}

	/*
	 * Moves FILE from old directory to new directory Outputs error text if
	 * duplicate file name exists destination directory does not exist
	 * 
	 * TODO add check if target file exists to begin with
	 *
	 * Later features: If destination folder doesn't exist, Then prompt user if they
	 * want to create new folder or cancel move If duplicate file name exist in
	 * destination folder Then prompt user to either Overwrite existing file Rename
	 * file being moved Cancel move
	 */
	private void moveFile() {
		try {
			// check if destination folder exists before trying to perform the move
			if (Files.exists(Paths.get(target_path))) {
				// check for duplicate file name before trying to perform move
				if (Files.notExists(target_dir)) {
					Files.move(old_dir, target_dir);

					// if a duplicate fileName.extention exists print an error message(for now)
				} else {
					System.out.println("ERR: Duplicate file name");
				}
				// if destination folder doesn't exist, then print an error message(for now)
			} else {

				/*
				 * Possible idea for later display a prompt asking if user wants to create the
				 * new folder if yes call the class that creates new directories then move the
				 * file
				 */
				System.out.println("ERR: Destination folder does not exist");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Moves DIRECTORY from old location into the specified directory Outputs error
	 * text if duplicate directory name exists destination directory does not exist
	 * 
	 * TODO handling of edge cases i.e target directory doesn't exist duplicate
	 * directory names destination folder does not exist etc...
	 */
	private void moveDirectory() {
		try {
			Files.move(old_dir, target_dir);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Parses through _path String to pluck last directory name Used with
	 * MoveDirectory method
	 */
	private String pluckDirectory(String _dir) {
		String result = "";
		// iterates backwards through the string
		// upon finding the index of the next instance of '/' creates a substring
		// starting there and saves it to result
		for (int index = _dir.length() - 1; index >= 0; index--) {
			if (_dir.charAt(index) == '/') {
				result = _dir.substring(index + 1);
			}
		}

		return result;
	}

	// dummy main for testing movement
	public static void main(String[] args) {
		// Move test = new Move("bin/testfolder1/","bin/testfolder2/");
		// Move test = new Move("bin/testfolder1/","bin/testfolder2/","test.txt");
		// test = null;
	}
}
