package Game.util;

import Game.RescaleUnit;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class HeartLabel extends JLabel {
    private int lives;
    private ImageIcon heartImage;

    private int heartWidth;
    private int heartHeight;

    private RescaleUnit rescaleUnit;

    public HeartLabel(int lives) {
        this.lives = lives;

            heartImage = new ImageIcon("./img/Heart10px.png");

        setForeground(Color.WHITE);
        heartWidth=heartImage.getIconWidth();
        heartHeight=heartImage.getIconHeight();
        rescaleUnit=RescaleUnit.getInstance();
        //assert rescaleUnit != null;
        //rescaleUnit.add(this,heartImage);
    }

    public void setLives(int lives) {
        this.lives = lives;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {



        int x = getInsets().left;
        int y = (getHeight() - heartHeight) / 2;

        g.setColor(getForeground());
        FontMetrics fm = g.getFontMetrics();
        g.drawString("Lives:", x, (getHeight() + fm.getAscent()) / 2 - 2);

        x += fm.stringWidth("Lives:") + 5;

        for (int i = 0; i < lives; i++) {
            g.drawImage(heartImage.getImage(), x, y, heartWidth, heartHeight, this);
            x += heartWidth + 5;
        }
        super.paintComponent(g);
    }

    @Override
    public Dimension getPreferredSize() {
        int heartWidth = heartImage.getIconWidth();
        int heartHeight = heartImage.getIconHeight();
        FontMetrics fm = getFontMetrics(getFont());
        int textWidth = fm.stringWidth("Lives:");
        int width = (heartWidth + 5) * lives + textWidth + 5;
        int height = Math.max(heartHeight, fm.getHeight());
        return new Dimension(width, height);
    }
}