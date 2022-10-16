package Connoisseur;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MoveFile {
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
	private MoveFile(String _old_dir, String _target_dir, String _file_name) {
		// new_path variable used to check if folder exists
		this.target_path = _target_dir;
		this.old_dir	= Paths.get(_old_dir + _file_name);
		this.target_dir = Paths.get(_target_dir + _file_name);
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
	 * Moves FILE from old directory to new directory
	 * 		Outputs error text if:
	 * 			duplicate file name exists destination
	 * 			directory does not exist
	 * 
	 * TODO add check if target file exists to begin with
	 *
	 * Later features:
	 * 		If destination folder doesn't exist,
	 * 			Then prompt user if they want to create new folder or cancel move
	 *		If duplicate file name exist in destination folder
	 *			Then prompt user to either Overwrite existing file Rename file
	 *			being moved or Cancel move
	 */
	private void move() {
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
				System.out.println("ERR: Destination folder does not exist");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// dummy main for testing file movement
	public static void main(String[] args) {
		// MoveFile test = new MoveFile("bin/testfolder1/","bin/testfolder2/","test.txt");
		// test = null;
	}
}
