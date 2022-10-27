package Connoisseur.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Connoisseur.gui.event.CKeyListener;

/**
 * Extension of JTexField to create a custom JTextField for the Connoisseur GUI.
 * Image Icon: <a target="_blank" href=
 * "https://icons8.com/icon/kLey780kTsdG/search">Search</a> icon by
 * <a target="_blank" href="https://icons8.com">Icons8</a>
 * 
 * @author Jonathan Vallejo
 *
 */
public class CSearchField extends JTextField {

	/**
	 * Auto generated UID
	 */
	private static final long serialVersionUID = -4998226154845976746L;

	private CToolBar toolbar;
	private Color bgColor = Color.WHITE;

	public CSearchField(CToolBar toolbar) {
		this.toolbar = toolbar;
		this.init();
	}

	private void init() {
		this.setBackground(new Color(255, 255, 255, 0));
		this.setOpaque(false);
		this.setBorder(new EmptyBorder(0, 0, this.getWidth(), 15));
		this.setMaximumSize(new Dimension(119, toolbar.getHeight()));
		this.setToolTipText("Search files");
		
		CKeyListener keyListener = new CKeyListener(this);
		this.addKeyListener(keyListener);
	}

	@Override
	protected void paintComponent(Graphics g) {
		int width = this.getWidth();
		int height = this.getHeight();

		Graphics2D g2D = (Graphics2D) g;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2D.setColor(bgColor);
		g2D.fillRect(0, 0, width, height);

//		g2D.fillRoundRect(0, 0, width, height, height, height); //makes search field circular

		// search button
		int marginButton = 0;
		int buttonSize = height - marginButton * 2;

		// add image
		int marginImage = 5;
		int imageSize = buttonSize - marginImage * 2;
		Image img = new ImageIcon("resources/gui/toolbar/icons8-search-16.png").getImage();
		g2D.drawImage(img, width - height + marginImage + 2, marginButton + marginImage, imageSize, imageSize, null);

		super.paintComponent(g);
	}

}
