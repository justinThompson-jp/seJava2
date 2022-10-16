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
public class MoveDirectory {
	private Path old_dir, target_dir;
	private String target_path;

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
	private MoveDirectory(String _old_dir, String _target_dir) {
		this.target_path = _target_dir;
		this.old_dir = Paths.get(_old_dir);
		/*
		 * appends the final directory from _old_path onto
		 * _new_path in order to move the old directory into
		 * the new one
		 */ 
		this.target_dir = Paths.get(_target_dir + pluckDirectory(_old_dir));
		move();
	}

	/*
	 * Getter method(s)
	 * this should be the only needed getter method
	 * if we create the functionality to create a new folder when
	 * the target folder doesn't exist this can be used to get the
	 * new folder's name/path
	 */
	public String getNewPath() {
		return this.target_path;
	}
	
	/*
	 * Setter Method(s)
	 * Not needed, object should only exist long enough
	 * to move the file or to error out, the private
	 * variables should not be altered/alterable
	 */

	/*
	 * Moves DIRECTORY from old location into the specified directory Outputs error
	 * text if duplicate directory name exists destination directory does not exist
	 * 
	 * TODO handling of edge cases
	 *		i.e target directory doesn't exist
	 *			duplicate directory names
	 *			destination folder does not exist
	 *			etc...
	 */
	private void move() {
		try {
			Files.move(old_dir, target_dir);
		} catch (IOException e) {
			System.out.println("Move Directory Failed");
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
