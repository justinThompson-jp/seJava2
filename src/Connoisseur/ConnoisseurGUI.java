package Connoisseur;

import java.awt.BorderLayout;
import java.awt.Dimension;

/*
*	@authors: Justin Thompson, Jonathan Vallejo, Jacob Crawford, Aristan Galindo
*/

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.AbstractTableModel;
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
	private JLabel folder_contents_label;
	private JScrollPane folder_tree;
	private JTree tree;
	
	private static ConnoisseurGUI instance;
	private static FileManager fileManager;
	private static TagManager tagManager;
	private PlaylistManager playlist_manager;
	
	// Saves the path to the System.getProperty("user.home") for easy access
	// This allows ViewDirectory code to work with Windows(tested), Linux(tested), and MacOS(untested)
	private String default_dir;
	private String current_dir;
	private String selected_file;
	
	
	/* 
	 * Code by Aristan Galindo
	 * START BLOCK
	 */
	
	// Directory listing
	private JTable table;
	
	private ListSelectionListener listSelectionListener;
	
	// Currently selected file
	private File currentFile;
	
	// Gives the name of the files
	private FileSystemView fileSystemView;
	
	// Table model for File
    private FileTableModel fileTableModel;
	
	// file details
	private JLabel fileName;
	private JTextField path;
	private JLabel date;
	private JLabel size;
	private JLabel tag;
	
	/*
	 * END BLOCK
	 */
	
	/*
	 * Code by Justin Thompson
	 * START BLOCK
	 */
	
	//Create the application.
	public ConnoisseurGUI() throws FileNotFoundException, IOException, ParseException {
		instance = this;
		fileManager = new FileManager();
		tagManager = new TagManager();
		playlist_manager = new PlaylistManager();
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
		gui_frame = new JFrame("Connoisseur");
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
		
		fileSystemView = FileSystemView.getFileSystemView();
		
		table = new JTable();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoCreateRowSorter(true);
        table.setShowVerticalLines(false);
		
		listSelectionListener =
                new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent lse) {
                        int row = table.getSelectionModel().getLeadSelectionIndex();
                        setFileDetails(((FileTableModel) table.getModel()).getFile(row));
                    }
                };
		
		
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
		main_hori_split.setDividerSize(5);
		gui_frame.getContentPane().add(main_hori_split, BorderLayout.CENTER);
		main_hori_split.setDividerLocation((int) (gui_frame.getWidth() * (0.3)));
		
		JSplitPane right_vert_split = new JSplitPane();
		right_vert_split.setOrientation(JSplitPane.VERTICAL_SPLIT);
		right_vert_split.setDividerSize(5);
		main_hori_split.setRightComponent(right_vert_split);
		right_vert_split.setDividerLocation((int) (gui_frame.getHeight() * (0.5)));
		right_vert_split.setResizeWeight(1); // only resize folder_contents panel automatically
		
		JSplitPane bot_right_hori_split = new JSplitPane();
		bot_right_hori_split.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		bot_right_hori_split.setDividerSize(5);
		right_vert_split.setRightComponent(bot_right_hori_split);
		bot_right_hori_split.setResizeWeight(1);
		
		JSplitPane folder_contents_pane = new JSplitPane();
		folder_contents_pane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		folder_contents_pane.setEnabled(false); // prevents resizing
		folder_contents_pane.setDividerSize(0);
		right_vert_split.setLeftComponent(folder_contents_pane);
		
		
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
		folder_contents_pane.setRightComponent(folder_contents);
		
		folder_contents_label = new JLabel(getCurrentDir());
		folder_contents_pane.setLeftComponent(folder_contents_label);
		
		// file metadata(left component of bot_right_hori_split, which is the right component of right_vert_split, which is the right component of main_hori_split)
		/* 
		 * Code by Aristan Galindo
		 * START BLOCK
		 */
		JPanel fileMetadata = new JPanel();
		bot_right_hori_split.setLeftComponent(fileMetadata);
		
		JPanel fileDetailsLabels = new JPanel(new GridLayout(0, 1, 2, 2));
        fileMetadata.add(fileDetailsLabels, BorderLayout.WEST);

        JPanel fileDetailsValues = new JPanel(new GridLayout(0, 1, 2, 2));
        fileMetadata.add(fileDetailsValues, BorderLayout.CENTER);
        
        // displays the file/directory selected
        fileDetailsLabels.add(new JLabel("File: ", JLabel.TRAILING));
        fileName = new JLabel();
        fileDetailsValues.add(fileName);
        
        // displays the path name of the file/folder selected
        fileDetailsLabels.add(new JLabel("Path name: ", JLabel.TRAILING));
        path = new JTextField(5);
        path.setEditable(false);
        fileDetailsValues.add(path);
        
        //displays the 'last modified' metadata of the selected file/directory
        fileDetailsLabels.add(new JLabel("Last Modified: ", JLabel.TRAILING));
        date = new JLabel();
        fileDetailsValues.add(date);
        
        // displays the 'size' of the selected file/folder
        fileDetailsLabels.add(new JLabel("Size: ", JLabel.TRAILING));
        size = new JLabel();
        fileDetailsValues.add(size);
        
        // displays the 'tag(s)' of the selected file/directory
        fileDetailsLabels.add(new JLabel("Tags: ", JLabel.TRAILING));
        tag = new JLabel();
        fileDetailsValues.add(size);
        
        int count = fileDetailsLabels.getComponentCount();
        for (int ii = 0; ii < count; ii++) {
            fileDetailsLabels.getComponent(ii).setEnabled(false);
        }
        
        /*
         * END BLOCK
         */
		
		// last/undecided panel SplitPane_3.setRightComponent()(right component of bot_right_hori_split)
		JPanel file_thumbnail = new JPanel();
		bot_right_hori_split.setRightComponent(file_thumbnail);

		// size constraints for swing objects
		gui_frame.setMinimumSize(new Dimension(720, 480));
		folder_tree.setMinimumSize(new Dimension((int) (gui_frame.getWidth() * (0.2)), (int) (gui_frame.getHeight())));
		folder_contents_pane.setMinimumSize(new Dimension((int) (gui_frame.getWidth() * (0.6)), (int) (gui_frame.getHeight() * (0.4))));
		bot_right_hori_split.setMinimumSize(new Dimension((int) (gui_frame.getWidth() * (0.6)), (int) (gui_frame.getHeight() * (0.2))));
		file_thumbnail.setMinimumSize(new Dimension((int) (gui_frame.getWidth() * (0.2)), (int) (gui_frame.getHeight() * (0.2))));
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
		
		
		Object[] columns = {"", "Name", "Creation Date", "Last Access", "Last Modified", "Size"}; // Set column names
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
		         	return getValueAt(1, column).getClass();
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
			for (int j = 0; j < columns.length - 2; j++) {
				dir_contents.setValueAt(mdata.get(j), i + move_down, j+2);
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
	
	/*
	 * Code by Aristan Galindo
	 * START BLOCK
	 */
	
	/** A TableModel to hold File[]. */
	class FileTableModel extends AbstractTableModel {

	    private File[] files;
	    private FileSystemView fileSystemView = FileSystemView.getFileSystemView();
	    private String[] columns = {"File", "Path name", "Size", "Last Modified"};

	    FileTableModel() {
	        this(new File[0]);
	    }

	    FileTableModel(File[] files) {
	        this.files = files;
	    }

	    public Object getValueAt(int row, int column) {
	        File file = files[row];
	        switch (column) {
	            case 0:
	                return fileSystemView.getSystemDisplayName(file);
	            case 1:
	                return file.getPath();
	            case 2:
	                return file.length();
	            case 3:
	                return file.lastModified();
	            default:
	                System.err.println("Logic Error");
	        }
	        return "";
	    }

	    public int getColumnCount() {
	        return columns.length;
	    }
	    
	    public Class <?> getColumnClass (int column) {
	    	switch (column) {
	    		case 0:
	    		case 1:
	    		case 2: return Long.class;
	    		case 3: return Date.class;
	    	}
	    	return String.class;
	    }

	    public String getColumnName(int column) {
	        return columns[column];
	    }

	    public int getRowCount() {
	        return files.length;
	    }

	    public File getFile(int row) {
	        return files[row];
	    }

	    public void setFiles(File[] files) {
	        this.files = files;
	        fireTableDataChanged();
	    }
	}
	
	// Update the file details for the selected file
		private void setFileDetails (File file) {
			
			currentFile = file;
			fileName.setText(fileSystemView.getSystemDisplayName(file));
			path.setText(file.getPath());
			date.setText(new Date(file.lastModified()).toString());
			size.setText(file.length() + " bytes");
			
			gui_frame.repaint();
			
		}
		
		/*
		 * END BLOCK
		 */
	
	
	public JTree getJTree() {return tree;}
	public JScrollPane getFolderTree() {return folder_tree;}
	public static ConnoisseurGUI getInstance() {return instance;}
	public static FileManager getFileManager() {return fileManager;}
	public static TagManager getTagManager() {return tagManager;}
	public JScrollPane getFolderContents() {return folder_contents;}
	public String getDefaultDir() {return default_dir;}
	public JTable getDirContents() {return dir_contents;}
	public String getCurrentDir() {return current_dir;}
	public String getSelectedFile() {return selected_file;}
	public JLabel getFolderContentsLabel() {return folder_contents_label;}

	public void setSelectedFile(String _file) {this.selected_file = _file;}
}
