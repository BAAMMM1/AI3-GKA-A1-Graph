package mvc.controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JFileChooser;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.ui.view.Viewer;

import mvc.model.Model;
import mvc.model.algorithmen.eulerTour.EulerTour;
import mvc.model.algorithmen.eulerTour.EulerTourFactory;
import mvc.model.algorithmen.eulerTour.Hierholzer;
import mvc.model.algorithmen.minimalSpanningTree.MinimalSpanningTree;
import mvc.model.algorithmen.minimalSpanningTree.MinimalSpanningTreeFactory;
import mvc.model.algorithmen.shortestPath.ShortestPath;
import mvc.model.algorithmen.shortestPath.ShortestPathFactory;
import mvc.model.graphGenerator.GraphGenerator;
import mvc.model.graphGenerator.GraphGeneratorFactory;
import mvc.model.graphGenerator.RandomSimple;
import mvc.view.View;

public class Controller {

	private Model model;
	private View view;

	private Viewer viewer;
	private org.graphstream.ui.view.View viewer_view;

	public Controller(Model model, View classView) {
		this.model = model;
		this.view = classView;
		this.initView();
		this.initGraphInterface();
		this.initController();

		/*
		 * ExceptionsHandler
		 */
		Controller.setupGlobalExceptionHandling(classView);
	}

	private void initView() {
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		this.setGrapgBuilderTextArea();

	}

	private void initGraphInterface() {
		// background
		// graph.addAttribute("ui.stylesheet", "graph { fill-color: red; }");

		model.getGraph().addAttribute("ui.quality");

		// Wendes Antialiasing zur Kantenglettung auf den Graph an
		model.getGraph().addAttribute("ui.antialias");

		// Knotennamen ausblenden
		// graph.addAttribute("ui.stylesheet", "node { text-mode: hidden;}");

		model.getGraph().addAttribute("ui.stylesheet",
				"node {	size: 10px, 10px; text-size: 14px; text-alignment: under; text-style: bold;} "
						+ "edge {	size: 1px, 1px; text-size: 14px; text-alignment: under; text-style: normal;}");

		/*
		 * "along" ... "at-left" ... "at-right" ... "center" ... "left" ...
		 * "right" ... "under" ... "above" ... "justify" ...
		 */
	}

	private void initController() {
		/*
		 * ComboBoxen befüllen
		 */
		this.populateComboBoxes();

		/*
		 * Menu
		 */
		this.view.getMntmLoadGraph().addActionListener(e -> this.menuFileLoadAction());
		this.view.getMntmSaveGraph().addActionListener(e -> this.menuFileSaveAction());
		this.view.getMntmUndirectedDirected().addActionListener(e -> this.menuModifierUndirectedToDirected());
		this.view.getMntmClose().addActionListener(e -> this.menueCloseAction());
		this.view.getRdbtnmntmAutolayout().addActionListener(e -> this.menuRdbTnAutolayoutAction());

		/*
		 * Buttons
		 */
		this.view.getBtnGenerator().addActionListener(e -> this.btnGeneratorAction());
		this.view.getBtnConsole().addActionListener(e -> this.btnGraphAnzeigenAction());
		this.view.getBtnMST().addActionListener(e -> this.btnMSTAction());
		this.view.getBtnSTP().addActionListener(e -> this.btnSTPAction());
		this.view.getBtnEuler().addActionListener(e -> this.btnEulerAction());

		/*
		 * Graph zur Ansicht und zur Console hinzufügen
		 */
		this.setGraphToPanel();
		this.setGrapgBuilderTextArea();

	}

	private void menueCloseAction() {
		//this.view.getFrameMain().setState(this.view.getFrameMain().ICONIFIED);
		//this.view.getFrameMain().setExtendedState(this.view.getFrameMain().MAXIMIZED_BOTH);
		this.view.getFrameMain().dispatchEvent(new WindowEvent(this.view.getFrameMain(), WindowEvent.WINDOW_CLOSING));
	}

	private void populateComboBoxes() {
		this.view.getComboBoxGenerator().addItem(new String("Simple"));
		this.view.getComboBoxGenerator().addItem(new String("Euler"));		

		this.view.getComboBoxShortestPath().addItem(new String("BreadthFirstSearch"));
		this.view.getComboBoxShortestPath().addItem(new String("Dijkstra"));

		this.view.getComboBoxMST().addItem(new String("Kruskal"));
		this.view.getComboBoxMST().addItem(new String("Prim"));

		this.view.getComboBoxEuler().addItem(new String("Hierholzer"));
		this.view.getComboBoxEuler().addItem(new String("Fleury"));
	}

	private void menuFileLoadAction() {
		// JFileChooser-Objekt erstellen
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		// Dialog zum Oeffnen von Dateien anzeigen
		int rueckgabeWert = chooser.showOpenDialog(null);

		/* Abfrage, ob auf "Öffnen" geklickt wurde */
		if (rueckgabeWert == JFileChooser.APPROVE_OPTION) {
			// Ausgabe der ausgewaehlten Datei
			System.out.println("Die zu öffnende Datei ist: " + chooser.getSelectedFile().getName());
			System.out.println("Die zu öffnende Datei ist: " + chooser.getSelectedFile().getPath());

			model.setGraph(model.getFileHandler().loadGraph(chooser.getSelectedFile().getPath()));
			setGraphToPanel();

			model.setGraphAsText(model.getFileHandler().loadFile(chooser.getSelectedFile().getPath()));
			setGrapgBuilderTextArea();
		}
	}

	private void menuFileSaveAction() {
		// JFileChooser-Objekt erstellen
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		// Dialog zum Oeffnen von Dateien anzeigen
		int rueckgabeWert = chooser.showSaveDialog(null);

		/* Abfrage, ob auf "Öffnen" geklickt wurde */
		if (rueckgabeWert == JFileChooser.APPROVE_OPTION) {
			// Ausgabe der ausgewaehlten Datei
			System.out.println("Die zu speichernde Datei ist: " + chooser.getSelectedFile().getName());
			System.out.println("Die zu speichernde Datei ist: " + chooser.getSelectedFile().getPath());
			this.model.getFileHandler().saveGraph(this.model.getGraph(), chooser.getSelectedFile().getPath());

		}
	}

	/**
	 * TODO Graph zur Console hinzufügen
	 */
	private void menuModifierUndirectedToDirected() {
		this.model.setGraph(this.model.getModifier().converteUndirectedToDirected(this.model.getGraph()));
		this.setGraphToPanel();
	}

	private void menuRdbTnAutolayoutAction() {

		if (!this.view.getRdbtnmntmAutolayout().isSelected()) {
			viewer.enableAutoLayout();

		} else {
			viewer.disableAutoLayout();

		}
	}

	private void btnGeneratorAction() {

		GraphGenerator generator = this.model
				.getGraphGenerator(this.view.getComboBoxGenerator().getSelectedItem().toString());

		this.model.setGraph(generator.generate(Integer.valueOf(this.view.getNodeSize().getText()).intValue(),
				Integer.valueOf(this.view.getEdgeSize().getText()).intValue(),
				Integer.valueOf(this.view.getWeightSize().getText()).intValue()));

		this.model.getLabeler().setDefaultLabels(this.model.getGraph());
		this.setGraphToPanel();

	}

	private void btnSTPAction() {
		this.model.getLabeler().setDefaultLabels(this.model.getGraph());

		ShortestPath sTP = this.model.getShortestPath(this.view.getComboBoxShortestPath().getSelectedItem().toString());

		String source = view.getSource().getText();
		String target = view.getTarget().getText();

		List<Node> result = sTP.calculate(model.getGraph(), source, target);
		this.view.getTextAreaResult().setText("Shortest path: " + result.toString());

		this.model.getLabeler().colorPath(model.getGraph(), result, 255, 10, 0);
		this.model.getLabeler().setFlagToNodeLabel(this.model.getGraph(), "BFS");

	}

	private void btnMSTAction() {
		this.model.getLabeler().setDefaultLabels(this.model.getGraph());

		MinimalSpanningTree mst = this.model
				.getMinimalSpanningTree(this.view.getComboBoxMST().getSelectedItem().toString());

		this.model.setGraph(mst.calculate(this.model.getGraph()));
		this.setGraphToPanel();

		// this.addTab();
	}

	private void btnEulerAction() {
		this.model.getLabeler().setDefaultLabels(this.model.getGraph());

		EulerTour tour = this.model.getEulerCircle(this.view.getComboBoxEuler().getSelectedItem().toString());

		List<Edge> result = tour.calculate(this.model.getGraph());
		this.model.getLabeler().colorEdges(this.model.getGraph(), result, 0, 100, 255);
		this.view.getTextAreaResult().setText("Tour:\n" + result.toString());

		if (tour instanceof Hierholzer) {
			tour = (Hierholzer) tour;
			this.model.getLabeler().colorListEdges(this.model.getGraph(), ((Hierholzer) tour).getEulerParts(), 0, 100,
					100);

			this.view.getTextAreaResult().setText(this.view.getTextAreaResult().getText() + "\nEulerparts:");
			for (int i = 0; i < ((Hierholzer) tour).getEulerParts().size(); i++) {
				this.view.getTextAreaResult().setText(this.view.getTextAreaResult().getText() + "\n"
						+ ((Hierholzer) tour).getEulerParts().get(i).toString());

			}
		}

	}

	private void btnGraphAnzeigenAction() {
		this.model.getLabeler().setDefaultLabels(this.model.getGraph());

		List<String> fromTextArex = new ArrayList<String>();

		String text = view.getTextAreaConsole().getText();
		while (text.length() > 0) {
			if (text.indexOf("\n") != -1) {
				fromTextArex.add(text.substring(0, text.indexOf("\n")));
				text = text.substring(text.indexOf("\n") + 1);
			} else {
				fromTextArex.add(text);
				break;
			}

		}
		System.out.println(fromTextArex.toString());
		model.setGraph(model.getFileHandler().loadGraphFromList(fromTextArex));

		setGraphToPanel();
	}

	private void addTab() {

		JLayeredPane layerGraph = new JLayeredPane();
		layerGraph.setForeground(Color.LIGHT_GRAY);
		layerGraph.setBackground(Color.LIGHT_GRAY);
		this.view.getTabbedPane().addTab("Tab", null, layerGraph, null);

		JPanel panelGraphStream = new JPanel();
		GroupLayout gl_layerGraph = new GroupLayout(layerGraph);
		gl_layerGraph.setHorizontalGroup(gl_layerGraph.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_layerGraph.createSequentialGroup().addContainerGap()
						.addComponent(panelGraphStream, GroupLayout.DEFAULT_SIZE, 936, Short.MAX_VALUE)
						.addContainerGap()));
		gl_layerGraph.setVerticalGroup(gl_layerGraph.createParallelGroup(Alignment.TRAILING).addGroup(Alignment.LEADING,
				gl_layerGraph.createSequentialGroup().addContainerGap()
						.addComponent(panelGraphStream, GroupLayout.DEFAULT_SIZE, 543, Short.MAX_VALUE)
						.addContainerGap()));
		panelGraphStream.setLayout(new GridLayout(1, 0, 0, 0));
		layerGraph.setLayout(gl_layerGraph);

		panelGraphStream.removeAll();
		panelGraphStream.add((Component) viewer_view);
		panelGraphStream.revalidate();
		panelGraphStream.repaint();

	}

	/*
	 * -----------------------------------------------------
	 */

	private void setGrapgBuilderTextArea() {
		String textAreaString = "";
		for (int i = 0; i <= model.getGraphAsText().size() - 1; i++) {
			textAreaString = textAreaString + model.getGraphAsText().get(i).toString() + "\n";
		}
		view.getTextAreaConsole().setText(textAreaString);
	}

	private void setGraphToPanel() {

		this.initGraphInterface();
		viewer = new Viewer(model.getGraph(), Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		viewer_view = viewer.addDefaultView(false);
		viewer.enableAutoLayout();
		this.view.getRdbtnmntmAutolayout().setSelected(false);
		view.getPanelGraphStream().removeAll();
		view.getPanelGraphStream().add((Component) viewer_view);
		view.getPanelGraphStream().revalidate();
		view.getPanelGraphStream().repaint();
	}

	/*
	 * -----------------------------------------------------
	 */

	/**
	 * Diese Mehtode ist dafür zuständig, dass die Exceptions gefangen werden
	 * und auf der console und in der view geprinted werden.
	 * 
	 * @param view
	 */
	public static void setupGlobalExceptionHandling(View view) {
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				e.printStackTrace();
				view.getTextAreaResult().setText(e.toString());
			}
		});
	}

}
