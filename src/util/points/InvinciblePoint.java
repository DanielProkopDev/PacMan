package util.points;

import util.Drawable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class InvinciblePoint implements DrawablePoint {


    BufferedImage invincibleImageMap;


    public InvinciblePoint(){
        try{
            invincibleImageMap= ImageIO.read(new File("./img/invincibleImageRadio.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g2d, int cellX, int cellY, int cellSize) {
        g2d.drawImage(invincibleImageMap, cellX + 5, cellY + 5, cellSize/2, cellSize/2, null);
    }
}
