import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;

/*
Authors: Alex Harry, Cory Johns, Justin Keeling
Date: April 7, 2018
Overview: Graph stores a two-dimensional array representation of the graph in the input file and
contains all the functions for running Prim’s, Kruskal’s, and Floyd-Warshall's Algorithms as well as
printing the graph.
*/
public class Graph {
	// the number of vertexes in the graph
    private int numVerts = 0;
    // the base graph
    private ArrayList<ArrayList<Integer>> graph = new ArrayList<ArrayList<Integer>>();
    // a list of each vertex in the graph
    private ArrayList<Vertex> vertexes;

    /**
     * Sets the graph size and initializes the array list
     * @param size of the graph
     */
    public void set_graph_size(int size) {
        numVerts = size;
        // Initialize the graph
        for (int i = 0; i < numVerts; i++) {
            graph.add(new ArrayList<Integer>());
        }
    }

    /**
     * Adds all the vertex names given by the input to this graph's vertex name list
     * @param names of each vertex
     */
    public void set_vertexes(String[] names) {
        // Initialize the name list
        vertexes = new ArrayList<Vertex>();
        // append each name
        for (String name : names) {
            vertexes.add(new Vertex(names));
        }
    }

    /**
     * Inserts the given number into the graph at the next available position
     * @param inNumber
     */
    public void insert(int inNumber) {
        boolean success = false;

        for (int i = 0; i < numVerts; i++) {
            // append inNumber to the last empty location in the graph
            // REQUIRES graph_size to be accurate!
            if (graph.get(i).size() < numVerts) {
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
     * Finds the Minimum Spanning Tree (MST) of graph by Prim's Algorithm and prints its edges to the console
     */
    public void prim() {
    	/* definitions : 
    	 * - MST is a list of edges
    	 * - Edge is a class containing the weight, vertex 1, vertex 2 for the edge, the print value is vertex1.name + vertex2.name
    	 * - vertex is a class containing the vertex name, visited flag
    	 * Need:
    	 * - a list of all vertex objects (already implemented as the global vertexes field)
    	 * - MST (empty at start)
    	 * - a list of all edges in the graph but don’t include duplicates (QueueEdge.equals will work here)
    	 * 	- start the list of edges with references to the list of vertexes (use QueueEdge it is already set up for the priority queue)
    	 */
    	
    	// set up the start MST
    	ArrayList<QueueEdge> mst = new ArrayList<QueueEdge>();
    	// make a new priority queue
    	PriorityQueue<QueueEdge> queue = new PriorityQueue<QueueEdge>(new QueueEdge());
    	
    	// set up the list of edges without duplicates
    	// add all edges to the list
    	ArrayList<QueueEdge> edgeList = new ArrayList<QueueEdge>();
    	// loop though only the upper diagonal to prevent duplicates, by starting j at i
        for (int i = 0; i < numVerts; i++) {
            for (int j = i; j < numVerts; j++) {
            	// checks if vertex is not infinity
                if (!is_max_value(graph.get(i).get(j))) { 
                    if (i != j) {   //checks that j != b, ex. A = A
                        edgeList.add(new QueueEdge(graph.get(i).get(j), vertexes.get(i), vertexes.get(j))); // Adds to the queue
                    }
                }
            }
        }
        Random random = new Random();
    	  // pick a start vertex
        Vertex start = vertexes.get(random.nextInt(numVerts));

    	  // set vertex as visited
        start.setVisited();
        
        // add lowest weight edge from start to queue
        for (QueueEdge edge : edgeList) {
        	if (edge.getVert1().equals(start) || edge.getVert2().equals(start)) {
        		queue.add(edge);
        	}
        }
        // add lowest weight edge containing start to the MST
        mst.add(queue.poll());
        if (mst.get(0).getVert1().equals(start)) {
        	mst.get(0).getVert2().setVisited();
        }
        else {
        	mst.get(0).getVert1().setVisited();
        }

    	  // while MST has less then the number of vertexes - 1 edges in it
        while (mst.size() < numVerts - 1) {
        // empty the queue for the new local vertexes
			  queue.clear();
			
    		// for all vertexes in the MST (i.e. all vertexes with visited == true)
    		for (int e=0; e<mst.size(); e++) {
    			// make the cut i.e.:
    			// add all edges containing the visited vertexes in position 1 to a priority queue
    			// if visited is false for vertex 2
    			// check first vertex
    			if (mst.get(e).getVert1().isVisited()) {
    				queue.addAll(addAllLocalEdges(queue, mst.get(e).getVert1(), edgeList));
    			}
    			// check second vertex
    			if (mst.get(e).getVert2().isVisited()) {
    				queue.addAll(addAllLocalEdges(queue, mst.get(e).getVert2(), edgeList));
    			}
    		}
    		// pick the best one
			QueueEdge best = queue.poll();
			// add the corresponding edge to to the MST (i.e. vertex1 -> vertex2)
			mst.add(best);
			
			// set the other vertex as visited
			best.setAllVisited();
      }
        
        System.out.println(mst);  // prints array list
    }
    
    /**
     * Adds all edges that have vert in them to the given queue, by checking all edges in the given edgeList
     * @param queue to add to
     * @param vert to look for
     * @param edgeList to look in
     */
    private ArrayList<QueueEdge> addAllLocalEdges(PriorityQueue<QueueEdge> queue, Vertex vert, ArrayList<QueueEdge> edgeList) {
    	// set up a list of edges
    	ArrayList<QueueEdge> edges = new ArrayList<QueueEdge>();
    	
    	// find all edges that have the parameter vertex and a unvisited vertex
    	for (QueueEdge edge : edgeList) {
    		// check vert 1 for parameter vertex
    		if (edge.getVert1().equals(vert) && !edge.getVert2().isVisited()) {
    			edges.add(edge);
    		}
    		// check vert 2 for parameter vertex
    		else if (edge.getVert2().equals(vert) && !edge.getVert1().isVisited()) {
    			edges.add(edge);
    		}
    	}
    	// return all the edges that where found
    	return edges;
    }

    /**
     * Finds the Minimum Spanning Tree (MST) of graph by Kruskal's Algorithm and prints its edges to the console
     */
    public void kruskal() {
        ArrayList<String> t = new ArrayList<String>(); //instantiates array t to return
        PriorityQueue<QueueEdge> q = new PriorityQueue<QueueEdge>(new QueueEdge());    //creates a priority queue for kruskal algo
        Cluster clusterList = new Cluster(vertexes);  // creates a list of elementary clusters and will manage all clusters
        
        // add all edges to the queue
        for (int i = 0; i < numVerts; i++) {
        	// loop though only the upper diagonal, by starting j at i
            for (int j = i; j < numVerts; j++) {
            	// checks if vertex is not infinity
                if (!is_max_value(graph.get(i).get(j))) { 
                    if (i != j) {   //checks that j != b, ex. A = A
                        q.add(new QueueEdge(graph.get(i).get(j), vertexes.get(i), vertexes.get(j))); // Adds to the queue
                    }
                }
            }
        }
        while (t.size() < numVerts - 1){    //loops while number of edges in MST is less then n-1
            QueueEdge edge_poll = q.poll();  // temp variable that pulls from the queue
            
            // check if the two vertexes of the poll edge are not in the same cluster
            if (!clusterList.isSameCluster(edge_poll.getVert1(), edge_poll.getVert2())) {
            	// adds to t array list
                t.add((edge_poll.getVert1().name + edge_poll.getVert2().name));
                // merge clusters of vert 1 & 2
                clusterList.mergeClusters(edge_poll.getVert1(), edge_poll.getVert2());
            }
        }
        System.out.println(t);  // prints array list
    }

    /**
     * Runs Floyd-Warshall's algorithm on the instance variable graph
     * does not modify graph but makes a copy and then prints each step of the algorithm
     */
    public void floyd_warshall() {
        // copy graph for use here
        ArrayList<ArrayList<Integer>> d = duplicate_matrix(graph);
        
        // set diagonal to 0
        for (int i=0; i < numVerts; i++) {
        	d.get(i).set(i, 0);
        }

        // find the shortest path
        for (int k = 0; k < numVerts; k++) {
            for (int i = 0; i < numVerts; i++) {
                for (int j = 0; j < numVerts; j++) {
                    // cannot perform arithmetic on infinity
                    if (!is_max_value(d.get(i).get(k).intValue()) && !is_max_value(d.get(k).get(j).intValue())) {
                        // test if path is shorter then current one
                        if (d.get(i).get(j).intValue() > d.get(i).get(k).intValue() + d.get(k).get(j).intValue()) {
                            // path is shorter, replace current
                            d.get(i).set(j, d.get(i).get(k).intValue() + d.get(k).get(j).intValue());
                            // print this step
                            print_graph(d, vertexes);
                        }
                    }
                }
            }
        }
    }

    /**
     * Makes a copy of the given matrix that is independent of the original
     * @param matrix ,the input matrix
     * @return a new matrix with the same content as the input
     */
    private ArrayList<ArrayList<Integer>> duplicate_matrix(ArrayList<ArrayList<Integer>> matrix) {
        // Initialize the min weight graph
        ArrayList<ArrayList<Integer>> d = new ArrayList<ArrayList<Integer>>();

        // Initialize the graph
        for (int i = 0; i < matrix.size(); i++) {
            d.add(new ArrayList<Integer>());
        }

        // add initial values
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                // graph is already initialized
                d.get(i).add(matrix.get(i).get(j).intValue());
            }
        }
        return d;
    }

    /**
     * Tests if the input is the designated infinity for the adjacency matrix as determined by Main.java
     * @param test
     * @return true if test is equal to the infinity value of Main
     */
    private boolean is_max_value(int test) {
        return test == Main.infinity;
    }

    /**
     * Prints the current version of the instance variable graph
     */
    public void print_graph() {
        print_graph(graph, vertexes);
    }

    /**
     * Prints the given matrix with the given vertex names
     * @param matrix
     * @param vertex_names
     */
    public void print_graph(ArrayList<ArrayList<Integer>> matrix, ArrayList<Vertex> vertex_names) {
        // print a spacer
        System.out.print("  ");
        // print vertex names along the top of the matrix
        for (int i = 0; i < vertex_names.size() - 1; i++) {
            System.out.print(vertex_names.get(i) + ",");
        }
        // print last vertex name with a newline instead of a ","
        System.out.print(vertex_names.get(vertex_names.size() - 1) + "\n");

        // print each row of the matrix
        for (int i = 0; i < matrix.size(); i++) {
            // print vertex name
            System.out.print(vertex_names.get(i) + " ");

            // print the values
            for (int j = 0; j < matrix.get(i).size() - 1; j++) {
                System.out.print(get_print_value(matrix, i, j) + ",");
            }
            // print the last value with a newline instead of a ","
            System.out.print(get_print_value(matrix, i, matrix.get(i).size() - 1) + "\n");
        }
        // print a space at the end of the matrix
        System.out.println();
    }

    /**
     * Gets the String that represents the value at the given row & col of the matrix
     * @param matrix the source matrix
     * @param row    of the value
     * @param col    of the value
     * @return the representative String
     */
    private String get_print_value(ArrayList<ArrayList<Integer>> matrix, int row, int col) {
        int value = matrix.get(row).get(col).intValue();
        if (is_max_value(value)) {
            return "\u221E";
        } else {
            return "" + value;
        }
    }
}

