package map;

import map.util.Scalable;
import util.Drawable;

import javax.swing.*;
import java.io.Serializable;

public abstract class AbstractMap extends JComponent implements DrawableMap, Serializable {

    public static volatile int[][] grid;
    protected int rows;
    protected int cols;
    public static int CELL_SIZE;



    public AbstractMap(int rows, int cols, int CELL_SIZE) {
        this.rows = rows;
        this.cols = cols;
        AbstractMap.CELL_SIZE = CELL_SIZE;
    }

    public static synchronized int[][] getGrid() {
        return grid;
    }

    public synchronized int getRows() {
        return rows;
    }

    public synchronized int getCols() {
        return cols;
    }


}
