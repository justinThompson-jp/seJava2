package Connoisseur.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/***
 * Extension of JLabel to show images
 * 
 * @author jonat
 *
 */
public class CImagePanel extends JLabel {

	/**
	 * Auto generated serial version ID
	 */
	private static final long serialVersionUID = 1307048256373672075L;
	
	private Image image;
	
	private boolean overrideDimensions = false;
	
	private int overrideWidth = this.getWidth();
	private int overrideHeight = this.getHeight();
	private int offsetWidth = 0;
	private int offsetHeight = 0;
	
	public CImagePanel() {
		super();
	}
	
	public CImagePanel(String text) {
		super(text);
	}

	@Override
	public void paint(Graphics g) {
		if (overrideDimensions) {
			g.drawImage(image, overrideWidth + offsetWidth, overrideHeight + offsetHeight, this.getOverrideWidth(), this.getOverrideHeight(), null);
		} else {
			g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
		}
	}

	public void setImage(String imagePath) {
		ImageIcon icon = new ImageIcon(imagePath);
		super.setIcon(icon);
		image = icon.getImage();
	}
	
	/**
	 * This function will set whether to use different dimensions to render the image, rather than just filling the whole panel
	 * @param bool
	 */
	public void setOverrrideDimensions(boolean bool) {
		this.overrideDimensions = bool;
	}
	
	public void setOverrideDimensions(int width, int height) {
		this.overrideWidth = width;
		this.overrideHeight = height;
	}
	
	public void setOverrideDimensions(int width, int height, int offsetWidth, int offsetHeight) {
		this.overrideWidth = width;
		this.overrideHeight = height;
		this.offsetWidth = offsetWidth;
		this.offsetHeight = offsetHeight;
	}
	
	public void setOverrideWidth(int width) {
		this.overrideWidth = width;
	}
	
	public void setOverrideHeight(int height) {
		this.overrideHeight = height;
	}
	
	public void setOverrideOffsetX(int offsetWidth) {
		this.offsetWidth = offsetWidth;
	}
	
	public void setOverrideOffsetY(int offsetHeight) {
		this.offsetHeight = offsetHeight;
	}
	
	public int getOverrideWidth() {
		return overrideWidth;
	}
	
	public int getOverrideHeight() {
		return overrideHeight;
	}
	
	public int getOverrideOffsetX() {
		return offsetWidth;
	}
	
	public int getOverrideOffsetY() {
		return offsetHeight;
	}

}
