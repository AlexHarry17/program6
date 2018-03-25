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
        Path file = Paths.get("input/input.txt");   //  path file location to grab input file
        String currentLine;
        Graph graph = new Graph();
        try (BufferedReader reader = Files.newBufferedReader(file)) {   //creates a new file reader
            while ((currentLine = reader.readLine()) != null) {
                String[] splitLine = currentLine.split(",");  //    splits input file by comma


                for (int i = 0; i < splitLine.length; i++) {   //for loop to insert input file into a graph

                    if (!splitLine[i].equals(",")) {    //checks for comma to insert only characters
                        if (Integer.parseInt(splitLine[i]) == 236) {    // checks for infinity with ascii value 236
                            graph.insert(infinity); //inserts infinity value
                        } else {
                            graph.insert(Integer.parseInt(splitLine[i]));   //inserts non infinity value
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}