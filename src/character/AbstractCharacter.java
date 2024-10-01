package character;
import map.util.Scalable;
import util.Drawable;
import util.Movable;

import java.io.Serializable;

import util.Range.Range;
import map.MapDeck;
import util.Range.RangeFace;

import javax.swing.*;

public abstract class AbstractCharacter extends JComponent implements Drawable, Movable,Serializable {

    protected  int x;
    protected  int y;


    protected final int initialX, initialY;
    protected int CHARACTER_SIZE;
    protected int MOVE_DISTANCE;

    protected int size;
    protected MapDeck mapDeck;
    protected Range rangeX;
    protected Range rangeY;


    public AbstractCharacter(int x,int y,int move_distance,int size, MapDeck mapDeck) {
        this.x=x;
        this.y=y;
        this.initialX = x;
        this.initialY =y;
        this.MOVE_DISTANCE=move_distance;
        this.size = size;
        this.CHARACTER_SIZE = size;
        this.mapDeck = mapDeck;
        this.rangeX=new Range(x,x+CHARACTER_SIZE);
        this.rangeY=new Range(y,y+CHARACTER_SIZE);
    }

    public void setMap(MapDeck mapDeck) {
        this.mapDeck = mapDeck;
    }

    public synchronized int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public synchronized int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getCHARACTER_SIZE() {
        return CHARACTER_SIZE;
    }

    public void setCHARACTER_SIZE(int CHARACTER_SIZE) {
        this.CHARACTER_SIZE = CHARACTER_SIZE;
    }

    public int getMOVE_DISTANCE() {
        return MOVE_DISTANCE;
    }

    public void setMOVE_DISTANCE(int MOVE_DISTANCE) {
        this.MOVE_DISTANCE = MOVE_DISTANCE;
    }




    public MapDeck getMap() {
        return mapDeck;
    }

    public Range getRangeX() {
        return rangeX;
    }

    public Range getRangeY() {
        return rangeY;
    }

    private int getCol(){
        return (this.x/MapDeck.CELL_SIZE);
    }
    private int getRow(){
        return (this.y/MapDeck.CELL_SIZE);
    }

    public void resetPosition() {
        this.x = initialX;
        this.y = initialY;
        this.rangeX.setStart(x);
        this.rangeX.setEnd(x+CHARACTER_SIZE);
        this.rangeY.setStart(y);
        this.rangeY.setEnd(y+CHARACTER_SIZE);
    }


    public void setRangeX(Range rangeX) {
        this.rangeX = rangeX;
    }

    public void setRangeY(Range rangeY) {
        this.rangeY = rangeY;
    }
}
