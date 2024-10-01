package map.handlers;


import map.util.Scalable;
import util.Range.Range;
import map.MapDeck;
import util.Range.RangeFace;

import java.util.ArrayList;

public class Block  {
    private  int x;
    private  int y;

    private int x1,x2,y1,y2;
    private final int row;
    private final int col;

    private  Range rangeX;
    private  Range rangeY;

    private boolean isWall;

    private boolean isOuterWall;

    private boolean hasPoint;

    private boolean isSpawn;

    private boolean isInUse;

    private  Block topNeighbour;

    private  Block bottomNeighbour;
    private Block leftNeighbour;
    private Block rightNeighbour;

    private final ArrayList<Block> neighbors = new ArrayList<>();


    public Block(int row, int col,int size){
        x = col * size;
        y = row * size;
        this.row=row;
        this.col=col;

        this.rangeX=new Range(x,x+size);
        this.rangeY = new Range(y,y+size);
    }
    public Block(int row, int col,int size,boolean isWall){
        x = col * size;
        y = row * size;
        this.row=row;
        this.col=col;

        this.rangeX=new Range(x,x+size);
        this.rangeY = new Range(y,y+size);
        this.isWall=isWall;
    }
    public Block(int row, int col,int size,boolean isWall, boolean hasPoint){
        x = col * size;
        y = row * size;
        this.row=row;
        this.col=col;

        this.rangeX=new Range(x,x+size);
        this.rangeY = new Range(y,y+size);
        this.isWall=isWall;
        this.hasPoint=hasPoint;
    }

    public Block(int row, int col,int size,boolean isWall, boolean hasPoint,boolean isSpawn){
        x = col * size;
        y = row * size;
        this.row=row;
        this.col=col;
        this.rangeX=new Range(x,x+size);
        this.rangeY = new Range(y,y+size);
        this.isWall=isWall;
        this.hasPoint=hasPoint;
        this.isSpawn=isSpawn;
    }
    public Block(int row, int col,int size,boolean isWall,boolean hasPoint,boolean isSpawn, boolean isOuterWall){
        x = col * size;
        y = row * size;
        this.row=row;
        this.col=col;
        this.rangeX=new Range(x,x+size);
        this.rangeY = new Range(y,y+size);
        this.isWall=isWall;
        this.hasPoint=hasPoint;
        this.isSpawn=isSpawn;
        this.isOuterWall=isOuterWall;
    }
    public void addNeighbors(ArrayList<Block> blocks){
        //top neighbor
        if (this.row > 0){
            blocks.stream()
                    .filter(a -> a.row == this.row - 1 && a.col == this.col)
                    .findFirst()
                    .ifPresent(a->{neighbors.add(a);
                        this.topNeighbour=a;
                    });
        }
        //top right neighbor
        if (this.row > 0){
            blocks.stream()
                    .filter(a -> a.row == this.row - 1 && a.col == this.col +1)
                    .findFirst()
                    .ifPresent(neighbors::add);
        }
        //top left neighbor
        if (this.row > 0){
            blocks.stream()
                    .filter(a -> a.row == this.row - 1 && a.col == this.col -1)
                    .findFirst()
                    .ifPresent(neighbors::add);
        }
        //right neighbor
        if (this.col < MapDeck.getGrid()[0].length){
            blocks.stream()
                    .filter(a -> a.row == this.row && a.col == this.col+1)
                    .findFirst()
                    .ifPresent(a->{neighbors.add(a);
                        this.rightNeighbour=a;
                    });
        }
        //bottom neighbor
        if (this.row < MapDeck.getGrid().length){
            blocks.stream()
                    .filter(a -> a.row == this.row + 1 && a.col == this.col)
                    .findFirst()
                    .ifPresent(a->{neighbors.add(a);
                        this.bottomNeighbour=a;
                    });

        }
        //bottom left neighbor
        if (this.row < MapDeck.getGrid().length){
            blocks.stream()
                    .filter(a -> a.row == this.row + 1 && a.col == this.col-1)
                    .findFirst()
                    .ifPresent(neighbors::add);

        }
        //bottom right neighbor
        if (this.row < MapDeck.getGrid().length){
            blocks.stream()
                    .filter(a -> a.row == this.row + 1 && a.col == this.col+1)
                    .findFirst()
                    .ifPresent(neighbors::add);

        }
        //left neighbor
        if (this.col > 0){
            blocks.stream()
                    .filter(a -> a.row == this.row && a.col == this.col-1)
                    .findFirst()
                    .ifPresent(a->{neighbors.add(a);
                        this.leftNeighbour=a;
                    });

        }
    }

    public ArrayList<Block> getNeighbors() {
        return neighbors;
    }

    public synchronized int getX() {
        return x;
    }

    public synchronized int getY() {
        return y;
    }

    public synchronized int getRow() {
        return row;
    }

    public synchronized int getCol() {
        return col;
    }

    public boolean isWall() {
        return isWall;
    }

    public Range getRangeX() {
        return rangeX;
    }

    public Range getRangeY() {
        return rangeY;
    }

    public boolean isNeighbor(Block block){
       return this.neighbors.contains(block);
    }



    public boolean isInUse() {
        return isInUse;
    }

    public void setWall(boolean wall) {
        isWall = wall;
    }

    public boolean isHasPoint() {
        return hasPoint;
    }

    public void setHasPoint(boolean hasPoint) {
        this.hasPoint = hasPoint;
    }

    public boolean isSpawn() {
        return isSpawn;
    }

    public void setSpawn(boolean spawn) {
        isSpawn = spawn;
    }

    public void setInUse(boolean inUse) {
        isInUse = inUse;
    }

    public boolean isOuterWall() {
        return isOuterWall;
    }

    public Block getTopNeighbour() {
        return topNeighbour;
    }

    public Block getBottomNeighbour() {
        return bottomNeighbour;
    }

    public Block getLeftNeighbour() {
        return leftNeighbour;
    }

    public Block getRightNeighbour() {
        return rightNeighbour;
    }

    @Override
    public String toString() {
        return "Block{" +
                "row=" + row +
                ", col=" + col +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            return obj instanceof Block && this.row == ((Block) obj).row && this.col == ((Block) obj).col;
        }
        return false;
    }





}
