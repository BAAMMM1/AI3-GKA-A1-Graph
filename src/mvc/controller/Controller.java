package mvc.controller;

import java.awt.Component;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import org.graphstream.graph.Node;
import org.graphstream.ui.view.Viewer;

import mvc.model.Model;
import mvc.view.View;

public class Controller {
	
	private Model model;
	private View view;
	
	private Viewer viewer;
	private org.graphstream.ui.view.View viewer_view;
	
	/*
	 * --------------------------------------------
	 */
	private boolean autolayout = true;
	/*
	 * --------------------------------------------
	 */
	
	public Controller(Model model, View classView){
		this.model = model;
		this.view = classView;
		this.initController();
		this.initView();
		this.initGraph();
	}
	
	private void initView(){
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		this.setGrapgBuilderTextArea();
		
	}
	
	private void initController(){
		this.view.getBtnLoadGraph().addActionListener(e -> this.btnLoadAction());
		this.view.getBtnSave().addActionListener(e -> this.btnSaveAction());
		this.view.getBtnAutolayout().addActionListener(e -> this.btnAutoLayoutAction());
		this.view.getBtnDijkstra().addActionListener(e -> this.btnDijkstraAction());
		this.view.getBtnBFS().addActionListener(e -> this.btnBFSAction());
		this.view.getBtnGraphanzeige().addActionListener(e -> this.btnGraphAnzeigenAction());
			
		// TODO Fehlerhafter load
		
		
		this.setGraphToPanel();
		this.setGrapgBuilderTextArea();
		
		

	}
	
	private void btnGraphAnzeigenAction(){
		List<String> fromTextArex = new ArrayList<String>();
		
		String text = view.getTextAreaConsole().getText();
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
		model.setGraph(model.getFileHandler().loadGraphFromList(fromTextArex));

		setGraphToPanel();
	}
	
	private void btnBFSAction(){

				String source = view.getTextFieldSource().getText();
				String target = view.getTextFieldTarget().getText();
		
		if(this.sourceTargetValid(source, target)){
			
		
		
		
		/*
		BreadthFirstSearch bfs = new BreadthFirstSearch(graph);
		bfs.shortestPath(source, target);
		*/

		//model.setGraph(model.getBfs().compute(model.getGraph(), model.getGraph().getNode(source), model.getGraph().getNode(target)));
		
		List<Node> result = model.getBfs().calculate(model.getGraph(), model.getGraph().getNode(source), model.getGraph().getNode(target));
		/*
		 * TODO Auslager in eine Mehtode 
		 * Weg vorhanden prüfung?
		 */
		if(result == null){
			this.setTextFieldAusgabe("BreadthFirstSearch:\nKein Weg vorhanden");
		} else {
			this.setTextFieldAusgabe("BreadthFirstSearch:\nVon source: " + source + " zu target: " + target + ": "+ result.toString());
		}		
		
		this.setGraphToPanel();
		} else {
			this.setTextFieldAusgabe("BreadthFirstSearch:\nSource oder Target nicht korret.");
		}
	}
	
	private boolean sourceTargetValid(String source, String target){
		
		if((model.getGraph().getNode(source) == null) || (model.getGraph().getNode(target) == null)){
			return false;
		} else {		
			return true;
		}
	
	}
	
	private void btnDijkstraAction(){
		String source = view.getTextField().getText();
		String target = view.getTextField_1().getText();	
		
		if(this.sourceTargetValid(source, target)){
		
		if(!model.getGraph().getEdge(0).isDirected()){
			
			if(this.model.getDijksta().isGraphCorrectWeighted(this.model.getGraph())){
				
			this.setTextFieldAusgabe("Dijkstra:\nGraph ungerichtet.");		
	
			model.setGraph(model.getDijksta().converteUndirectedToDirected(model.getGraph()));			
			//model.setGraph(djk.compute(model.getGraph(), model.getGraph().getNode(source), model.getGraph().getNode(target)));
			
			List<Node> result = this.model.getDijksta().calculate(model.getGraph(), model.getGraph().getNode(source), model.getGraph().getNode(target));
			
			this.setTextFieldAusgabe("Dijkstra-Algorithmus:\n" + this.model.getDijksta().getShortestPathWithCoast());		
			
			this.setGraphToPanel();
			System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
			} else {
				this.setTextFieldAusgabe("Dijkstra:\nGraph Gewichtungsfehler.");
			}
			
		} else {
			
			if(this.model.getDijksta().isGraphCorrectWeighted(this.model.getGraph())){
				
					//model.setGraph(djk.compute(model.getGraph(), model.getGraph().getNode(source), model.getGraph().getNode(target)));
					
					List<Node> result = this.model.getDijksta().calculate(model.getGraph(), model.getGraph().getNode(source), model.getGraph().getNode(target));
					
					this.setTextFieldAusgabe("Dijkstra-Algorithmus:\n" + this.model.getDijksta().getShortestPathWithCoast());		
					
					this.setGraphToPanel();
				
				
			
			} else {
				this.setTextFieldAusgabe("Dijkstra:\nGraph Gewichtungsfehler.");
			}
		}
		} else {
			this.setTextFieldAusgabe("Dijkstra:\nSource oder Target nicht korret.");
		}
	}
	

		
	private void btnAutoLayoutAction(){
		if(autolayout == true){
			autolayout = false;
			viewer.disableAutoLayout();
		} else {
			autolayout = true;
			viewer.enableAutoLayout();
		}
	}
	
	private void btnSaveAction(){
		// JFileChooser-Objekt erstellen
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
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
            this.model.getFileHandler().saveGraph(this.model.getGraph(), chooser.getSelectedFile().getPath());
            

        }
	}
	
	private void btnLoadAction(){
		 // JFileChooser-Objekt erstellen
        JFileChooser chooser = new JFileChooser();       
        chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
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
            
            model.setGraph(model.getFileHandler().loadGraph(chooser.getSelectedFile().getPath()));	
            setGraphToPanel();
            
            
            model.setGraphAsText(model.getFileHandler().loadFile(chooser.getSelectedFile().getPath()));		            
            setGrapgBuilderTextArea();
        }
		
	}
	
	
	
	
	
	
	
	/*
	 * -----------------------------------------------------
	 */
	
	private void setGrapgBuilderTextArea(){
		String textAreaString = "";
		for(int i = 0; i<= model.getGraphAsText().size()-1; i++ ){
			textAreaString = textAreaString + model.getGraphAsText().get(i).toString() + "\n";
		}
		view.getTextAreaConsole().setText(textAreaString);
	}
	
	private void setGraphToPanel(){
		
		this.initGraph();
		viewer = new Viewer(model.getGraph(), Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);         
 	    viewer_view = viewer.addDefaultView(false);
 	    viewer.enableAutoLayout();
 	    view.getPanelGraphStream().removeAll();
 	    view.getPanelGraphStream().add((Component) viewer_view);
 	    view.getPanelGraphStream().revalidate(); 
 	    view.getPanelGraphStream().repaint(); 	   
	}
	
	private void initGraph(){
		// background
		//graph.addAttribute("ui.stylesheet", "graph { fill-color: red; }");
		
		model.getGraph().addAttribute("ui.quality");
		
		// Wendes Antialiasing zur Kantenglettung auf den Graph an
		model.getGraph().addAttribute("ui.antialias");
		
		// Knotennamen ausblenden
		//graph.addAttribute("ui.stylesheet", "node {	text-mode: hidden;}");
		
		model.getGraph().addAttribute("ui.stylesheet", "node {	size: 10px, 10px; text-size: 14px; text-alignment: under; text-style: bold;} "
				+ "edge {	size: 1px, 1px; text-size: 14px; text-alignment: under; text-style: normal;}");


		/*
		 * "along" ...
    "at-left" ...
    "at-right" ...
    "center" ...
    "left" ...
    "right" ...
    "under" ...
    "above" ...
    "justify" ...
		 */
	}
	
	/*
	 * -----------------------------------------------------
	 */
	
	
	private void setTextFieldAusgabe(String text){
		view.getTextArea_1().setText(text);
	}
		


}
