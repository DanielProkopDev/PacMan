package character.ghost;

import Game.RescaleUnit;
import character.AbstractCharacter;
import character.PacMan;
import character.ghost.util.AAsterisk;
import character.ghost.util.GhostHandler;
import map.MapDeck;
import map.handlers.Block;
import map.handlers.BlockHandler;
import util.Range.Range;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

import java.util.Random;
import java.util.Stack;




public abstract class AbstractGhost extends AbstractCharacter {


    private boolean stateChase;
    private boolean stateScatter;
    private boolean stateRun;
    private boolean stateDead;

    private final PacMan pacMan;

    private Stack<AAsterisk.Pair> path;

    private AAsterisk.Pair src;
    private AAsterisk.Pair dst;

    private final AAsterisk asteriks;

    private Block nextStep;

    private Block currentblock;

    private Block startBlock;

    private Block pacmanBlock;

    private final BlockHandler blockHandler;

    private  GhostHandler ghostHandler;

    protected int targetX,targetY;

    protected int ghostCenterPoint;

    protected ImageIcon badGhost;

    private long deadTimeStart=0;

    private RescaleUnit rescaleUnit;

    private int blueOffset,pinkOffset,redOffset,orangeOffset;

    private Block prevblock;



    public AbstractGhost(int x,int y,int move_distance,int size, MapDeck mapDeck) {
        super(x,y,move_distance,size,mapDeck);
        this.pacMan=PacMan.getInstance();
        this.asteriks=new AAsterisk();
        this.blockHandler=BlockHandler.getInstance();
        rescaleUnit=RescaleUnit.getInstance();
      blueOffset=5;
      pinkOffset=7;
      redOffset=4;
      orangeOffset=6;
      if (mapDeck.getRows()==16&&mapDeck.getCols()==16){
          blueOffset-=2;
          pinkOffset-=2;
          redOffset-=2;
          orangeOffset-=2;
      }

        if (this.CHARACTER_SIZE>35) {
            this.ghostCenterPoint = (int) (size * 0.2);
        }  else {
            this.ghostCenterPoint = 0;
        }

        this.badGhost= new ImageIcon("./img/badLittle.png");
        rescaleUnit.addGhostBadImage(this,badGhost);

        this.setStateScatter(true);
        assert pacMan != null;
        this.pacmanBlock=pacMan.getPacmanBlock();
        blockHandler.getBlocks().stream().filter(block -> block.getRow()==y/MapDeck.CELL_SIZE && block.getCol()==x/MapDeck.CELL_SIZE).findFirst().ifPresent(block -> this.startBlock=block);

    }




    public synchronized boolean isStateChase() {
        return stateChase;
    }

    public synchronized void setStateChase(boolean stateChase) {
        this.stateChase = stateChase;
    }

    public synchronized boolean isStateScatter() {
        return stateScatter;
    }

    public synchronized void setStateScatter(boolean stateScatter) {
        this.stateScatter = stateScatter;
    }

    public boolean isStateRun() {
        return stateRun;
    }

    public void setStateRun(boolean stateRun) {
        this.stateRun = stateRun;
    }

    public boolean isStateDead() {
        return stateDead;
    }

    public void setStateDead(boolean stateDead) {
        this.stateDead = stateDead;
    }


    @Override
    public void move() {
        try {
            this.rangeX.setStart(this.x);
            this.rangeX.setEnd(this.x + this.CHARACTER_SIZE);
            this.rangeY.setStart(this.y);
            this.rangeY.setEnd(this.y + this.CHARACTER_SIZE);
            if (!this.isStateDead() && !this.isStateRun()) {
                blockHandler.getBlocks().stream().filter(block -> block.getRow() == ((this.y + this.ghostCenterPoint) / MapDeck.CELL_SIZE) && block.getCol() == ((this.x + this.ghostCenterPoint) / MapDeck.CELL_SIZE)).findFirst().ifPresent(block -> this.currentblock = block);
                if ((!this.pacmanBlock.equals(this.currentblock.getBottomNeighbour()) && !this.pacmanBlock.equals(this.currentblock.getTopNeighbour()) && !this.pacmanBlock.equals(this.currentblock.getLeftNeighbour()) && !this.pacmanBlock.equals(this.currentblock.getRightNeighbour()) && !this.currentblock.equals(pacmanBlock)) || pacMan.isInvincible()) {
                    this.src = new AAsterisk.Pair(this.currentblock.getRow(), this.currentblock.getCol());


                    if (this.isStateChase()) {
                        if (!this.pacmanBlock.equals(this.pacMan.getPacmanBlock()) || this.path == null || this.path.isEmpty()) {
                            this.pacmanBlock = this.pacMan.getPacmanBlock();
                            this.dst = new AAsterisk.Pair(this.pacmanBlock.getRow(), this.pacmanBlock.getCol());
                            setTarget(this.pacmanBlock.getX(), this.pacmanBlock.getY());
                        } else {
                            this.dst = new AAsterisk.Pair(this.pacmanBlock.getRow(), this.pacmanBlock.getCol());
                            setTarget(this.pacmanBlock.getX(), this.pacmanBlock.getY());
                        }
                    }
                    if (this.isStateScatter()) {
                        if (this.path == null || this.path.isEmpty()) {
                            setDSTScatter();
                        }
                        if (!this.pacmanBlock.equals(this.pacMan.getPacmanBlock())) {
                            this.pacmanBlock = pacMan.getPacmanBlock();
                            setDSTScatter();
                        }
                    }

                    if ((this.path == null || this.path.isEmpty())) {
                        this.path = asteriks.aStarSearch(MapDeck.getGrid(), mapDeck.getRows(), mapDeck.getCols(), this.src, this.dst);
                        if (this.path == null) {
                            this.blockHandler.getBlocks().stream().filter(block -> !block.equals(new Block(this.dst.first, this.dst.second, MapDeck.CELL_SIZE)) && ((block.getRow() > this.dst.first + 3 || block.getRow() < this.dst.first - 3) && (block.getCol() > this.dst.second + 3 || block.getCol() < this.dst.second - 3))
                                            && !block.isWall() && !block.isInUse() && !block.equals(this.prevblock))
                                    .findAny()
                                    .ifPresent(block -> {
                                        this.dst = new AAsterisk.Pair(block.getRow(), block.getCol());
                                        block.setInUse(true);
                                        setTarget(block.getX(), block.getY());
                                    });
                            this.path = asteriks.aStarSearch(MapDeck.getGrid(), mapDeck.getRows(), mapDeck.getCols(), this.src, this.dst);
                        }
                        if (!this.path.isEmpty()) {
                            AAsterisk.Pair tmp = path.pop();
                            this.nextStep = new Block(tmp.first, tmp.second, MapDeck.CELL_SIZE);
                            blockHandler.getBlocks().stream().filter(block -> block.equals(nextStep)).findFirst().ifPresent(block -> block.setInUse(false));
                        }

                    }

                    if ((this.x == (nextStep.getX() + this.ghostCenterPoint) && this.y == (this.nextStep.getY() + this.ghostCenterPoint)) && this.path != null && !this.path.isEmpty()) {
                        AAsterisk.Pair tmp = path.pop();
                        this.nextStep = new Block(tmp.first, tmp.second, MapDeck.CELL_SIZE);
                        blockHandler.getBlocks().stream().filter(block -> block.equals(nextStep)).findFirst().ifPresent(block -> block.setInUse(false));
                        if (this.path == null) {
                            this.path = asteriks.aStarSearch(MapDeck.getGrid(), mapDeck.getRows(), mapDeck.getCols(), this.src, this.dst);

                        }
                        // System.out.println("popped the path: " + this.nextStep);
                    }

                    moveGhost(this.nextStep);
                } else if (!this.stateRun) {
                    this.nextStep = this.pacmanBlock;
                    this.path = null;
                    moveGhost(this.nextStep);
                    setTarget(this.pacmanBlock.getX(), this.pacmanBlock.getY());
                    this.pacmanBlock = pacMan.getPacmanBlock();
                }
                if (!this.pacMan.isInvincible() && !this.isStateRun()) {
                    if (this.rangeX.isInRange(pacMan.getRangeX()) && this.rangeY.isInRange(pacMan.getRangeY())) {
                        pacMan.loseLife();
                        ghostHandler.resetGhosts();
                        blockHandler.getBlocks().stream().filter(block -> block.getRow() == this.y / MapDeck.CELL_SIZE && block.getCol() == this.x / MapDeck.CELL_SIZE).findFirst().ifPresent(block -> this.currentblock = block);

                        ghostHandler.sleepNow(5000);

                    }
                }
            }
            if (this.isStateRun()) {
                blockHandler.getBlocks().stream().filter(block -> block.getRow() == ((this.y + this.ghostCenterPoint) / MapDeck.CELL_SIZE) && block.getCol() == ((this.x + this.ghostCenterPoint) / MapDeck.CELL_SIZE)).findFirst().ifPresent(block -> this.currentblock = block);
                this.dst = new AAsterisk.Pair(this.startBlock.getRow(), this.startBlock.getCol());
                if (this.path == null) {
                    if (!this.currentblock.equals(this.startBlock)) {
                        this.path = asteriks.aStarSearch(MapDeck.getGrid(), mapDeck.getRows(), mapDeck.getCols(), this.src, this.dst);
                    }
                    if (this.currentblock.equals((this.startBlock))) {
                        this.setStateRun(false);
                        this.setStateScatter(true);
                    }
                    if (this.path == null) {
                        if (!this.currentblock.equals(this.startBlock)) {
                            this.path = asteriks.aStarSearch(MapDeck.getGrid(), mapDeck.getRows(), mapDeck.getCols(), this.src, this.dst);
                        }
                    }

                    setTarget(this.startBlock.getX(), this.startBlock.getY());
                }

                if ((this.x == (this.nextStep.getX() + this.ghostCenterPoint) && this.y == (this.nextStep.getY() + this.ghostCenterPoint)) && this.path != null && !this.path.isEmpty()) {
                    AAsterisk.Pair tmp = path.pop();
                    this.nextStep = new Block(tmp.first, tmp.second, MapDeck.CELL_SIZE);
                    // System.out.println("popped the path: " + this.nextStep);
                }
                if (this.path != null) {
                    if (this.path.isEmpty()) {
                        this.setStateRun(false);
                        this.setStateScatter(true);
                    }
                }
                moveGhost(this.nextStep);
                if (this.rangeX.isInRange(pacMan.getRangeX()) && this.rangeY.isInRange(pacMan.getRangeY())) {
                    this.setStateDead(true);
                    this.deadTimeStart = System.currentTimeMillis();
                    this.setStateRun(false);
                }


            }
            if (this.isStateDead()) {
                if ((System.currentTimeMillis() - this.deadTimeStart) / 1000 >= 10) {
                    this.resetPosition();
                    this.setStateDead(false);
                    this.setStateScatter(true);
                    this.setPath(null);
                    blockHandler.getBlocks().stream().filter(block -> block.getRow() == this.y / MapDeck.CELL_SIZE && block.getCol() == this.x / MapDeck.CELL_SIZE).findFirst().ifPresent(block -> this.currentblock = block);
                }
            }
            prevblock = new Block(this.dst.first, this.dst.second, MapDeck.CELL_SIZE);
        }catch (Exception e){
            return;
        }
    }
    private void setDSTScatter(){
        switch (this.getClass().getSimpleName()){

            case "GhostBlue" :
                this.blockHandler.getBlocks().stream()
                        .filter(a -> ((a.getRow() > pacmanBlock.getRow() + blueOffset  && a.getCol() > pacmanBlock.getCol() + blueOffset  )&& !a.isWall() && !a.isInUse())||
                                ((a.getRow() < pacmanBlock.getRow() - blueOffset && (a.getCol() < pacmanBlock.getCol() - blueOffset) && !a.isWall() && !a.isInUse())))
                        .findAny()
                        .ifPresent(currentBlock ->{dst = new AAsterisk.Pair(currentBlock.getRow(), currentBlock.getCol());
                        currentBlock.setInUse(true);
                        setTarget(currentBlock.getX(), currentBlock.getY());});
                break;
            case "GhostOrange" :
                this.blockHandler.getBlocks().stream()
                        .filter(a -> ((a.getRow() > pacmanBlock.getRow() + orangeOffset  && a.getCol() > pacmanBlock.getCol() + orangeOffset  )&& !a.isWall() && !a.isInUse())||
                                ((a.getRow() < pacmanBlock.getRow() - orangeOffset  && (a.getCol() < pacmanBlock.getCol() - orangeOffset ) && !a.isWall() && !a.isInUse())))
                        .findAny()
                        .ifPresent(currentBlock -> {dst = new AAsterisk.Pair(currentBlock.getRow(), currentBlock.getCol());
                            currentBlock.setInUse(true);
                            setTarget(currentBlock.getX(), currentBlock.getY());});
                break;
            case "GhostPink" :
                this.blockHandler.getBlocks().stream()
                        .filter(a -> ((a.getRow() > pacmanBlock.getRow() + pinkOffset  && a.getCol() > pacmanBlock.getCol() + pinkOffset )&& !a.isWall() && !a.isInUse())||
                                ((a.getRow() < pacmanBlock.getRow() - pinkOffset && (a.getCol() < pacmanBlock.getCol() - pinkOffset) && !a.isWall() && !a.isInUse())))
                        .findAny()
                        .ifPresent(currentBlock -> {dst = new AAsterisk.Pair(currentBlock.getRow(), currentBlock.getCol());
                            currentBlock.setInUse(true);
                            setTarget(currentBlock.getX(), currentBlock.getY());});
                break;
            default:
                this.blockHandler.getBlocks().stream()
                        .filter(a -> ((a.getRow() > pacmanBlock.getRow() + redOffset  && a.getCol() > pacmanBlock.getCol() + redOffset  )&& !a.isWall() && !a.isInUse())||
                                ((a.getRow() < pacmanBlock.getRow() - redOffset && (a.getCol() < pacmanBlock.getCol() - redOffset) && !a.isWall() && !a.isInUse())))
                        .findAny()
                        .ifPresent(currentBlock -> {dst = new AAsterisk.Pair(currentBlock.getRow(), currentBlock.getCol());
                            currentBlock.setInUse(true);
                            setTarget(currentBlock.getX(), currentBlock.getY());});
                break;

        }
    }

    private void moveGhost(Block block){


        if ((block.getX() + ghostCenterPoint > x)) {
            x+=MOVE_DISTANCE;

        } else if ((block.getX()  < x)) {
            x -= MOVE_DISTANCE;

        }
         if ((block.getY() + ghostCenterPoint > y)) {
            y+=MOVE_DISTANCE;

        } else if ((block.getY()  < y)) {
            y-=MOVE_DISTANCE;

        }
    }
    private void moveGhostClose(Block block){


        if ((block.getX() + this.ghostCenterPoint > this.x)) {
            targetX+=MOVE_DISTANCE;
            if (!mapDeck.isWall((targetY/MapDeck.CELL_SIZE),targetX/MapDeck.CELL_SIZE)){
                this.x = targetX;
            }
        } else if ((block.getX()  < this.x)) {

            this.x -= MOVE_DISTANCE;
        }
        if ((block.getY() + this.ghostCenterPoint > this.y)) {
            this.y += MOVE_DISTANCE;


        } else if ((block.getY()  < this.y)) {
            this.y -= MOVE_DISTANCE;

        }
    }
    private void setDSTRun(){

    }
    private void setDSTDead(){

    }

    public void setPath(Stack<AAsterisk.Pair> path) {
        this.path = path;
    }

    public void setGhostHandler(GhostHandler ghostHandler) {
        this.ghostHandler = ghostHandler;
    }

    public void dropGhost(){
        Random random = new Random();
        int chance= random.nextInt(5);
        switch (chance){
            case 0:
                if (checkValue()){
                    MapDeck.grid[currentblock.getRow()][currentblock.getCol()] = MapDeck.grid[currentblock.getRow()][currentblock.getCol()] + 18;
                }
                break;
            case 1:
                if (checkValue()) {
                    MapDeck.grid[currentblock.getRow()][currentblock.getCol()] = MapDeck.grid[currentblock.getRow()][currentblock.getCol()] + 19;
                }
                break;
            case 2:
                if (checkValue() ) {
                    MapDeck.grid[currentblock.getRow()][currentblock.getCol()] = MapDeck.grid[currentblock.getRow()][currentblock.getCol()] + 12;
                }
                break;
            case 3:
                if (checkValue()) {
                    MapDeck.grid[currentblock.getRow()][currentblock.getCol()] = MapDeck.grid[currentblock.getRow()][currentblock.getCol()] + 13;
                }
                break;
            case 4:
                if (checkValue()) {
                    MapDeck.grid[currentblock.getRow()][currentblock.getCol()] = MapDeck.grid[currentblock.getRow()][currentblock.getCol()] + 14;
                }
                break;

            default:
        }
    }

    public Block getCurrentblock() {
        return currentblock;
    }

    private boolean checkValue(){
        int row= this.currentblock.getRow();
        int col = this.currentblock.getCol();
        return ((MapDeck.grid[row][col] != 18 && MapDeck.grid[row][col] != 21 && MapDeck.grid[row][col] != 19 && MapDeck.grid[row][col] != 22
                && MapDeck.grid[row][col] != 12 && MapDeck.grid[row][col] != 15 && MapDeck.grid[row][col] != 13 && MapDeck.grid[row][col] != 16
                && MapDeck.grid[row][col] != 14 && MapDeck.grid[row][col] != 17&& MapDeck.grid[row][col] != 51&& MapDeck.grid[row][col] != 52&& MapDeck.grid[row][col] != 45&& MapDeck.grid[row][col] != 46&& MapDeck.grid[row][col] != 47 ) && !currentblock.isWall() );
     }
        protected void drawEyes(Graphics2D g2d, int ghostX, int ghostY, int ghostWidth, int ghostHeight, int targetX, int targetY) {
            int eyeWidth = (ghostWidth / 4)+1;
            int eyeHeight = (ghostHeight / 4)+1;

            int leftEyeX = ghostX + ghostWidth / 6;
            int rightEyeX = ghostX + ghostWidth / 2;
            int eyeY = ghostY + ghostHeight / 4;

            int pupilSize = (eyeWidth / 2)+2;
            int maxPupilOffset = pupilSize / 2;

            int dx = targetX - ghostX;
            int dy = targetY - ghostY;
            double angle = Math.atan2(dy, dx);

            int pupilOffsetX = (int) (maxPupilOffset * Math.cos(angle));
            int pupilOffsetY = (int) (maxPupilOffset * Math.sin(angle));

            g2d.setColor(Color.WHITE);
            g2d.fillOval(leftEyeX, eyeY, eyeWidth, eyeHeight);
            g2d.fillOval(rightEyeX, eyeY, eyeWidth, eyeHeight);

            g2d.setColor(Color.BLUE);
            g2d.fillOval(leftEyeX + eyeWidth / 4 + pupilOffsetX, eyeY + eyeHeight / 4 + pupilOffsetY, pupilSize, pupilSize);
            g2d.fillOval(rightEyeX + eyeWidth / 4 + pupilOffsetX, eyeY + eyeHeight / 4 + pupilOffsetY, pupilSize, pupilSize);
        }


    public void setTarget(int targetX, int targetY) {
        this.targetX = targetX;
        this.targetY = targetY;
    }

    public ImageIcon getBadGhost() {
        return badGhost;
    }

    public void setBadGhost(ImageIcon badGhost) {
        this.badGhost = badGhost;
    }
}
