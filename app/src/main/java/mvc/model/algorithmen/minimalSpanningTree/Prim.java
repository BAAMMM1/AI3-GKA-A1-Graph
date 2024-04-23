package mvc.model.algorithmen.minimalSpanningTree;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import mvc.model.exceptions.IllegalNotConnectedGraph;
import utility.Printer;

/**
 * Diese Klasse stellt den Prim-Algorithmus dar. In der Priority Queue sind alle
 * Knoten gespeichert, die noch nicht zum minimalen Spannbaum gehoeren. Alle
 * Knoten haben einen primWeight-Wert, der dem der leichtesten Kante entspricht
 * - und eine Kante primEdge, die diesem Wert zugeordnet ist - durch die der
 * Knoten mit dem minimalen Spannbaum verbunden werden kann. Existiert keine
 * solche Kante, wird dem Knoten der Wert OMEGA zugewiesen. Die Warteschlange
 * liefert nun immer einen Knoten mit dem kleinsten Wert zurück, bis sich alle
 * Knoten im minimalen Spannbaum befinden.
 */
public class Prim extends MinimalSpanningTree {

	private Graph tree;
	private PriorityQueue<Node> queue;
	private long runTime;

	private static final int OMEGA = Integer.MAX_VALUE;

	/**
	 * Diese Mehtode stellt die Handlungsvorschrift Prim-Algorithmus da.
	 * 
	 * @return Gibt den minimalen Spannbaum zurück
	 */
	protected Graph procedure() {
		long timeStart = System.currentTimeMillis();

		this.initPrim();
		Node nextNode = this.getRandomStartNode();
		this.addNodeToTree(nextNode);

		while (this.treeHasNotAllNodes()) {
			/*
			 * Prioritäten-Updated der Knoten, nur bei Knoten die jetzt
			 * ereichbar sind.
			 */
			this.updateNodePriority(nextNode);
			nextNode = this.getNextNodeFromQueue();
			this.addNodeToTree(nextNode);
			this.addEdgeToTree(nextNode.getAttribute("primEdge"));
		}

		long timeEnd = System.currentTimeMillis();
		runTime = (timeEnd - timeStart);

		Printer.prompt(this, "time needed: " + this.getRunTime());
		Printer.prompt(this, "edge-weight sum: " + this.getEdgeWeightes());

		return this.tree;
	}

	/**
	 * Diese Mehtode initialisiert den Prim-Algorithmus. Zunächst befindet sich
	 * in dem minimalen Spannbaum keine Knoten. Diese müssen nach einander aus
	 * der PriorityQueue hinzugefügt werden. Die PriorityQueue enthält zum Start
	 * alle Knoten mit der Priorität OMEGA.
	 */
	private void initPrim() {
		this.tree = new MultiGraph("Prim");

		/*
		 * Die PriorityQueue beinhaltet immer das Objekt mit der höchsten
		 * Proiority, hier wieder gespiegelt durch den kleinsten primWeight-Wert
		 */
		this.queue = new PriorityQueue<Node>(this.getGraph().getNodeSet().size(),
				(e1, e2) -> ((int) e1.getAttribute("primWeight")) - (int) e2.getAttribute("primWeight"));

		for (Node node : this.getGraph().getNodeSet()) {
			node.addAttribute("primWeight", OMEGA);
		}

		this.queue.addAll(this.getGraph().getNodeSet());
	}

	/**
	 * Diese Methode ermittelt ob sich alle Knoten im minimalen Spannbaum
	 * befinden.
	 * 
	 * @return true falls der minimale Spannbaum komplett ist
	 */
	private boolean treeHasNotAllNodes() {
		return !(this.tree.getNodeSet().size() == this.getGraph().getNodeSet().size());
	}

	/**
	 * Gibt einen zufällig ausgewählten Knoten aus der Menge an Knoten, welche
	 * sich nicht in dem minimalen Spannbaum befinden und löscht diesen von der
	 * PrioritätenWarteschlange.
	 * 
	 * @return Zufallsknoten
	 */
	private Node getRandomStartNode() {
		List<Node> alleNodes = new LinkedList<Node>(this.getGraph().getNodeSet());
		Random random = new Random();
		Node randomStart = alleNodes.get(random.nextInt(alleNodes.size()));
		// Der Start muss aus der Warteschlangefallen
		this.queue.remove(randomStart);
		return randomStart;
	}

	/**
	 * Fügt einen übergebenen Knoten dem minimalen Spannbaum hinzu.
	 * 
	 * @param node
	 *            Knoten der hinzugefügt werden soll
	 */
	private void addNodeToTree(Node node) {
		Printer.promptTestOut(this, "add node to tree: " + node.toString());

		this.tree.addNode(node.getId());
		this.tree.getNode(node.getId()).setAttribute("ui.label", node.getAttribute("ui.label").toString());
	}

	/**
	 * Fügt dem minimalen Spannbaum eine Kante hinzu
	 * 
	 * @param nextNode
	 *            Kante die hinzugefügt werden soll
	 */
	private void addEdgeToTree(Edge edge) {

		Node source = this.tree.getNode(edge.getSourceNode().getId());
		Node target = this.tree.getNode(edge.getTargetNode().getId());

		this.tree.addEdge(edge.getId(), source, target);
		this.tree.getEdge(edge.getId()).addAttribute("weight", (int) edge.getAttribute("weight"));
		this.tree.getEdge(edge.getId()).addAttribute("ui.label", edge.getAttribute("ui.label").toString());
		Printer.promptTestOut(this, "Add Edge: " + edge.toString());

	}

	/**
	 * Diese Methode updatet die PriorityQueue und die Priotitäten der Nodes,
	 * welches sich in der PriorityQueue befinden. Dafür wird ein Knoten des
	 * minimalen Spannbaums übergeben. Jeder weiterer Knoten der außerhalb des
	 * minimalen Spannbaums liegt und von den übergebenen Knoten über eine Kante
	 * ereichbar ist, erhält als Priorität die Kosten der Kante die dafür
	 * genutzt werden muss. Befindet sich der Knoten bereist in der
	 * PriorityQueue wird seine Priorität geupdated, wenn sie kleiner als die
	 * breist eingetragene Priorität ist.
	 * 
	 * @param node
	 */
	private void updateNodePriority(Node node) {
		Printer.promptTestOut(this, "update priorityQueue from node: " + node.toString());

		for (Edge edge : node.getEachEdge()) {
			/*
			 * Es muss jede Kante angeschaut werden, außer die Kante, die
			 * bereits im Spannbaum sind.
			 */
			if (!this.tree.getEdgeSet().contains(edge)) {
				Printer.promptTestOut(this, "look at edge: " + edge.toString());

				Node nodeForQueue;
				Node source = edge.getSourceNode();
				Node target = edge.getTargetNode();

				/*
				 * Es geht darum die Priorität eines Knoten zu setzten. Diese
				 * Priorität hängt ab von dem Kantengewicht Da jede Kante ein
				 * Source und ein Target hat und da wir nicht die Priorität des
				 * Knoten den wir uns gerade anschauen ermitteln wollen, sondern
				 * den er verbindet, müssen wir diesen ermitteln
				 */
				if (source != node) {
					nodeForQueue = source;
				} else {
					nodeForQueue = target;
				}

				/*
				 * Die Warteschlang beinhaltet alle Knoten die noch nicht zum
				 * spannbaum hinzugefügt wurde, deshalb muss die Priorität eines
				 * Knoten, welcher bereist im Spannbaum sich befindet, nicht neu
				 * gesetzt werden und nur bei den Knoten, die noch in der
				 * Warteschlange sind.
				 * 
				 * Außerdem darf der Knoten nicht im Spannbaum schon sein und
				 * hier erneut eine neue Priorität erhalten, damit kein Kreis
				 * entsteht, da der Knoten von dem wir aus die Prioritäten
				 * aktualisieren bereits im Spannbaum ist.
				 * 
				 * Nur Wenn der Knoten noch nicht im Spannbaum ist ->
				 * Prioritäten updated.
				 */
				if (this.queue.contains(nodeForQueue)) {

					/*
					 * PrimWeightUpdated eines Knoten: Da jede Kante von der
					 * Node neu angeschaut wird, kann es sein, dass eine
					 * Node-Priorität neue gesetzt werden könnte, deshalb muss
					 * geprüft werden, ob s dieser primWeight nicht über des
					 * bereits eingetragenden Wert liegt
					 */
					if ((int) nodeForQueue.getAttribute("primWeight") > (int) edge.getAttribute("weight")) {
						nodeForQueue.setAttribute("primWeight", (int) edge.getAttribute("weight"));
						nodeForQueue.setAttribute("primEdge", edge);
						Printer.promptTestOut(this, "update node: " + nodeForQueue.toString() + " primWeight: "
								+ nodeForQueue.getAttribute("primWeight"));

						/*
						 * Remove, falls der Node bereits in der Queue war, weil
						 * er schon einmal einen Wert hatte, muss diese Nod in
						 * der Queue mit der kleinern Priorität neu angeordnet
						 * werden
						 */
						this.queue.remove(nodeForQueue);
						this.queue.add(nodeForQueue);
						/*
						 * Wenn ein Element in die Queue gelegt wird, wird sie
						 * anhand des Comperator einsortiert, ändert sich der
						 * Wert im Element durch den es einsortiert wrude, muss
						 * es entfernt werden und wieder neue hinzugefügt
						 * werden. Da * Element in der Queuer doppelt mit
						 * unterschiedlichen Prioritäten sein können.
						 */
					}
				}
			}
		}
		Printer.promptTestOut(this, "PriorityQueue: " + this.queue.toString());
	}

	/**
	 * Summiert alle Kantengewichtungen des berechneten minimalen Spannbaums
	 * auf.
	 * 
	 * @return Kantengewichtssumme des ermittelten minimalen Spannbuams
	 */
	public double getEdgeWeightes() {
		return (double) this.tree.getEdgeSet().stream().map(e1 -> (int) e1.getAttribute("weight")).reduce(0,
				(e1, e2) -> e1.intValue() + e2.intValue());
	}

	public int getKnotenAnzahl() {
		return this.tree.getNodeSet().size();
	}

	public int getKantenAnzahl() {
		return this.tree.getEdgeSet().size();
	}

	/**
	 * Diese Mehtode gibt den nächsten Knoten von der Prioritätenwarteschlange
	 * zurück.
	 * 
	 * @return Knoten mit der höchsten Priorität
	 */
	private Node getNextNodeFromQueue() {
		Node nextNode = this.queue.poll();

		/*
		 * Wenn der Ursprungsgraph nicht zusammenhängend war, gibt es hier einen
		 * Fehler
		 */
		if ((int) nextNode.getAttribute("primWeight") == OMEGA) {
			throw new IllegalNotConnectedGraph("Graph ist nicht zusammenhängend");
		} else {
			return nextNode;
		}
	}

	/**
	 * Gibt die RunTime des Algorithmus in Sekunden zurück
	 * 
	 * @return RunTime
	 */
	public long getRunTime() {
		return this.runTime;
	}

	@Override
	public String toString() {
		return "Prim";
	}

}
