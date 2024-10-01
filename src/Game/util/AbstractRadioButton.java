package Game.util;

import Game.RescaleUnit;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AbstractRadioButton extends JRadioButton {
    protected ImageIcon IconOff;

    protected ImageIcon IconOn;

    protected JLabel someLabel;

    protected int value=0;

    protected RescaleUnit rescaleUnit;

    protected Font defaultFont;
    protected Font scaledFont;

    protected double widthScale;
    protected double heightScale;
    protected int scaledWidth;
    protected int scaledHeight;

    protected ArrayList<ImageIcon> images;



    public AbstractRadioButton(double widthScale,double heightScale){
        this.rescaleUnit=RescaleUnit.getInstance();
        this.widthScale=widthScale;
        this.heightScale=heightScale;
        images=new ArrayList<>();

        defaultFont = new Font("Arial", Font.PLAIN, 10);
        scaledFont = scaleFont(defaultFont, widthScale);
        setFocusable(false);
    }

    public void setIcon(boolean doOrNot){
        //setIcon(scaleImageIcon(IconOff,IconOff.getIconWidth()*));
        this.someLabel.setEnabled(doOrNot);
    }

    public void setValue(int value) {
        this.value = value;
        this.someLabel.setText(String.valueOf(value));
    }
    private Font scaleFont(Font font, double scale) {
        return font.deriveFont((float)(font.getSize2D() * scale));
    }
    protected ImageIcon scaleImageIcon(ImageIcon icon, int width, int height) {
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    public ImageIcon getIconOff() {
        return IconOff;
    }

    public ImageIcon getIconOn() {
        return IconOn;
    }

    public void setIconOff(ImageIcon iconOff) {
        IconOff = iconOff;
    }

    public void setIconOn(ImageIcon iconOn) {
        IconOn = iconOn;
    }

    public JLabel getSomeLabel() {
        return someLabel;
    }
}
