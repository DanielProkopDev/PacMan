package Game;

import Game.util.*;
import character.PacMan;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class StatusPanel extends JPanel {

    private JLabel timeLabel;
    private JLabel scoreLabel;

    private JLabel maxScoreLabel;
    private final HeartLabel livesLabel;

    private RemoveWallRadioButton removeWallRadioButton;
    private SpeedRadioButton speedRadioButton;
    private TeleportRadioButton teleportRadioButton;
    private InvincibleRadioButton invincibleRadioButton;
    private int score;

    private int maxScore;
    private final long startTime;

    private PacMan pacMan;

    private int scaledWidth;
    private int scaledHeight;

    private Font defaultFont;

    private RescaleUnit rescaleUnit;

    private long elapsedTime;

    public StatusPanel(int width,int height,double widthScale,double heightScale) {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(width,height));
        scaledWidth=width;
        scaledHeight=height;
        rescaleUnit=RescaleUnit.getInstance();

        defaultFont = new Font("Arial", Font.PLAIN, 10);
        Font scaledFont = scaleFont(defaultFont, widthScale);

        timeLabel = new JLabel();
        timeLabel.setFont(scaledFont);
        rescaleUnit.addLabelNoIcon(timeLabel);
        scoreLabel = new JLabel();
        scoreLabel.setFont(scaledFont);
        rescaleUnit.addLabelNoIcon(scoreLabel);
        maxScoreLabel=new JLabel();
        maxScoreLabel.setFont(scaledFont);
        rescaleUnit.addLabelNoIcon(maxScoreLabel);
        livesLabel = new HeartLabel(3);
        livesLabel.setFont(scaledFont);
        rescaleUnit.addLabelNoIcon(livesLabel);

        removeWallRadioButton=new RemoveWallRadioButton(pacMan,widthScale,heightScale);
        speedRadioButton = new SpeedRadioButton(pacMan,widthScale,heightScale);
        teleportRadioButton = new TeleportRadioButton(pacMan,widthScale,heightScale);
        invincibleRadioButton = new InvincibleRadioButton(pacMan,widthScale,heightScale);
        removeWallRadioButton.setOthers(invincibleRadioButton,teleportRadioButton,speedRadioButton);
        speedRadioButton.setOthers(invincibleRadioButton,teleportRadioButton,removeWallRadioButton);
        teleportRadioButton.setOthers(invincibleRadioButton,speedRadioButton,removeWallRadioButton);
        invincibleRadioButton.setOthers(removeWallRadioButton,teleportRadioButton,speedRadioButton);


        ButtonGroup btg = new ButtonGroup();
        btg.add(removeWallRadioButton);
        btg.add(speedRadioButton);
        btg.add(teleportRadioButton);
        btg.add(invincibleRadioButton);

        timeLabel.setForeground(Color.WHITE);
        scoreLabel.setForeground(Color.WHITE);
        maxScoreLabel.setForeground(Color.WHITE);

        maxScoreLabel.setText("Max Score: " + maxScore);
        add(timeLabel);
        add(scoreLabel);
        add(maxScoreLabel);
        add(livesLabel);
        add(removeWallRadioButton);
        add(speedRadioButton);
        add(teleportRadioButton);
        add(invincibleRadioButton);

        startTime = System.currentTimeMillis();
        update(3, 0, 0,0,0,0,0);
    }

    public void update(int lives, int score, int valueRemoveWall, int valueSpeed, int valueTeleport, int valueInvincible, int maxScore) {
        this.score = score;
        this.maxScore=maxScore;
        removeWallRadioButton.setValue(valueRemoveWall);
        speedRadioButton.setValue(valueSpeed);
        teleportRadioButton.setValue(valueTeleport);
        invincibleRadioButton.setValue(valueInvincible);
        long currentTime = System.currentTimeMillis();
        elapsedTime = (currentTime - startTime) / 1000;

        timeLabel.setText("Time: " + elapsedTime + "s");
        scoreLabel.setText("Score: " + score);
        maxScoreLabel.setText("Max Score: " + maxScore);

        livesLabel.setLives(lives);

        revalidate();
        repaint();
    }

    public void setPacMan(PacMan pacMan) {
        this.pacMan = pacMan;
        this.removeWallRadioButton.setPacMan(pacMan);
        this.speedRadioButton.setPacMan(pacMan);
        this.teleportRadioButton.setPacMan(pacMan);
        this.invincibleRadioButton.setPacMan(pacMan);
    }

    public RemoveWallRadioButton getRemoveWallRadioButton() {
        return removeWallRadioButton;
    }

    public SpeedRadioButton getSpeedRadioButton() {
        return speedRadioButton;
    }

    public TeleportRadioButton getTeleportRadioButton() {
        return teleportRadioButton;
    }

    public InvincibleRadioButton getInvincibleRadioButton() {
        return invincibleRadioButton;
    }

    private Font scaleFont(Font font, double scale) {
        return font.deriveFont((float)(font.getSize2D() * scale));
    }

    public void rescale(double widthScale, double heightScale){

    }

    public long getElapsedTime() {
        return elapsedTime;
    }
}
