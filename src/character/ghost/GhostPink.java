package character.ghost;

import character.AbstractCharacter;
import character.ghost.util.GhostHandler;
import map.MapDeck;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GhostPink extends AbstractGhost {

    private static volatile GhostPink instance=null;
    private BufferedImage pinkGhostRight;



    public GhostPink(int x,int y,int move_distance,int cellSize, MapDeck mapDeck)  {
        super(x,y,move_distance,cellSize, mapDeck);
        try {
            this.pinkGhostRight = ImageIO.read(new File("./img/blueLittle2.png"));
        }catch (IOException e) {
            e.printStackTrace();
        }
instance=this;
    }
    public static GhostPink getInstance(int x, int y, int move_distance, int cellSize, MapDeck mapDeck){
        if (instance==null){
            synchronized (GhostPink.class){
                if (instance==null){
                    instance=new GhostPink(x,y,move_distance,cellSize, mapDeck);
                }
            }
        }
        return instance;
    }
    public static GhostPink getInstance(){
        if (instance!=null){
            synchronized (GhostPink.class){
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
                g2d.drawImage(pinkGhostRight, x, y, CHARACTER_SIZE, CHARACTER_SIZE, null);

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
        g2d.drawImage(pinkGhostRight,x,y,CHARACTER_SIZE,CHARACTER_SIZE,null);
        g2d.dispose();
    }


}


