package Game.util;

import character.PacMan;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

public class SpeedRadioButton extends AbstractRadioButton {

    private PacMan pacMan;

    private InvincibleRadioButton invincibleRadioButton;
    private TeleportRadioButton teleportRadioButton;
    private RemoveWallRadioButton removeWallRadioButton;
    public SpeedRadioButton(PacMan pacMan,double widthScale,double heightScale){
        super(widthScale,heightScale);
        this.pacMan=pacMan;
        scaledWidth=(int)(16* widthScale);
        scaledHeight=(int) (16* heightScale);

        this.IconOff=new ImageIcon("./img/speedImageRadioOff.png");
        this.IconOn=new ImageIcon("./img/speedImageRadio.png");

        this.images.add(0,IconOn);
        this.images.add(1,IconOff);

        this.someLabel=new JLabel(String.valueOf(value),IconOn,JLabel.LEFT);
        this.someLabel.setDisabledIcon(IconOff);
        this.someLabel.setEnabled(false);
        someLabel.setFont(scaledFont);



        rescaleUnit.addRadioRadio(this,this.images);
        add(someLabel);


    }
    public void keyPressed(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_2){
            pacMan.unSetAll();
            pacMan.setSpeedChosen(true);
            this.setIcon(true);
            this.removeWallRadioButton.setIcon(false);
            this.teleportRadioButton.setIcon(false);
            this.invincibleRadioButton.setIcon(false);
        }
    }
    public void setPacMan(PacMan pacMan) {
        this.pacMan = pacMan;
    }
    public void setOthers(InvincibleRadioButton invincibleRadioButton,TeleportRadioButton teleportRadioButton, RemoveWallRadioButton removeWallRadioButton){
        this.invincibleRadioButton=invincibleRadioButton;
        this.teleportRadioButton=teleportRadioButton;
        this.removeWallRadioButton=removeWallRadioButton;
    }

}

