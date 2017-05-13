/**
 * InputParser does all the preprocessing on the
 * input, for later efficient use.
 *
 * @author  Unathi Koketso Skosana
 * @version 1.0
 * @since   2017-02-05
 */
import java.util.ArrayList;

public class InputParser {
    private Digraph G;
    private int nodeCount;
    private ArrayList<String[]> vertices;
    private ArrayList<String> edges;

    /**
     * Initializes an inputStream instance and
     * creates a Digraph instance.
     * @param inputStream
     */
    public InputParser(In inputStream) {
        addEdges(inputStream);
        initialiseDigraph();
        hasCycle();
    }

    /**
     * Initializes a Digraph instance.
     */
    private void initialiseDigraph() {
        G = new Digraph(nodeCount);
        for (int i = 0; i < vertices.size(); i++) {
            String[] vertex = vertices.get(i);
            int w = Integer.parseInt(vertex[0]);
            int v = Integer.parseInt(vertex[1]);
            isLoop(v, w);
            G.addEdge(w, v);
        }
    }

    /**
     * Extracts Digraph edges from inputStream, by splitting each line
     * on either side of delimiter " -> " and gets the number of nodes
     * in the graph.
     * @param inputStream
     */
    private void addEdges(In inputStream) {
        int max = 0;
        String[] vertex;
        edges = new ArrayList<String>();
        vertices = new ArrayList<String[]>();
        while (!inputStream.isEmpty()) {
            String dirEdge = inputStream.readLine();
            if (!edges.contains(dirEdge)) {
                vertex = dirEdge.split(" -> ");
                int w = Integer.parseInt(vertex[0]);
                int v = Integer.parseInt(vertex[1]);
                vertices.add(vertex);
                max = Math.max(Math.max(w, v), max);
            }
            edges.add(dirEdge);
        }
        this.nodeCount = max + 1;
    }

    /**
     * Checks if an edge leads to a loop
     * if so, exits the program.
     * @param w tail of edge
     * @param v head of edge
     */
    private void isLoop(int v, int w) {
        if (v == w) {
            throw new InputException("Invalid input: "
                    + "Loop dectected "
                    + "in the graph input.");
        }
    }

    /**
     * Exits the Program, if a cycle is detected in
     * the cycle.
     */
    public void hasCycle() {
        DirectedCycle finder = new DirectedCycle(G);
        if (finder.hasCycle()) {
            throw new InputException("Invalid input: "
                    + "Cycle detected "
                    + "in the graph input.");
        }
    }

    /**
     * Getter method
     *
     * @return G Digraph instantiated
     */
    public Digraph getDigraph() {
        return G;
    }

    /**
     *  Getter method
     * @return returns the input stream's lines put into
     *         an arrayList.
     */
    public ArrayList<String> getEdges() {
       return edges;
    }

}
