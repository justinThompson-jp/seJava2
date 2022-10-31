package Connoisseur;

/*
*	@authors: Justin Thompson, Jonathan Vallejo
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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ConnoisseurGUI {

	private JFrame frame;
	private JTable table;
	private DefaultTableModel model = new DefaultTableModel();

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
		
		CMenuBar menuBar = new CMenuBar();
		frame.setJMenuBar(menuBar);
		
		CToolBar toolBar = new CToolBar(0, 0, 704, 25);
		frame.getContentPane().add(toolBar);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setBounds(130, 280, 564, 128);
		frame.getContentPane().add(splitPane);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 27, 120, 381);
		frame.getContentPane().add(scrollPane);
		
		JTree tree = new JTree();
		tree.setModel(new FileSystemModel(new File("C:\\")));
		/* Populates tree with given directory
		 * TODO: Get rid of hardcoded directory name
		 */
		scrollPane.setViewportView(tree);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(130, 36, 564, 233);
		frame.getContentPane().add(scrollPane_1);
		
		ViewDirectory dir = new ViewDirectory();
		dir.Directory();
		
		
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
		scrollPane_1.setViewportView(table);
	}
}
