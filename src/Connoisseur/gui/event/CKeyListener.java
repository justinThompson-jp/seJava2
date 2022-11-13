package Connoisseur.gui.event;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JTextField;

import Connoisseur.ConnoisseurGUI;
import Connoisseur.file.MediaFile;

/**
 * Custom KeyListener implementation for a JTextField.
 * @author Jonathan Vallejo
 *
 */
public class CKeyListener implements KeyListener {

	private JTextField textField;
	
	public CKeyListener(JTextField textField) {
		this.textField = textField;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		// user pressed enter
		if (e.getKeyCode() == 10) {
			// eventually this would trigger a search query
			System.out.println("*SEARCH QUERY*: " + textField.getText());
			ArrayList<MediaFile> searchResults = ConnoisseurGUI.getTagManager().searchByTags(textField.getText().split(","));
			for (MediaFile mFile : searchResults) {
				ConnoisseurGUI.getFileManager().log("[search results] " + mFile.getName() + " has a matching tag.");
			}
			if (searchResults.size() <= 0) {
				ConnoisseurGUI.getFileManager().log("[search results] could not find any files that have matching tags.");
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

}
