package mvc.view;

import java.awt.Color;
import java.awt.Component;
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
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;

import org.graphstream.graph.Graph;
import org.graphstream.ui.view.Viewer;

import mvc.model.algorithmusSystem.Dijkstra.Dijkstra;
import mvc.model.algorithmusSystem.breadthFirstSearch.BreadthFirstSearch;
import mvc.model.fileExtensionSystem.FileExtension;
import mvc.model.fileExtensionSystem.GraphFileExtensionHandler;

public class View {
	
	private JFrame frameMain;
	private JPanel panelGraphStream;
	private JTextArea textAreaConsole;
	
	private JTextField textFieldSource;
	private JTextField textFieldTarget;
	private JTextField textField;
	private JTextField textField_1;
	private JPanel panelOptions;
	private JButton btnSave;
	private JButton btnLoadGraph;
	private JButton btnAutolayout;
	private JPanel panelConsole;
	private JPanel panelBFS;
	private JPanel panelDijkstra;
	private JPanel panelAusgabe;	
	private JScrollPane scrollPaneAusgabe;		
	private JLabel labelAusgabe;
	private GroupLayout gl_panelAusgabe;
	private JTextArea textArea_1;
	private JLabel labelDijkstra;
	private JButton btnDijkstra;
	private JLabel labelDijkstraSource;		
	private JLabel labelDijkstraTarget;
	private GroupLayout gl_panelDijkstra;
	private JLabel labelBFS;		
	private JButton btnBFS;
	private JButton btnGraphanzeige;
	private JLabel labelConsole;
	private JScrollPane scrollPaneConsole;
	private GroupLayout gl_panelConsole;
	
	private Graph graph;
	private Viewer viewer;
	private org.graphstream.ui.view.View view;
	private boolean autolayout = true;
	private List<String> graphAsText;


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
		textAreaConsole = new JTextArea();
		panelOptions = new JPanel();
		btnLoadGraph = new JButton("Load Graph");	
		btnSave = new JButton("Save Graph");
		btnAutolayout = new JButton("AutoLayout");
		panelGraphStream = new JPanel();
		panelConsole = new JPanel();
		panelBFS = new JPanel();
		panelDijkstra = new JPanel();
		panelAusgabe = new JPanel();		
		scrollPaneAusgabe = new JScrollPane();		
		labelAusgabe = new JLabel("Ausgabe:");
		gl_panelAusgabe = new GroupLayout(panelAusgabe);
		textArea_1 = new JTextArea();
		labelDijkstra = new JLabel("Dijkstra-Algorithmus");
		textField = new JTextField();
		textField_1 = new JTextField();
		btnDijkstra = new JButton("DSF - Anzeigen");
		labelDijkstraSource = new JLabel("Source:");		
		labelDijkstraTarget = new JLabel("Target:");
		gl_panelDijkstra = new GroupLayout(panelDijkstra);
		labelBFS = new JLabel("Breadth-first search");		
		textFieldSource = new JTextField();
		textFieldTarget = new JTextField();
		btnBFS = new JButton("BFS - Anzeigen");
		btnGraphanzeige = new JButton("Graphanzeige aktualisieren");
		labelConsole = new JLabel("Console:");
		scrollPaneConsole = new JScrollPane();
		gl_panelConsole = new GroupLayout(panelConsole);
		
		
		
		frameMain.setBounds(100, 100, 1280, 720);
		frameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// TODO Headline über den Konstruktor
		frameMain.setTitle("GKA-A1-Graph");

		panelOptions.setBorder(new LineBorder(new Color(192, 192, 192)));
		
		panelGraphStream.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panelGraphStream.setLayout(new GridLayout(1, 0, 0, 0));
		

		
		panelConsole.setBorder(new LineBorder(Color.LIGHT_GRAY));
		
		
		panelBFS.setBorder(new LineBorder(Color.LIGHT_GRAY));
		
		
		panelDijkstra.setBorder(new LineBorder(Color.LIGHT_GRAY));
		
		
		panelAusgabe.setBorder(new LineBorder(Color.LIGHT_GRAY));
		GroupLayout groupLayout = new GroupLayout(frameMain.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
							.addComponent(panelOptions, GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(panelBFS, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(panelDijkstra, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)))
						.addComponent(panelConsole, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panelGraphStream, GroupLayout.DEFAULT_SIZE, 908, Short.MAX_VALUE)
						.addComponent(panelAusgabe, GroupLayout.DEFAULT_SIZE, 908, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(panelGraphStream, GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panelAusgabe, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panelOptions, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(panelDijkstra, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
								.addComponent(panelBFS, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panelConsole, GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
							.addGap(10))))
		);
		
		
		gl_panelAusgabe.setHorizontalGroup(
			gl_panelAusgabe.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelAusgabe.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelAusgabe.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPaneAusgabe, GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
						.addComponent(labelAusgabe))
					.addContainerGap())
		);
		gl_panelAusgabe.setVerticalGroup(
			gl_panelAusgabe.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panelAusgabe.createSequentialGroup()
					.addContainerGap()
					.addComponent(labelAusgabe)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(scrollPaneAusgabe, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		
		scrollPaneAusgabe.setViewportView(textArea_1);
		panelAusgabe.setLayout(gl_panelAusgabe);
		
		

		textField.setColumns(10);
		
		
		textField_1.setColumns(10);
		
		
	
		gl_panelDijkstra.setHorizontalGroup(
			gl_panelDijkstra.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDijkstra.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelDijkstra.createParallelGroup(Alignment.LEADING)
						.addComponent(labelDijkstra)
						.addGroup(Alignment.TRAILING, gl_panelDijkstra.createSequentialGroup()
							.addComponent(labelDijkstraSource)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.TRAILING, gl_panelDijkstra.createSequentialGroup()
							.addComponent(labelDijkstraTarget)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnDijkstra, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE))
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
					.addContainerGap(12, Short.MAX_VALUE))
		);
		panelDijkstra.setLayout(gl_panelDijkstra);
		
		
		
		textFieldSource.setColumns(10);
		
		
		textFieldTarget.setColumns(10);		
		
		
		JLabel labelBFSTarget = new JLabel("Source:");
		
		JLabel labelBFSSource = new JLabel("Target:");
		
	
		
		
		
		GroupLayout gl_panelBFS = new GroupLayout(panelBFS);
		gl_panelBFS.setHorizontalGroup(
			gl_panelBFS.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelBFS.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelBFS.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelBFS.createSequentialGroup()
							.addGroup(gl_panelBFS.createParallelGroup(Alignment.LEADING)
								.addComponent(labelBFS)
								.addComponent(btnBFS, GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE))
							.addContainerGap())
						.addGroup(gl_panelBFS.createSequentialGroup()
							.addGroup(gl_panelBFS.createParallelGroup(Alignment.TRAILING)
								.addComponent(labelBFSSource)
								.addComponent(labelBFSTarget))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panelBFS.createParallelGroup(Alignment.LEADING)
								.addComponent(textFieldTarget, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
								.addComponent(textFieldSource, GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE))
							.addContainerGap())))
		);
		gl_panelBFS.setVerticalGroup(
			gl_panelBFS.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelBFS.createSequentialGroup()
					.addContainerGap()
					.addComponent(labelBFS)
					.addGap(5)
					.addGroup(gl_panelBFS.createParallelGroup(Alignment.BASELINE)
						.addComponent(textFieldSource, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(labelBFSTarget))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelBFS.createParallelGroup(Alignment.BASELINE)
						.addComponent(textFieldTarget, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(labelBFSSource))
					.addPreferredGap(ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
					.addComponent(btnBFS)
					.addContainerGap())
		);
		panelBFS.setLayout(gl_panelBFS);
		
		JLabel labelOptions = new JLabel("Options");
		GroupLayout gl_panelOptions = new GroupLayout(panelOptions);
		gl_panelOptions.setHorizontalGroup(
			gl_panelOptions.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelOptions.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelOptions.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelOptions.createSequentialGroup()
							.addComponent(btnLoadGraph, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnAutolayout, GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE))
						.addComponent(labelOptions))
					.addContainerGap())
		);
		gl_panelOptions.setVerticalGroup(
			gl_panelOptions.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelOptions.createSequentialGroup()
					.addGap(9)
					.addComponent(labelOptions)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelOptions.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnLoadGraph)
						.addComponent(btnAutolayout)
						.addComponent(btnSave))
					.addGap(69))
		);
		panelOptions.setLayout(gl_panelOptions);
		
		
		gl_panelConsole.setHorizontalGroup(
			gl_panelConsole.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelConsole.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_panelConsole.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPaneConsole, GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
						.addComponent(labelConsole)
						.addComponent(btnGraphanzeige, GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panelConsole.setVerticalGroup(
			gl_panelConsole.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelConsole.createSequentialGroup()
					.addGap(11)
					.addComponent(labelConsole)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPaneConsole, GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnGraphanzeige)
					.addContainerGap())
		);
		
		
		scrollPaneConsole.setViewportView(textAreaConsole);		
		panelConsole.setLayout(gl_panelConsole);
		

		frameMain.getContentPane().setLayout(groupLayout);
		
		
		frameMain.setVisible(true);
		
		
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


	public JTextArea getTextAreaConsole() {
		return textAreaConsole;
	}


	public void setTextAreaConsole(JTextArea textAreaConsole) {
		this.textAreaConsole = textAreaConsole;
	}


	public JTextField getTextFieldSource() {
		return textFieldSource;
	}


	public void setTextFieldSource(JTextField textFieldSource) {
		this.textFieldSource = textFieldSource;
	}


	public JTextField getTextFieldTarget() {
		return textFieldTarget;
	}


	public void setTextFieldTarget(JTextField textFieldTarget) {
		this.textFieldTarget = textFieldTarget;
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


	public JPanel getPanelOptions() {
		return panelOptions;
	}


	public void setPanelOptions(JPanel panelOptions) {
		this.panelOptions = panelOptions;
	}


	public JButton getBtnSave() {
		return btnSave;
	}


	public void setBtnSave(JButton btnSave) {
		this.btnSave = btnSave;
	}


	public JButton getBtnLoadGraph() {
		return btnLoadGraph;
	}


	public void setBtnLoadGraph(JButton btnLoadGraph) {
		this.btnLoadGraph = btnLoadGraph;
	}


	public JButton getBtnAutolayout() {
		return btnAutolayout;
	}


	public void setBtnAutolayout(JButton btnAutolayout) {
		this.btnAutolayout = btnAutolayout;
	}


	public JPanel getPanelConsole() {
		return panelConsole;
	}


	public void setPanelConsole(JPanel panelConsole) {
		this.panelConsole = panelConsole;
	}


	public JPanel getPanelBFS() {
		return panelBFS;
	}


	public void setPanelBFS(JPanel panelBFS) {
		this.panelBFS = panelBFS;
	}


	public JPanel getPanelDijkstra() {
		return panelDijkstra;
	}


	public void setPanelDijkstra(JPanel panelDijkstra) {
		this.panelDijkstra = panelDijkstra;
	}


	public JPanel getPanelAusgabe() {
		return panelAusgabe;
	}


	public void setPanelAusgabe(JPanel panelAusgabe) {
		this.panelAusgabe = panelAusgabe;
	}


	public JScrollPane getScrollPaneAusgabe() {
		return scrollPaneAusgabe;
	}


	public void setScrollPaneAusgabe(JScrollPane scrollPaneAusgabe) {
		this.scrollPaneAusgabe = scrollPaneAusgabe;
	}


	public JLabel getLabelAusgabe() {
		return labelAusgabe;
	}


	public void setLabelAusgabe(JLabel labelAusgabe) {
		this.labelAusgabe = labelAusgabe;
	}


	public GroupLayout getGl_panelAusgabe() {
		return gl_panelAusgabe;
	}


	public void setGl_panelAusgabe(GroupLayout gl_panelAusgabe) {
		this.gl_panelAusgabe = gl_panelAusgabe;
	}


	public JTextArea getTextArea_1() {
		return textArea_1;
	}


	public void setTextArea_1(JTextArea textArea_1) {
		this.textArea_1 = textArea_1;
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


	public JLabel getLabelBFS() {
		return labelBFS;
	}


	public void setLabelBFS(JLabel labelBFS) {
		this.labelBFS = labelBFS;
	}


	public JButton getBtnBFS() {
		return btnBFS;
	}


	public void setBtnBFS(JButton btnBFS) {
		this.btnBFS = btnBFS;
	}


	public JButton getBtnGraphanzeige() {
		return btnGraphanzeige;
	}


	public void setBtnGraphanzeige(JButton btnGraphanzeige) {
		this.btnGraphanzeige = btnGraphanzeige;
	}


	public JLabel getLabelConsole() {
		return labelConsole;
	}


	public void setLabelConsole(JLabel labelConsole) {
		this.labelConsole = labelConsole;
	}


	public JScrollPane getScrollPaneConsole() {
		return scrollPaneConsole;
	}


	public void setScrollPaneConsole(JScrollPane scrollPaneConsole) {
		this.scrollPaneConsole = scrollPaneConsole;
	}


	public GroupLayout getGl_panelConsole() {
		return gl_panelConsole;
	}


	public void setGl_panelConsole(GroupLayout gl_panelConsole) {
		this.gl_panelConsole = gl_panelConsole;
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


	

	
	
	
	

}
