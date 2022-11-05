/* 
 * Fall 2022
 * CS4800 - Software Engineering
 * Group - Java2
 * 
 * Class initially created by Jacob Crawford
 */

package Connoisseur.gui.event;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

import Connoisseur.ConnoisseurGUI;

public class CMouseListener implements MouseListener {

	private JTree tree;
	private ConnoisseurGUI instance;
	private String file_clicked;
	private String file_dragged_to;
	
	// Constructor(s)
	/**
	  * Creates a CMouseListener object that will allow navigation of folders through JTree
	  * <p>
	  * Lorem ipsum dolor...
	  * </p>
	  * 
	  */
	public CMouseListener(JTree _tree, ConnoisseurGUI _instance) {
		this.tree = _tree;
		this.instance = _instance;
		this.file_clicked = "";
		this.file_dragged_to = "";
	}
	

	// Getter method(s)
	public Object getFileDraggedTo() {
		return file_dragged_to;
	}
	public Object getFileClicked() {
		return file_clicked;
	}

	// Setter method(s)
	public void setFileDraggedTo(String _file_dragged_to) {
		this.file_dragged_to = _file_dragged_to;
	}
	public void setFileClicked(String _file_clicked) {
		this.file_clicked = _file_clicked;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// simple guard clauses
		// checks if first selection is empty space/not a file or folder
		if (tree.getSelectionPath() == null) {
			return;
		}
		
		String new_clicked = treePathToString(tree.getSelectionPath());
		// checks if the selected object is even readable
		if (!Files.isReadable(Paths.get(new_clicked))) {
			return;
		}
		// checks if the selected object is even a directory
		if (!Files.isDirectory(Paths.get(new_clicked))) {
			return;
		}
		// checks if the newly select object isn't the most recently prior selected object
		if (getFileClicked().equals(new_clicked)) {
			return;
		}
		setFileClicked(new_clicked);
		
		/*	tested to see if it was selecting the correct folder and if could differentiate between files and directories
		if (Files.isDirectory(Paths.get(new_clicked))) {
			System.out.print("Directory: ");
		} else {
			System.out.print("File: ");
		}
		System.out.println(new_clicked);
		 */
		
		instance.getFolderContents().setViewportView(instance.displayDirContents(new_clicked));
	}

	// these can be later used for click-and-dragged file/folder movement
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}

	// possibly used to highlight which node the mouse is over
	// possibly ignored as useless
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}

	/**
	  * Converts an input TreePath object into a String
	  * <p>
	  * Lorem ipsum dolor...
	  * </p>
	  * 
	  * @param TreePath _tree_path - The TreePath object to be converted
	  * @return String path - The absolute path from the drive to the end of the path formatted as a String
	  */
	public String treePathToString(TreePath _tree_path) {
		String path = "";
		Object[] steps = _tree_path.getPath();
		for (int i = 0; i < steps.length; i++) {
			path += steps[i].toString() + "\\";
		} 
		return path;
	}
}
