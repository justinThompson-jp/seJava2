package Connoisseur.gui.event;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

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
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

}
