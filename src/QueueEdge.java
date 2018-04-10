import java.util.Comparator;

/*
Authors: Alex Harry, Cory Johns, Justin Keeling
Date: April 7, 2018
Overview: QueueEdge contains the weight and both vertexes that compose this edge as well as all
needed functions for interacting with it such as a comparable definition
*/
public class QueueEdge implements Comparator<QueueEdge>, Comparable<QueueEdge>{
    private int edge;
    private Vertex vert1;
    private Vertex vert2;


    public int getEdge() {
        return edge;
    }   //getter for edge value

    public Vertex getVert1() {
        return vert1;
    }   //getter for vertex 1

    public Vertex getVert2() {
        return vert2;
    }   //getter for vertex 2
    
    /**
     * For use as the comparator don't use as normal constructor
     */
    public QueueEdge() {
    	
    }

    public QueueEdge(int inEdge, Vertex inVert1, Vertex inVert2) {
        edge = inEdge;
        vert1 = inVert1;
        vert2 = inVert2;
    }
    
    /**
     * Checks if this edge contains the given vertex
     * @param vert
     * @return true if vert is in this edge
     */
    public boolean containsVert(Vertex vert) {
    	return vert1.equals(vert) || vert2.equals(vert);
    }
    
    /**
     * gets the other vertex in this edge when given one of the vertexes
     * @param vert
     * @return the vertex other than vert, null if vert is not in the edge
     */
    public Vertex getOtherVert(Vertex vert) {
    	if (vert1.equals(vert)) {
    		return vert2;
    	}
    	else if (vert2.equals(vert)) {
    		return vert1;
    	}
    	else {
    		return null;
    	}
    }
    
    /**
     * Sets all vertexes in this edge to visited
     */
    public void setAllVisited() {
    	vert1.setVisited();
    	vert2.setVisited();
    }
    
    /**
     * Tests if this edge object represents the same edge as the given Edge
     * @param otherEdge the edge to test against
     * @return true if they represent the same edge
     */
    public boolean equals(QueueEdge otherEdge) {
    	// weight should be the same if the vertex pair is the same
		// check if it has the same vertex pair
		if (vert1.equals(otherEdge.vert1) && vert2.equals(otherEdge.vert2)) {
			return true;
		}
		// check if the vertex pair are reversed
		else if (vert2.equals(otherEdge.vert1) && vert1.equals(otherEdge.vert2)) {
			return true;
		}
		else {
			return false;
		}
    }

    public int compareTo(QueueEdge otherQueue) {    //compares the queues for sorting
        if (this.edge >= otherQueue.getEdge()) { // compares this edge with the edges in the queue
            return 1;   // returns if this edge is greater than an edge already in the queue
        } else {
            return -1; // returns if this edge is greater than an edge already in the queue

        }
    }
    
    public int compare(QueueEdge e1, QueueEdge e2) {
    	if (e1.edge > e2.edge) {
    		return 1;
    	}
    	else if (e1.edge < e2.edge) {
    		return -1;
    	}
    	else {
    		return 0;
    	}
    }
    
    public String toString() {
    	return vert1.name + vert2.name;
    }
}
