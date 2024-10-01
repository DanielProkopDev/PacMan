package util.points;

import util.Range.Range;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Point extends JComponent implements DrawablePoint {


    private File plik;

    private BufferedImage point;


    public Point(){
        plik = new File("./img/NormalPoint.png");
        if (plik.exists() && plik.canRead()){
            try{
                point = ImageIO.read(plik);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void draw(Graphics2D g2d,int cellX, int cellY, int cellSize){
        int pointSize = cellSize / 3;
        int offset = cellSize / 3;

        g2d.drawImage(point, cellX + offset, cellY + offset, pointSize, pointSize, null);
      //  g2d.dispose();


    }
    public void drawOnBoost(Graphics2D g2d,int cellX, int cellY, int cellSize){
        int pointSize = cellSize / 3;
        int offset = cellSize / 3;
        float alpha = 0.7f;
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
        g2d.setComposite(ac);

        g2d.drawImage(point, cellX + offset, cellY + offset, pointSize, pointSize, null);
        g2d.dispose();
    }


}