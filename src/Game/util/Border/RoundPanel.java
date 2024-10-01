package Game.util.Border;




import Game.RescaleUnit;

import javax.swing.*;
import java.awt.*;

import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;


public class RoundPanel extends JPanel {



    private int roundTopLeft = 0;
    private int roundTopRight = 0;
    private int roundBottomLeft = 0;
    private int roundBottomRight = 0;
    private int width;
    private int height;
    private ArrayList<Integer> sizes;
    private RescaleUnit rescaleUnit;

    public RoundPanel(int width, int height,int roundTopLeft, int roundTopRight, int roundBottomLeft, int roundBottomRight){
        this.roundTopLeft=roundTopLeft;
        this.roundTopRight=roundTopRight;
        this.roundBottomLeft=roundBottomLeft;
        this.roundBottomRight=roundBottomRight;
        this.sizes=new ArrayList<>();
        this.rescaleUnit=RescaleUnit.getInstance();
        sizes.add(0,width);
        sizes.add(1,height);
        assert rescaleUnit != null;
       // rescaleUnit.addPanels(this,sizes);


        this.width=width;
        this.height=height;
        setSize(width, height);
        setOpaque(false);


    }



    @Override
    public void paint(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(getColor());
        Area area = new Area(createRoundTopLeft());
        if (roundTopRight > 0) {
            area.intersect(new Area(createRoundTopRight()));
        }
        if (roundBottomLeft > 0) {
            area.intersect(new Area(createRoundBottomLeft()));
        }
        if (roundBottomRight > 0) {
            area.intersect(new Area(createRoundBottomRight()));
        }
        //g2.dr
       // g2.draw(area);
       g2.fill(area);
        g2.dispose();
        super.paint(grphcs);
    }



    private Shape createRoundTopLeft() {

        int roundX = Math.min(width, roundTopLeft);
        int roundY = Math.min(height, roundTopLeft);
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double(roundX / 2, 0, width - roundX / 2, height)));
        area.add(new Area(new Rectangle2D.Double(0, roundY / 2, width, height - roundY / 2)));
        return area;
    }

    private Shape createRoundTopRight() {

        int roundX = Math.min(width, roundTopRight);
        int roundY = Math.min(height, roundTopRight);
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double(0, 0, width - roundX / 2, height)));
        area.add(new Area(new Rectangle2D.Double(0, roundY / 2, width, height - roundY / 2)));
        return area;
    }

    private Shape createRoundBottomLeft() {

        int roundX = Math.min(width, roundBottomLeft);
        int roundY = Math.min(height, roundBottomLeft);
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double(roundX / 2, 0, width - roundX / 2, height)));
        area.add(new Area(new Rectangle2D.Double(0, 0, width, height - roundY / 2)));
        return area;
    }

    private Shape createRoundBottomRight() {

        int roundX = Math.min(width, roundBottomRight);
        int roundY = Math.min(height, roundBottomRight);
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double(0, 0, width - roundX / 2, height)));
        area.add(new Area(new Rectangle2D.Double(0, 0, width, height - roundY / 2)));
        return area;
    }

    public int getRoundTopLeft() {
        return roundTopLeft;
    }

    public void setRoundTopLeft(int roundTopLeft) {
        this.roundTopLeft = roundTopLeft;
        repaint();
    }

    public int getRoundTopRight() {
        return roundTopRight;
    }

    public void setRoundTopRight(int roundTopRight) {
        this.roundTopRight = roundTopRight;
        repaint();
    }

    public int getRoundBottomLeft() {
        return roundBottomLeft;
    }

    public void setRoundBottomLeft(int roundBottomLeft) {
        this.roundBottomLeft = roundBottomLeft;
        repaint();
    }

    public int getRoundBottomRight() {
        return roundBottomRight;
    }

    public void setRoundBottomRight(int roundBottomRight) {
        this.roundBottomRight = roundBottomRight;
        repaint();
    }

    private GradientPaint getColor() {

        // Losowe wartości składowych RGB dla pierwszego koloru
        int r1 = 100;
        int g1 = 100;
        int b1 = 100;

        // Losowe wartości składowych RGB dla drugiego koloru
        int r2 = 190;
        int g2 = 170;
        int b2 = 150;

        // Zwróć kolor w środkowym punkcie gradientu
        return new GradientPaint(0, 0, new Color(r1, g1, b1), 0, this.getHeight(), new Color(r2, g2, b2));
    }

}
