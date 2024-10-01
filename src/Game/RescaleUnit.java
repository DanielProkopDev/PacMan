package Game;

import Game.util.AbstractRadioButton;
import character.ghost.AbstractGhost;
import map.MapDeck;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RescaleUnit {

    private  static volatile RescaleUnit instance=null;

   private Map<JLabel, ImageIcon> labels;
   private ArrayList<JLabel> lonelyLabels;
   private ArrayList<ImageIcon> images;

   private Map<AbstractRadioButton,ArrayList<ImageIcon>> radioRadio;

   private Map<AbstractGhost,ImageIcon> ghostsBadImages;

   private Map<JPanel,ArrayList<Integer>> panels;

    private Font defaultFont;

    private RescaleUnit(double widthScale){
        labels=new HashMap<>();
        lonelyLabels=new ArrayList<>();
        images=new ArrayList<>();
        radioRadio=new HashMap<>();
        ghostsBadImages=new HashMap<>();
        panels = new HashMap<>();
        defaultFont = new Font("Arial", Font.PLAIN, 10);

    }
    public static RescaleUnit getInstance(double widthScale){
        if (instance==null){
            synchronized (RescaleUnit.class){
                if (instance==null){
                    instance=new RescaleUnit(widthScale);
                }
            }
        }
        return instance;
    }
    public static RescaleUnit getInstance(){
        if (instance!=null){
            synchronized (RescaleUnit.class){
                if (instance!=null){
                    return instance;
                }
            }
        }
        return null;
    }
    public void add(JLabel label,ImageIcon icon){
        labels.put(label,icon);
    }

    public void addLabelNoIcon(JLabel label){
        lonelyLabels.add(label);
    }

    public void addImage(ImageIcon image){
        images.add(image);
    }

    public void addGhostBadImage(AbstractGhost ghost, ImageIcon badImage){
        ghostsBadImages.put(ghost,badImage);
    }

    public void addRadioRadio(AbstractRadioButton radio, ArrayList<ImageIcon> images){
        radioRadio.put(radio,images);
    }
    public void addPanels(JPanel panel,ArrayList<Integer> sizes){
        panels.put(panel,sizes);
    }

    private Font scaleFont(Font font, double scale) {
        return font.deriveFont((float)(font.getSize2D() * scale));
    }

    private ImageIcon scaleImageIcon(ImageIcon icon, int width, int height) {
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
    private ImageIcon scaleImage(Image image, int width, int height) {
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    public void rescale(double widthScale, double heightScale){
        labels.forEach((label,icon) ->{
            if ((int)(icon.getIconWidth()*widthScale)!=0&&(int)(icon.getIconHeight()*heightScale)!=0) {
                label.setIcon(scaleImageIcon(icon, (int) (icon.getIconWidth() * widthScale), (int) (icon.getIconHeight() * heightScale)));
                label.setFont(scaleFont(defaultFont, widthScale));
            }

        });


        radioRadio.forEach((radio,list)->{
            if ((int)(list.getLast().getIconWidth()*widthScale)!=0&&(int)(list.getLast().getIconHeight()*heightScale)!=0) {
                radio.setIconOff(scaleImageIcon(list.getLast(), (int) (list.getLast().getIconWidth() * widthScale), (int) (list.getLast().getIconHeight() * heightScale)));
                radio.setIconOn(scaleImageIcon(list.getFirst(), (int) (list.getFirst().getIconWidth() * widthScale), (int) (list.getFirst().getIconHeight() * heightScale)));
                radio.getSomeLabel().setSize(new Dimension((int) (13 * widthScale), (int) (17 * heightScale)));
            }


        });

     ghostsBadImages.forEach((ghost,badImage)->{
         if ((int)(badImage.getIconWidth()*widthScale)!=0&&(int)(badImage.getIconHeight()*heightScale)!=0) {
             ghost.setBadGhost(scaleImageIcon(badImage, (int) (badImage.getIconWidth() * widthScale), (int) (badImage.getIconHeight() * heightScale)));
         }
     });

        lonelyLabels.forEach(label-> label.setFont(scaleFont(defaultFont,widthScale)));

      /** panels.forEach((panel,list)->{
           System.out.println(panel.getWidth());
           System.out.println(panel.getHeight());
           System.out.println(widthScale);
           System.out.println(heightScale);
         panel.setSize(new Dimension((int)(list.getFirst()*widthScale),(int)(list.getLast()*heightScale)));

      // panel.setBounds(panel.getX(),panel.getY(),(int)(list.getFirst()*widthScale),(int)(list.getLast()*heightScale));
       panel.repaint();
          System.out.println(panel.getClass().getSimpleName());
           System.out.println(panel.getWidth());
           System.out.println(panel.getHeight());

       });**/
    }
}
