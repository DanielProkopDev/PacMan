package character.ghost;

import character.AbstractCharacter;
import character.ghost.util.GhostHandler;
import map.MapDeck;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GhostBlue extends AbstractGhost {

    private static volatile GhostBlue instance=null;
    private BufferedImage blueGhostRight;



    public GhostBlue(int x,int y,int move_distance,int cellSize, MapDeck mapDeck)  {
        super(x,y,move_distance,cellSize, mapDeck);
        try {
            this.blueGhostRight = ImageIO.read(new File("./img/ghostBlueLittle.png"));
        }catch (IOException e) {
            e.printStackTrace();
        }
        instance=this;

    }
    public static GhostBlue getInstance(int x, int y, int move_distance, int cellSize, MapDeck mapDeck){
        if (instance==null){
            synchronized (GhostBlue.class){
                if (instance==null){
                    instance=new GhostBlue(x,y,move_distance,cellSize, mapDeck);
                }
            }
        }
        return instance;
    }
    public static GhostBlue getInstance(){
        if (instance!=null){
            synchronized (GhostBlue.class){
                if (instance!=null){
                    return instance;
                }
            }
        }
        return null;
    }
    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        if (!isStateDead()) {
            if (!isStateRun()) {
                g2d.drawImage(blueGhostRight, x, y, CHARACTER_SIZE, CHARACTER_SIZE, null);

            } else {
                g2d.drawImage(badGhost.getImage(), x, y, CHARACTER_SIZE, CHARACTER_SIZE, null);
            }
            drawEyes(g2d, x, y, CHARACTER_SIZE, CHARACTER_SIZE, targetX, targetY);
        }

        g2d.dispose();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.drawImage(blueGhostRight,x,y,getWidth(),getHeight(),null);
        g2d.dispose();
    }
}

