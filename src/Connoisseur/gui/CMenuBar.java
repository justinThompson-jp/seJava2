package Connoisseur.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

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
		if (e.getSource() == newFileMenuItem) {
			System.out.println("*NEW FILE CLICKED*");
		}
		if (e.getSource() == newDirectoryMenuItem) {
			System.out.println("*NEW DIRECTORY CLICKED*");
		}
		
		
	}

}
