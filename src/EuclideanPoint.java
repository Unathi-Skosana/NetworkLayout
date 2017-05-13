/**
 * EuclideanPoint is a class that represents
 * a point in two dimensional Euclidean space.
 *
 * @author  Unathi Koketso Skosana
 * @version 1.0
 * @since   2017-27-02
 */
public class EuclideanPoint {
    private final double x;
    private final double y;
    
    /**
     * Constructor, initializes EuclideanPoint
     * @param x horizontal position
     * @param y vertical position
     */
    public EuclideanPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }
    /**
     * Getter method
     * @return returns x position
     */
    public double getXCoordinate() {
        return x;
    }

    /**
     * Getter method
     * @return returns y position
     */
    public double getYCoordinate() {
        return y;
    }
}
