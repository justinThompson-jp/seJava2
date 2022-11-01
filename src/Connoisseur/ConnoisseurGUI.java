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

	/*
	 * Code by Justin Thompson
	 * START BLOCK
	 */
	private JFrame frame;
	private JTable table;
	private DefaultTableModel model = new DefaultTableModel();
	/*
	 * Code by Justin Thompson
	 * END BLOCK
	 */
	
	// Saves the path to the System.getProperty("user.home") for easy access
	// This allows ViewDirectory code to work with Windows(tested), Linux(tested), and MacOS(untested)
	private String default_dir = System.getProperty("user.home");
	
	/*
	 * Code by Justin Thompson
	 * START BLOCK
	 */
	
	//Launch the application.
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


	//Create the application.
	public ConnoisseurGUI() {
		default_dir = System.getProperty("user.home"); //Added by Jacob Crawford
		initialize();
	}

	//Initialize the contents of the frame.
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 720, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*
		 * Code by Justin Thompson
		 * END BLOCK
		 */
		CMenuBar menuBar = new CMenuBar();
		frame.setJMenuBar(menuBar);

		CToolBar toolBar = new CToolBar(0, 0, 704, 25);
		frame.getContentPane().add(toolBar, BorderLayout.NORTH);
		
		/*
		 * Code by Justin Thompson
		 * Rearranged/added descriptive names and comments by Jacob Crawford
		 * START BLOCK
		 */
		/*
		 * Uses JSplitPanes to divide the ConnoisseurGUI window into four subwindows
		 * First the Left section containing the folder_tree 
		 * Then the Upper-Right section displaying the current folder_contents
		 * Then the Bottom-Center section displaying the current file_metadata
		 * Finally the Bottom-Right section displaying <undecided information, probably current file's tags>
		 */
		JSplitPane main_hori_split = new JSplitPane();
		main_hori_split.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		frame.getContentPane().add(main_hori_split, BorderLayout.CENTER);
		main_hori_split.setResizeWeight(0.4);
		
		JSplitPane right_vert_split = new JSplitPane();
		right_vert_split.setOrientation(JSplitPane.VERTICAL_SPLIT);
		main_hori_split.setRightComponent(right_vert_split);
		right_vert_split.setResizeWeight(0.7);

		JSplitPane bot_right_hori_split = new JSplitPane();
		bot_right_hori_split.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		right_vert_split.setRightComponent(bot_right_hori_split);
		bot_right_hori_split.setResizeWeight(.8);
		/*
		 * END BLOCK
		 */
		
		// folder tree(left component of main_hori_split)
		JScrollPane folder_tree = new JScrollPane();
		main_hori_split.setLeftComponent(folder_tree);

		
		JLabel folder_tree_label = new JLabel("Library");
		folder_tree.setColumnHeaderView(folder_tree_label);

		/*
		 * Code by Justin Thompson
		 * START BLOCK
		 */
		JTree tree = new JTree();
		tree.setModel(new FileSystemModel(new File(default_dir)));// changed hard referenced "C:\\" to call to private variable by Jacob Crawford
		folder_tree.setViewportView(tree);
		/*
		 * END BLOCK
		 */
		
		
		// folder contents(left component of right_vert_split, which is the right component of main_hori_split)
		JScrollPane folder_contents = new JScrollPane();
		right_vert_split.setLeftComponent(folder_contents);
		
		JTable table  = displayFolderContents(default_dir);
		folder_contents.setViewportView(table);

		// file metadata(left component of bot_right_hori_split, which is the right component of right_vert_split, which is the right component of main_hori_split)
		JPanel file_metadata = new JPanel();
		bot_right_hori_split.setLeftComponent(file_metadata);

		JLabel file_metadata_label = new JLabel("<change this name to current file name>");
		file_metadata_label.setEnabled(false);
		file_metadata_label.setVerticalAlignment(SwingConstants.NORTH);
		file_metadata.add(file_metadata_label);
		
		// last/undecided panel SplitPane_3.setRightComponent()(right component of bot_right_hori_split)

	}
	
	/**
	  * Returns a JTable object of the contents of input directory
	  * 
	  * <p>
	  * Lorem ispum dolor...
	  * </p>
	  * <p>
	  * The bulk of the contents of this method were written by Justin Thompson
	  * Conversion from direct call to method done by Jacob Crawford
	  * </p>
	  * 
	  * @param String _dir - String path to target directory
	  * @return JTable table - Formatted JTable of the contents of target directory
	  */
	private JTable displayFolderContents(String _dir) {
		/*
		 * Code by Justin Thompson
		 * START BLOCK
		 */
		ViewDirectory dir = new ViewDirectory();
		dir.Directory(_dir);
		
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
		/*
		 * END BLOCK
		 */
		
		return table;
	}
}
