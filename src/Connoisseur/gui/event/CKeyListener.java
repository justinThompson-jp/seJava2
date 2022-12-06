package Connoisseur.gui.event;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

import Connoisseur.ConnoisseurGUI;
import Connoisseur.gui.CSearchFrame;

/**
 * Custom KeyListener implementation for a JTextField.
 * 
 * @author Jonathan Vallejo
 *
 */
public class CKeyListener implements KeyListener {

	private JTextField textField;
	private CSearchFrame searchFrame;
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
//				ConnoisseurGUI.getFileManager().log("ABORTING PREVIOUS SEARCH, STARTING NEW SEARCH..");
				searchThread.interrupt();
				searchStarted = false;
			}
			
			searchThread = new Thread(new Runnable() {
				@Override
				public void run() {
					searchFrame = new CSearchFrame("Search Results for \"" + textField.getText() + "\"");
					searchFrame.getProgressBar().setIndeterminate(true);
					searchFrame.showFrame();
					
					long startTime = System.currentTimeMillis();
					ConnoisseurGUI.getFileManager().searchDirectory(searchFrame, ConnoisseurGUI.getInstance().getCurrentDir(), textField.getText());
					double searchTime = ((System.currentTimeMillis() - startTime)/1000);
					searchFrame.setBarProgress(searchFrame.getProgressBar().getString() + " in " + searchTime + " seconds.");
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
