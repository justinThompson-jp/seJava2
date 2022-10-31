
/* 
 * Fall 2022
 * CS4800 - Software Engineering
 * Group - Java2
 * 
 * Class initially created by Jacob Crawford
 */


package Connoisseur.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class CPopup extends JFrame implements ActionListener{
	
	
	/*
	 * Initial plan, this class will create a JFrame with a custom JLabel and two JButtons
	 * The plan is that when a method such as MoveDirectory or MoveFile need to get user input
	 * 		Like in the case of of a duplicate directory merge or a duplicate file overwrite
	 * Those classes will create an instance of this object
	 * This object will create the JFrame with an appropriate prompt
	 * 		This prompt could be specified by an input when calling CPopup
	 * Once the user selects their option, either yes or no in most cases, this object will return a true/false value to
	 * the class that called it
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
