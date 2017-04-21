package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;

import org.graphstream.graph.Graph;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

import model.algorithmusSystem.Dijkstra.Dijkstra;
import model.algorithmusSystem.breadthFirstSearch.BreadthFirstSearch2;
import model.fileExtensionSystem.FileExtension;
import model.fileExtensionSystem.GraphFileExtensionHandler;

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
	
	JTextArea textArea;
	
	boolean autolayout = true;
	List<String> graphAsText;
	private JTextField textFieldSource;
	private JTextField textFieldTarget;
	private JTextField textField;
	private JTextField textField_1;
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

		FileExtension fileHandler = new GraphFileExtensionHandler();
		
		//test Graph
		graph = fileHandler.loadGraph("db/examples/dijkstra.graph");
		graphAsText = fileHandler.loadFile("db/examples/dijkstra.graph");
		
		
		
		
		
		frame = new JFrame();		
		
		textArea = new JTextArea();	
		
		frame.setBounds(100, 100, 1280, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Headline
		frame.setTitle("GKA-A1-Graph");
	
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(192, 192, 192)));
		

		
		this.setGrapgBuilderTextArea();
		
		
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
		            setGraphToPanel();
		            
		            graphAsText = fileHandler.loadFile(chooser.getSelectedFile().getPath());		            
		            setGrapgBuilderTextArea();
		            
		            
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
		panel_graph.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(Color.LIGHT_GRAY));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(Color.LIGHT_GRAY));
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(Color.LIGHT_GRAY));
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new LineBorder(Color.LIGHT_GRAY));
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
							.addComponent(panel, GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)))
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_graph, GroupLayout.DEFAULT_SIZE, 908, Short.MAX_VALUE)
						.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 908, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(panel_graph, GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
								.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
							.addGap(10))))
		);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		JLabel lblNewLabel_4 = new JLabel("Ausgabe:");
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
						.addComponent(lblNewLabel_4))
					.addContainerGap())
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_4.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_4)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		JTextArea textArea_1 = new JTextArea();
		scrollPane_1.setViewportView(textArea_1);
		panel_4.setLayout(gl_panel_4);
		
		JLabel lblNewLabel_1 = new JLabel("Dijkstra-Algorithmus");
		
		textField = new JTextField();
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		
		JButton btnNewButton_2 = new JButton("DSF - Anzeigen");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				// TODO Prüfen ob das hier auch richtige Knoten sind
				String source = textField.getText();
				String target = textField_1.getText();

				
				Dijkstra djk = new Dijkstra(graph);
				graph = djk.runStart(graph.getNode(source), graph.getNode(target));
				textArea_1.setText("Dijkstra-Algorithmus:\n" + djk.getShortestPath().toString());
				
				setGraphToPanel();
			}
		});
		
		JLabel lblNewLabel_2 = new JLabel("Source:");
		
		JLabel lblNewLabel_3 = new JLabel("Target:");
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_1)
						.addGroup(Alignment.TRAILING, gl_panel_3.createSequentialGroup()
							.addComponent(lblNewLabel_2)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.TRAILING, gl_panel_3.createSequentialGroup()
							.addComponent(lblNewLabel_3)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnNewButton_2, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_2))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_3))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnNewButton_2)
					.addContainerGap(12, Short.MAX_VALUE))
		);
		panel_3.setLayout(gl_panel_3);
		
		JLabel lblNewLabel = new JLabel("Breadth-first search");
		
		
		textFieldSource = new JTextField();
		textFieldSource.setColumns(10);
		
		textFieldTarget = new JTextField();
		textFieldTarget.setColumns(10);		
		
		JButton btnNewButton_3 = new JButton("BFS - Anzeigen");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Prüfen ob das hier auch richtige Knoten sind
				String source = textFieldSource.getText();
				String target = textFieldTarget.getText();
				
				/*
				BreadthFirstSearch bfs = new BreadthFirstSearch(graph);
				bfs.shortestPath(source, target);
				*/
				
				BreadthFirstSearch2 bfs2 = new BreadthFirstSearch2(graph);
				graph = bfs2.stpAlgorithmus(graph.getNode(source), graph.getNode(target));
				
				setGraphToPanel();
				
			}
		});
		
		JLabel lblStartknoten = new JLabel("Source:");
		
		JLabel lblZielknoten = new JLabel("Target:");
		
	
		
		
		
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel)
								.addComponent(btnNewButton_3, GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE))
							.addContainerGap())
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblZielknoten)
								.addComponent(lblStartknoten))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(textFieldTarget, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
								.addComponent(textFieldSource, GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE))
							.addContainerGap())))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addGap(5)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(textFieldSource, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblStartknoten))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(textFieldTarget, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblZielknoten))
					.addPreferredGap(ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
					.addComponent(btnNewButton_3)
					.addContainerGap())
		);
		panel_2.setLayout(gl_panel_2);
		
		JLabel lblMenl = new JLabel("Options");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(btnLoadGraph, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnAutolayout, GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE))
						.addComponent(lblMenl))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(9)
					.addComponent(lblMenl)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnLoadGraph)
						.addComponent(btnAutolayout)
						.addComponent(btnNewButton))
					.addGap(69))
		);
		panel.setLayout(gl_panel);
		
		

		
		JButton btnNewButton_1 = new JButton("Graphanzeige aktualisieren");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<String> fromTextArex = new ArrayList<String>();
		
				String text = textArea.getText();
				while(text.length()>0){
					if(text.indexOf("\n") != -1){						
						fromTextArex.add(text.substring(0, text.indexOf("\n")));
						text = text.substring(text.indexOf("\n")+1);
					} else {
						fromTextArex.add(text);
						break;
					}
					
				}				
				System.out.println(fromTextArex.toString());
				graph = fileHandler.loadGraphFromList(fromTextArex);

				setGraphToPanel();
				
			}
		});
		
		JLabel lblGraph = new JLabel("Console:");
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
						.addComponent(lblGraph)
						.addComponent(btnNewButton_1, GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(11)
					.addComponent(lblGraph)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton_1)
					.addContainerGap())
		);
		
		
		scrollPane.setViewportView(textArea);		
		panel_1.setLayout(gl_panel_1);
		
		
		

		
		setGraphToPanel();

		
		frame.getContentPane().setLayout(groupLayout);
		
		/**/
		
		
		
		
		
	}
	
	public void setGrapgBuilderTextArea(){
		String textAreaString = "";
		for(int i = 0; i<= graphAsText.size()-1; i++ ){
			textAreaString = textAreaString + graphAsText.get(i).toString() + "\n";
		}
		textArea.setText(textAreaString);
	}
	
	public void setGraphToPanel(){
		this.initializeGraph();
		viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);         
 	    view = viewer.addDefaultView(false);
 	    viewer.enableAutoLayout(); 	    
 	    panel_graph.removeAll();
 	    panel_graph.add((Component) view);
 	    panel_graph.revalidate(); 
 	    panel_graph.repaint(); 	   
	}
	
	public void initializeGraph(){
		// background
		//graph.addAttribute("ui.stylesheet", "graph { fill-color: red; }");
		
		graph.addAttribute("ui.quality");
		
		// Wendes Antialiasing zur Kantenglettung auf den Graph an
		graph.addAttribute("ui.antialias");
		
		// Knotennamen ausblenden
		//graph.addAttribute("ui.stylesheet", "node {	text-mode: hidden;}");
		
		graph.addAttribute("ui.stylesheet", "node {	size: 10px, 10px; text-size: 14px; text-alignment: under; text-style: bold;}");
		
		
	}
}
