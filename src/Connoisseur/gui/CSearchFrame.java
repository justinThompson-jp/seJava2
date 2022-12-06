package Connoisseur.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import Connoisseur.ConnoisseurGUI;


/**
 * Code adapted from:
 * https://kodejava.org/how-do-i-create-a-scrollable-jtable-component/
 * 
 * @author jonat
 *
 */
public class CSearchFrame extends JPanel {
	/**
	 * Auto generated serial version ID
	 */
	private static final long serialVersionUID = 7092375012252323832L;
	
	private JFrame frame;
	private JTable table;
	private JProgressBar progressBar;
	
	private String frameTitle = "Search Results";
	
	public CSearchFrame() {
		initializeUI();
	}
	
	public CSearchFrame(String title) {
		this.frameTitle = title;
		initializeUI();
	}

	public void showFrame() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle(frameTitle);
		frame.setContentPane(this);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(ConnoisseurGUI.getInstance().getGUI());
	}

	private void initializeUI() {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(500, 250));

		DefaultTableModel tableModel = new DefaultTableModel() {
			/**
			 * Auto generated serial version ID
			 */
			private static final long serialVersionUID = -4320293916449487858L;

			@Override
			public Class<?> getColumnClass(int column) {
				if (column == 0) {
					return ImageIcon.class;
				} else {
					return Object.class;
				}
			}
			
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tableModel.addColumn("");
		tableModel.addColumn("File Name");
		tableModel.addColumn("File Path");

		table = new JTable(tableModel);

		// Turn off JTable's auto resize so that JScrollPane will show a horizontal
		// scroll bar.
//        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setShowHorizontalLines(false);
		table.setShowVerticalLines(true);
		
		table.getColumnModel().getColumn(0).setMinWidth(20);
		table.getColumnModel().getColumn(0).setMaxWidth(20);
		table.getColumnModel().getColumn(1).setPreferredWidth((table.getColumnModel().getColumn(0).getPreferredWidth() - 50));
		table.getColumnModel().getColumn(2).setPreferredWidth((table.getColumnModel().getColumn(1).getPreferredWidth() * 2));

		JScrollPane pane = new JScrollPane(table);
		add(pane, BorderLayout.CENTER);

		progressBar = new JProgressBar();
		progressBar.setForeground(new Color(50, 205, 50));
		progressBar.setStringPainted(true);
		progressBar.setString("Scanning files..");
		progressBar.setIndeterminate(false);
		add(progressBar, BorderLayout.SOUTH);
	}
	
	public void setBarProgress(String s) {
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	try {
			        progressBar.setString(s);
			        progressBar.update(progressBar.getGraphics());
		    	} catch (Exception ex) {}
		    }
		});
	}
	
	public void addRow(Object[] row) {
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	try {
		    		((DefaultTableModel)table.getModel()).addRow(row);
		    	} catch (Exception ex) {
		    		System.out.println("An error happened while adding: " + row.toString() + " to the table.");
		    	}
		    }
		});
	}

	public JTable getTable() {
		return table;
	}
	
	public JProgressBar getProgressBar() {
		return progressBar;
	}
}