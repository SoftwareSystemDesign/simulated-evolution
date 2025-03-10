package org.woehlke.computer.kurzweil.simulated.evolution.model.geometry;

import lombok.*;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;

/**
 * A Point is used to define the Position of Cell or as a Vector for defining Dimensions.
 * <p>
 * Simulated Evolution.
 * Artificial Life Simulation of Bacteria Motion depending on DNA.
 * <p>
 * <p>
 * &copy; 2006 - 2008 Thomas Woehlke.
 *
 * @author Thomas Woehlke
 * @see <a href="https://thomas-woehlke.blogspot.com/2016/01/mandelbrot-set-drawn-by-turing-machine.html">Blog Article</a>
 * @see <a href="https://github.com/Computer-Kurzweil/simulated-evolution">Github Repository</a>
 * @see <a href="https://java.woehlke.org/simulated-evolution/">Maven Project Repository</a>
 * <p>
 * Date: 04.02.2006
 * Time: 23:47:05
 */
@Log4j2
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LatticePoint implements Serializable {
    private final static int[][] OFFSETS = {
        {-1, -1}, {-1, 0}, {-1, 1},
        {0, -1},  {0, 0},  {0, 1},
        {1, -1},  {1, 0},  {1, 1}
    };

    private final static int INDEX_MIN = 0;
    private final static int INDEX_MAX = 1;

    static final long serialVersionUID = 242L;


    private int x;
    private int y;

    public LatticePoint(LatticePoint other) {
        this.x = other.getX();
        this.y = other.getY();
    }

    public void makePositive() {
        x = Math.abs(x);
        y = Math.abs(y);
    }


    public void addAndAbsolute(LatticePoint p) {
        this.x = Math.abs(x + p.getX());
        this.y = Math.abs(y + p.getY());
    }

    public void add(LatticePoint p) {
        this.x += p.getX();
        this.y += p.getY();
    }

    public void normalize(LatticePoint p) {
        this.x %= p.getX();
        this.y %= p.getY();
    }

    public LatticePoint copy() {
        return new LatticePoint(this);
    }

    public LatticeDimension toLatticePoint() {
        return new LatticeDimension(
            this.getX(),
            this.getY()
        );
    }

    public static LatticePoint of(LatticeDimension p) {
        return new LatticePoint(p.getWidth(), p.getHeight());
    }

    /**
     * Get Neighbourhood.
     *
     * @param max - limit the dimensions of the world around
     * @return The Set of Points belonging to the Neighbourhood of the position given by this Point Object.
     */

    public LatticePoint[] getNeighbourhood(LatticePoint max) {


        LatticePoint[] neighbourhood = new LatticePoint[9];
        int maxX = max.getX();
        int maxY = max.getY();

        for (int i = 0; i < neighbourhood.length; i++) {
            int offsetX = OFFSETS[i][INDEX_MIN];
            int offsetY = OFFSETS[i][INDEX_MAX];
            int newX = (this.x + maxX + offsetX) % maxX;
            int newY = (this.y + maxY + offsetY) % maxY;
            neighbourhood[i] = new LatticePoint(newX, newY);
        }
        return neighbourhood;
    }

}
