package Game.util;


import character.PacMan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;


//NALEZY DODAC MAPE W RESCALEUNIT KTORA BEDIZE ZAWIERAC NASZE RADIOBUTTONY A W NICH ZROBIC METODE GET ICONON ORAZ ICONOFF I ZMIENIAC IM ROZMIARY
// CO OZNACZAC BEDIZE ZE DUCHY BEDA CIAGLE W STANIE SCATTER, NIE BEDA MOGLY SLEDZIC RUCHU PACMANA ORAZ PACMAN BEDZIE MOGL PRZENIKAC PRZEZ DUCHY NA OKRESLONY CZAS
public class InvincibleRadioButton extends AbstractRadioButton {


    private PacMan pacMan;

    private RemoveWallRadioButton removeWallRadioButton;
    private TeleportRadioButton teleportRadioButton;
    private SpeedRadioButton speedRadioButton;
    public InvincibleRadioButton(PacMan pacMan,double widthScale,double heightScale){
        super(widthScale,heightScale);
        this.pacMan=pacMan;
        scaledWidth=(int)(12* widthScale);
        scaledHeight=(int) (16* heightScale);


        this.IconOff=new ImageIcon("./img/invincibleImageRadioOff.png");
        this.IconOn=new ImageIcon("./img/invincibleImageRadio.png");

        this.images.add(0,IconOn);
        this.images.add(1,IconOff);

        this.someLabel=new JLabel(String.valueOf(value),IconOn,JLabel.LEFT);
        this.someLabel.setDisabledIcon(IconOff);
        this.someLabel.setEnabled(false);
        this.someLabel.setFont(scaledFont);




        rescaleUnit.addRadioRadio(this,this.images);
        add(someLabel);


    }
    public void keyPressed(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_4){
            pacMan.unSetAll();
            pacMan.setInvincibleChosen(true);
            this.setIcon(true);
            this.removeWallRadioButton.setIcon(false);
            this.teleportRadioButton.setIcon(false);
            this.speedRadioButton.setIcon(false);
        }
    }

    public void setPacMan(PacMan pacMan) {
        this.pacMan = pacMan;
    }

    public void setOthers(RemoveWallRadioButton removeWallRadioButton,TeleportRadioButton teleportRadioButton, SpeedRadioButton speedRadioButton){
        this.removeWallRadioButton=removeWallRadioButton;
        this.teleportRadioButton=teleportRadioButton;
        this.speedRadioButton=speedRadioButton;
    }


}

