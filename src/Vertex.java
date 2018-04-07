
/*
Authors: Alex Harry, Cory Johns, Justin Keeling
Date: April 6, 2018
Overview: Vertex tracks the name and visited status of a vertex, it is basically a String with a visited flag.
*/
public class Vertex {
	// the name of this vertex
	public String name;
	// if this vertex has been included in the MST
	private boolean visited;
	
	public Vertex(String name) {
		this.name = name;
		visited = false;
	}
	
	/**
	 * Sets this vertex to visited
	 */
	public void setVisited() {
		visited = true;
	}
	
	/**
	 * Returns if this vertex has already been visited
	 * @return true if this vertex is in the MST
	 */
	public boolean isVisited() {
		return visited;
	}
	
	/**
	 * Tests if this vertex is the same as the given one
	 * @param other the vertex to compare to
	 * @return true if they represent the same vertex
	 */
	public boolean equals(Vertex other) {
		return name.equals(other.name);
	}
	
	public String toString() {
		return name;
	}
}
