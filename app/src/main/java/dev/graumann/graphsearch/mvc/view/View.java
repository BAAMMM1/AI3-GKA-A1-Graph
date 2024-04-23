package dev.graumann.graphsearch.mvc.view;

import java.awt.Color;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.graphstream.graph.Graph;
import org.graphstream.ui.view.Viewer;
import java.awt.GridLayout;
import java.awt.Font;
import javax.swing.JCheckBoxMenuItem;
import java.awt.SystemColor;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class View {
	
	private JFrame frameMain;
	private JPanel panelGraphStream;
	private JTextField textField;
	private JTextField textField_1;
	private JPanel panelDijkstra;
	private JScrollPane scrollPaneAusgabe;		
	private JTextArea textAreaResult;
	private JLabel labelDijkstra;
	private JButton btnDijkstra;
	private JLabel labelDijkstraSource;		
	private JLabel labelDijkstraTarget;
	private GroupLayout gl_panelDijkstra;
	
	private Graph graph;
	private Viewer viewer;
	private org.graphstream.ui.view.View view;
	private boolean autolayout = true;
	private List<String> graphAsText;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenu mnHelp;
	private JMenuItem mntmLoadGraph;
	private JMenuItem mntmSaveGraph;
	private JMenu mnSettings;
	private JRadioButtonMenuItem rdbtnmntmAutolayout;
	private JLayeredPane layerGraph;
	private JPanel panel;
	private JPanel panel_1;
	private JScrollPane scrollPane;
	private JButton btnConsole;
	private JPanel panel_2;
	private JLabel label;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;
	private JTextField weightSize;
	private JTextField edgeSize;
	private JTextField nodeSize;
	private JComboBox comboBoxGenerator;
	private JPanel panel_3;
	private JLabel label_4;
	private JLabel label_5;
	private JLabel label_6;
	private JButton btnSTP;
	private JTextField target;
	private JTextField source;
	private JComboBox comboBoxShortestPath;
	private JPanel panel_4;
	private JLabel label_7;
	private JButton btnMST;
	private JComboBox comboBoxMST;
	private JTextArea textAreaConsole;
	private JButton btnGenerator;
	private JTabbedPane tabbedPane;
	private JMenu mnModifier;
	private JMenuItem mntmUndirectedDirected;
	private JMenuItem mntmAboutGkaagraph;
	private JButton btnEuler;
	private JComboBox comboBoxEuler;
	private JTabbedPane tabbedPane_1;
	private JLayeredPane layeredPane;
	private JLayeredPane layeredPane_1;
	private JPanel panel_6;
	private JPanel panel_7;
	private JPanel panel_8;
	private JMenuItem mntmClose;

	public View() {
		// Look and Fell in Controller? Oder View?
		try {
			UIManager.setLookAndFeel(
			        UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		initialize();
	}	
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frameMain = new JFrame();	
		frameMain.getContentPane().setBackground(new Color(240, 240, 240));
		panelDijkstra = new JPanel();
		labelDijkstra = new JLabel("Dijkstra-Algorithmus");
		textField = new JTextField();
		textField_1 = new JTextField();
		btnDijkstra = new JButton("DSF - Anzeigen");
		labelDijkstraSource = new JLabel("Source:");		
		labelDijkstraTarget = new JLabel("Target:");
		gl_panelDijkstra = new GroupLayout(panelDijkstra);
		
		
		gl_panelDijkstra.setHorizontalGroup(
			gl_panelDijkstra.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDijkstra.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelDijkstra.createParallelGroup(Alignment.LEADING)
						.addComponent(labelDijkstra)
						.addGroup(Alignment.TRAILING, gl_panelDijkstra.createSequentialGroup()
							.addGroup(gl_panelDijkstra.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnDijkstra, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
								.addGroup(Alignment.LEADING, gl_panelDijkstra.createSequentialGroup()
									.addComponent(labelDijkstraTarget)
									.addPreferredGap(ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
									.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panelDijkstra.createSequentialGroup()
									.addComponent(labelDijkstraSource)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(textField, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)))
							.addGap(42)))
					.addContainerGap())
		);
		gl_panelDijkstra.setVerticalGroup(
			gl_panelDijkstra.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDijkstra.createSequentialGroup()
					.addContainerGap()
					.addComponent(labelDijkstra)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelDijkstra.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(labelDijkstraSource))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelDijkstra.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(labelDijkstraTarget))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnDijkstra)
					.addContainerGap(18, Short.MAX_VALUE))
		);
		
		
		// TODO Headline über den Konstruktor
		frameMain.setTitle("GKA-A1-GraphSearch");
		frameMain.setBounds(100, 100, 1280, 866);
		frameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panelDijkstra.setLayout(gl_panelDijkstra);
		panelDijkstra.setBorder(new LineBorder(Color.LIGHT_GRAY));
		
		textField.setColumns(10);	
		textField_1.setColumns(10);
		
		JPanel panel_9 = new JPanel();
		GroupLayout groupLayout = new GroupLayout(frameMain.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_9, GroupLayout.DEFAULT_SIZE, 1264, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_9, GroupLayout.DEFAULT_SIZE, 806, Short.MAX_VALUE)
		);
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Menu", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		tabbedPane_1 = new JTabbedPane(JTabbedPane.BOTTOM);
		
		layeredPane = new JLayeredPane();
		layeredPane.setBackground(new Color(240, 240, 240));
		tabbedPane_1.addTab("Algorithm", null, layeredPane, null);
		
		panel_6 = new JPanel();
		GroupLayout gl_layeredPane = new GroupLayout(layeredPane);
		gl_layeredPane.setHorizontalGroup(
			gl_layeredPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_6, GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
		);
		gl_layeredPane.setVerticalGroup(
			gl_layeredPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_6, GroupLayout.DEFAULT_SIZE, 722, Short.MAX_VALUE)
		);
		
		panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Generator", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBackground(new Color(240, 240, 240));
		
		label = new JLabel("Typ:");
		label.setForeground(Color.BLACK);
		
		label_1 = new JLabel("Nodes:");
		label_1.setForeground(Color.BLACK);
		
		label_2 = new JLabel("Edges:");
		label_2.setForeground(Color.BLACK);
		
		label_3 = new JLabel("Weight:");
		label_3.setForeground(Color.BLACK);
		
		weightSize = new JTextField();
		weightSize.setColumns(10);
		
		edgeSize = new JTextField();
		edgeSize.setColumns(10);
		
		nodeSize = new JTextField();
		nodeSize.setColumns(10);
		
		comboBoxGenerator = new JComboBox();
		
		btnGenerator = new JButton("Generieren");
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(label, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(weightSize, Alignment.LEADING)
								.addComponent(edgeSize, Alignment.LEADING)
								.addComponent(nodeSize, Alignment.LEADING)
								.addComponent(comboBoxGenerator, Alignment.LEADING, 0, 127, Short.MAX_VALUE)))
						.addComponent(btnGenerator, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(label)
						.addComponent(comboBoxGenerator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(nodeSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_1))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(edgeSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_2))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(weightSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_3))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnGenerator)
					.addContainerGap(17, Short.MAX_VALUE))
		);
		panel_2.setLayout(gl_panel_2);
		
		panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Shortest Path", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		panel_3.setBackground(new Color(240, 240, 240));
		
		label_4 = new JLabel("Source:");
		label_4.setForeground(Color.BLACK);
		
		label_5 = new JLabel("Target:");
		label_5.setForeground(Color.BLACK);
		
		label_6 = new JLabel("Typ:");
		label_6.setForeground(Color.BLACK);
		
		btnSTP = new JButton("Berechnen");
		btnSTP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		target = new JTextField();
		target.setColumns(10);
		
		source = new JTextField();
		source.setColumns(10);
		
		comboBoxShortestPath = new JComboBox();
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 237, Short.MAX_VALUE)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addComponent(label_4)
						.addComponent(label_5)
						.addComponent(label_6, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnSTP, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(target, Alignment.TRAILING)
						.addComponent(source, Alignment.TRAILING)
						.addComponent(comboBoxShortestPath, Alignment.TRAILING, 0, 127, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 141, Short.MAX_VALUE)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(comboBoxShortestPath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_6))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(source, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_4))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(target, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_5))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSTP)
					.addGap(20))
		);
		panel_3.setLayout(gl_panel_3);
		
		panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Minimal spanning Tree", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		panel_4.setBackground(new Color(240, 240, 240));
		
		label_7 = new JLabel("Typ:");
		label_7.setForeground(Color.BLACK);
		
		btnMST = new JButton("Anzeigen");
		
		comboBoxMST = new JComboBox();		
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 237, Short.MAX_VALUE)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_7, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED, 42, Short.MAX_VALUE)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnMST, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(comboBoxMST, Alignment.TRAILING, 0, 126, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGap(0, 91, Short.MAX_VALUE)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_7)
						.addComponent(comboBoxMST, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnMST)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_4.setLayout(gl_panel_4);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "EulerCircle", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_5.setBackground(SystemColor.menu);
		
		JLabel label_8 = new JLabel("Typ:");
		label_8.setForeground(Color.BLACK);
		
		btnEuler = new JButton("Anzeigen");
		
		comboBoxEuler = new JComboBox();
		GroupLayout gl_panel_5 = new GroupLayout(panel_5);
		gl_panel_5.setHorizontalGroup(
			gl_panel_5.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 225, Short.MAX_VALUE)
				.addGroup(gl_panel_5.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_8, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED, 32, Short.MAX_VALUE)
					.addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnEuler, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(comboBoxEuler, Alignment.TRAILING, 0, 126, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_5.setVerticalGroup(
			gl_panel_5.createParallelGroup(Alignment.LEADING)
				.addGap(0, 91, Short.MAX_VALUE)
				.addGroup(gl_panel_5.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_5.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_8)
						.addComponent(comboBoxEuler, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnEuler)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_5.setLayout(gl_panel_5);
		GroupLayout gl_panel_6 = new GroupLayout(panel_6);
		gl_panel_6.setHorizontalGroup(
			gl_panel_6.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_6.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_6.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_5, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
						.addComponent(panel_4, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
						.addComponent(panel_3, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
						.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_6.setVerticalGroup(
			gl_panel_6.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_6.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_5, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(194, Short.MAX_VALUE))
		);
		panel_6.setLayout(gl_panel_6);
		layeredPane.setLayout(gl_layeredPane);
		
		layeredPane_1 = new JLayeredPane();
		tabbedPane_1.addTab("Console", null, layeredPane_1, null);
		
		panel_7 = new JPanel();
		GroupLayout gl_layeredPane_1 = new GroupLayout(layeredPane_1);
		gl_layeredPane_1.setHorizontalGroup(
			gl_layeredPane_1.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_7, GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
		);
		gl_layeredPane_1.setVerticalGroup(
			gl_layeredPane_1.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_7, GroupLayout.DEFAULT_SIZE, 761, Short.MAX_VALUE)
		);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192)), "Console", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		panel_1.setBackground(new Color(240, 240, 240));
		
		scrollPane = new JScrollPane();
		
		btnConsole = new JButton("Graphanzeige aktualisieren");
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 237, Short.MAX_VALUE)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
						.addComponent(btnConsole, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 234, Short.MAX_VALUE)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnConsole)
					.addContainerGap())
		);
		
		textAreaConsole = new JTextArea();
		scrollPane.setViewportView(textAreaConsole);
		panel_1.setLayout(gl_panel_1);
		GroupLayout gl_panel_7 = new GroupLayout(panel_7);
		gl_panel_7.setHorizontalGroup(
			gl_panel_7.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_7.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel_7.setVerticalGroup(
			gl_panel_7.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_7.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
					.addContainerGap())
		);
		panel_7.setLayout(gl_panel_7);
		layeredPane_1.setLayout(gl_layeredPane_1);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane_1, GroupLayout.PREFERRED_SIZE, 255, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(tabbedPane_1, GroupLayout.DEFAULT_SIZE, 750, Short.MAX_VALUE)
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		
		panel_8 = new JPanel();
		panel_8.setBorder(new TitledBorder(null, "View", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		layerGraph = new JLayeredPane();
		layerGraph.setForeground(Color.LIGHT_GRAY);
		layerGraph.setBackground(Color.LIGHT_GRAY);
		tabbedPane.addTab("Graph", null, layerGraph, null);
		panelGraphStream = new JPanel();
		panelGraphStream.setLayout(new GridLayout(1, 0, 0, 0));
		GroupLayout gl_layerGraph = new GroupLayout(layerGraph);
		gl_layerGraph.setHorizontalGroup(
			gl_layerGraph.createParallelGroup(Alignment.LEADING)
				.addComponent(panelGraphStream, GroupLayout.DEFAULT_SIZE, 916, GroupLayout.DEFAULT_SIZE)
		);
		gl_layerGraph.setVerticalGroup(
			gl_layerGraph.createParallelGroup(Alignment.LEADING)
				.addComponent(panelGraphStream, GroupLayout.DEFAULT_SIZE, 533, GroupLayout.DEFAULT_SIZE)
		);
		layerGraph.setLayout(gl_layerGraph);
		scrollPaneAusgabe = new JScrollPane();
		scrollPaneAusgabe.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPaneAusgabe.setViewportBorder(null);
		textAreaResult = new JTextArea();
		textAreaResult.setEditable(false);
		textAreaResult.setBorder(new LineBorder(Color.WHITE, 5));
		
		scrollPaneAusgabe.setViewportView(textAreaResult);
		GroupLayout gl_panel_8 = new GroupLayout(panel_8);
		gl_panel_8.setHorizontalGroup(
			gl_panel_8.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_8.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_8.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPaneAusgabe, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 915, Short.MAX_VALUE)
						.addComponent(tabbedPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 915, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_8.setVerticalGroup(
			gl_panel_8.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_8.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 652, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPaneAusgabe, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		panel_8.setLayout(gl_panel_8);
		GroupLayout gl_panel_9 = new GroupLayout(panel_9);
		gl_panel_9.setHorizontalGroup(
			gl_panel_9.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_9.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panel_8, GroupLayout.DEFAULT_SIZE, 947, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel_9.setVerticalGroup(
			gl_panel_9.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_9.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_9.createParallelGroup(Alignment.BASELINE)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panel_8, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap())
		);
		panel_9.setLayout(gl_panel_9);
		frameMain.getContentPane().setLayout(groupLayout);
		
		menuBar = new JMenuBar();
		menuBar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		frameMain.setJMenuBar(menuBar);
		
		mnFile = new JMenu("File");
		mnFile.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		menuBar.add(mnFile);
		
		mntmLoadGraph = new JMenuItem("Load Graph...");
		mnFile.add(mntmLoadGraph);
		
		mntmSaveGraph = new JMenuItem("Save Graph...");
		mnFile.add(mntmSaveGraph);
		
		mntmClose = new JMenuItem("Close");
		mnFile.add(mntmClose);
		
		mnSettings = new JMenu("Settings");
		menuBar.add(mnSettings);
		
		rdbtnmntmAutolayout = new JRadioButtonMenuItem("Autolayout");
		mnSettings.add(rdbtnmntmAutolayout);
		
		mnModifier = new JMenu("Modifier");
		menuBar.add(mnModifier);
		
		mntmUndirectedDirected = new JMenuItem("undirected -> directed");
		mnModifier.add(mntmUndirectedDirected);
		
		mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		mntmAboutGkaagraph = new JMenuItem("About GKA-A1-Graph");
		mnHelp.add(mntmAboutGkaagraph);
		
		frameMain.setVisible(true);				
	}
	
	


	public JMenuItem getMntmUndirectedDirected() {
		return mntmUndirectedDirected;
	}


	public void setMntmUndirectedDirected(JMenuItem mntmUndirectedDirected) {
		this.mntmUndirectedDirected = mntmUndirectedDirected;
	}


	public JMenuItem getMntmAboutGkaagraph() {
		return mntmAboutGkaagraph;
	}


	public void setMntmAboutGkaagraph(JMenuItem mntmAboutGkaagraph) {
		this.mntmAboutGkaagraph = mntmAboutGkaagraph;
	}


	public JFrame getFrameMain() {
		return frameMain;
	}


	public void setFrameMain(JFrame frameMain) {
		this.frameMain = frameMain;
	}


	public JPanel getPanelGraphStream() {
		return panelGraphStream;
	}


	public void setPanelGraphStream(JPanel panelGraphStream) {
		this.panelGraphStream = panelGraphStream;
	}


	public JTextField getTextField() {
		return textField;
	}


	public void setTextField(JTextField textField) {
		this.textField = textField;
	}


	public JTextField getTextField_1() {
		return textField_1;
	}


	public void setTextField_1(JTextField textField_1) {
		this.textField_1 = textField_1;
	}


	public JPanel getPanelDijkstra() {
		return panelDijkstra;
	}


	public void setPanelDijkstra(JPanel panelDijkstra) {
		this.panelDijkstra = panelDijkstra;
	}





	public JScrollPane getScrollPaneAusgabe() {
		return scrollPaneAusgabe;
	}


	public void setScrollPaneAusgabe(JScrollPane scrollPaneAusgabe) {
		this.scrollPaneAusgabe = scrollPaneAusgabe;
	}


	public JTextArea getTextAreaResult() {
		return textAreaResult;
	}


	public void setTextAreaResult(JTextArea textAreaResult) {
		this.textAreaResult = textAreaResult;
	}


	public JLabel getLabelDijkstra() {
		return labelDijkstra;
	}


	public void setLabelDijkstra(JLabel labelDijkstra) {
		this.labelDijkstra = labelDijkstra;
	}


	public JButton getBtnDijkstra() {
		return btnDijkstra;
	}


	public void setBtnDijkstra(JButton btnDijkstra) {
		this.btnDijkstra = btnDijkstra;
	}


	public JLabel getLabelDijkstraSource() {
		return labelDijkstraSource;
	}


	public void setLabelDijkstraSource(JLabel labelDijkstraSource) {
		this.labelDijkstraSource = labelDijkstraSource;
	}


	public JLabel getLabelDijkstraTarget() {
		return labelDijkstraTarget;
	}


	public void setLabelDijkstraTarget(JLabel labelDijkstraTarget) {
		this.labelDijkstraTarget = labelDijkstraTarget;
	}


	public GroupLayout getGl_panelDijkstra() {
		return gl_panelDijkstra;
	}


	public void setGl_panelDijkstra(GroupLayout gl_panelDijkstra) {
		this.gl_panelDijkstra = gl_panelDijkstra;
	}


	public Graph getGraph() {
		return graph;
	}


	public void setGraph(Graph graph) {
		this.graph = graph;
	}


	public Viewer getViewer() {
		return viewer;
	}


	public void setViewer(Viewer viewer) {
		this.viewer = viewer;
	}


	public org.graphstream.ui.view.View getView() {
		return view;
	}


	public void setView(org.graphstream.ui.view.View view) {
		this.view = view;
	}


	public boolean isAutolayout() {
		return autolayout;
	}


	public void setAutolayout(boolean autolayout) {
		this.autolayout = autolayout;
	}


	public List<String> getGraphAsText() {
		return graphAsText;
	}


	public void setGraphAsText(List<String> graphAsText) {
		this.graphAsText = graphAsText;
	}


	public JMenuBar getMenuBar() {
		return menuBar;
	}


	public void setMenuBar(JMenuBar menuBar) {
		this.menuBar = menuBar;
	}


	public JMenu getMnFile() {
		return mnFile;
	}


	public void setMnFile(JMenu mnFile) {
		this.mnFile = mnFile;
	}


	public JMenu getMnHelp() {
		return mnHelp;
	}


	public void setMnHelp(JMenu mnHelp) {
		this.mnHelp = mnHelp;
	}


	public JMenuItem getMntmLoadGraph() {
		return mntmLoadGraph;
	}


	public void setMntmLoadGraph(JMenuItem mntmLoadGraph) {
		this.mntmLoadGraph = mntmLoadGraph;
	}


	public JMenuItem getMntmSaveGraph() {
		return mntmSaveGraph;
	}


	public void setMntmSaveGraph(JMenuItem mntmSaveGraph) {
		this.mntmSaveGraph = mntmSaveGraph;
	}


	public JMenu getMnSettings() {
		return mnSettings;
	}


	public void setMnSettings(JMenu mnSettings) {
		this.mnSettings = mnSettings;
	}


	public JRadioButtonMenuItem getRdbtnmntmAutolayout() {
		return rdbtnmntmAutolayout;
	}


	public void setRdbtnmntmAutolayout(JRadioButtonMenuItem rdbtnmntmAutolayout) {
		this.rdbtnmntmAutolayout = rdbtnmntmAutolayout;
	}


	public JLayeredPane getLayerGraph() {
		return layerGraph;
	}


	public void setLayerGraph(JLayeredPane layerGraph) {
		this.layerGraph = layerGraph;
	}





	public JPanel getPanel() {
		return panel;
	}


	public void setPanel(JPanel panel) {
		this.panel = panel;
	}


	public JPanel getPanel_1() {
		return panel_1;
	}


	public void setPanel_1(JPanel panel_1) {
		this.panel_1 = panel_1;
	}


	public JScrollPane getScrollPane() {
		return scrollPane;
	}


	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}


	public JButton getBtnConsole() {
		return btnConsole;
	}


	public void setBtnConsole(JButton btnConsole) {
		this.btnConsole = btnConsole;
	}


	public JPanel getPanel_2() {
		return panel_2;
	}


	public void setPanel_2(JPanel panel_2) {
		this.panel_2 = panel_2;
	}


	public JLabel getLabel() {
		return label;
	}


	public void setLabel(JLabel label) {
		this.label = label;
	}


	public JLabel getLabel_1() {
		return label_1;
	}


	public void setLabel_1(JLabel label_1) {
		this.label_1 = label_1;
	}


	public JLabel getLabel_2() {
		return label_2;
	}


	public void setLabel_2(JLabel label_2) {
		this.label_2 = label_2;
	}


	public JLabel getLabel_3() {
		return label_3;
	}


	public void setLabel_3(JLabel label_3) {
		this.label_3 = label_3;
	}


	public JTextField getWeightSize() {
		return weightSize;
	}


	public void setWeightSize(JTextField weightSize) {
		this.weightSize = weightSize;
	}


	public JTextField getEdgeSize() {
		return edgeSize;
	}


	public void setEdgeSize(JTextField edgeSize) {
		this.edgeSize = edgeSize;
	}


	public JTextField getNodeSize() {
		return nodeSize;
	}


	public void setNodeSize(JTextField nodeSize) {
		this.nodeSize = nodeSize;
	}


	public JComboBox getComboBoxGenerator() {
		return comboBoxGenerator;
	}


	public void setComboBoxGenerator(JComboBox comboBoxGenerator) {
		this.comboBoxGenerator = comboBoxGenerator;
	}


	public JPanel getPanel_3() {
		return panel_3;
	}


	public void setPanel_3(JPanel panel_3) {
		this.panel_3 = panel_3;
	}


	public JLabel getLabel_4() {
		return label_4;
	}


	public void setLabel_4(JLabel label_4) {
		this.label_4 = label_4;
	}


	public JLabel getLabel_5() {
		return label_5;
	}


	public void setLabel_5(JLabel label_5) {
		this.label_5 = label_5;
	}


	public JLabel getLabel_6() {
		return label_6;
	}


	public void setLabel_6(JLabel label_6) {
		this.label_6 = label_6;
	}


	public JButton getBtnSTP() {
		return btnSTP;
	}


	public void setBtnSTP(JButton btnSTP) {
		this.btnSTP = btnSTP;
	}


	public JTextField getTarget() {
		return target;
	}


	public void setTarget(JTextField target) {
		this.target = target;
	}


	public JTextField getSource() {
		return source;
	}


	public void setSource(JTextField source) {
		this.source = source;
	}


	public JComboBox getComboBoxShortestPath() {
		return comboBoxShortestPath;
	}


	public void setComboBoxShortestPath(JComboBox comboBoxShortestPath) {
		this.comboBoxShortestPath = comboBoxShortestPath;
	}


	public JPanel getPanel_4() {
		return panel_4;
	}


	public void setPanel_4(JPanel panel_4) {
		this.panel_4 = panel_4;
	}


	public JLabel getLabel_7() {
		return label_7;
	}


	public void setLabel_7(JLabel label_7) {
		this.label_7 = label_7;
	}


	public JButton getBtnMST() {
		return btnMST;
	}


	public void setBtnMST(JButton btnMST) {
		this.btnMST = btnMST;
	}


	public JComboBox getComboBoxMST() {
		return comboBoxMST;
	}


	public void setComboBoxMST(JComboBox comboBoxMST) {
		this.comboBoxMST = comboBoxMST;
	}


	public JTextArea getTextAreaConsole() {
		return textAreaConsole;
	}


	public void setTextAreaConsole(JTextArea textAreaConsole) {
		this.textAreaConsole = textAreaConsole;
	}


	public JButton getBtnGenerator() {
		return btnGenerator;
	}


	public void setBtnGenerator(JButton btnGenerator) {
		this.btnGenerator = btnGenerator;
	}


	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}


	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}


	public JButton getBtnEuler() {
		return btnEuler;
	}


	public JComboBox getComboBoxEuler() {
		return comboBoxEuler;
	}


	public JMenuItem getMntmClose() {
		return mntmClose;
	}


	public void setMntmClose(JMenuItem mntmClose) {
		this.mntmClose = mntmClose;
	}
	
	
}
