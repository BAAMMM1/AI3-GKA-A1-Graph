package mvc.model.algorithmusSystem;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public abstract class Algorithmus {
	
	public abstract Graph start(Node source, Node target);

}
