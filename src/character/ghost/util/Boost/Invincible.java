package character.ghost.util.Boost;

import util.Drawable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Invincible implements Drawable {

    BufferedImage invincibleImageMap;


    public Invincible(){
        try{
            invincibleImageMap= ImageIO.read(new File("src/img/invincibleImageRadio.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void draw(Graphics g) {

    }
}
