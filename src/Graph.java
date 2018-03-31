import java.util.ArrayList;

/*
Authors: Alex Harry, Cory Johns, Justin Keeling
Date: March 31, 2018
Overview: Program reads in a graph containing an adjacency matrix
*/
public class Graph {
	private int graph_size = 0;
	private ArrayList<ArrayList<Integer>> graph = new ArrayList<ArrayList<Integer>>();

    public Graph(){ 
    	
    }
    
    /**
     * Sets the graph size and initializes the array list
     * @param size of the graph
     */
    public void set_graph_size(int size) {
    	graph_size = size;
    	// Initialize the graph
    	for (int i=0; i<graph_size; i++) {
			graph.add(new ArrayList<Integer>());
		}
    }

    /**
     * Inserts the given number into the graph at the next available position
     * @param inNumber
     */
    public void insert(int inNumber){
    	boolean success = false;
    	
    	for (int i=0; i<graph_size; i++) {
    		// append inNumber to the last empty location in the graph
    		// REQUIRES graph_size to be accurate!
    		if (graph.get(i).size() < graph_size) {
    			graph.get(i).add(inNumber);
    			success = true;
    			break;
    		}
    	}
    	if (!success) {
    		// should not happen if the input is formated correctly
    		System.out.println("Insert failed on: " + inNumber);
    	}
    }
    
    /**
     * Prints the current graph
     */
    public void print_graph() {
    	for (int i=0; i<graph.size(); i++) {
    		for (int j=0; j<graph.get(i).size() - 1; j++) {
    			System.out.print(graph.get(i).get(j).intValue() + ",");
    		}
    		System.out.print(graph.get(i).get(graph.get(i).size() - 1).intValue() + "\n");
    	}
    }

}
