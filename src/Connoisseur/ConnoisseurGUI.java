package Connoisseur;

import java.awt.EventQueue;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTree;

import Connoisseur.gui.CMenuBar;
import Connoisseur.gui.CToolBar;
import Connoisseur.gui.FileSystemModel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;

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
		
		CMenuBar menuBar = new CMenuBar();
		frame.setJMenuBar(menuBar);
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setResizeWeight(0.25);
		frame.getContentPane().add(splitPane_1, BorderLayout.CENTER);
		
		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_1.setRightComponent(splitPane_2);
		splitPane_2.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_2.setResizeWeight(.6);
		
		JScrollPane folder_contents = new JScrollPane();
		splitPane_2.setLeftComponent(folder_contents);
		
		JPanel contents = new JPanel();
		folder_contents.setViewportView(contents);
		
		JLabel folder_contents_label = new JLabel("path name");
		folder_contents.setColumnHeaderView(folder_contents_label);
		
		JSplitPane splitPane_3 = new JSplitPane();
		splitPane_3.setResizeWeight(.8);
		splitPane_2.setRightComponent(splitPane_3);
		
		JPanel file_metadata = new JPanel();
		splitPane_3.setLeftComponent(file_metadata);
		
		JLabel lblNewLabel_1 = new JLabel("file name");
		lblNewLabel_1.setVerticalAlignment(SwingConstants.TOP);
		file_metadata.add(lblNewLabel_1);
		
		JScrollPane folder_tree = new JScrollPane();
		splitPane_1.setLeftComponent(folder_tree);
		
		JTree tree = new JTree();
		folder_tree.setViewportView(tree);
		tree.setModel(new FileSystemModel(new File(System.getProperty("user.home"))));
		
		JLabel lblNewLabel = new JLabel("Library");
		folder_tree.setColumnHeaderView(lblNewLabel);
		
		CToolBar toolBar = new CToolBar(0, 0, 704, 25);
		frame.getContentPane().add(toolBar, BorderLayout.NORTH);
	}
}
