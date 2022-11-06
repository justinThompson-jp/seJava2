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

import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

import Connoisseur.ConnoisseurGUI;

public class CMouseListener implements MouseListener {

	private JTable source_table;
	private JTree source_tree;
	private ConnoisseurGUI instance;
	private String file_clicked;
	private String file_dragged_to;
	
	// tester var
	private int id;
	
	// Constructor(s)
	/**
	  * Creates a CMouseListener object that will allow navigation of folders through JTree
	  * <p>
	  * Lorem ipsum dolor...
	  * </p>
	  * 
	  */
	public CMouseListener(Object _source, ConnoisseurGUI _instance, int _id) {
		
		/*
		 * This check will typcast the _source input into the appropriate class and set all other options to null
		 */
		if (_source instanceof JTree) {
			source_tree = (JTree) _source;
			source_table = null;
		} else if (_source instanceof JTable) {
			source_table = (JTable) _source;
			source_tree = null;
		}
		this.instance = _instance;
		this.file_clicked = "";
		this.file_dragged_to = "";
		this.id = _id;
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
		// TODO add alternative mouseClicked functionality for JTree and JScrollPane
		// functionality for when this is called in a JTree
		if (source_tree != null) {
			// start guard clauses
			// checks if first selection is empty space/not a file or folder
			// only important for the first click after launching
			if (source_tree.getSelectionPath() == null) {
				System.out.println(id + " ERR: Must click a directory or file");
				return;
			}
			
			// variable that holds whatever node from the JTree was most recently selected
			String new_clicked = treePathToString((source_tree.getSelectionPath()));
			
			// checks if the selected object is not readable
			if (!Files.isReadable(Paths.get(new_clicked))) {
				System.out.println(id + " ERR: Unreadable file");
				return;
			}
			// checks if the selected object is not a directory
			if (!Files.isDirectory(Paths.get(new_clicked))) {
				System.out.println(id + " ERR: Must click a directory");
				return;
			}
			// checks if the newly select object is different from the most recently selected node
			if (getFileClicked().equals(new_clicked)) {
				System.out.println(id + " ERR: Same directory as previously selected");
				return;
			}
			// end guard clauses
			
			setFileClicked(new_clicked);

			System.out.println(id + " Open directory " + new_clicked);
			//instance.getDirContents().removeMouseListener(instance.getFolderContents().getMouseListeners()[0]);
			//instance.id--;
			instance.getFolderContents().setViewportView(instance.displayDirContents(new_clicked));
		}
		// functionality for if this is called from a JTable
		if (source_table != null) {
			// double-click from folder_contents will change to directory in folder_contents JScrollPane or open file in either built in view or separate app
			if (e.getClickCount() >= 2) {
				System.out.println(id + " JTable: Change directory or open file");
			// single click will bring focus on target directory or file and display info in file_metadata JPane
			} else {
				System.out.println(id + " JTable: Focus on file/directory");
			}
		}
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
