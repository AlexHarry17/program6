import java.util.ArrayList;

/*
Authors: Alex Harry, Cory Johns, Justin Keeling
Date: April 6, 2018
Overview: Cluster contains a list of all current vertex clusters in the graph as used by Kruskal’s Algorithm.
The cluster list may be initialized by declaring a new Cluster and giving it a String array of all the vertex names in
the graph. This will make a list of clusters each containing one vertex. Then simply use the isSameCluster and mergeClusters
methods as needed by the algorithm.
*/
public class Cluster {
	// the list of clusters currently in the graph, vertexes should only appear once in the cluster list
	private ArrayList<ArrayList<String>> clusterList;
	
	/**
	 * Initializes a cluster list with each of the given vertexes in there own cluster
	 * @param vertexes list of vertexes in the graph
	 */
	public Cluster(ArrayList<Vertex> vertexes) {
		clusterList = new ArrayList<ArrayList<String>>();
		for (int i=0; i<vertexes.size(); i++) {
			// add a new cluster
			clusterList.add(new ArrayList<String>());
			// add the vertex to the cluster
			clusterList.get(i).add(vertexes.get(i).name);
		}
	}
	
	/**
	 * Checks if the two given vertexes are in the same cluster
	 * @param vert1
	 * @param vert2
	 * @return true if they are in the same cluster, false otherwise
	 */
	public boolean isSameCluster(Vertex vert1, Vertex vert2) {
		// cluster index of vert 1
		int tmpCluster1 = getCluster(vert1.name);
		// cluster index of vert 2
		int tmpCluster2 = getCluster(vert2.name);
		
		// check that they are both in the cluster list
		if (tmpCluster1 != -1 && tmpCluster2 != -1) {
			// they are in the same cluster
			if (tmpCluster1 == tmpCluster2) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Merges the clusters of the given vertexes so that all vertexes in each cluster are put in to one final cluster
	 * @param vert1
	 * @param vert2
	 */
	public void mergeClusters(Vertex vert1, Vertex vert2) {
		// cluster index of vert 1
		int tmpCluster1 = getCluster(vert1.name);
		// cluster index of vert 2
		int tmpCluster2 = getCluster(vert2.name);
		
		for (int i=0; i<clusterList.get(tmpCluster1).size(); i++) {
			// add the ith value of cluster 1 to cluster 2
			clusterList.get(tmpCluster2).add(clusterList.get(tmpCluster1).get(i));
		}
		// remove cluster 1
		clusterList.remove(tmpCluster1);
	}
	
	/**
	 * Finds the index of the cluster containing the vertex or -1 if the vertex was not found
	 * @param vert the vertex to search for
	 * @return index of the cluster containing vert
	 */
	private int getCluster(String vert) {
		for (int i=0; i<clusterList.size(); i++) {
			for (int j=0; j<clusterList.get(i).size(); j++) {
				if (vert.equals(clusterList.get(i).get(j))) {
					return i;
				}
			}
		}
		return -1;
	}
}
