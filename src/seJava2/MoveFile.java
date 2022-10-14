package seJava2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
 *	Connoisseur / seJava2
 *	CS4800 Software Engineering
 *
 */
public class MoveFile {
	private Path old_loc, new_loc;
	private String new_path;
	
	/**
	* The object will take String parameters to create relative directory<br>
	* paths of the specified file's current and target directory.
	* <br><br>
	* This checks if the target directory exists and if the target directory already has a<br>
	* file with the same name/extension.
	* <br><br>
	* Currently prints error message if target directory doesn't<br>
	*  exist or if duplicate file name is detected
	* 
	* @param  String _file_name&ensp;-The name of the file to be moved
	* @param  String _old_path&emsp;- The file's current relative directory path
	* @param  String _new_path&ensp;- The file's target relative directory path 
	* @return none
	*/
	private MoveFile(String _file_name, String _old_path, String _new_path) {
		// new_path variable used to check if folder exists
		new_path = _new_path;
		old_loc = Paths.get(_old_path + "/" + _file_name);
		new_loc = Paths.get(new_path + "/" +_file_name);
		Move();
	}
	
	/*	
	 *  Actually moves the file from old location to new location
	 *	handles edge cases and exceptions
	 *		i.e	duplicate file names
	 *			folder does not exist
	 *			etc...
	 *
	 *	Later features:
	 *		If destination folder doesn't exist,
	 *			Then prompt user if they want to create new folder or cancel move
	 *		If duplicate file name exist in destination folder
	 *			Then prompt user to either
	 *				Overwrite existing file
	 *				Rename file being moved
	 *				Cancel move
	*/
	public void Move() {
		try {
			// check if destination folder exists before trying to perform the move
			if(Files.exists(Paths.get(new_path))) {
				// check for duplicate file name before trying to perform move
				if(Files.notExists(new_loc)) {
					Files.move(old_loc, new_loc);
					
				// if a duplicate fileName.extention exists print an error message(for now)
				} else {
					System.out.println("ERR: Duplicate file name");
				}
			// if destination folder doesn't exist, then print an error message(for now)
			} else {
				
				/*
				 * Possible idea for later
				 * display a prompt asking if user wants to create the new folder
				 * 		if yes call the class that creates new directories then move the file
				*/ 
				System.out.println("ERR: Destination folder does not exist");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// dummy main for testing movement
	public static void main(String[] args) {

		//MoveFile test = new MoveFile("test.txt","bin/testfolder1","bin/testfolder2");
		//test = null;
	}
}
