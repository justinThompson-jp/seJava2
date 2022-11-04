package Connoisseur;

import java.awt.BorderLayout;

/*
*	@authors: Justin Thompson, Jonathan Vallejo, Jacob Crawford
*/

import java.awt.EventQueue;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import Connoisseur.gui.CMenuBar;
import Connoisseur.gui.CToolBar;
import Connoisseur.gui.FileSystemModel;
import Connoisseur.gui.event.CMouseListener;

public class ConnoisseurGUI {
	
	/*
	 * Code by Justin Thompson
	 * START BLOCK
	 */
	private JFrame gui_frame;
	private JTable dir_contents;
	private DefaultTableModel contents_table = new DefaultTableModel();
	/*
	 * Code by Justin Thompson
	 * END BLOCK
	 */
	
	public JScrollPane folder_contents; 
	
	private JTree tree;
	
	private static ConnoisseurGUI instance;
	private static FileManager fileManager;
	
	// Saves the path to the System.getProperty("user.home") for easy access
	// This allows ViewDirectory code to work with Windows(tested), Linux(tested), and MacOS(untested)
	private String default_dir = System.getProperty("user.home");
	
	/*
	 * Code by Justin Thompson
	 * START BLOCK
	 */
	

	//Create the application.
	public ConnoisseurGUI() {
		instance = this;
		this.default_dir = System.getProperty("user.home"); //Added by Jacob Crawford
		this.folder_contents = null;
		fileManager = new FileManager();
		initialize();
	}

	//Launch the application.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConnoisseurGUI window = new ConnoisseurGUI();
					window.gui_frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Initialize the contents of the frame.
	private void initialize() {
		gui_frame = new JFrame();
		gui_frame.setBounds(100, 100, 720, 480);
		gui_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*
		 * Code by Justin Thompson
		 * END BLOCK
		 */
		CMenuBar menuBar = new CMenuBar();
		gui_frame.setJMenuBar(menuBar);

		CToolBar toolBar = new CToolBar(0, 0, 704, 25);
		gui_frame.getContentPane().add(toolBar, BorderLayout.NORTH);
		
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
		gui_frame.getContentPane().add(main_hori_split, BorderLayout.CENTER);
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

		folder_tree.setViewportView(displayFolderTree(default_dir));
				
		// folder contents(left component of right_vert_split, which is the right component of main_hori_split)
		this.folder_contents = new JScrollPane();
		right_vert_split.setLeftComponent(folder_contents);
		
		folder_contents.setViewportView(displayDirContents(default_dir));

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
	  * <p>
	  * Lorem ispum dolor...
	  * </p>
	  * <p>
	  * The bulk of the contents of this method were written by Justin Thompson
	  * Conversion from sequential commands to method done by Jacob Crawford
	  * </p>
	  * 
	  * @param String _dir - String path to target directory
	  * @return JTable table - Formatted JTable of the contents of target directory
	  */
	public JTable displayDirContents(String _dir) {
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
		contents_table = new DefaultTableModel(h,k);
		contents_table.setColumnIdentifiers(columns);
		
		dir_contents = new JTable(contents_table);
		
		// Fill first column with names of files pulled from ViewDirectory
		for (int i = 0; i < children.length; i++) {
		dir_contents.setValueAt(children[i], i, 0);
		}
		/*
		 * END BLOCK
		 */
		
		return dir_contents;
	}
	
	/**
	  * Returns a JTree object of the contents of input directory
	  * <p>
	  * Lorem ispum dolor...
	  * </p>
	  * <p>
	  * The bulk of the contents of this method were written by Justin Thompson
	  * Conversion from sequential commands to method done by Jacob Crawford
	  * </p>
	  * 
	  * @param String _dir - String path to target directory
	  * @return JTree tree - Nestable JTree of the contents of target directory
	  */
	public JTree displayFolderTree(String _dir) {
		/*
		 * Code by Justin Thompson
		 * START BLOCK
		 */
		
		tree = new JTree();
		tree.addMouseListener(new CMouseListener(tree, instance));// added mouselistener and feeds it the tree to be interacted and a reference to the GUI itself
		tree.setModel(new FileSystemModel(new File(_dir)));// changed hard referenced "C:\\" to call to private variable by Jacob Crawford
		/*
		 * END BLOCK
		 */
		return tree;
	}
	
	public JTree getJTree() {return tree;}
	public static ConnoisseurGUI getInstance() {return instance;}
	public static FileManager getFileManager() {return fileManager;}
	public JScrollPane getFolderContents() {return folder_contents;}
	public String getDefaultDir() {return default_dir;}

	public void setFolderContents(JScrollPane _folder_contents) {
		this.folder_contents = _folder_contents;
	}
}
