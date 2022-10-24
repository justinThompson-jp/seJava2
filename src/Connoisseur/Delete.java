package Connoisseur;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Delete {
	private Path target;
	
	// Constructor(s)
	/** 
	 * The object will take String parameters to delete a target file or directory
	 * <br>
	 * <p>
	 * This checks if the target exists before running
	 * </p>
	 * <p>
	 * Currently prints error message if target directory doesn't
	 * </p>
	 * @param String _target_path - The path of the directory to be deleted
	 * @exception ErrorMessage if _target_path is invalid
	 * @exception ErrorMessage if _target_path is not found
	 * @author Jacob Crawford
	 */
	public Delete(String _target_path) {
		// TODO convert input paths into absolute paths
		this.target = Paths.get(_target_path);
		delete();
	}
	// empty constructor
	// use if you want to delete multiple directories with a single object
	public Delete() {
		this.target = null;
		// doesn't automatically run delete() after assigning variables
	}
	
	// Getter method(s)
	public String getTarget() {
		return this.target.toString();
	}
	
	// Setter method(s)
	public void setTarget(String _new_target) {
		this.target = Paths.get(_new_target);
	}
	
	private void delete() {
		try {
			if (Files.notExists(target)) {
				System.out.println("ERR: " + target + " not found, delete aborted");
				return;
			}
			Files.delete(target);
			System.out.println("Successfully deleted " + target);
		} catch (IOException e) {
			System.out.println("ERR: Delete failed");
			e.printStackTrace();
		}
	}
	
	// dummy main for testing
	public static void main(String[] args) {
		//Delete test = new Delete("bin/testfolder1");
		//test = null;
	}
}
