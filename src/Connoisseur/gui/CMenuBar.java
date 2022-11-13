package Connoisseur.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.json.simple.JSONObject;

import Connoisseur.ConnoisseurGUI;
import Connoisseur.file.MediaFile;

/***
 * Extension of the JMenuBar class to include the functionality of the Connoisseur MenuBar. <br>
 * Image files from: <a target="_blank" href="https://icons8.com">Icons8</a>
 * @author Jonathan Vallejo
 * 
 */
public class CMenuBar extends JMenuBar implements ActionListener {
	
	/**
	 * Auto generated UID
	 */
	private static final long serialVersionUID = -350515317725327497L;
	
	private JMenu fileMenu, editMenu, helpMenu;
	private JMenu newFileSubMenu;
	
	private JMenuItem newFileMenuItem, newDirectoryMenuItem, setDefaultDirMenuItem;
	private JMenuItem editTagsMenuItem;
	

	public CMenuBar() {
		this.addContent();
	}
	
	private void addContent() {
		this.initFileMenu();
		this.initEditMenu();
		this.initHelpMenu();
	}
	
	private void initFileMenu() {
		fileMenu = new JMenu("File");
		
		// set keyboard shortcut to Alt + F to open File menu
		fileMenu.setMnemonic(KeyEvent.VK_F); 
		
		// create "New" sub-menu
		newFileSubMenu = new JMenu("New");
		
		// initialize the "New" sub-menu items
		newFileMenuItem = new JMenuItem("New File");
		newFileMenuItem.setIcon(new ImageIcon("resources/gui/menubar/icons8-add-file-16.png"));
		newFileMenuItem.addActionListener(this);
		
		newDirectoryMenuItem = new JMenuItem("New Directory");
		newDirectoryMenuItem.setIcon(new ImageIcon("resources/gui/menubar/icons8-add-folder-16.png"));
		newDirectoryMenuItem.addActionListener(this);
		
		setDefaultDirMenuItem = new JMenuItem("Set Default Directory");
//		setDefaultDirMenuItem.setIcon(new ImageIcon("resources/gui/menubar/icons8-folder-16.png"));
		setDefaultDirMenuItem.addActionListener(this);
		
		// add sub-menu items to "New" menu
		newFileSubMenu.add(newFileMenuItem);
		newFileSubMenu.addSeparator();
		newFileSubMenu.add(newDirectoryMenuItem);
		
		// add sub-menu to "New" menu
		fileMenu.add(newFileSubMenu);
		fileMenu.addSeparator();
		fileMenu.add(setDefaultDirMenuItem);
		
		this.add(fileMenu);
	}
	
	private void initEditMenu() {
		// initialize the "Edit" menu
		this.editMenu = new JMenu("Edit");
		
		// create "Edit Tags" option within the "Edit" menu
		editTagsMenuItem = new JMenuItem("Edit Tags");
		editTagsMenuItem.setIcon(new ImageIcon("resources/gui/menubar/icons8-pencil-16.png"));
		editTagsMenuItem.addActionListener(this);
		
		// add "edit tags" to "Edit" menu
		editMenu.add(editTagsMenuItem);
		this.add(editMenu);
	}
	
	private void initHelpMenu() {
		this.helpMenu = new JMenu("Help");
		// TODO: add help options
		this.add(helpMenu);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent e) {
		// handle clicks within the "New" sub-menu
		
		// figure out if user currently has a folder selected
		JTree files = ConnoisseurGUI.getInstance().getJTree();
		TreePath[] pathsSelected = files.getSelectionPaths();
		
		if (e.getSource() == newFileMenuItem) {
			ImageIcon icon = new ImageIcon("resources/gui/menubar/icons8-add-file-16.png");
			
			String defaultPath = ConnoisseurGUI.getInstance().getDefaultDir() + File.separator;
			String targetPath = "";
			String userInput = "";
			
			FileCreationType creationType = null;
			// user has nothing selected
			if (pathsSelected == null || pathsSelected.length == 0) {
				// show create file dialogue at default directory
				targetPath = defaultPath;
				userInput = (String) JOptionPane.showInputDialog(null, "Creating new file at: ", "New File", JOptionPane.QUESTION_MESSAGE, icon, null, defaultPath);
				creationType = FileCreationType.DIRECT;
			} else if (pathsSelected.length > 1) {
				// user has more than one thing selected
				
				// show create file dialogue at default directory
				targetPath = defaultPath;
				userInput = (String) JOptionPane.showInputDialog(null, "Creating new file at: ", "New File", JOptionPane.QUESTION_MESSAGE, icon, null, defaultPath);	
				creationType = FileCreationType.DIRECT;
			} else {
				// user only has one thing selected
				targetPath = treePathToString(pathsSelected[0].toString());
				File fileSelected = new File(targetPath);
				
				// file selected is a directory, proceed with prompt
				if (fileSelected.list() != null) {
					targetPath = treePathToString(pathsSelected[0].toString());
					userInput = (String) JOptionPane.showInputDialog(null, "Creating new file at: " + targetPath, "New File", JOptionPane.QUESTION_MESSAGE, icon, null, null);		
				} else {
					// file selected is not a directory, show create file dialogue at parent directory
					targetPath = defaultPath;
					if (pathsSelected[0].getParentPath() != null) {
						targetPath = treePathToString(pathsSelected[0].getParentPath().toString());	
					}
					userInput = (String) JOptionPane.showInputDialog(null, "Creating new file at: " + targetPath, "New File", JOptionPane.QUESTION_MESSAGE, icon, null, null);		
				}
				creationType = FileCreationType.FROM_PARENT;
			}
			
			// user actually entered something
			if (userInput != null && userInput != "" && userInput != " ") {
				File fileCreated = null;
				switch (creationType) {
				case DIRECT:
					fileCreated = ConnoisseurGUI.getFileManager().newFileDirectly(userInput);
					System.out.println("Created file at: " + userInput);
					break;
				case FROM_PARENT:
					fileCreated = ConnoisseurGUI.getFileManager().newFileDirectly(targetPath, userInput);
					System.out.println("Created file at: " + targetPath + " with the name: " + userInput);
					break;
				default:
					break;
				}
				// reload the JTree
				files.setModel(new FileSystemModel(new File(ConnoisseurGUI.getInstance().getDefaultDir())));
				if (pathsSelected != null && pathsSelected[0] != null) {
					files.setSelectionPath(pathsSelected[0].pathByAddingChild(fileCreated));
				}
				
				// TODO: set selection to the file that was just created
			}
		}
		
		if (e.getSource() == newDirectoryMenuItem) {
			System.out.println("*NEW DIRECTORY CLICKED*");
		}
		
		if (e.getSource() == setDefaultDirMenuItem) {
			ImageIcon icon = new ImageIcon("resources/gui/menubar/icons8-folder-16.png");
			ImageIcon errorIcon = new ImageIcon("resources/gui/menubar/icons8-cancel-30.png");
			JSONObject systemData = ConnoisseurGUI.getFileManager().getSystemData();
			
			String defaultDir = (String) systemData.get("default-directory");
			if (defaultDir == null || defaultDir.isEmpty()) defaultDir = System.getProperty("user.home") + File.separator;
			String userInput = (String) JOptionPane.showInputDialog(null, "Setting the default directory to: ", "Configure Default Directory", JOptionPane.QUESTION_MESSAGE, icon, null, defaultDir);
			
			if (userInput == null) {
				return;
			}
			
			File targetDir = new File(userInput);
			if (targetDir == null || !Files.isDirectory(Paths.get(userInput))) {
				JOptionPane.showMessageDialog(null, "Please enter a valid directory!", "Error", JOptionPane.ERROR_MESSAGE, errorIcon);	
				return;
			}
			
			systemData.put("default-directory", userInput);
			ConnoisseurGUI.getFileManager().log("Set default dir to " + userInput);
			ConnoisseurGUI.getFileManager().saveSystemData();
			ConnoisseurGUI.getInstance().displayFolderTree(userInput);
			ConnoisseurGUI.getInstance().getFolderTree().setViewportView(ConnoisseurGUI.getInstance().displayFolderTree(targetDir.getPath()));
		}
		
		if (e.getSource() == editTagsMenuItem) {
			String userInput = "";
			
			// user has nothing selected
			ImageIcon errorIcon = new ImageIcon("resources/gui/menubar/icons8-cancel-30.png");
			ImageIcon editIcon = new ImageIcon("resources/gui/menubar/icons8-pencil-16.png");
			
			if (pathsSelected == null || pathsSelected.length == 0) {
				JOptionPane.showMessageDialog(null, "You must have a file selected to edit tags!", "Error", JOptionPane.ERROR_MESSAGE, errorIcon);	
			} else if (pathsSelected.length > 1) {
				// user has more than one thing selected
				
				JOptionPane.showMessageDialog(null, "WIP: You can only edit 1 file's tags at a time! (for now)", "Error", JOptionPane.ERROR_MESSAGE, errorIcon);	
			} else {
				//user only has one thing selected
				File selected = new File(treePathToString(pathsSelected[0].toString()));
				System.out.println(selected.getPath());
				
				if (selected.list() != null) {
					JOptionPane.showMessageDialog(null, "You cannot edit tags on a directory yet!", "Error", JOptionPane.ERROR_MESSAGE, errorIcon);	
					return;
				} 
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
				}
			}
		}
	}

	/*
	 * Enum class to identify which file creation method the program will use
	 */
	private enum FileCreationType {
		FROM_PARENT, DIRECT;
	}
	
	// takes a path in the TreePath format and converts it to a normal path format
	private String treePathToString(String input) {
		return input.replace("[", "").replace("]", "").replace(" ", "").replace(",", File.separator);
	}

}
