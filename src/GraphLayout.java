/**b/
 * Graph is a program that draws a directed graph
 * after all the preprocessing that been done
 * i.e ranking, node ordering
 * 
 * @author Unathi Koketso Skosana
 * @version 1.0
 * @since 2017-02-05
 */
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class GraphLayout {
    private EuclideanPoint[] nodes;
    private Digraph G;
    private Ranker ranker;
    private HashMap<Integer, ArrayList<Integer>> ranks;
    private ArrayList<String> edges;
    private EdgeColorer edgecolorer;
    private Layout layout;
    private double radius;
    private double scale;
    private int vertices;
    private Out output;


    public static void main(String[] args) {
        InputParser parse = new InputParser(
                new In(args[0]));
        GraphLayout graph =  new GraphLayout(parse);
        graph.draw();
    }

    /**
     * Constructor, does all the necessary processing
     * behind the scenes.
     * @param graphInput
     */

    public GraphLayout(InputParser input) {
        prepareOut();
        pollOldDigraph(input);
        pollNewDigraph();
        StdDrawConfig();
        processNodesByRank();
    }

    /**
     * Draw the node graph
     */
    public void draw() {
        setScales();
        drawEdges();
        drawNodes();
        labelNodes();
        StdDraw.save("BinaryTree.png");
    }

    /**
     * Initializes the outfile.
     */
    private void prepareOut() {
        output = new Out("output.txt");
    }

    /**
     * Polls information relating the Digraph
     * before the Digraph is processed.
     *
     * @param input InputParser graph input
     */
    private void pollOldDigraph(InputParser input) {
        this.ranker      = new Ranker(input.getDigraph());
        this.vertices    = input.getDigraph().V();
        this.G           = ranker.getRankedDigraph();
        this.edges       = input.getEdges();
        this.nodes       = new EuclideanPoint[G.V()];
        this.layout      = new Layout(ranker);
        this.edgecolorer = new EdgeColorer(edges,
            ranker.getDummyNodes());
    }

   /**
    * Polls information relating to the
    * now processed Digraph
    */
    private void pollNewDigraph() {
      this.ranker = layout.getUntangledRanker();
      this.ranks  = ranker.getRankSets();
    }

    /**
     * Configures StdDraw in preparation for
     * drawing.
     */
    private void StdDrawConfig() {
      this.scale  = getMaxScale();
      this.radius = setRadius();
    }

    /**
     * Labels a node with it's number.
     *
     * @param x coordinate
     * @param y coordinate
     * @param text number of the node.
     */
    private void labelNode(double x, double y, String text) {
        StdDraw.setPenRadius(GraphConfig.EDGE_PEN_SIZE);
        StdDraw.setPenColor(GraphConfig.WHITE);
        Font font = new Font("Arial", Font.BOLD, 10);
        StdDraw.setFont(font);     
        StdDraw.text(x, y, text);
        StdDraw.setPenColor(GraphConfig.BLACK);
        StdDraw.setPenRadius(GraphConfig.DEFAULT_PEN_SIZE);
    }

    /**
     * Dynamic radius
     *
     * @return returns the global radius of the nodes
     */
    private double setRadius() {
        if (vertices >= 100) {
            return scale/500.00;
        }
        else {
           return scale/25.00;
        }
    }

    /**
     * Gets the width of the graph by finding the size
     * of the largest rank.
     *
     * @return returns the width.
     */
    private int getDigraphWidth() {
       int max = 0;
       Set<Integer> keys = ranks.keySet();
       for (int key: keys) {
            if(ranks.get(key).size() > max) {
                max = ranks.get(key).size();
            }
       }
       return max;
    }

    /**
     * Gets the height of the graph, which is the
     * number of ranks the graph has.
     *
     * @return returns the number of ranks
     */
    private int getDigraphHeight() {
        return ranks.size();
    }
    /**
     * Gets the scale of StdDraw window, by computing
     * the average of the width and height of the
     * graph.
     *
     * @return
     */
    private double getMaxScale() {
        return (getDigraphWidth()
            + getDigraphHeight())/2.00;
    }

    /**
     * Sets the scales of the StdDraw window.
     */
    private void setScales() {
      StdDraw.setXscale(-1, scale + 1);
      StdDraw.setYscale(-1, scale + 1);
      if (vertices >=  100) {
          StdDraw.setCanvasSize(4000, 1000);
      } else {
          StdDraw.setCanvasSize(500, 500);
      }
    }

    /**
     * Process nodes rank by rank, and centering
     * the nodes in each rank.
     */
    private void processNodesByRank() {
        HashMap<Integer, ArrayList<Integer>> ranks
            = ranker.getRankSets();
        double centerY = scale/(ranks.size() + 1);
        Set<Integer> keys = ranks.keySet();
        for (int key : keys) {
            ArrayList<Integer> vertexSet = ranks.get(key); 
            double centerX = scale/(vertexSet.size() + 1);
            for (int v = 0; v < vertexSet.size(); v++) {
                nodes[vertexSet.get(v)] = new EuclideanPoint(
                        (v+1)*centerX,
                        key*centerY);
                writeToFile(vertexSet.get(v),
                        nodes[vertexSet.get(v)].getXCoordinate(),
                        nodes[vertexSet.get(v)].getYCoordinate());
            }
        }
    }

    /**
     * Draws nodes of the graph.
     */
    private void drawNodes() {
       for (int i = 0; i < vertices; i++) {
            drawNode(nodes[i].getXCoordinate(),
               nodes[i].getYCoordinate(), radius);
       }
    }

    /**
     * Draws a node.
     *
     * @param x coordinate
     * @param y coordinate
     * @param scaleRadius radius
     */
    private void drawNode(double x,
            double y, double scaleRadius) {
        if (vertices >= 100) {
            StdDraw.filledEllipse(x, y,
                scaleRadius, scaleRadius*4);
        }  else {
            StdDraw.filledCircle(x, y,
                scaleRadius);
        }
    }

    /**
     * Draws an colored edge from point p1 to point p2
     * @param p1 coordinates of tail
     * @param p2 coordinates of head
     * @param color color of edge.
     */
    private void drawEdge(EuclideanPoint p1,
                EuclideanPoint p2, String color) {
        switch (color) {
            case "red":
                StdDraw.setPenColor(GraphConfig.RED);
                break;
            case "orange":
                StdDraw.setPenColor(GraphConfig.ORANGE);
                break;
            case "yellow":
                StdDraw.setPenColor(GraphConfig.YELLOW);
                break;
            default:
                StdDraw.setPenColor(GraphConfig.BLACK);
                break;
        }
        StdDraw.line(p1.getXCoordinate(),
                p1.getYCoordinate(),
                p2.getXCoordinate(),
                p2.getYCoordinate());
        StdDraw.setPenColor(GraphConfig.BLACK);
    }

    /**
     * Draws colored edges.
     */
    private void drawEdges() {
        ArrayList<String[]> edges = ranker.getVertices();
        for (int i = 0; i < G.E(); i++) {
            String[] vertex = edges.get(i);
            int tail = Integer.parseInt(vertex[0]);
            int head = Integer.parseInt(vertex[1]);
            String color = "";
            color = edgecolorer.belongsTo(tail, head);
            drawEdge(nodes[tail], nodes[head], color);
        }
    }

    /**
     * Draws labels on the nodes.
     */
    private void labelNodes() {
    	ArrayList<String[]> edges = ranker.getVertices();
    	for (int i = 0; i < G.E(); i++) {
            String[] vertex = edges.get(i);
            int tail = Integer.parseInt(vertex[0]);
            int head = Integer.parseInt(vertex[1]);
            if (tail < vertices) {
                labelNode(nodes[tail].getXCoordinate(),
                    nodes[tail].getYCoordinate(), "" + tail);
            }
            if (head < vertices) {
                labelNode(nodes[head].getXCoordinate(),
                    nodes[head].getYCoordinate(), "" + head);
            }
        }
    }

    /**
     * Writes position of a node to
     * a file
     */
    private void writeToFile(int node, double x, double y) {
        output.println(node + " -> " + "("+x+","+y+")");
    }
}
