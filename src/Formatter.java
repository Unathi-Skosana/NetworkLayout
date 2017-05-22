/** 
 * For formatting output from a toString() method
 * of digraph into a usable format.
 *
 */
public class Formatter {
    private String[] input;
    private Out output;

    /** 
     * Instatiates the formatter with
     * a Digraph and does the formatting.
     *
     */
    public Formatter(Digraph G, String name) {
        this.input = G.toString().split("\n");
        this.output = new Out("../examples/generated_input/" + name + ".txt");
        StdOut.println(G);
        format();
    }

    /**
     * Formats the input line by line 
     * by splitting each on ": ", this
     * split finds the tail node on one side
     * and it's connections on the other. The
     * formatted output is written to a file.
     * 
     */
    private void format() {
        for (int i = 1; i < input.length; i++) {
            String[] nodes = input[i].split(": ");
            String tail = nodes[0];
            if (nodes.length == 1) { continue; }
            String[] heads = nodes[1].split(" ");
            for (int j = 0; j < heads.length; j++) {
                output.println(tail + " -> " + heads[j]);
            }
        }
    }
}
