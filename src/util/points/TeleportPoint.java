package util.points;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TeleportPoint implements DrawablePoint {

    BufferedImage teleportImageMap;


    public TeleportPoint(){
        try{
            teleportImageMap= ImageIO.read(new File("./img/teleportImageRadio.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g2d, int cellX, int cellY, int cellSize) {
        g2d.drawImage(teleportImageMap, cellX + 5, cellY + 5, cellSize/2, cellSize/2, null);
    }
}
