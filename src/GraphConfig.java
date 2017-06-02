import java.awt.Color;

/**
 * A class to enforce clean code without magic
 * numbers
 * <p>
 * @author Unathi Koketso Skosana
 * @version 1.0
 * @since 2017-02-05
 */

public class GraphConfig {
    public static Color RED                  = new Color(255, 0, 0);
    public static Color YELLOW               = new Color(255, 199, 0);
    public static Color ORANGE               = new Color(255, 144, 0);
    public static Color BLACK                = new Color(0, 0, 0);
    public static Color WHITE                = new Color(255, 255, 255);
    public static Color GREY          = new Color(109,111,114);
    public static double DEFAULT_PEN_SIZE    = 0.005;
    public static double EDGE_PEN_SIZE       = 0.010;
    public static double NODE_RADIUS = 0.45;
}
