import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/*
Authors: Alex Harry, Cory Johns, Justin Keeling
Date: April 2, 2018
Overview: Graph stores a two-dimensional array representation of the graph in the input file and
contains all the functions for running Prim’s, kruskal’s, and Floyd-Warshall's Algorithms as well as
printing the graph.
*/
public class Graph {
    private int graph_size = 0;
    private ArrayList<ArrayList<Integer>> graph = new ArrayList<ArrayList<Integer>>();
    private ArrayList<String> vertexes;

    public Graph() {

    }

    /**
     * Sets the graph size and initializes the array list
     *
     * @param size of the graph
     */
    public void set_graph_size(int size) {
        graph_size = size;
        // Initialize the graph
        for (int i = 0; i < graph_size; i++) {
            graph.add(new ArrayList<Integer>());
        }
    }

    /**
     * Adds all the vertex names given by the input to this graph's vertex name list
     *
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
     *
     * @param inNumber
     */
    public void insert(int inNumber) {
        boolean success = false;

        for (int i = 0; i < graph_size; i++) {
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

    public void prim() {
        // copy graph for use here
        ArrayList<ArrayList<Integer>> matrix_D = duplicate_matrix(graph);

        //starting vertex
        Vertex current = null;

        //used to randomly choose starting vertex
        int random_start = (int) (Math.random() * graph_size);

        //add all vertices to an Arraylist
        ArrayList<Vertex> vertices = new ArrayList<>();

        for (int i = 0; i < graph_size; i++) {
            //creates a new vertex with its 'edge' currently set to null and infinity, and the vertex's status is false.
            Vertex vertex = new Vertex(new MST_LinkedList(null, Main.infinity), false, i);

            //chooses 'start' equal to the'vertex' when 'i' = 'rand_start'
            if (random_start == i) {
                //set the 'start' vertex's status to true, and its length to 0: Requirements for start vertex
                current = vertex;
                current.status = true;
                current.edge.weight = 0;
                vertices.add(current);
            } else {
                vertices.add(vertex);
            }
        }
        // instance variable to put into a queue of edges
        Vertex compare = null;
        PriorityQueue<MST_LinkedList> edges = new PriorityQueue<MST_LinkedList>(graph_size, new MST_LL_Comparator());

        int min = Main.infinity;

        //checks the current vertexes edges from graph to put into queue
        for (int v = 0; v < graph_size; v++) {
            // vertex v from graph must not equal the current vertex, and must not be visited.
            if (v != current.index && !vertices.get(v).status) {
                // set 'compare' equal to the vertex object
                compare = vertices.get(v);
                //find the edge weight to put into queue
                compare.edge.weight = matrix_D.get(current.index).get(v);
                //assigns the predecessor as the current vertex
                compare.edge.pd = current;
                // add the edge to the queue
                edges.add(compare.edge);
                current = compare;
            }
            while (!edges.isEmpty()) {

            }
        }
        current.edge = edges.poll();


    }

    public void kruskal() {

        ArrayList<String> t = new ArrayList<String>(); //instantiates array t to return
        ArrayList<ArrayList<Integer>> d = duplicate_matrix(graph);  // copy of graph
        PriorityQueue<QueueEdge> q = new PriorityQueue<QueueEdge>();    //creates a priority queue for kruskal algo
        for (int i = 0; i < graph_size; i++) {
            for (int j = 0; j < graph_size; j++) {
                if (!is_max_value(d.get(i).get(j))) { // checks if vertex is not infinity
                    if (i != j) {   //checks that j != b, ex. A = A
                        q.add(new QueueEdge(d.get(i).get(j), vertexes.get(i), vertexes.get(j))); // Adds to the queue
                    }
                }
            }
        }
        do {    //loops while q is not empty
            QueueEdge temp = q.poll();  // temp variable that pulls from the queue
            if (!temp.getVert1().equals(temp.getVert2())) {
                t.add((temp.getVert1() + temp.getVert2()));    // adds to t array list
            }
        } while (!q.isEmpty());
        System.out.println(t);  // prints array list
    }


    /**
     * Runs Floyd-Warshall's algorithm on the instance variable graph
     * does not modify graph but makes a copy and then prints each step of the algorithm
     */
    public void floyd_warshall() {
        // copy graph for use here
        ArrayList<ArrayList<Integer>> d = duplicate_matrix(graph);

        // find the shortest path
        for (int k = 0; k < graph_size; k++) {
            for (int i = 0; i < graph_size; i++) {
                for (int j = 0; j < graph_size; j++) {
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
     *
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
     *
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
     *
     * @param matrix
     * @param vertex_names
     */
    public void print_graph(ArrayList<ArrayList<Integer>> matrix, ArrayList<String> vertex_names) {
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
     *
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

    public static class Vertex {

        MST_LinkedList edge;
        boolean status;
        int index;

        public Vertex(MST_LinkedList in_edge, boolean in_status, int in_index) {
            // variable to hold status if it has been visited or not
            status = in_status;
            // edge to get weight of a vertex.
            edge = in_edge;
            // index from array
            index = in_index;
        }
    }

    public static class MST_LinkedList {
        //essentially parent
        Vertex pd;

        int weight;

        public MST_LinkedList(Vertex in_pd, int in_length) {
            pd = in_pd;
            weight = in_length;
        }
    }

    public static class MST_LL_Comparator implements Comparator<MST_LinkedList> {
        // used to compare weights of edges.
        @Override
        public int compare(MST_LinkedList e1, MST_LinkedList e2) {
            if (e1.weight < e2.weight) {
                return 1;
            } else if (e1.weight > e2.weight) {
                return -1;
            }
            return 0;
        }
    }
}

