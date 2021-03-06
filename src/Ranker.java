/* Default java imports */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/* User defined imports */
import algs4.AcyclicLP;
import algs4.Digraph;

/**
 * {@code Ranker} is a class that represents a data type that ranks the nodes
 * in a directed graph into separate sets, A node is placed in rank according to it's
 * indegree and outdegree.
 * <p>
 * @author Unathi Koketso Skosana
 * @version 1.0
 * @since 2017-02-05
 */

public class Ranker {
    private ArrayList<Integer> minSet;
    private ArrayList<Integer> maxSet;
    private Digraph G;
    private Digraph newG;
    private boolean[] ranked;
    private int nodeCount;
    private int newNodeCount;
    private int distToMaxFromMin;
    private HashMap<Integer,
            ArrayList<Integer>> rankSets;
    private HashMap<String,
            ArrayList<String>> dummyEdges;

    /**
     * Initializes a ranker.
     *
     * @param G Digraph to be ranked.
     */

    public Ranker(Digraph G) {
        this.G = G;
        this.ranked = new boolean[G.V()];
        this.nodeCount = G.V();
        this.newNodeCount = G.V();
        this.rankSets = new HashMap<Integer,
                ArrayList<Integer>>();
        this.dummyEdges = new HashMap<String,
                ArrayList<String>>();
        collectSets();
        addDummyNodes();
    }

    /**
     * Finds the minimum and maximum rank sets.
     *
     */

    public void findExtremaSets() {
        this.maxSet = new ArrayList<Integer>();
        this.minSet = new ArrayList<Integer>();
        for (int v = 0; v < G.V(); v++) {
            if (G.indegree(v) == 0) {
                if (!maxSet.contains(v)) {
                    maxSet.add(v);
                    ranked[v] = true;
                }
            }
            for (int w: G.adj(v)) {
                if (G.outdegree(w) == 0) {
                    if (!minSet.contains(w)) {
                        minSet.add(w);
                        ranked[w] = true;
                    }
                }
            }
        }
    }

    /**
     * Finds ranks in between the minimum and maximum set.
     *
     */

    public void findOtherSets() {
        for (int v = 0; v < G.V(); v++) {
            if (!ranked[v]) {
                AcyclicLP lp = new AcyclicLP(G, v);
                int p = findLPToSmin(lp);
                addToSet(p + 1, v);
                ranked[v] = true;
            }
        }
    }

    /**
     * Returns the ranked digraph.
     *
     * @return Digraph after being ranked
     */

    public Digraph  getRankedDigraph() {
        return newG;
    }

    /**
     * Returns the minimum set.
     *
     * @return minimum rank set
     */

    public ArrayList<Integer> getMinSet() {
        return minSet;
    }

    /**
     * Returns the maximum set.
     *
     * @return maximum set
     */

    public ArrayList<Integer> getMaxSet() {
        return maxSet;
    }

    /**
     * Returns all the node rank sets.
     *
     * @return every rank sets.
     */

    public HashMap<Integer
        , ArrayList<Integer>> getRankSets() {
        return rankSets;
    }

    /**
     * Returns the distance from the maximum set
     * to the minimum set.
     *
     * @return distance from the max set to min set
     */

    public int getDistToMaxFromMin() {
        return distToMaxFromMin;
    }

    /**
     * Returns the vertices of the new Digraph.
     *
     * @return vertices of the new Digraph
     */

    public ArrayList<String[]> getVertices() {
        ArrayList<String[]> edges = new ArrayList<String[]>();
        for (int v = 0; v < newG.V(); v++) {
            for (int w: newG.adj(v)) {
                String[] edge = new String[2];
                edge[0] = v + "";
                edge[1] = w + "";
                edges.add(edge);
            }
        }
        return edges;
    }

    /**
     * Returns the map, mapping an edge with the dummy edges associated
     * with that edge.
     *
     * @return dummyEdges
     */

    public HashMap<String, ArrayList<String>> getDummyEdges() {
        return dummyEdges;
    }

    /**
     * toString method
     *
     * @return String representation
     */

    public String toString() {
        StringBuilder s = new StringBuilder("");
        Set<Integer> keys = rankSets.keySet();
        for (int rank : keys) {
            if (rank == distToMaxFromMin + 1) {
                s.append(rankSets.get(rank).toString());
            } else {
                s.append(rankSets.get(rank).toString() + " <- ");
            }
        }
        return s.toString();
    }

    /**
     * Finds longest path from the edge instantiated with an AcyclicLP
     * object.
     *
     * @param lp AcyclicLP from the source.
     * @return max longest path
     */

    private int findLPToSmin(AcyclicLP lp) {
        double max = 0.0;
        for (int w: minSet) {
            double p  = lp.distTo(w);
            if (p > max) {
                max = p;
            }
        }
        return (int) max;
    }

    /**
     * Finds the maximum distance from any of the nodes in the minimum set
     * to the nodes in the maximum.
     *
     * @return the maximum distance.
     */

    private int distToMaxFromMin() {
        double max = 0.0;
        for (int w: maxSet) {
            AcyclicLP lp = new AcyclicLP(G, w);
            for (int v: minSet) {
                if (lp.hasPathTo(v)) {
                    if (lp.distTo(v) > max) {
                        max = lp.distTo(v);
                    }
                }
            }
        }
        return (int) max;
    }

    /**
     * Adds a node to a set ranked index.
     *
     * @param rankNum rank number
     * @param node  node to be added
     */

    private void addToSet(int rankNum, int node) {
        if (rankSets.containsKey(rankNum)) {
            rankSets.get(rankNum).add(node);
        } else {
            rankSets.put(rankNum, new ArrayList<Integer>());
            rankSets.get(rankNum).add(node);
        }
    }

    /**
     * Maps ranks sets onto to their rank numbers.
     *
     */

    private void collectSets() {
        findExtremaSets();
        findOtherSets();
        this.distToMaxFromMin = distToMaxFromMin();
        rankSets.put(1, minSet);
        rankSets.put(distToMaxFromMin + 1, maxSet);
    }

    /**
     * Adds dummy nodes in between nodes that have edges that stretch over
     * more than one rank.
     *
     */

    private void addDummyNodes() {
        Set<Integer> keys = rankSets.keySet();
        for (int s: keys) {
            ArrayList<Integer> vertexSet = rankSets.get(s);
            search(vertexSet, s);
        }
        newG = new Digraph(nodeCount);
        for (int s: keys) {
            ArrayList<Integer> vertexSet = rankSets.get(s);
            populateNewDigraph(vertexSet, s);
        }
    }

    /**
     * From a node in rank s, searches for a node in it's adjacency list
     * that stretches over two or more ranks.
     *
     * @param set nodes in rank s.
     * @param source   source rank
     */

    private void search(ArrayList<Integer> set , int source) {
        int size = set.size();
        for (int i = 0; i < size; i++) {
            int v = set.get(i);
            for (int w: G.adj(v)) {
                Set<Integer> keys = rankSets.keySet();
                for (int dest: keys) {
                    if (dest != source)
                        if (rankSets.get(dest).contains(w)
                                &&  Math.abs(dest - source) > 1) {
                            rankSets.get(dest + 1).add(nodeCount);
                            nodeCount++;
                            percolateDummyNode(source, dest + 1);
                        }
                }
            }
        }
    }

    /**
     * Percolates a node in between nodes that have edges that stretch over
     * more than one rank.
     *
     * @param from starting rank
     * @param to   destination rank
     */

    private void percolateDummyNode(int from, int to) {
        if (to < from) {
            int temp = from;
            from = to;
            to = temp;
        }

        for (int i = from + 1; i < to; i++) {
            rankSets.get(i).add(nodeCount);
            nodeCount++;
        }
    }

    /**
     * Instantiates a new Digraph instance from the old Digraph after the
     * addition of the dummy nodes.
     *
     */

    private void populateNewDigraph(
            ArrayList<Integer> set, int source) {
        int size = set.size();
        for (int i = 0; i < size; i++) {
            int v = set.get(i);
            if (v > G.V() - 1) { continue; }
            for (int w: G.adj(v)) {
                Set<Integer> keys = rankSets.keySet();
                ArrayList<Integer> outcasts =
                        new ArrayList<Integer>();
                for (int dest: keys) {
                    if (dest != source)
                        if (rankSets.get(dest).contains(w)
                                && Math.abs(dest - source) > 1) {
                            connectNodes(v, w, dest + 1, source);
                            newNodeCount++;
                            outcasts.add(w);
                        }
                }
                if (!outcasts.contains(w)) {
                    newG.addEdge(v, w);
                }
            }
        }
    }

    /**
     * Connects the dummy nodes added in between ranks.
     *
     * @param startNode node to start from
     * @param from starting rank number
     * @param to destination rank number
     */

    private void connectNodes(
            int startNode,
            int endNode,
            int from,
            int to) {

        ArrayList<String> virtualNodes = new ArrayList<String>();
        newG.addEdge(newNodeCount, endNode);
        virtualNodes.add(
                constructEdge(
                        newNodeCount,
                        endNode
                )
        );
        for (int i = from + 1; i < to; i++) {
            newG.addEdge(
                    newNodeCount + 1,
                    newNodeCount
            );
            virtualNodes.add(
                    constructEdge(
                            newNodeCount + 1,
                            newNodeCount
                    )
            );
            newNodeCount++;
        }
        newG.addEdge(startNode, newNodeCount);
        virtualNodes.add(
                constructEdge(
                        startNode,
                        newNodeCount
                )
        );
        dummyEdges.put(
                constructEdge(startNode, endNode),
                virtualNodes
        );
    }

    /**
     * Takes in values of the tail and head of
     * an edge and formats it as String.
     *
     * @return an edge formatted as
     *         a String
     */

    private String constructEdge(int tail, int head) {
        return tail + " -> " + head;
    }
}