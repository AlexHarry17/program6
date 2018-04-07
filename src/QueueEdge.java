/*
Authors: Alex Harry, Cory Johns, Justin Keeling
Date: April 2, 2018
Overview: QueueEdge contains the weight and both vertexes that compose this edge as well as all
needed functions for interacting with it such as a comparable definition
*/
public class QueueEdge implements Comparable<QueueEdge>{
    private int edge;
    private String vert1;
    private String vert2;


    public int getEdge() {
        return edge;
    }   //getter for edge value

    public String getVert1() {
        return vert1;
    }   //getter for vertex 1

    public String getVert2() {
        return vert2;
    }   //getter for vertex 2



    public QueueEdge(int inEdge, String inVert1, String inVert2) {
        edge = inEdge;
        vert1 = inVert1;
        vert2 = inVert2;
    }

    public int compareTo(QueueEdge otherQueue) {    //compares the queues for sorting
        if (this.edge > otherQueue.getEdge()) { // compares this edge with the edges in the queue
            return 1;   // returns if this edge is greater than an edge already in the queue
        } else {
            return -1; // returns if this edge is greater than an edge already in the queue

        }

    }

}
