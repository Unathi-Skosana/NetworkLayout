import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

/**
 * The {@code EdgeFreq} class represents a data type that allows better visualization
 * of nature of the data in a acyclic directed digraph by assigning colors
 * to the node connections (edges). The color assignments are based on the frequency
 * of the node connections.
 * <p>
 * This implementation uses a {@code HashMap} containing edge frequencies, another {@code HashMap}
 * mapping an edge (in the original input) to the spline edges associated with it and
 * finally an {@code ArrayList} for polling the occurrences. {@code EdgeFreq} does all the necessary
 * processing in the constructor, so clients can query what color is an edge.
 *
 * Red - Most Frequent
 * Orange - Second Most Frequent
 * Yellow - Third Most Frequent
 * Black - The rest.
 *
 * @author  Unathi Koketso Skosana
 * @version 1.1
 * @since   2017-02-06
 */

public class EdgeFreq {
    private HashMap<String, Integer> edgeFreqCount;
    private HashMap<String, ArrayList<String>> dummyNodes;
    private ArrayList<Integer> occurrenceSet;

    /**
     * Initialises a new instance of edgeFreq and does all the preprocessing.
     *
     * @param edgeFreqCount
     * @param dummyNodes
     */
    public EdgeFreq(HashMap<String, Integer> edgeFreqCount, HashMap<String,
            ArrayList<String>> dummyNodes) {
        this.edgeFreqCount = edgeFreqCount;
        this.dummyNodes = dummyNodes;
        this.occurrenceSet = new ArrayList<Integer>();
        spreadFrequencies();
        pollOccurrences();
    }

    /**
     * Returns the color an edge
     *
     * @param edge edge to be inspected
     * @return color of edge.
     */
    public String belongsTo(String edge) {
        if (!edgeFreqCount.containsKey(edge)) {
            return "";
        }
        int occurence = edgeFreqCount.get(edge);
        int index = -1; // Invalid index
        if (occurrenceSet.contains(occurence)) {
            index = occurrenceSet.indexOf(occurence);
        }

        return colorCode(index);
    }



    /**
     *  Associates the frequencies of the edges connecting
     *  dummy nodes, with the frequency of the edge that
     *  initially split to facilitate the dummy nodes.
     */
    private void spreadFrequencies() {
        String[] keys = edgeFreqCount.keySet().toArray(new String[0]);
        for (String key : keys) {
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
     * Polls the frequencies of the edges
     * to an occurrence set that filters out duplicates
     * and sorts the occurrences in ascending order. This will later
     * used to the determine the color groups in which
     * edges belong in.
     */
    private void pollOccurrences() {
        Set<String> keys = edgeFreqCount.keySet();
        for (String edge : keys) {
            int occurrence = edgeFreqCount.get(edge);
            if (!occurrenceSet.contains(occurrence)) {
                occurrenceSet.add(occurrence);
            }
        }
        Collections.sort(occurrenceSet);
    }

    /**
     * This helper method specifies the color group at certain index in
     * the occurrence set, taking into consideration the different sizes
     * of the occurrence set.
     *
     * @param  index index in the occurrence set
     * @return the color group
     */
    private String colorCode(int index) {
        int setSize = occurrenceSet.size();
        switch (setSize) {
            case 3: // There can only be three options, red, orange and black.
                switch (index) {
                    case 2:
                        return "red";
                    case 1:
                        return "orange";
                    default:
                        return "black";
                }
            case 2: // There can only be two options, red and black.
                switch (index) {
                    case 1:
                        return "red";
                    default:
                        return "black";
                }
            case 1: // All the edges have the exact occurrences.
                return "black";
            default: // In this case, the groups are located at the last indices of the sorted occurrence set.
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
