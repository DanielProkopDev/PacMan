package util.points;


import util.Drawable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RemoveWallPoint implements DrawablePoint {


    BufferedImage removeWallImageMap;


    public RemoveWallPoint(){
        try{
            removeWallImageMap= ImageIO.read(new File("./img/removeWallImageRadio.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g2d, int cellX, int cellY, int cellSize) {
        g2d.drawImage(removeWallImageMap, cellX + 5, cellY + 5, cellSize/2, cellSize/2, null);
    }
}

