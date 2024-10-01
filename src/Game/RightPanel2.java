package Game;



import Game.util.AbstractRadioButton;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RightPanel2 extends JPanel {

    private final ImageIcon buttonW;
    private final  ImageIcon buttonA;
    private final ImageIcon buttonS;
    private final ImageIcon buttonD;

    private final ImageIcon button1;
    private final ImageIcon button2;
    private final ImageIcon button3;
    private final ImageIcon button4;

    private final ImageIcon buttonSpaceBar;
    private final ImageIcon buttonBackSpace;

    private final ImageIcon buttonMouseLeft;

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

    private Font defaultFont;

    private RescaleUnit rescaleUnit;

    private int scaledWidth;
    private int scaledHeight;

    private ArrayList<Integer> sizes;


    public RightPanel2(double widthScale,double heightScale,int width, int height, int roundTopLeft, int roundTopRight, int roundBottomLeft, int roundBottomRight){

        rescaleUnit=RescaleUnit.getInstance();
        sizes = new ArrayList<>();


      //  setSize(width, height);
        setBackground(Color.BLACK);
        setLayout(new FlowLayout());
        scaledWidth=(int)(24* widthScale);
        scaledHeight=(int) (24* heightScale);
       sizes.add(0,width);
       sizes.add(1,height);
     //  rescaleUnit.addPanels(this,sizes);

        buttonW = scaleImageIcon(new ImageIcon("./img/buttonW.png"), scaledWidth, (int)(24 * heightScale));
        buttonA = scaleImageIcon(new ImageIcon("./img/buttonA.png"), scaledWidth, (int)(24 * heightScale));
        buttonS = scaleImageIcon(new ImageIcon("./img/buttonS.png"), scaledWidth, (int)(24 * heightScale));
        buttonD = scaleImageIcon(new ImageIcon("./img/buttonD.png"), scaledWidth, (int)(24 * heightScale));
        button1 = scaleImageIcon(new ImageIcon("./img/button1.png"), scaledWidth, (int)(24 * heightScale));
        button2 = scaleImageIcon(new ImageIcon("./img/button2.png"), scaledWidth, (int)(24 * heightScale));
        button3 = scaleImageIcon(new ImageIcon("./img/button3.png"), scaledWidth, (int)(24 * heightScale));
        button4 = scaleImageIcon(new ImageIcon("./img/button4.png"), scaledWidth, (int)(24 * heightScale));
        buttonSpaceBar = scaleImageIcon(new ImageIcon("./img/buttonSpaceBar.png"), scaledWidth, scaledHeight);
        buttonBackSpace = scaleImageIcon(new ImageIcon("./img/buttonBackSpace.png"), scaledWidth, scaledHeight);
        buttonMouseLeft = scaleImageIcon(new ImageIcon("./img/buttonMouseLeft.png"), scaledWidth, scaledHeight);





        defaultFont = new Font("Arial", Font.PLAIN, 8);
        Font scaledFont = scaleFont(defaultFont, widthScale);

        moveUp = new JLabel("MoveUp");
        moveUp.setIcon(buttonW);
        moveUp.setFont(scaledFont);
        //rescaleUnit.add(moveUp,buttonW);

        moveDown = new JLabel("MoveDown");
        moveDown.setIcon(buttonS);
        moveDown.setFont(scaledFont);
       // rescaleUnit.add(moveDown,buttonS);

        moveLeft = new JLabel("MoveLeft");
        moveLeft.setIcon(buttonA);
        moveLeft.setFont(scaledFont);
      //  rescaleUnit.add(moveLeft,buttonA);

        moveRight = new JLabel("MoveRight");
        moveRight.setIcon(buttonD);
        moveRight.setFont(scaledFont);
      //  rescaleUnit.add(moveRight,buttonD);

        removeWall = new JLabel("RemoveWall");
        removeWall.setIcon(button1);
        removeWall.setFont(scaledFont);
       // rescaleUnit.add(removeWall,button1);

        speed = new JLabel("Speed100%Up");
        speed.setIcon(button2);
        speed.setFont(scaledFont);
       // rescaleUnit.add(speed,button2);

        teleport = new JLabel("Teleport");
        teleport.setIcon(button3);
        teleport.setFont(scaledFont);
       // rescaleUnit.add(teleport,button3);

        invincible = new JLabel("Invincible");
        invincible.setIcon(button4);
        invincible.setFont(scaledFont);
      //  rescaleUnit.add(invincible,button4);

        spaceBar = new JLabel("SpaceBar-activate skill");
        spaceBar.setFont(scaledFont);
       // rescaleUnit.addLabelNoIcon(spaceBar);

        backSpace = new JLabel("BackSpace-to cancel game");
        backSpace.setFont(scaledFont);
       // rescaleUnit.addLabelNoIcon(backSpace);

        mouseLeft = new JLabel("space(hold)+leftClick=teleport");
        mouseLeft.setFont(scaledFont);
      //  rescaleUnit.addLabelNoIcon(mouseLeft);


        this.roundPanel = new RoundPanel(width, height+20, roundTopLeft, roundTopRight, roundBottomLeft, roundBottomRight);
        roundPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 3, 2, 1);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        roundPanel.add(moveUp, gbc);
        gbc.gridy++;
        roundPanel.add(moveLeft, gbc);
        gbc.gridy++;
        roundPanel.add(moveDown, gbc);
        gbc.gridy++;
        roundPanel.add(moveRight, gbc);
        gbc.gridy++;
        roundPanel.add(removeWall, gbc);
        gbc.gridy++;
        roundPanel.add(speed, gbc);
        gbc.gridy++;
        roundPanel.add(teleport, gbc);
        gbc.gridy++;
        roundPanel.add(invincible, gbc);
        gbc.gridy++;
        roundPanel.add(spaceBar, gbc);
        gbc.gridy++;
        roundPanel.add(backSpace, gbc);
        gbc.gridy++;
        roundPanel.add(mouseLeft, gbc);

        prev = mouseLeft.getForeground();

        add(roundPanel);
    }

    private ImageIcon scaleImageIcon(ImageIcon icon, int width, int height) {
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    private Font scaleFont(Font font, double scale) {
        return font.deriveFont((float)(font.getSize2D() * scale));
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
