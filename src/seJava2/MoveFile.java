package seJava2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
 *	Connoisseur / seJava2
 *	CS4800 Software Engineering
 *
 *	currently can move files
 *
 *	need it to check for edge cases when moving the file
 */
public class MoveFile {
	private Path old_loc, new_loc;
	
	// object constructor
	public MoveFile(String _file_name, String _old_path, String _new_path) {
		old_loc = Paths.get(_old_path + "/" + _file_name);
		new_loc = Paths.get(_new_path + "/" +_file_name);
		Move();
	}
	
	/*	actually move the file from old location to new location
	 *	handles edge cases and exceptions
	 *		i.e	duplicate file names
	 *			folder does not exist
	 *			etc...
	*/
	public void Move() {
		try {
			Files.move(old_loc, new_loc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// dummy main for testing movement
	public static void main(String[] args) {

		MoveFile test = new MoveFile("test.txt","test/testfolder1","test/testfolder2");
		test = null;
	}
}
