/**
 * EdgeColorer takes in a list of edges as
 * input and assigns the colors to edge
 * according to their frequency.
 *
 * Red - Most Frequent
 * Orange - Second Most Frequent
 * Yellow - Third Most Frequent
 * Black - The rest.
 */

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;
import java.util.Collections;

public class EdgeColorer {
    private HashMap<String, Integer> edgeFreqCount;
    private HashMap<String, ArrayList<String>> dummyNodes;
    private ArrayList<Integer> occurenceSet;

    /**
     * Constructor, that processes everything beforehand and allows the user
     * to query about the color groups of the edges.
     * @param edgeFreqCount
     * @param dummyNodes
     */
    public EdgeColorer(HashMap<String, Integer> edgeFreqCount, HashMap<String,
            ArrayList<String>> dummyNodes) {
        this.edgeFreqCount  = edgeFreqCount;
        this.dummyNodes = dummyNodes; 
        this.occurenceSet =  new ArrayList<Integer>();
        spreadFrequencies();
        pollOccurences();
    }
    
    /**
     *  Associates the frequencies of the edges connecting
     *  dummy nodes, with the frequency of the edge that
     *  initially split to facilitate the dummy nodes. 
     */
    private void spreadFrequencies() {
        String[] keys = edgeFreqCount.keySet().toArray(new String[0]);
        for (String key: keys) {
            if (dummyNodes.containsKey(key)) {
                ArrayList<String> splineEdges =
                    dummyNodes.get(key);
                for (String splineEdge : splineEdges) {
                    edgeFreqCount.put(splineEdge,
                            edgeFreqCount.get(key));
                }
            }
        }
    }
    
    /**
     * Polls the frequencies(occurrences) of the edges
     * to an occurrence set that filters out duplicates
     * and sorts the occurrences in ascending order. This will later
     * used to the determine the color groups in which
     * edges belong in.
     */
    private void pollOccurences() {
        Set<String> keys = edgeFreqCount.keySet();
        for (String edge: keys) {
            int occurence =  edgeFreqCount.get(edge);
            if (!occurenceSet.contains(occurence)) {
                occurenceSet.add(occurence);
            }
        }
        Collections.sort(occurenceSet);
    }

    /**
     * Specifies in which color group does edge(edge must be in the graph) belong to
     * using the frequency counts and the occurrence set , and calling colorCode()
     * to get the group.
     *   
     * @param tail the edge's tail
     * @param head the edge's head
     * @return returns the color group of edge.
     */
    public String belongsTo(int tail, int head) {
        String edge = tail + " -> " + head;
        if (!edgeFreqCount.containsKey(edge)) { return ""; }
            int occurence = edgeFreqCount.get(edge);
            int index = -1; // Invalid index
        if (occurenceSet.contains(occurence)) {
            index = occurenceSet.indexOf(occurence);
        }

        return colorCode(index);
    }
    
    /**
     * This method specifies the color group at certain index in
     * the occurrence set, considering different sizes of the
     * occurrence set.
     * 
     * @param  index frequency count of an edge
     * @return returns the color group by indexing the 
     *         occurrence set.
     */
    private String colorCode(int index) {
        int setSize = occurenceSet.size();
        switch (setSize) {
            case 3: // There can only be three options, red, orange or black.
                switch (index) {
                    case 2:
                        return "red";
                    case 1:
                        return "orange";
                    default:
                        return "black";
                }
            case 2: // There can only be two options, either red or black.
                switch (index) {
                    case 1:
                        return "red";
                    default:
                        return "black";
                }
            case 1: // All the edges exact occurrences 
                return "black";
            default: // In this case, the groups are located at the last indices of the  occurrence set(sorted)
                int redGroup = setSize - 1;
                int orangeGroup = setSize - 2;
                int yellowGroup = setSize - 3;

                if (index == redGroup) {
                    return "red";
                } else if (index == orangeGroup) {
                    return "orange";
                } else if (index == yellowGroup) {
                    return "yellow";
                } else {
                    return "black";
                }
        }
    }
}
