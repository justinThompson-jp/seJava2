package Connoisseur;

/*
*	@authors: Justin Thompson, Jonathan Vallejo, Jacob Crawford
*/

import java.awt.EventQueue;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JTree;

import Connoisseur.gui.CMenuBar;
import Connoisseur.gui.CToolBar;
import Connoisseur.gui.FileSystemModel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import javax.swing.table.DefaultTableModel;

public class ConnoisseurGUI {

	private JFrame frame;
	private JTable table;
	private DefaultTableModel model = new DefaultTableModel();
	
	private String default_dir = System.getProperty("user.home");
	
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
		default_dir = System.getProperty("user.home");
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

		CToolBar toolBar = new CToolBar(0, 0, 704, 25);
		frame.getContentPane().add(toolBar, BorderLayout.NORTH);
		
		// splits folder_tree from the rest
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		frame.getContentPane().add(splitPane_1, BorderLayout.CENTER);
		splitPane_1.setResizeWeight(0.4);
		
		// splits folder_contents from the rest
		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_1.setRightComponent(splitPane_2);
		splitPane_2.setResizeWeight(0.7);

		// splits file_metadata from last section
		JSplitPane splitPane_3 = new JSplitPane();
		splitPane_3.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		splitPane_2.setRightComponent(splitPane_3);
		splitPane_3.setResizeWeight(.8);
		
		// file tree
		JScrollPane folder_tree = new JScrollPane();
		splitPane_1.setLeftComponent(folder_tree);

		
		JLabel folder_tree_label = new JLabel("Library");
		folder_tree.setColumnHeaderView(folder_tree_label);

		JTree tree = new JTree();

		tree.setModel(new FileSystemModel(new File(default_dir)));
		folder_tree.setViewportView(tree);
		
		// folder contents
		JScrollPane folder_contents = new JScrollPane();
		splitPane_2.setLeftComponent(folder_contents);

		JLabel folder_contents_label = new JLabel("<change this name to current folder name>");
		folder_contents_label.setEnabled(false);
		folder_contents.setColumnHeaderView(folder_contents_label);
		
		ViewDirectory dir = new ViewDirectory();
		dir.Directory(default_dir);
		
		Object[] columns = {"Name", "Creation Date", "Last Access", "Last Modified", "Size"}; // Set column names
		Object[] children = ViewDirectory.pathnames;
		int h = children.length; // Used to create amount of rows for table
		int k = columns.length;// Used to create amount of columns for table
		model = new DefaultTableModel(h,k);
		model.setColumnIdentifiers(columns);
		
		table = new JTable(model);
		
		// Fill first column with names of files pulled from ViewDirectory
		for (int i = 0; i < children.length; i++) {
		table.setValueAt(children[i], i, 0);
		}

		folder_contents.setViewportView(table);
		// file metadata
		JPanel file_metadata = new JPanel();
		splitPane_3.setLeftComponent(file_metadata);

		JLabel file_metadata_label = new JLabel("<change this name to current file name>");
		file_metadata_label.setEnabled(false);
		file_metadata_label.setVerticalAlignment(SwingConstants.NORTH);
		file_metadata.add(file_metadata_label);
		
		// last/undecided panel SplitPane_3.setRightComponent()

	}
}
