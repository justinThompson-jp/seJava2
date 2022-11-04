package Connoisseur.gui.event;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JScrollPane;
import javax.swing.JTree;

import Connoisseur.ConnoisseurGUI;

public class CMouseListener implements MouseListener {

	private JTree tree;
	private ConnoisseurGUI window;
	private Object file_clicked;
	private Object file_dragged_to;
	
	// Constructor(s)
	/**
	  * Creates a CMouseListener object that will allow navigation of folders through JTree
	  * <p>
	  * Lorem ipsum dolor...
	  * </p>
	  * 
	  */
	public CMouseListener(JTree _tree, ConnoisseurGUI _window) {
		this.tree = _tree;
		this.window = _window;
	}
	

	// Getter method(s)
	public Object getFileDraggedTo() {
		return file_dragged_to;
	}
	public Object getFileClicked() {
		return file_clicked;
	}

	// Setter method(s)
	public void setFileDraggedTo(Object _file_dragged_to) {
		this.file_dragged_to = _file_dragged_to;
	}
	public void setFileClicked(Object _file_clicked) {
		this.file_clicked = _file_clicked;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// simple guard clauses
		if (tree.getSelectionPath() == null) {
			return;
		}
		Object new_clicked = tree.getSelectionPath().getLastPathComponent();
		if (getFileClicked() == new_clicked) {
			return;
		}
		setFileClicked(new_clicked);
		
		// TODO for some reason this thinks everything but user.home is not a directory
		if (Files.isDirectory(Paths.get(new_clicked.toString()))) {
			System.out.print("Directory: ");
		} else {
			System.out.print("File: ");
		}
		System.out.println(toAbsolute(file_clicked.toString()));
		
		// change the displayed directory in the folder_contents subwindow to reflect the chosen directory
		// TODO get the selected folder to display in the main GUI
		//JScrollPane temp = window.getFolderContents();
		//temp.setViewportView(window.displayDirContents(new_clicked.toString()));
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

	
	private static String toAbsolute(String _rel_path) {
		String abs_path = new File(_rel_path).getAbsolutePath();
		return abs_path;
	}
}
