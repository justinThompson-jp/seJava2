package Connoisseur.gui;

import javax.swing.JToolBar;

/**
 * Extension of the JToolBar class to include the functionality of the Connoisseur Toolbar. <br>
 * @author Jonathan Vallejo
 *
 */
public class CToolBar extends JToolBar {

	/**
	 * Auto Generated UID
	 */
	private static final long serialVersionUID = -4558531102217508332L;
	
	private CSearchField textField;
	
	/***
	 * Creates a new toolbar.
	 * @param x x-coordinate of this component
	 * @param y y-coordinate of this component
	 * @param width width of this component
	 * @param height height of this component
	 */
	public CToolBar(int x, int y, int width, int height) {
		this.setBounds(x, y, width, height);
		this.setFloatable(false);
		this.addContent();
	}
	
	private void addContent() {
		// search bar
		this.textField = new CSearchField(this);
		this.add(textField);
		
		// TODO: add other toolbar buttons
	}
}
