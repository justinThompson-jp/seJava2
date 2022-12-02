package Connoisseur;

import java.awt.BorderLayout;

/*
*	@authors: Justin Thompson, Jonathan Vallejo, Jacob Crawford
*/

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import Connoisseur.file.TagManager;
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
	
	private JScrollPane folder_contents; 
	private JScrollPane folder_tree;
	private JTree tree;
	
	private static ConnoisseurGUI instance;
	private static FileManager fileManager;
	private static TagManager tagManager;
	
	// Saves the path to the System.getProperty("user.home") for easy access
	// This allows ViewDirectory code to work with Windows(tested), Linux(tested), and MacOS(untested)
	private String default_dir;
	private String current_dir;
	
	/*
	 * Code by Justin Thompson
	 * START BLOCK
	 */
	
	//Create the application.
	public ConnoisseurGUI() throws FileNotFoundException, IOException, ParseException {
		instance = this;
		fileManager = new FileManager();
		tagManager = new TagManager();
		ViewDirectory x = new ViewDirectory();
		this.default_dir = x.getDefaultDir(); //Added by Jacob Crawford
		this.current_dir = default_dir;
		
		initialize();
		
		// allows us to detect when our program is terminated
        gui_frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	
            	// store all our data to the JSON files
            	FileWriter file;
            	
            	// save system data file
            	try {
    				file = new FileWriter(fileManager.getSystemDataFile().getPath());
    				file.write(fileManager.getSystemData().toJSONString());
    				file.close();
    				fileManager.log("Saved system data file.");
    			} catch (Exception ex) {
    				ex.printStackTrace();
    				fileManager.log("Something went wrong while saving system data.");
    			}
            	
            	// save directory data file
            	try {
    				file = new FileWriter(fileManager.getDirectoryDataFile().getPath());
    				file.write(fileManager.getDirectoryData().toJSONString());
    				file.close();
    				fileManager.log("Saved directory data file.");
    			} catch (Exception ex) {
    				ex.printStackTrace();
    				fileManager.log("Something went wrong while saving directory data.");
    			}
            }
        });
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
		// TODO set minimum/maximum sizes for elements for better scalability
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
		folder_tree = new JScrollPane();
		main_hori_split.setLeftComponent(folder_tree);
		
		JLabel folder_tree_label = new JLabel("Library");
		folder_tree.setColumnHeaderView(folder_tree_label);
		
		tree = new JTree();
		tree.addMouseListener(new CMouseListener(tree, instance));
		
		folder_tree.setViewportView(displayFolderTree(default_dir));
				
		// folder contents(left component of right_vert_split, which is the right component of main_hori_split)
		this.folder_contents = new JScrollPane(displayDirContents(default_dir));
		right_vert_split.setLeftComponent(folder_contents);
		
		//folder_contents.setViewportView(displayDirContents(default_dir));

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
		current_dir = _dir;
		ViewDirectory dir = new ViewDirectory();
		dir.Directory(_dir);
		
		
		Object[] columns = {"", "Name","Tags", "Creation Date", "Last Access", "Last Modified", "Size"}; // Set column names
		Object[] children = ViewDirectory.pathnames;
		
		//If given null or invalid path name, default to user.home as initial directory
		if (children == null) {
			_dir = System.getProperty("user.home");
			dir.Directory(_dir);
			children = ViewDirectory.pathnames;
		}
		
		int table_rows = children.length; // Used to create amount of rows for table
		int table_columns = columns.length;// Used to create amount of columns for table
		
		// adds an additional row to the JTable IFF we aren't at our current main directory
		// TODO access JSON to see what we have the directory set as
		JSONObject systemData = ConnoisseurGUI.getFileManager().getSystemData();
		
		String defaultDir = (String) systemData.get("default-directory");
		int move_down = 0;
		if (!_dir.equals(defaultDir)) {
			move_down = 1;
		}
		contents_table = new DefaultTableModel(table_rows + move_down, table_columns) {
			private static final long serialVersionUID = -2825576874268568706L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		contents_table.setColumnIdentifiers(columns);
		
		if (table_rows == 0) {
			dir_contents = new JTable(contents_table) {
				private static final long serialVersionUID = -7048758524571061712L;
	
				public Class getColumnClass(int column) {
		         	return ImageIcon.class;
				}
			};
		} else {
			dir_contents = new JTable(contents_table) {
				private static final long serialVersionUID = -7048758524571061712L;
	
				public Class getColumnClass(int column) {
		         	return getValueAt(1, 1).getClass();
				}
			};
		}
		dir_contents.addMouseListener(new CMouseListener(dir_contents, instance));
		dir_contents.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		dir_contents.getColumn("").setMinWidth(20);
		dir_contents.getColumn("").setMaxWidth(20);
		
		// condition check so that it doesn't show back arrow if in current default directory
		if (move_down == 1) {
			// setting up return to previous directory
			dir_contents.setValueAt("..", 0, 1);
			dir_contents.setValueAt(new ImageIcon("resources/gui/view/back.png"), 0, 0);
		}
		
		// For loop for filling out JTable
		for (int i = 0; i < table_rows; i++) {
			// Concatenates current directory and each value of the first column into 
			// a string to form its absolute file path.
	        String i_file_path = _dir + "\\" + children[i].toString();
	        
			// Fill first column with icon differentiating files and folders
	        if (Files.isDirectory(Paths.get(i_file_path))) {
	        	dir_contents.setValueAt(new ImageIcon("resources/gui/view/folder.png"), i + move_down, 0);
	        } else {
	        	dir_contents.setValueAt(new ImageIcon("resources/gui/view/file.png"), i + move_down, 0);
	        }

	        // Fill second column with names of files pulled from ViewDirectory
			dir_contents.setValueAt(children[i], i + move_down, 1);
     
	        // Runs all created strings to get metadata for every file in directory.
			ViewFile.FileAttributes(i_file_path);
			ArrayList<String> mdata = ViewFile.mdata;
			
			// Fills table with each file's corresponding metadata
			for (int j = 0; j < columns.length - 3; j++) {
				dir_contents.setValueAt(mdata.get(j), i + move_down, j+3);
			}
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
		File f = new File(_dir);
		//If given null or invalid path name, default to user.home as initial directory
		if (_dir == null || _dir.isEmpty() || f.isDirectory() == false) {
			_dir = System.getProperty("user.home");
		}
		// changed hard referenced "C:\\" to call to private variable by Jacob Crawford
		tree.setModel(new FileSystemModel(new File(_dir)));
		/*
		 * END BLOCK
		 */
		return tree;
	}
	
	public JTree getJTree() {return tree;}
	public JScrollPane getFolderTree() {return folder_tree;}
	public static ConnoisseurGUI getInstance() {return instance;}
	public static FileManager getFileManager() {return fileManager;}
	public static TagManager getTagManager() {return tagManager;}
	public JScrollPane getFolderContents() {return folder_contents;}
	public String getDefaultDir() {return default_dir;}
	public JTable getDirContents() {return dir_contents;}
	public String getCurrentDir() {return current_dir;}
}
