/* JUnit imports */
import static org.junit.Assert.*;
import org.junit.Test;

/* User defined imports */
import algs4.Digraph;
import algs4.DirectedCycle;
import algs4.DirectedEdge;
import std.In;
import std.StdOut;
import std.StdDraw;

public class LayoutTester {

	private Ranker ranker_ONE;
	private Digraph G_ONE;
	private Digraph rankedG_ONE;
	private InputParser input_ONE;

	private Ranker ranker_TWO;
	private Digraph G_TWO;
	private Digraph rankedG_TWO;
	private InputParser input_TWO;

	private Digraph DG_ONE;
	private Digraph DG_TWO;


	@Test
	public void test() throws Exception {
		setUp();
		testRankers();
		testNodeGraphs();
		testDependencies();
		testDigraphs();
		testInvalidInput();

	}

	public void setUp() {
		In edges_ONE = new In("examples/small_graphs/edges.txt");
		this.input_ONE = new InputParser(edges_ONE);
		this.G_ONE = input_ONE.getDigraph();
		this.ranker_ONE  = new Ranker(G_ONE);
		this.rankedG_ONE = ranker_ONE.getRankedDigraph();

		In edges_TWO = new In("examples/small_graphs/edges1.txt");
		this.input_TWO = new InputParser(edges_TWO);
		this.G_TWO = input_TWO.getDigraph();
		this.ranker_TWO  = new Ranker(G_TWO);
		this.rankedG_TWO = ranker_TWO.getRankedDigraph();

		this.DG_ONE = new Digraph(new In ("examples/small_graphs/tinyDG.txt"));
		this.DG_TWO = new Digraph(new In("examples/small_graphs/tinyDAG.txt"));
	}

	public void testRankers() {
		String expectedOutput_ONE = "[9, 5, 6, 4] <- [3, 8, 11, 13] <- [2, 12, 14] <- [1] <- [0, 7, 10]";
		String expectedOutput_TWO = "[0] <- [5, 8] <- [3, 6, 10] <- [1, 2, 4, 9] <- [7]";
		assertEquals(ranker_ONE.toString(),expectedOutput_ONE);
		assertEquals(ranker_TWO.toString(),expectedOutput_TWO);
		assertEquals(ranker_ONE.getMinSet().toString(), "[9, 5, 6, 4]");
		assertEquals(ranker_ONE.getMaxSet().toString(), "[0, 7, 10]");
		assertEquals(ranker_TWO.getMinSet().toString(), "[0]");
		assertEquals(ranker_TWO.getMaxSet().toString(), "[7]");

	}

	public void testNodeGraphs() throws Exception {
		GraphLayout.main(new String[] {"examples/small_graphs/edges.txt"});
		GraphLayout.main(new String[] {"examples/small_graphs/edges1.txt"});
		GraphLayout.main(new String[] {"examples/small_graphs/edges2.txt"});
		GraphLayout.main(new String[] {"examples/small_graphs/edges3.txt"});
		GraphLayout.main(new String[] {"examples/small_graphs/binaryTree.txt"});
		GraphLayout.main(new String[] {"examples/small_graphs/rooted-in tree.txt"});
	}

	public void testDigraphs() {
		String expected_ONE = "15 vertices, 15 edges \n"+
		"0: 1 \n"+
		"1: 2 14 12 \n"+
		"2: 3 8 \n"+
		"3: 4 6 \n"+
		"4: \n"+
		"5: \n"+
		"6: \n"+
		"7: 1 \n"+
		"8: 6 \n"+
		"9: \n"+
		"10: 1 \n"+
		"11: 9 \n"+
		"12: 11 \n"+
		"13: 5 \n"+
		"14: 13 \n";
		String expected_TWO = "11 vertices, 11 edges \n"+
		"0: 1 \n"+
		"1: 9 5 2 \n"+
		"2: 8 3 \n"+
		"3: 6 4 \n"+
		"4: \n"+
		"5: \n"+
		"6: \n"+
		"7: 1 \n"+
		"8: 6 \n"+
		"9: \n"+
		"10: 1 \n";

		String expected_THREE ="11 vertices, 14 edges \n"+
		"0: \n"+
		"1: 6 \n"+
		"2: 3 \n"+
		"3: 5 8 \n"+
		"4: 3 \n"+
		"5: 0 \n"+
		"6: 8 \n"+
	    "7: 9 2 4 1 \n"+
		"8: 0 \n"+
		"9: 10 \n"+
		"10: 5 \n";

		String expected_FOUR = "11 vertices, 14 edges \n"+
		"0: \n"+
	    "1: 6 \n"+
	    "2: 3 \n"+
		"3: 8 5 \n"+
		"4: 3 \n"+
		"5: 0 \n"+
		"6: 8 \n"+
		"7: 1 4 2 9 \n"+
		"8: 0 \n"+
		"9: 10 \n"+
		"10: 5 \n";

		assertEquals(rankedG_ONE.toString(), expected_ONE);
		assertEquals(G_ONE.toString(), expected_TWO);
		assertEquals(rankedG_TWO.toString(), expected_THREE);
		assertEquals(G_TWO.toString(), expected_FOUR);
	}

	public void testDependencies() {
		StdOut.main(new String[] {});
		StdDraw.main(new String[] {});
		DirectedCycle.main(new String [] {"examples/small_graphs/tinyDG.txt"});
		DirectedCycle.main(new String[] {"examples/small_graphs/tinyDAG.txt"});
		DirectedEdge.main(new String[] {});
	}

	public void testInvalidInput() {
		try {
		    InputParser invalidInput_ONE = new InputParser(new In("examples/small_graphs/cycle.txt"));
		} catch (InputException e) {
		    assertEquals("Invalid input: Cycle detected in the graph input.", e.getMessage());
		}
		try {
		    InputParser invalidInput_TWo = new InputParser(new In("examples/small_graphs/loop.txt"));
		} catch (InputException e) {
			assertEquals("Invalid input: Loop dectected in the graph input.", e.getMessage());
		}

		try {
		    throw new InputException();
		} catch (InputException e) {
			assertEquals("An unknown error occured.", e.getMessage());
		}
	}
}
