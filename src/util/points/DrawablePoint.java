package util.points;

import java.awt.*;

public interface DrawablePoint {
    void draw(Graphics2D g2d,int cellX, int cellY, int cellSize);
}
