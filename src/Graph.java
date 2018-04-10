import java.util.ArrayList;
import java.util.PriorityQueue;

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
    private PriorityQueue<QueueEdge> edges;

    /**
     * Sets the graph size and initializes the array list
     * @param size of the graph
     */
    public void set_numVerts(int size) {
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
            vertexes.add(new Vertex(name));
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
            // REQUIRES numVerts to be accurate!
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
     * Runs Prims Algorithm to create a Minimum Search Tree.
     * Does not modify graph, only makes references from it.
     */
    public void prim() {
        //starting vertex
        Vertex current = null;

        //used to randomly choose starting vertex
        int random_start = (int) (Math.random() * numVerts);

        //add all vertices to an ArrayList
        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<QueueEdge> mst = new ArrayList<>();

        // instantiates PriorityQueue when size is applicable.
        edges = new PriorityQueue<>((numVerts * numVerts));

        for (int i = 0; i < numVerts; i++) {
            // create new vertex for each vertex.
            Vertex vertex = new Vertex(vertexes.get(i).name);
            vertex.set_index(i);
            //chooses 'start' equal to the'vertex' when 'i' = 'rand_start'.
            if (random_start == i) {
                //set the 'current' vertex's visited to true, and its length to 0: Requirements for start vertex.
                current = vertex;
                // change status of current vertex.
                current.setVisited();
                // adds all vertices to ArrayList for future reference.
                vertices.add(current);
            } else {
                // adds all vertices to ArrayList for future reference.
                vertices.add(vertex);
            }
        }
        // add elements to queue before while loop
        add_to_queue(vertices, current);
        while (edges.peek() != null) {
            // obtains the minimum edge.
            QueueEdge min_edge = edges.poll();
            // obtains the new current vertex.
            current = min_edge.getVert2();
            mst.add(min_edge);
            if (!current.isVisited()) {
                current.setVisited();
            }
            // add more edges to queue from a new vertex.
            add_to_queue(vertices, current);
        }
        System.out.println(mst);    //prints the mst for prims algorithm
    }

    /**
     * This method adds every possible edge to the queue from a given vertex
     *
     * @param vertices
     * @param current
     */
    private void add_to_queue(ArrayList<Vertex> vertices, Vertex current) {
       int index = current.get_index();
        // checks each vertex.
        for (int i = 0; i < numVerts; i++) {
            // remove invalid edges.
            check_queue_invalid_edges();
            //if the edge in the graph does not equal infinity from the current vertex to the i'th vertex, set i'th vertex edge-weight.
            if (graph.get(index).get(i) != Integer.MAX_VALUE && !vertices.get(i).isVisited()) {
                // create a new edge with its predecessor as current.
                QueueEdge edge = new QueueEdge(graph.get(index).get(i), current, vertices.get(i));
                // adds the new edge to PriorityQueue.
                edges.add(edge);

            }
        }
    }

    /**
     * This method checks an edge's validity PriorityQueue.
     * If invalid, then its removes from queue.
     */
    public void check_queue_invalid_edges() {
        // creates an array of the PriorityQueue.
        Object[] array = edges.toArray();
        // variable to check the edges validity.
        QueueEdge e;
        // checks each edge in queue.
        for (int i = 0; i < edges.size(); i++) {
            e = (QueueEdge) array[i];
            // if the tail has been visited, then it is not a valid edge anymore,remove it.
            if (e.getVert2().isVisited()) {
                edges.remove(e);
            }
        }
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
