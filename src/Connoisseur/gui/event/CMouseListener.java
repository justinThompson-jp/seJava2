/* 
 * Fall 2022
 * CS4800 - Software Engineering
 * Group - Java2
 * 
 * Class initially created by Jacob Crawford, small edit added by Jonathan Vallejo
 */

package Connoisseur.gui.event;

import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

import Connoisseur.ConnoisseurGUI;
import Connoisseur.file.MediaFile;

public class CMouseListener implements MouseListener {

	private JTable source_table;
	private JTree source_tree;
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
	public CMouseListener(Object _source, ConnoisseurGUI _instance) {
		
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
		System.out.println("file clicked " + _file_clicked);
		ConnoisseurGUI.getInstance().setSelectedFile(_file_clicked);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		String new_clicked;
		// functionality for when this is called in a JTree
		if (source_tree != null) {
			// start guard clauses
			// checks if first selection is empty space/not a file or folder
			// only important for the first click after launching
			if (source_tree.getSelectionPath() == null) {
				System.out.println(" ERR: Must click a directory or file");
				return;
			}
			
			// variable that holds whatever node from the JTree was most recently selected
			new_clicked = treePathToString((source_tree.getSelectionPath()));
			
			// checks if the selected object is not readable
			if (!Files.isReadable(Paths.get(new_clicked))) {
				System.out.println(" ERR: Unreadable file");
				return;
			}
			// checks if the selected object is not a directory
			if (!Files.isDirectory(Paths.get(new_clicked))) {
				setFileClicked(new_clicked);
				// double-clicked file from JTree
				if (e.getClickCount() == 2) {
					System.out.println("Launch/Open " + new_clicked + " from JTree");
					try {
						Desktop.getDesktop().open(new File(new_clicked));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				// single-click file from JTree
				} else {
					System.out.println("Focus file from JTree");
				}
				return;
			}
			// end guard clauses
			
			instance.getFolderContents().setViewportView(instance.displayDirContents(new_clicked));
			instance.getFolderContentsLabel().setText(new_clicked);
		}
		// functionality for if this is called from a JTable
		if (source_table != null) {
			// double-click from folder_contents will change to directory in folder_contents JScrollPane or open file in either built in view or separate app
			if (source_table.getValueAt(source_table.getSelectedRow(), source_table.getColumn("Name").getModelIndex()) == null) {
				System.out.println(" ERR: Must click a directory or file");
				return;
			}
			
			// assigned selected row's Name column to the new_clicked variable
			new_clicked = (String) source_table.getValueAt(source_table.getSelectedRow(), source_table.getColumn("Name").getModelIndex());
			
			String temp = new_clicked;
			// formats the new_clicked variable to an absolute path to the file
			new_clicked = instance.getCurrentDir() + "\\" + new_clicked;
			if (temp == "..") {
				new_clicked = new File(new_clicked).getParent();
				new_clicked = new File(new_clicked).getParent();
				
			}
			if (e.getClickCount() == 2) {
				// checks if the selected object is not readable
				if (!Files.isReadable(Paths.get(new_clicked))) {
					System.out.println(" ERR: Unreadable file");
					return;
				}
				// checks if the selected object is not a directory
				// aka a file is double-clicked
				if (!Files.isDirectory(Paths.get(new_clicked))) {
					setFileClicked(new_clicked);
					
					//Code START by Jonathan Vallejo lines 155-187
					// if "Tags" column is double-clicked, it will attempt to edit tags of selected file
					if (source_table.getSelectedColumn() == 2) {
						String userInput = "";
						
						ImageIcon editIcon = new ImageIcon(getClass().getResource("/gui/menubar/icons8-pencil-16.png"));
						
						File selected = new File(new_clicked);
						
						// fetch data within our system for the selected file
						MediaFile mFile = ConnoisseurGUI.getTagManager().findFile(selected);
						
						// there is no data in our data base for the selected file
						if (mFile == null) {
							userInput = (String) JOptionPane.showInputDialog(null, "Editing tags for: " + selected.getName().toString(), "Edit Tags", JOptionPane.QUESTION_MESSAGE, editIcon, null, "exampleTag, anotherTag");
						} else {
							// tag data exists in our data base
							String tags = mFile.getTagsString().replace("[", "").replace("]", "");
							userInput = (String) JOptionPane.showInputDialog(null, "Editing tags for: " + selected.getName().toString(), "Edit Tags", JOptionPane.QUESTION_MESSAGE, editIcon, null, tags);
						}
						
						// user actually entered something
						if (userInput != null && userInput != "" && userInput != " ") {
							String[] tagsEntered = userInput.replace(" ", "").split(",");
							
							ConnoisseurGUI.getTagManager().setTags(selected, tagsEntered);
							// updating MediaFile object
							mFile = ConnoisseurGUI.getTagManager().findFile(selected);
							ConnoisseurGUI.getFileManager().log("Set tags of: " + selected.getName() + " to: " + mFile.getTagsString());
							
							// update gui
							ConnoisseurGUI.getInstance().getFolderContents().setViewportView(ConnoisseurGUI.getInstance().displayDirContents(ConnoisseurGUI.getInstance().getCurrentDir()));
						}
						return;
					}
					//Code END by Jonathan Vallejo lines 153-187
					
					System.out.println("Launch/Open " + new_clicked + " from JTable");
					try {
						Desktop.getDesktop().open(new File(new_clicked));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					return;
				}
				// end guard clauses

				instance.getFolderContents().setViewportView(instance.displayDirContents(new_clicked));
				instance.getFolderContentsLabel().setText(new_clicked);
			}
			setFileClicked(new_clicked);
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
	private String treePathToString(TreePath _tree_path) {
		String path = "";
		Object[] steps = _tree_path.getPath();
		path = steps[0].toString();
		for (int i = 1; i < steps.length; i++) {
			path += "\\" + steps[i].toString();
		} 
		return path;
	}
	
}
