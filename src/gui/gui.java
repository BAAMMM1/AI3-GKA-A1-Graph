package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;

import org.graphstream.graph.Graph;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

import model.fileExtensionSystem.FileExtension;
import model.fileExtensionSystem.GraphFileExtensionHandler;
import javax.swing.JScrollPane;

public class gui {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// Look and Fell
		try {
			UIManager.setLookAndFeel(
			        UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gui window = new gui();
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
	public gui() {
		initialize();
	}

	Graph graph;
	Viewer viewer;
	View view;
	JPanel panel_graph;
	
	boolean autolayout = true;
	List<String> graphAsText;
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

		FileExtension fileHandler = new GraphFileExtensionHandler();
		
		graph = fileHandler.loadGraph("db/examples/graph01.graph");
		graphAsText = fileHandler.loadFile("db/examples/graph01.graph");
		
		// Graphstream integration in die GUI
		 // create a view *without* a JFrame
		viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
	    view = viewer.addDefaultView(false);
	    viewer.enableAutoLayout();

		
		
		
		
		
		frame = new JFrame();		
		
		JTextArea textArea = new JTextArea();	
		
		frame.setBounds(100, 100, 1280, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("GKA Graphomat");
	
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(192, 192, 192)));
		
		JButton btnLoadGraph = new JButton("Load Graph");
		btnLoadGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 // JFileChooser-Objekt erstellen
		        JFileChooser chooser = new JFileChooser();
		        // Dialog zum Oeffnen von Dateien anzeigen		        
		        int rueckgabeWert = chooser.showOpenDialog(null);
		        
		        /* Abfrage, ob auf "Öffnen" geklickt wurde */
		        if(rueckgabeWert == JFileChooser.APPROVE_OPTION)
		        {
		             // Ausgabe der ausgewaehlten Datei
		            System.out.println("Die zu öffnende Datei ist: " +
		                  chooser.getSelectedFile().getName());
		            System.out.println("Die zu öffnende Datei ist: " +
			                  chooser.getSelectedFile().getPath());
		            graph = fileHandler.loadGraph(chooser.getSelectedFile().getPath());
		            
		            graphAsText = fileHandler.loadFile(chooser.getSelectedFile().getPath());
		            String textAreaString = null;
		    		for(int i = 0; i<= graphAsText.size()-1; i++ ){
		    			textAreaString = textAreaString + graphAsText.get(i).toString() + "\n";
		    		}
		    		textArea.setText(textAreaString);
		            
		            viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
		            
		    	    view = viewer.addDefaultView(false);
		    	    viewer.enableAutoLayout();
		    	    panel_graph.removeAll();
		    	    panel_graph.add((Component) view);
		    	    panel_graph.isDisplayable();
		    	    panel_graph.revalidate(); 
		    	    panel_graph.repaint();
		        }
			}
		});
		
		JButton btnNewButton = new JButton("Save Graph");
		
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// JFileChooser-Objekt erstellen
		        JFileChooser chooser = new JFileChooser();
		        // Dialog zum Oeffnen von Dateien anzeigen
		        int rueckgabeWert = chooser.showSaveDialog(null);
		        
		        /* Abfrage, ob auf "Öffnen" geklickt wurde */
		        if(rueckgabeWert == JFileChooser.APPROVE_OPTION)
		        {
		             // Ausgabe der ausgewaehlten Datei
		            System.out.println("Die zu speichernde Datei ist: " +
		                  chooser.getSelectedFile().getName());
		            System.out.println("Die zu speichernde Datei ist: " +
			                  chooser.getSelectedFile().getPath());
		            fileHandler.saveGraph(graph, chooser.getSelectedFile().getPath());
		            
	
		        }
			}
		});
		
		JButton btnAutolayout = new JButton("AutoLayout");
		btnAutolayout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(autolayout == true){
					autolayout = false;
					viewer.disableAutoLayout();
				} else {
					autolayout = true;
					viewer.enableAutoLayout();
				}
			}
		});
		
		panel_graph = new JPanel();
		panel_graph.setBorder(new LineBorder(Color.LIGHT_GRAY));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(Color.LIGHT_GRAY));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(Color.LIGHT_GRAY));
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 321, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 321, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panel_graph, GroupLayout.DEFAULT_SIZE, 917, Short.MAX_VALUE)
					.addGap(6))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
							.addContainerGap())
						.addComponent(panel_graph, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 670, Short.MAX_VALUE)))
		);
		
		JLabel lblNewLabel = new JLabel("Algorithmen");
		
		JComboBox comboBox = new JComboBox();
		
		JButton btnNewButton_2 = new JButton("Anzeigen");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(comboBox, 0, 299, Short.MAX_VALUE)
						.addComponent(lblNewLabel)
						.addComponent(btnNewButton_2, GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton_2)
					.addContainerGap(12, Short.MAX_VALUE))
		);
		panel_2.setLayout(gl_panel_2);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(10)
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnNewButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
								.addComponent(btnLoadGraph, GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)))
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnAutolayout, GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(5)
					.addComponent(btnLoadGraph)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnAutolayout)
					.addGap(53))
		);
		panel.setLayout(gl_panel);
		
			
		String textAreaString = null;
		for(int i = 0; i<= graphAsText.size()-1; i++ ){
			textAreaString = textAreaString + graphAsText.get(i).toString() + "\n";
		}
		textArea.setText(textAreaString);
		
		JButton btnNewButton_1 = new JButton("Graphanzeige aktualisieren");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<String> fromTextArex = new ArrayList<String>();
		
				String text = textArea.getText();
				while(text.length()>0){
					fromTextArex.add(text.substring(0, text.indexOf("\n")));
					text = text.substring(text.indexOf("\n")+1);
				}				
				System.out.println(fromTextArex.toString());
				graph = fileHandler.loadGraphFromList(fromTextArex);
				viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
		    	view = viewer.addDefaultView(false);
		    	viewer.enableAutoLayout();
		    	panel_graph.removeAll();
		    	panel_graph.add((Component) view);
		    	panel_graph.isDisplayable();
		    	panel_graph.revalidate(); 
		    	panel_graph.repaint();
				
			}
		});
		
		JLabel lblGraph = new JLabel("Graphbuilder:");
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
						.addComponent(lblGraph)
						.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 299, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(11)
					.addComponent(lblGraph)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton_1)
					.addGap(11))
		);
		
		
		scrollPane.setViewportView(textArea);		
		panel_1.setLayout(gl_panel_1);
		
		panel_graph.add((Component) view);
		panel_graph.setLayout(new GridLayout(1, 0, 0, 0));

		
		frame.getContentPane().setLayout(groupLayout);
		
		/**/
		
		
		
		
		
	}
}
