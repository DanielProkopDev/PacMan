package Game;

import Game.util.Border.RoundPanel;
import Game.util.Border.RoundedBorder;
import util.Drawable;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class RightPanel extends JPanel {

    private ImageIcon buttonW;
    private  ImageIcon buttonA;
    private ImageIcon buttonS;
    private ImageIcon buttonD;

    private ImageIcon button1;
    private ImageIcon button2;
    private ImageIcon button3;
    private ImageIcon button4;

    private ImageIcon buttonSpaceBar;
    private ImageIcon buttonBackSpace;

    private ImageIcon buttonMouseLeft;

    private JLabel moveUp;
    private JLabel moveDown;

    private JLabel moveLeft;
    private JLabel moveRight;

    private JLabel removeWall;

    private JLabel speed;
    private JLabel teleport;
    private JLabel invincible;
    private JLabel spaceBar;
    private JLabel backSpace;
    private JLabel mouseLeft;

    private  Color prev;



    private RoundPanel roundPanel;


    public RightPanel(int width, int height, int roundTopLeft, int roundTopRight, int roundBottomLeft, int roundBottomRight){

       this.roundPanel = new RoundPanel(width,height,roundTopLeft,roundTopRight,roundBottomLeft,roundBottomRight);

       setSize(width,height);
        setBackground(Color.BLACK);
        setLayout(new FlowLayout());
        roundPanel.setLayout(new GridBagLayout());



        buttonW= new ImageIcon("src/img/buttonW.png");
        buttonA=new ImageIcon("src/img/buttonA.png");
        buttonS=new ImageIcon("src/img/buttonS.png");
        buttonD=new ImageIcon("src/img/buttonD.png");
        button1=new ImageIcon("src/img/button1.png");
        button2=new ImageIcon("src/img/button2.png");
        button3=new ImageIcon("src/img/button3.png");
        button4=new ImageIcon("src/img/button4.png");
        buttonSpaceBar=new ImageIcon("src/img/buttonSpaceBar.png");
        buttonBackSpace=new ImageIcon("src/img/buttonBackSpace.png");
        buttonMouseLeft=new ImageIcon("src/img/buttonMouseLeft.png");

        moveUp= new JLabel("Move Up");
        moveUp.setIcon(buttonW);
        moveDown= new JLabel("Move Down");
        moveDown.setIcon(buttonS);
        moveLeft = new JLabel("Move Left");
        moveLeft.setIcon(buttonA);
        moveRight = new JLabel("Move Right");
        moveRight.setIcon(buttonD);

        removeWall = new JLabel("Remove Wall");
        removeWall.setIcon(button1);
        speed = new JLabel("Speed 100% Up");
        speed.setIcon(button2);
        teleport = new JLabel("Teleport");
        teleport.setIcon(button3);
        invincible = new JLabel("Invincible");
        invincible.setIcon(button4);

        spaceBar = new JLabel("Space Bar");
        spaceBar.setIcon(buttonSpaceBar);
        backSpace = new JLabel("Back Space");
        backSpace.setIcon(buttonBackSpace);

        mouseLeft = new JLabel("Mouse Left");
        mouseLeft.setIcon(buttonMouseLeft);

        prev = moveUp.getForeground();


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // odstępy między elementami
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        roundPanel.add(moveUp,gbc);
        gbc.gridy++;
        roundPanel.add(moveLeft,gbc);
        gbc.gridy++;
        roundPanel.add(moveDown,gbc);
        gbc.gridy++;
        roundPanel.add(moveRight,gbc);
        gbc.gridy++;
        roundPanel.add(removeWall,gbc);
        gbc.gridy++;
        roundPanel.add(speed,gbc);
        gbc.gridy++;
        roundPanel.add(teleport,gbc);
        gbc.gridy++;
        roundPanel.add(invincible,gbc);
        gbc.gridy++;
        roundPanel.add(spaceBar,gbc);
        gbc.gridy++;
        roundPanel.add(backSpace,gbc);
        gbc.gridy++;
        roundPanel.add(mouseLeft,gbc);

        add(roundPanel);

    }



    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
     switch (keyCode){

         case KeyEvent.VK_W:
               moveUp.setForeground(Color.YELLOW);
               break;
         case KeyEvent.VK_A:
             moveLeft.setForeground(Color.YELLOW);
             break;
         case KeyEvent.VK_S:
             moveDown.setForeground(Color.YELLOW);
             break;
         case KeyEvent.VK_D:
             moveRight.setForeground(Color.YELLOW);
             break;
         case KeyEvent.VK_1:
             removeWall.setForeground(Color.YELLOW);
             break;
         case KeyEvent.VK_2:
             speed.setForeground(Color.YELLOW);
             break;
         case KeyEvent.VK_3:
             teleport.setForeground(Color.YELLOW);
             break;
         case KeyEvent.VK_4:
             invincible.setForeground(Color.YELLOW);
             break;
         case KeyEvent.VK_SPACE:
             spaceBar.setForeground(Color.YELLOW);
             break;
         case KeyEvent.VK_BACK_SPACE:
             backSpace.setForeground(Color.YELLOW);
             break;

         default: break;
     }
    }


    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode){

            case KeyEvent.VK_W:
             moveUp.setForeground(prev);
             break;
            case KeyEvent.VK_A:
                moveLeft.setForeground(prev);
                break;
            case KeyEvent.VK_S:
                moveDown.setForeground(prev);
                break;
            case KeyEvent.VK_D:
                moveRight.setForeground(prev);
                break;
            case KeyEvent.VK_1:
                removeWall.setForeground(prev);
                break;
            case KeyEvent.VK_2:
                speed.setForeground(prev);
                break;
            case KeyEvent.VK_3:
                teleport.setForeground(prev);
                break;
            case KeyEvent.VK_4:
                invincible.setForeground(prev);
                break;
            case KeyEvent.VK_SPACE:
                spaceBar.setForeground(prev);
                break;
            case KeyEvent.VK_BACK_SPACE:
                backSpace.setForeground(prev);
                break;
            default: break;
        }
    }

}
