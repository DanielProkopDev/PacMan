package character.ghost;

import character.ghost.util.AAsterisk;
import character.ghost.util.GhostHandler;
import map.MapDeck;
import map.handlers.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;


public class GhostRed extends AbstractGhost {


    private static volatile GhostRed instance = null;
    private BufferedImage redGhostRight;


    public GhostRed(int x, int y, int move_distance, int cellSize, MapDeck mapDeck) {
        super(x, y, move_distance, cellSize, mapDeck);

        this.setStateScatter(true);
        try {
            this.redGhostRight = ImageIO.read(new File("./img/ghostRedMenu1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
instance=this;
    }

    public static GhostRed getInstance(int x, int y, int move_distance, int cellSize, MapDeck mapDeck) {
        if (instance == null) {
            synchronized (GhostRed.class) {
                if (instance == null) {
                    instance = new GhostRed(x, y, move_distance, cellSize, mapDeck);
                }
            }
        }
        return instance;
    }

    public static GhostRed getInstance() {
        if (instance != null) {
            synchronized (GhostRed.class) {
                if (instance != null) {
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
                g2d.drawImage(redGhostRight, x, y, CHARACTER_SIZE, CHARACTER_SIZE, null);
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

        g2d.drawImage(redGhostRight, x, y, CHARACTER_SIZE, CHARACTER_SIZE, null);
        g2d.dispose();
    }
}



