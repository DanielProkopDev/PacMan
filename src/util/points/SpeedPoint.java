package util.points;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpeedPoint implements DrawablePoint {


    BufferedImage speedImageMap;


    public SpeedPoint(){
        try{
            speedImageMap= ImageIO.read(new File("./img/speedImageRadio.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g2d, int cellX, int cellY, int cellSize) {
        g2d.drawImage(speedImageMap, cellX + 5, cellY + 5, cellSize/2, cellSize/2, null);
    }
}
