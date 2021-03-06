import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
Authors: Alex Harry, Cory Johns, Justin Keeling
Date: April 7, 2018
Overview: Program reads in the graph containing the adjacency matrix from the input file at ./input/input.csv
and repeats the following until there are no more graphs remaining in the input:
	- prints the graph
	- runs Prim’s Algorithm and prints the edges in the minimum spanning tree
	- runs Kruuskal’s Algorithm and prints the edges in the minimum spanning tree
	- runs Floyd-Warshall's Algorithm and prints every step in finding all the shortest paths
*/
public class Main {
	// the value that represents infinity for this program
	public static int infinity = Integer.MAX_VALUE;
	// path to the input file
	private static Path file = Paths.get("input/input.csv");
	// the contents of the current line in the input file
    private static String currentLine;
    // the graph object that will be made from the input file
    private static Graph graph = new Graph();
    // simple use tracker variables
    private static boolean doOnce = true; // for trigering special conditions
    private static int current_row = 0;   // the current row of the matrix that is being added
    private static int size = 0;          // the size of the matrix (the number of vertexes)

    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(file)) {   //creates a new file reader
            while ((currentLine = reader.readLine()) != null) {
            	// ignore lines to small to be matrixes
            	if (currentLine.length() > 1) {
            		
            		//splits input file by comma, the commas will not be included in the resulting array
            		String[] splitLine = currentLine.split(",");
            		
            		// Initialize this graph
            		if (doOnce) {
            			size = splitLine.length;
            			graph = new Graph();
            			graph.set_numVerts(size);
            			graph.set_vertexes(splitLine);
            			doOnce = false;
            		}
            		// increment current row
            		else {
            			current_row++;
            			// by placing this block here the vertex name line will be skipped
            			for (int i = 0; i < splitLine.length; i++) {   //for loop to insert input file into a graph
                            if (splitLine[i].equals("\u221E")) {    // checks for infinity by matching its unicode value
                                graph.insert(infinity); //inserts infinity value
                            } else {
                                graph.insert(Integer.parseInt(splitLine[i]));   //inserts non infinity value
                            }
                        }
            		}
            		
                    
                    
                    // check if graph is full
                    if (current_row == size) {	// row 0 is actually when current_row = 1, since the vertex name line is 0
                    	// reset variables
                    	doOnce = true;
                    	current_row = 0;
                    	// print the graph for reference
                    	System.out.println("New Graph...");
                    	graph.print_graph();
                    	
                    	// run the Prim’s algorithm part
                    	System.out.println("Started Prim’s Algorithm...");
                    	graph.prim();
                    	
                    	// run the Kruuskal’s algorithm part
                    	System.out.println("Started Kruuskal’s Algorithm...");
                    	graph.kruskal();
                    	
                    	// run the Floyd-Warshall's algorithm part
                    	System.out.println("Started Floyd-Warshall's Algorithm...");
                    	graph.floyd_warshall();
                    }
            	}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}