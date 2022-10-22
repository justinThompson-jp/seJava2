package Connoisseur;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MoveDirectory {
	private Path curr_start_path, target_end_path;

	// Constructor(s)
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
	 * @param String _curr_dir_path - The directory's current path
	 * @param String _target_dir_path - The path of the destination directory
	 * @exception ErrorMessage if target directory is not found
	 * @exception ErrorMessage if destination directory is not found 
	 * @author Jacob Crawford
	 */
	public MoveDirectory(String _curr_dir_path, String _target_dir_path) {
		// TODO convert input paths into absolute paths
		this.curr_start_path = Paths.get(_curr_dir_path);
		/*
		 * appends the final directory from _old_path onto
		 * _new_path in order to move the old directory into
		 * the new one
		 */ 
		this.target_end_path = Paths.get(_target_dir_path + "\\" + curr_start_path.getFileName());
		move();
	}
	// empty constructor
	// use if you want to make multiple moves with the same object
	public MoveDirectory() {
		this.curr_start_path = null;
		this.target_end_path = null;
		// doesn't automatically run move() after assigning variables
	}

	/*
	 *	Getter method(s)
	 *		Should not need to be used, added just in case
	 */
	public String getStartPath() {
		return this.curr_start_path.toString();
	}
	public String getTargetPath() {
		return this.target_end_path.toString();
	}
	
	/*
	 *	Setter Method(s)
	 *		Should not need to be used, added just in case
	 */
	public void setStartPath(String _new_curr_dir_path) {
		this.curr_start_path = Paths.get(_new_curr_dir_path);
	}
	public void setEndPath(String _new_target_end_path) {
		if (this.curr_start_path != null) {
			this.target_end_path = Paths.get(_new_target_end_path + "\\" + curr_start_path.getFileName());
		} else {
			System.out.println("ERR: You need to call setStartPath() before calling setEndPath()");
		}
	}

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
	public void move() {
		try {
			System.out.println("Attempt Move " + curr_start_path + " to " + target_end_path);
			// if directory to be moved doesn't exist, then print an error message
			if (Files.notExists(curr_start_path)) {
				System.out.println("ERR: " + curr_start_path + "  not found");
				return;
			}
			// if destination folder doesn't exist, then print an error message(for now)
			if (Files.notExists(target_end_path.getParent())) {
				System.out.println("ERR: " + target_end_path.getParent() + "  not found, move aborted");
				return;
			}
			// if a duplicate directory name exists, then print an error message(for now)
			if (Files.exists(target_end_path)) {
				promptMergeDirectory(curr_start_path);
				return;
			}
			Files.move(curr_start_path, target_end_path);
			System.out.println("Successfully moved " + curr_start_path + " to " + target_end_path);
			
		} catch (IOException e) {
			System.out.println("ERR: MoveDirectory failed");
			e.printStackTrace();
		}
	}

	/*	
	 *	Send request to UI layer to display window asking if User want to merge directories
	 *	if yes, then iterate through contents of curr_start_path and move them to target_end_path
	 *		if another duplicate directory is encountered prompt user again(undecided)
	 *	if no, then cancel move
	*/
	private void promptMergeDirectory(Path _old_dir) {
		
		// TODO change "true" to a call to GUI layer, for now it's assumed yes
		boolean merge = true;
		
		if (merge) {
			/*
			 * Would prefer to do this by calling ViewDirectory class but that doesn't work currently
			 * ideally calling it by doing
			 * ViewDirectory dir = new ViewDirectory(String path);
			 * then String[] dir_contents = dir.listContents();
			 * or something equivalent
			*/
			// create a list of all the contents of the directory
			String[] dir_contents = null;
	        File dir = new File(_old_dir.toString());
	        dir_contents = dir.list();
	        
			// iterate through the list, creating Path objects using the target_end_path appended with the current item on the list
	        MoveDirectory temp_dir = new MoveDirectory();
	        MoveFile temp_file = new MoveFile();
			for (int i = 0; i < dir_contents.length; i++) {
				String temp_curr = curr_start_path.toString() + "\\" + dir_contents[i];
				String temp_dest = target_end_path.toString();
				
				// check if temp_dest points to a directory
				if (Files.isDirectory(Paths.get(temp_dest))) {
					temp_dir.setStartPath(temp_curr);
					temp_dir.setEndPath(temp_dest);
					temp_dir.move();
				
				// else temp_dest must be pointing at a file
				} else {
					temp_file.setStartPath(temp_curr);
					temp_file.setEndPath(temp_dest);
					temp_file.move();
				}
			}
			// after loop is done moving all contents of curr_start_path to target_end_path, delete curr_start_path directory
			DeleteDirectory del = new DeleteDirectory(_old_dir.toString());
			
			// object cleanup
			temp_dir = null;
			temp_file = null;
			del = null;
		} else {
			System.out.println("ERR: Duplicate directory name in target directory, move aborted");
		}
	}

	// dummy main for testing directory movement
	public static void main(String[] args) throws IOException {
		//MoveDirectory test = new MoveDirectory("bin/testfolder1","bin/testfolder2/");
		//test = null;
		
		//Path test = Paths.get("D:/Users/jdcra/Documents/School/FALL2022/CS4800/seJava2/bin/testfolder1");
		//System.out.println(test.getParent().getParent().getParent());
				
	}
}
