package Connoisseur.gui.event;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JTextField;

import Connoisseur.ConnoisseurGUI;

/**
 * Custom KeyListener implementation for a JTextField.
 * 
 * @author Jonathan Vallejo
 *
 */
public class CKeyListener implements KeyListener {

	private JTextField textField;
	private Thread searchThread;
	private boolean searchStarted = false;
	
	public CKeyListener(JTextField textField) {
		this.textField = textField;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// user pressed enter
		if (e.getKeyCode() == 10) {
			if (searchThread != null || searchStarted == true) {
				ConnoisseurGUI.getFileManager().log("ABORTING PREVIOUS SEARCH, STARTING NEW SEARCH..");
				searchThread.interrupt();
				searchStarted = false;
			}
			
			searchThread = new Thread(new Runnable() {
				@Override
				public void run() {
					long startTime = System.currentTimeMillis();
					ArrayList<File> searchResults = ConnoisseurGUI.getFileManager().searchDirectory(ConnoisseurGUI.getInstance().getCurrentDir(), textField.getText());
					System.out.println("   ");
					System.out.println("   ");
					double searchTime = ((System.currentTimeMillis() - startTime)/1000);
					System.out.println("Search completed: Took " + searchTime + " seconds.");
					for (File f : searchResults) {
						System.out.println(f.getName());
					}
					searchThread.interrupt();
					searchStarted = false;
				}
			});
			searchThread.start();
			searchStarted = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
