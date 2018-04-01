import java.util.ArrayList;

/*
Authors: Alex Harry, Cory Johns, Justin Keeling
Date: March 31, 2018
Overview: Program reads in a graph containing an adjacency matrix
*/
public class Graph {
	private int graph_size = 0;
	private ArrayList<ArrayList<Integer>> graph = new ArrayList<ArrayList<Integer>>();
	private ArrayList<String> vertexes;

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
     * Adds all the vertex names given by the input to this graph's vertex name list
     * @param names of each vertex
     */
    public void set_vertexes(String[] names) {
    	// Initialize the name list
    	vertexes = new ArrayList<String>();
    	// append each name
    	for (String name : names) {
    		vertexes.add(name);
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
    	// print a spacer
    	System.out.print("  ");
    	// print vertex names along the top of the matrix
    	for (int i=0; i<vertexes.size() - 1; i++) {
    		System.out.print(vertexes.get(i)+ ",");
    	}
    	// print last vertex name with a newline instead of a ","
    	System.out.print(vertexes.get(vertexes.size() - 1)+ "\n");
    	
    	// print each row of the matrix
    	for (int i=0; i<graph.size(); i++) {
    		// print vertex name
    		System.out.print(vertexes.get(i) + " ");
    		
    		// print the values
    		for (int j=0; j<graph.get(i).size() - 1; j++) {
    			System.out.print(get_print_value(i, j) + ",");
    		}
    		// print the last value with a newline instead of a ","
    		System.out.print(get_print_value(i, graph.get(i).size() - 1) + "\n");
    	}
    }
    
    /**
     * Gets the String that represents the value at the given row & col of the matrix
     * @param row of the value
     * @param col of the value
     * @return the representative String
     */
    private String get_print_value(int row, int col) {
    	int value = graph.get(row).get(col).intValue();
    	if (value == Integer.MAX_VALUE) {
    		return "\u221E";
    	}
    	else {
    		return "" + value;
    	}
    }

}
