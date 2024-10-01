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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LeftPanel2 extends JPanel  {

    private ImageIcon removeWallImage;
    private  ImageIcon speedImage;
    private ImageIcon teleportImage;
    private ImageIcon invincibleImage;

    private RoundPanel roundPanel;

    private JLabel removeWallLabel;
    private JLabel speedLabel;
    private JLabel teleportLabel;
    private JLabel invincibleLabel;

    private Font defaultFont;

    private int scaledWidth;
    private int scaledHeight;

    private ArrayList<Integer> sizes;

    private RescaleUnit rescaleUnit;
    public LeftPanel2(double widthScale,double heightScale,int width,int height,int roundTopLeft, int roundTopRight, int roundBottomLeft, int roundBottomRight){





        setSize(width+20,height);
        setBackground(Color.BLACK);
        setLayout(new FlowLayout());

         rescaleUnit=RescaleUnit.getInstance();
        sizes = new ArrayList<>();
        scaledWidth=(int)(24* widthScale);
        scaledHeight=(int) (24* heightScale);
        sizes.add(0,width);
        sizes.add(1,height);
       // rescaleUnit.addPanels(this,sizes);

        removeWallImage= scaleImageIcon(new ImageIcon("./img/removeWallImage1.png"),scaledWidth,scaledHeight);
        speedImage= scaleImageIcon(new ImageIcon("./img/speedImage1.png"),scaledWidth,scaledHeight);
        teleportImage=scaleImageIcon(new ImageIcon("./img/teleportImage1.png"),scaledWidth,scaledHeight);
        invincibleImage=scaleImageIcon(new ImageIcon("./img/invincibleImage1.png"),scaledWidth,scaledHeight);

        defaultFont = new Font("Arial", Font.PLAIN, 10);
        Font scaledFont = scaleFont(defaultFont, widthScale);

        removeWallLabel=new JLabel("Remove Wall");
        removeWallLabel.setIcon(removeWallImage);
        removeWallLabel.setFont(scaledFont);
       // rescaleUnit.add(removeWallLabel,removeWallImage);


        speedLabel=new JLabel("Speed");
        speedLabel.setIcon(speedImage);
        speedLabel.setFont(scaledFont);
       // rescaleUnit.add(speedLabel,speedImage);

        teleportLabel=new JLabel("Teleport");
        teleportLabel.setIcon(teleportImage);
        teleportLabel.setFont(scaledFont);
       // rescaleUnit.add(teleportLabel,teleportImage);

        invincibleLabel=new JLabel("Invincible",JLabel.LEFT);
        invincibleLabel.setIcon(invincibleImage);
        invincibleLabel.setFont(scaledFont);
       // rescaleUnit.add(invincibleLabel,invincibleImage);


        this.roundPanel = new RoundPanel(width,height,roundTopLeft,roundTopRight,roundBottomLeft,roundBottomRight);
        roundPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 3, 3, 3);
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

    private ImageIcon scaleImageIcon(ImageIcon icon, int width, int height) {
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    private Font scaleFont(Font font, double scale) {
        return font.deriveFont((float)(font.getSize2D() * scale));
    }

}
