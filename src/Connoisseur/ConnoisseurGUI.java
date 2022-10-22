package Connoisseur;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JDesktopPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.JMenuBar;
import javax.swing.JSplitPane;
import javax.swing.JPanel;

public class ConnoisseurGUI {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConnoisseurGUI window = new ConnoisseurGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ConnoisseurGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 720, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setBounds(0, 0, 704, 16);
		frame.getContentPane().add(toolBar);
		
		JTree tree = new JTree();
		tree.setBounds(0, 27, 120, 414);
		frame.getContentPane().add(tree);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setBounds(130, 302, 564, 128);
		frame.getContentPane().add(splitPane);
		
		JPanel panel = new JPanel();
		panel.setBounds(130, 27, 564, 264);
		frame.getContentPane().add(panel);
	}
}
