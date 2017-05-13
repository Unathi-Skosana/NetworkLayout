/**
 * Layout is class that does the necessary processing to
 * a directed graph to minimize edge crossings
 * this is achieved by using the barycentric method
 * and comparative sorting on the positions generated
 * by barycentric method.
 *
 * @author  Unathi Koketso Skosana
 * @version 1.0
 * @since   2017-27-02
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.Collections;

public class Layout {
    private Digraph G;
    private Ranker ranker;
    private HashMap<Integer, ArrayList<Integer>> ranks;

    /**
     * Instantiates a layout instance
     * @param ranker Ranker instance that internally
     *               has the ranked directed graph.
     */

    public Layout(Ranker ranker) {
        this.ranker = ranker;
        this.G = ranker.getRankedDigraph();
        this.ranks = ranker.getRankSets();
        untangleFromMinimumSet();
    }

    /**
     *  Fixes the order of nodes in nextRank using the connections it
     *  has to the nodes in the currentRank, by using the barycenteric method
     *  this is basically equivalent to finding the position of the center
     *  of mass of each vertex in a rank.
     *
     * @param currentRank rank used to fix nextRank
     * @param nextRank rank that is ordered.
     * @return returns nextRank optimized for minimal edge crossings.
     */

    private ArrayList<Integer> fixOrder(
            ArrayList<Integer> currentRank
            , ArrayList<Integer> nextRank) {
        ArrayList<Double> ratios = new ArrayList<Double>();
        for (int j = 0; j < nextRank.size(); j++) {
            int v = nextRank.get(j);
            int tally= 0;
            for (int i = 0; i < currentRank.size(); i++) {
                int w = currentRank.get(i);
                if (isAdjTo(v, w)) {
                   tally = tally + currentRank.indexOf(w);
                }
            }
            ratios.add(1.00*tally/G.outdegree(v)); // Add position of center of mass.
        }
        return comparatorSort(ratios, nextRank);
    }

    /**
     * This sorts an ArrayList of integers, then mirroring the sort to
     * another ArrayList of doubles.
     *
     * @return returns the ArrayList<Double> 'sorted' using the other list.
     */
    private ArrayList<Integer> comparatorSort(
            ArrayList<Double> indices
            , ArrayList<Integer> list) {
        Double[] indicesArray =
            indices.toArray(new Double[indices.size()]);
        
        Integer[] listArray  =
            list.toArray(new Integer[list.size()]);
        
        Shell.sort(indicesArray, listArray);
        
        ArrayList<Integer> sorted =
            new ArrayList<Integer>(Arrays.asList(listArray));
        return sorted;
    }

    /**
     *  Iterates the over the adjacency list of tail
     *  in the graph to establish adjacency between
     *  the two nodes.
     *
     * @param tail tail of the edge
     * @param head head of the edge
     * @return returns @true if tail is adjacent to head
     *         otherwise returns @false.
     */
    public boolean isAdjTo(int tail, int head) {
       for (int w: G.adj(tail)) {
          if (head == w) {
            return true;
          }
       }
       return false;
    }

    /**
     * Fixes the node ordering in the minimum set
     * by sorting the nodes in the minimum set.
     */
    private void fixMinSetOrder() {
        Collections.sort(ranker.getMinSet());
    }

    /**
     * Untangles the graph rank by rank from the minimum up to the maximum
     * rank's predecesor using fixOrder() to get minimal edge crossings in the
     * graph.
     */
    public void untangleFromMinimumSet() {
        fixMinSetOrder();
        Set<Integer> keys = ranks.keySet();
        for (int key: keys) {
            if (key > ranker.getDistToMaxFromMin()
                || ranks.get(key).size() == 1
                || ranks.get(key+1).size() == 1) {
                continue;
            }
            ArrayList<Integer> fixed = fixOrder(ranks.get(key),
                    ranks.get(key+1));
            ranks.put(key+1, fixed);
       }
    }

    /**
     * Getter method
     * @return returns a ranker instance with minimal edge crossings.
     */
    public Ranker getUntangledRanker() {
        return ranker;
    }
}
