package Connoisseur.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

import Connoisseur.ConnoisseurGUI;

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
	
	private JMenuItem newFileMenuItem, newDirectoryMenuItem;
	

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
		
		// add sub-menu items to "New" menu
		newFileSubMenu.add(newFileMenuItem);
		newFileSubMenu.addSeparator();
		newFileSubMenu.add(newDirectoryMenuItem);
		
		// add sub-menu to "New" menu
		fileMenu.add(newFileSubMenu);
		
		this.add(fileMenu);
	}
	
	private void initEditMenu() {
		this.editMenu = new JMenu("Edit");
		// TODO: add edit options
		this.add(editMenu);
	}
	
	private void initHelpMenu() {
		this.helpMenu = new JMenu("Help");
		// TODO: add help options
		this.add(helpMenu);
	}
	
	
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
				switch (creationType) {
				case DIRECT:
					ConnoisseurGUI.getFileManager().createFileDirectly(userInput);
					System.out.println("Created file at: " + userInput);
					break;
				case FROM_PARENT:
					ConnoisseurGUI.getFileManager().createFileDirectly(targetPath, userInput);
					System.out.println("Created file at: " + targetPath + " with the name: " + userInput);
					break;
				default:
					break;
				}
			}
		}
		
		if (e.getSource() == newDirectoryMenuItem) {
			System.out.println("*NEW DIRECTORY CLICKED*");
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
