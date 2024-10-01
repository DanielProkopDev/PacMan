package Game.util;

import character.PacMan;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

public class RemoveWallRadioButton extends AbstractRadioButton {


    private PacMan pacMan;
    private InvincibleRadioButton invincibleRadioButton;
    private TeleportRadioButton teleportRadioButton;
    private SpeedRadioButton speedRadioButton;
    public RemoveWallRadioButton(PacMan pacMan,double widthScale,double heightScale){
        super(widthScale,heightScale);
        this.pacMan=pacMan;
        scaledWidth=(int)(16* widthScale);
        scaledHeight=(int) (16* heightScale);

        this.IconOff=new ImageIcon("./img/removeWallImageRadioOff.png");
        this.IconOn=new ImageIcon("./img/removeWallImageRadio.png");

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
        if (e.getKeyCode() == KeyEvent.VK_1){
            pacMan.unSetAll();
            pacMan.setRemoveWallChosen(true);
            this.setIcon(true);
            this.invincibleRadioButton.setIcon(false);
            this.teleportRadioButton.setIcon(false);
            this.speedRadioButton.setIcon(false);
        }
    }
    public void setPacMan(PacMan pacMan) {
        this.pacMan = pacMan;
    }

    public void setOthers(InvincibleRadioButton invincibleRadioButton,TeleportRadioButton teleportRadioButton, SpeedRadioButton speedRadioButton){
        this.invincibleRadioButton=invincibleRadioButton;
        this.teleportRadioButton=teleportRadioButton;
        this.speedRadioButton=speedRadioButton;
    }

}
