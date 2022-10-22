package Connoisseur;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DeleteDirectory {
	private Path target_dir;
	
	// Constructor(s)
	/** 
	 * The object will take String parameters to delete a target directory
	 * <br>
	 * <p>
	 * This checks if the target directory exists and if the target directory before running
	 * </p>
	 * <p>
	 * Currently prints error message if target directory doesn't
	 * </p>
	 * @param String _target_path - The path of the directory to be deleted
	 * @exception ErrorMessage if _target_path is invalid
	 * @exception ErrorMessage if _target_path is not found
	 * @author Jacob Crawford
	 */
	public DeleteDirectory(String _target_path) {
		this.target_dir = Paths.get(_target_path);
		delete();
	}
	
	// Getter method(s)
	public String getTargetDir() {
		return this.target_dir.toString();
	}
	
	// Setter method(s)
	public void setTargetDir(String _new_target_dir) {
		this.target_dir = Paths.get(_new_target_dir);
	}
	
	private void delete() {
		try {
			if (Files.notExists(target_dir)) {
				System.out.println("ERR: " + target_dir.toString() + " not found, delete aborted");
				return;
			}
			Files.delete(target_dir);
			System.out.println("Successfully deleted " + target_dir.toString());
		} catch (IOException e) {
			System.out.println("ERR: DeleteDirectory failed");
			e.printStackTrace();
		}
	}
	
	// dummy main for testing
	public static void main(String[] args) {
		//DeleteDirectory test = new DeleteDirectory("bin/testfolder1");
		//test = null;
	}
}
