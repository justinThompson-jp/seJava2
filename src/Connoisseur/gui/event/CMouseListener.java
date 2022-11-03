package Connoisseur.gui.event;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class CMouseListener implements MouseListener {

	private JTree tree;
	private Object file_clicked;
	private Object file_dragged_to;
	/**
	  * Creates a CMouseListener object that will allow for mouse control in a JTree
	  * <p>
	  * Lorem ipsum dolor...
	  * </p>
	  * 
	  */
	public CMouseListener(JTree _tree) {
		this.tree = _tree;
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
		// simple guard clause to only update file_clicked when a new file is clicked, prevents duplicate calls
		Object new_clicked = tree.getSelectionPath().getLastPathComponent();
		if (getFileClicked() != new_clicked) {
			setFileClicked(new_clicked);
			System.out.println(file_clicked.toString());
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

}
