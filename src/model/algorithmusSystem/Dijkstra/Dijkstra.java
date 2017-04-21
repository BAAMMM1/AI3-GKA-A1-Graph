package model.algorithmusSystem.Dijkstra;

import java.util.ArrayList;
import java.util.List;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.AbstractEdge;
import org.graphstream.graph.implementations.AbstractNode;

import model.algorithmusSystem.Algorithmus;

//übernommen aus Breadth First Seach Algorithmus
public class Dijkstra extends Algorithmus {
	private Graph graph;
	private List<AbstractNode> nodes;
	private List<AbstractEdge> edges;
	
	public Dijkstra(Graph graph){
		this.graph = graph;
		this.edges = new ArrayList<AbstractEdge>();
		edges.addAll(graph.getEdgeSet());
		
		System.out.println(edges.toString());
		
		this.nodes = new ArrayList<AbstractNode>();
		nodes.addAll(graph.getNodeSet());
		
		System.out.println(nodes.toString());
		
	}
	
	// nun der günstigste weg
	public String cheapestPath(String sourceNode, String targetNode){
		
		AbstractNode source = null;
		AbstractNode target = null;
		
		// Dijkstra Initialisierung -> hier Kostenstartwert von 0
		
		for(int i = 0; i <= nodes.size() -1 ; i++){
			nodes.get(i).setAttribute("Dijkstra", "0");			
		}
	
		System.out.println(sourceNode);
		for(int i = 0; i <= nodes.size()-1 ; i++){
			if(nodes.get(i).getId().toString().equals(sourceNode)){
				source = nodes.get(i);
				System.out.println("-------------" + nodes.get(i).getAttribute("Dijkstra").toString());
				/* doppelt gemoppelt auf 0 gesetzt -> 
				 * eventuell kann man das auch direkt weglassen oder startwert ist was anderes
				 * theoretisch kann man den Start wert auch als String setzen, um später einfach addieren zu können
				 * und nicht mehr ab zu fragen, ob du deshalb 0 bist, weil du startwert bist */
				nodes.get(i).setAttribute("Dijkstra", 0);
				System.out.println("-------------" + nodes.get(i).getAttribute("Dijkstra").toString());
			}
		}
		
		//Sicherstellung, das Targetnode auch im Graph vorhanden ist
		for(int i = 0; i <= nodes.size()-1 ; i++){
			if(nodes.get(i).getId().toString().equals(targetNode)){
				target = nodes.get(i);
			}
		}
		
		//Den Startwert in die Liste laden
		List<AbstractNode> queue = new ArrayList<AbstractNode>();
		queue.add(source);

		// Hin erstmal übernommen
		boolean run = true;
		while((!queue.isEmpty()) && run){
			AbstractNode toLook = queue.get(0);
			
			System.out.println("Look at Vertex: " + toLook.toString());
			queue.remove(0);
			
			for(int i = 0; i <= edges.size() -1 ; i++){
			
				if(edges.get(i).getSourceNode() == toLook){
					/* hier nochmal sourcenode" hinzugefügt, vermutlich falsch, da ich den source meine,
					 * von welcher Kante ich gerade komme */
					AbstractNode targetNode2 = edges.get(i).getTargetNode();
					AbstractNode sourceNode2 = edges.get(i).getSourceNode();
					
					/*
					 * Relikt aus BFS, Einschränkung gilt nicht, da jeder Pfad nach dem günstigstem Weg überprüft werden muss
					
					 if(targetNode2.getAttribute("Dijkstra") == "not visit"){ */
					
						System.out.println("added to queue: " + targetNode2.toString());
						queue.add(targetNode2);
						/* Addweight = Gewichtung der Kante 
						 * Sourceweight = Pfad kosten bis zum Knoten der Kante zu neuem Knoten
						 * targetweight = Kosten, die gerade auf dem neuen Knoten vorhanden sind
						 * 				  wenn 0, dann noch unvisited, wenn ein Wert vorhanden
						 * 				  dann Kosten von dem Kürzerem Weg*/
						int addweight = edges.get(i).getAttribute("weight");
						int sourceweight = sourceNode2.getAttribute("Dijkstra");
						int targetweight = targetNode2.getAttribute("Dijkstra");
						/* Wenn Schrittziel nicht gleich source ist -> Abfrage, weil man bei dijkestra = 0 (Initialisierung) den wert ändern darf!!! 
						 * oder 
						 * (wenn der source nicht 0 ist und Wenn vorhandener Pfad teurer ist)
						 *  oder 
						 *  wenn source = 0, 
						 *  
						 *  dann setze neue weight */
						if (targetNode2!= sourceNode2 || (targetweight != 0 && targetweight > sourceweight + addweight) || targetweight == 0 ) {
						targetNode2.setAttribute("Dijkstra", sourceweight + addweight);
						};
						
						System.out.println("Vertex: " + targetNode2.toString() + " Dijkstra = " + targetNode2.getAttribute("Dijkstra"));
						
						/* Entfällt, da weiternach günstigerem Weg gesucht werden muss.
						 * Einzige mögliche Abbruchbedinung:
						 * Wenn alle Pfarde, die man noch am berechnen ist, schon höhere Kosten haben, wie das Target besitzt,
						 * da dann ja kein Pfad mehr günstiger sein kann!!!
						 * if(targetNode2 == target){
							System.out.println("target achieved!");
							run = false;
							break; */							
						}						
					}
				}				
			
		
		
		/* Relikt aus altem Code
		 *  Und zurÃ¼ck
		 */
		/*List<AbstractNode> path = new ArrayList<AbstractNode>();
		path.add(target);
		int counter = 0;
		int highestBFS = target.getAttribute("BFS");;
		while(highestBFS != 0){
			for(int i = 0; i <= nodes.size() -1 ; i++) {
				int bfs = nodes.get(i).getAttribute("BFS");
				
				if( bfs == (highestBFS-1) && this.isThereAEdgeBetweenThisNodes(nodes.get(i), path.get(counter))){
					path.add(nodes.get(i));
					highestBFS--;
					counter++;
				}
			}
		}*/
		
		
		//System.out.println(path.toString());
		
	/////////		Weiter bin ich noch nicht gekommen^^	
		return null;
	}
	
	
	public boolean isThereAEdgeBetweenThisNodes(AbstractNode bfsNode, AbstractNode targetNode){
		boolean toReturn = false;
		
		for(int i = 0; i <= edges.size() -1 ; i++){
			if(edges.get(i).getSourceNode() == bfsNode && edges.get(i).getTargetNode() == targetNode){
				toReturn = true;
			}
		}
		
		return toReturn;
	}
}
