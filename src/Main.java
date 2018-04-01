/*
Authors: Alex Harry, Cory Johns, Justin Keeling
Date: March 24, 2018
Overview: Program reads in a graph containing an adjacency matrix
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        int infinity = Integer.MAX_VALUE;
        Path file = Paths.get("input/input.csv");   //  path file location to grab input file
        String currentLine;
        Graph graph = new Graph();;
        boolean doOnce = true;
        int current_row = 0;
        int size = 0;
        
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
            			graph.set_graph_size(size);
            			graph.set_vertexes(splitLine);
            			doOnce = false;
            		}
            		// increment current row
            		else {
            			current_row++;
            			// by placing this block here the A,B,C,.. line will be skipped
            			for (int i = 0; i < splitLine.length; i++) {   //for loop to insert input file into a graph
                            if (splitLine[i].equals("\u221E")) {    // checks for infinity by matching its unicode value
                                graph.insert(infinity); //inserts infinity value
                            } else {
                                graph.insert(Integer.parseInt(splitLine[i]));   //inserts non infinity value
                            }
                        }
            		}
            		
                    
                    
                    // check if graph is full
                    if (current_row == size) {
                    	// reset variables
                    	doOnce = true;
                    	current_row = 0;
                    	// print the graph
                    	graph.print_graph();
                    	System.out.println();
                    }
            	}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}