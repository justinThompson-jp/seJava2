package Connoisseur.gui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/***
 * Extension of JLabel to show a thumbnail icon
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
	
	/**
	 * Set the width and height overrides
	 * @param width
	 * @param height
	 */
	public void setOverrideDimensions(int width, int height) {
		this.overrideWidth = width;
		this.overrideHeight = height;
	}
	
	/**
	 * Set the width, height, offset width and offset height overrides
	 * @param width
	 * @param height
	 * @param offsetWidth
	 * @param offsetHeight
	 */
	public void setOverrideDimensions(int width, int height, int offsetWidth, int offsetHeight) {
		this.overrideWidth = width;
		this.overrideHeight = height;
		this.offsetWidth = offsetWidth;
		this.offsetHeight = offsetHeight;
	}
	
	/**
	 * Set the width override
	 * @param width
	 */
	public void setOverrideWidth(int width) {
		this.overrideWidth = width;
	}
	
	/**
	 * Set the height override
	 * @param height
	 */
	public void setOverrideHeight(int height) {
		this.overrideHeight = height;
	}
	
	/**
	 * Set the override offset width
	 * @param offsetWidth
	 */
	public void setOverrideOffsetX(int offsetWidth) {
		this.offsetWidth = offsetWidth;
	}
	
	/**
	 * Set the override offset height
	 * @param offsetHeight
	 */
	public void setOverrideOffsetY(int offsetHeight) {
		this.offsetHeight = offsetHeight;
	}
	
	/**
	 * Get the override width
	 * @return override width
	 */
	public int getOverrideWidth() {
		return overrideWidth;
	}
	
	/**
	 * Get the override height
	 * @return override height
	 */
	public int getOverrideHeight() {
		return overrideHeight;
	}
	
	/**
	 * Get the override offset width
	 * @return override offset width
	 */
	public int getOverrideOffsetX() {
		return offsetWidth;
	}
	
	/**
	 * Get the override offset height
	 * @return override offset height
	 */
	public int getOverrideOffsetY() {
		return offsetHeight;
	}
	
	/**
	 * Check whether this panel is currently overriding the default dimensions
	 * @return boolean
	 */
	public boolean isOverridingDimensions() {
		return overrideDimensions;
	}

}
