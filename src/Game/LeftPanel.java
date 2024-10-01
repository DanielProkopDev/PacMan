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

public class LeftPanel extends JPanel  {

    private ImageIcon removeWallImage;
    private  ImageIcon speedImage;
    private ImageIcon teleportImage;
    private ImageIcon invincibleImage;

    private RoundPanel roundPanel;

    private JLabel removeWallLabel;
    private JLabel speedLabel;
    private JLabel teleportLabel;
    private JLabel invincibleLabel;


    public LeftPanel(int width,int height,int roundTopLeft, int roundTopRight, int roundBottomLeft, int roundBottomRight){

        this.roundPanel = new RoundPanel(width,height,roundTopLeft,roundTopRight,roundBottomLeft,roundBottomRight);
        setSize(width,height);
        setBackground(Color.BLACK);
        setLayout(new FlowLayout());
        roundPanel.setLayout(new GridBagLayout());



        removeWallImage= new ImageIcon("src/img/removeWallImage1.png");
        speedImage=new ImageIcon("src/img/speedImage1.png");
        teleportImage=new ImageIcon("src/img/teleportImage1.png");
        invincibleImage=new ImageIcon("src/img/invincibleImage1.png");

        removeWallLabel=new JLabel("Remove Wall Skill- Must be chosen and must be in movement+hold space",removeWallImage,JLabel.LEFT);
        speedLabel=new JLabel("Speed Skill- Must be chosen",speedImage,JLabel.LEFT);
        teleportLabel=new JLabel("Teleport Skill - Must be chosen and hold.space+leftmouseClick",teleportImage,JLabel.LEFT);
        invincibleLabel=new JLabel("Invincible Skill - 20 sec ghost can't chase you neither touch you",invincibleImage,JLabel.LEFT);




        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5); // odstępy między elementami
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        roundPanel.add(removeWallLabel,gbc);
        gbc.gridy++;
        roundPanel.add(speedLabel,gbc);
        gbc.gridy++;
        roundPanel.add(teleportLabel,gbc);
        gbc.gridy++;
        roundPanel.add(invincibleLabel,gbc);

        add(roundPanel);

    }

    public void setColor(int value, Color color ){
        switch (value){
            case 1:
                speedLabel.setForeground(color);
                break;
            case 2:
                invincibleLabel.setForeground(color);
                break;
            default:
        }
    }


}
