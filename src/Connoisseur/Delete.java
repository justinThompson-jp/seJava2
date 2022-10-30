package Connoisseur;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Delete {
	private Path target;
	
	// Constructor(s)
	/** 
	 * The object will take String parameters to delete a target file or directory<br>
	 * <p>
	 * After declaration the object will automatically run the Object.delete() method deleting the target if guard clauses are not flagged.<br>
	 * Currently prints error message if target doesn't exist
	 * </p>
	 * @param String _target_path - The path of the directory to be deleted
	 * @exception ErrorMessage if _target_path is invalid
	 * @exception ErrorMessage if _target_path is not found
	 * @author Jacob Crawford
	 */
	public Delete(String _target_path) {
		this.target = Paths.get(toAbsolute(_target_path));
		delete();
	}
	// empty constructor
	// use if you want to delete multiple directories with a single object
	/** 
	 * The empty constructor for the Delete object.<br>
	 * <p>
	 * This object will not delete anything on initial creation.<br>
	 * The path to the target must be set first with Delete.setTarget(String _new_target) prior to running Delete.move()
	 * </p>
	 * @exception ErrorMessage if _target_path is invalid
	 * @exception ErrorMessage if _target_path is not found
	 * @author Jacob Crawford
	 */
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
		this.target = Paths.get(toAbsolute(_new_target));
	}
	
	/**
	  * Deletes the file specified by the Delete object
	  * <p>
	  * If the path to the target object has not been specified yet,<br>
	  * then it will output an error message to the console
	  * </p>
	  * @exception ErrorMessage if target not set
	  * @exception ErrorMessage if target not found 
	  * 
	  */
	public void delete() {
		if (target == null) {
			System.out.println("ERR: No target set to be deleted");
			return;
		}
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

	/** 
	  * Converts a relative path to an absolute path
	  * <p>
	  * This converts the input String _rel_path first to a File object, then back to a String object named abs_path.<br>
	  * This String abs_path is then returned to the caller.
	  * </p>
	  * 
	  * @param String _rel_path - relative path to specified file or directory
	  * @return String abs_path - absolute path to specified file or directory
	  * 
	  */
	private static String toAbsolute(String _rel_path) {
		String abs_path = new File(_rel_path).getAbsolutePath();
		return abs_path;
	}

	
	// dummy main for testing
	public static void main(String[] args) {
		//Delete test = new Delete("bin/testfolder1");
		//test = null;
	}
}
