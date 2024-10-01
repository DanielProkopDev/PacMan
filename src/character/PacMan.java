package character;

import Game.LeftPanel;
import Game.LeftPanel2;
import character.ghost.AbstractGhost;
import character.ghost.util.AAsterisk;
import map.MapDeck;
import map.handlers.Block;
import map.handlers.BlockHandler;
import util.Range.CollisionPredicate;
import util.Range.PointPredicate;
import util.Range.Range;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.security.Key;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Vector;

public class PacMan extends AbstractCharacter{


    private static volatile PacMan instance = null;
    protected int score = 0;


    protected int lives =3;

    protected int mouthAngle = 45;
    protected int rotationAngle = 0;
    private boolean movingUp = false;
    private boolean movingDown = false;
    private boolean movingLeft = false;
    private boolean movingRight = false;

    private Block pacmanBlock;

    private final BlockHandler blockHandler;

    private int direction;

    private boolean moving=false;

    private boolean spaceBarActive=false;

    private boolean removeWallChosen;

    private boolean speedChosen;

    private boolean teleportChoosen;

    private boolean invincibleChosen;

    private long timeStartSpeed=0;

    private long timeStartInvincible=0;

    private boolean invincible=false;

    private int valueRemoveWall,valueSpeed,valueTeleport,valueInvincible;

    private LeftPanel2 leftPanel;

    private ArrayList<AbstractGhost> ghosts;

    private boolean breakTime;

    private boolean speed;


    public PacMan(int x,int y,int move_distance,int direction,int size, MapDeck mapDeck) {
        super(x,y,move_distance,size, mapDeck);
        this.direction=direction;
        this.blockHandler = BlockHandler.getInstance();
        blockHandler.getBlocks().stream()
                .filter(a -> (a.getRow() == (y / MapDeck.CELL_SIZE) && a.getCol() == (x / MapDeck.CELL_SIZE) ) && !a.isWall())
                .findFirst()
                .ifPresent(currentBlock -> pacmanBlock = currentBlock);
        instance=this;

    }
    public static PacMan getInstance(int x, int y, int move_distance, int direction, int cellSize, MapDeck mapDeck){
        if (instance==null){
            synchronized (PacMan.class){
                if (instance==null){
                    instance=new PacMan(x,y,move_distance,direction,cellSize, mapDeck);
                }
            }
        }
        return instance;
    }
    public static PacMan getInstance(){
        if (instance!=null){
            synchronized (PacMan.class){
                if (instance!=null){
                    return instance;
                }
            }
        }
        return null;
    }
    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.rotate(Math.toRadians(rotationAngle), x + (double) CHARACTER_SIZE / 2, y + (double) CHARACTER_SIZE / 2);
        g2d.setColor(Color.YELLOW);
        g2d.fillArc(x, y, CHARACTER_SIZE, CHARACTER_SIZE, mouthAngle, 360 - 2 * mouthAngle);

        super.paintComponent(g);
        g2d.dispose();
        System.out.println("PACMAN PAINTCPOMPONENT ZOSTAL WEZWANY!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + getX() + " " + getY() + " " + "A pacmana nigdzie nie ma, jest duchem ale nei cialem");
    }
    @Override
    public void draw(Graphics g) {

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.rotate(Math.toRadians(rotationAngle), x + (double) CHARACTER_SIZE / 2, y + (double) CHARACTER_SIZE / 2);
        g2d.setColor(Color.YELLOW);
        g2d.fillArc(x, y, CHARACTER_SIZE, CHARACTER_SIZE, mouthAngle, 360 - 2 * mouthAngle);

        g2d.dispose();
    }

    @Override
    public  void move() {

        int targetX = x;
        int targetY = y;

        blockHandler.getBlocks().stream()
                .filter(a -> (a.getRow() == (y / MapDeck.CELL_SIZE) && a.getCol() == (x / MapDeck.CELL_SIZE) ) && !a.isWall())
                .findFirst()
                .ifPresent(currentBlock -> pacmanBlock = currentBlock);


        if (movingLeft) {
            targetX -= MOVE_DISTANCE;
        }
        if (movingRight) {
            targetX += MOVE_DISTANCE;
        }
        if (movingUp) {
            targetY -= MOVE_DISTANCE;
        }
        if (movingDown) {
            targetY += MOVE_DISTANCE;
        }
        this.rangeX.setStart(targetX);
        this.rangeX.setEnd(targetX+CHARACTER_SIZE);
        this.rangeY.setStart(targetY);
        this.rangeY.setEnd(targetY+CHARACTER_SIZE);

        boolean collision = pacmanBlock.getNeighbors().stream().filter(Block::isWall).anyMatch(block -> {
            Range blockRangeX = block.getRangeX();
            Range blockRangeY = block.getRangeY();
            CollisionPredicate predicate = (pacmanX,pacmanY, movingRight, movingLeft, movingUp, movingDown) ->
                    pacmanX.isInRangeCollision(blockRangeX, movingRight, movingLeft, movingUp, movingDown) &&
                            pacmanY.isInRangeCollision(blockRangeY, movingRight, movingLeft, movingUp, movingDown);

            return predicate.test(rangeX,rangeY, movingRight, movingLeft, movingUp, movingDown);
        });


        boolean isOnPoint=false;
        if (pacmanBlock != null) {
            PointPredicate pointPredicate = (pacmanX,pacmanY,right,left,up,down) ->
                    pacmanBlock.getRangeX().isInRangePoint(pacmanX,right,left,up,down) && pacmanBlock.getRangeY().isInRangePoint(pacmanY,right,left,up,down);

            isOnPoint=pointPredicate.test(rangeX, rangeY, movingRight, movingLeft, movingUp, movingDown);

        }


        if (!collision) {
            x = targetX;
            y = targetY;
        }
       if (collision){
           if (!spaceBarActive) {
               setMoving(false);
               if (direction == KeyEvent.VK_D) {
                   this.x -= 3;

                   unSetTheMove(false,false,false,false);
               } else if (direction == KeyEvent.VK_A) {
                   this.x += 3;

                   unSetTheMove(false,false,false,false);

               } else if (direction == KeyEvent.VK_S) {
                   this.y -= 3;

                   unSetTheMove(false,false,false,false);

               } else {
                   this.y += 3;

                   unSetTheMove(false,false,false,false);

               }
           }else if (removeWallChosen&&valueRemoveWall>0){
               Optional<Block> neighborOptional;
               boolean hasOuterWall;
               if (direction == KeyEvent.VK_D) {
                   assert pacmanBlock != null;
                   neighborOptional=pacmanBlock.getNeighbors().stream().filter(block -> block.getRow()== pacmanBlock.getRow() && block.getCol()== pacmanBlock.getCol()+1).findFirst();
                   hasOuterWall=neighborOptional.map(Block::isOuterWall).orElse(false);
                   if (!hasOuterWall) {
                       MapDeck.grid[pacmanBlock.getRow()][pacmanBlock.getCol() + 1] = 0;
                       pacmanBlock.getNeighbors().stream().filter(block -> block.getRow() == pacmanBlock.getRow() && block.getCol() == pacmanBlock.getCol() + 1).findFirst().ifPresent(block -> block.setWall(false));
                   }
               } else if (direction == KeyEvent.VK_A) {
                   assert pacmanBlock != null;
                   neighborOptional=pacmanBlock.getNeighbors().stream().filter(block -> block.getRow()== pacmanBlock.getRow() && block.getCol()== pacmanBlock.getCol()-1).findFirst();
                   hasOuterWall=neighborOptional.map(Block::isOuterWall).orElse(false);
                   if (!hasOuterWall) {
                       MapDeck.grid[pacmanBlock.getRow()][pacmanBlock.getCol() - 1] = 0;
                       pacmanBlock.getNeighbors().stream().filter(block -> block.getRow() == pacmanBlock.getRow() && block.getCol() == pacmanBlock.getCol() - 1).findFirst().ifPresent(block -> block.setWall(false));
                   }
               } else if (direction == KeyEvent.VK_S) {
                   assert pacmanBlock != null;
                   neighborOptional=pacmanBlock.getNeighbors().stream().filter(block -> block.getRow()== pacmanBlock.getRow()+1 && block.getCol()== pacmanBlock.getCol()).findFirst();
                   hasOuterWall=neighborOptional.map(Block::isOuterWall).orElse(false);
                   if (!hasOuterWall) {
                   MapDeck.grid[pacmanBlock.getRow()+1][pacmanBlock.getCol()]=0;
                   pacmanBlock.getNeighbors().stream().filter(block -> block.getRow()== pacmanBlock.getRow()+1 && block.getCol()== pacmanBlock.getCol()).findFirst().ifPresent(block -> block.setWall(false));
                   }
               } else {
                   assert pacmanBlock != null;
                   neighborOptional=pacmanBlock.getNeighbors().stream().filter(block -> block.getRow()== pacmanBlock.getRow()-1 && block.getCol()== pacmanBlock.getCol()).findFirst();
                   hasOuterWall=neighborOptional.map(Block::isOuterWall).orElse(false);
                   if (!hasOuterWall) {
                       MapDeck.grid[pacmanBlock.getRow() - 1][pacmanBlock.getCol()] = 0;
                       pacmanBlock.getNeighbors().stream().filter(block -> block.getRow() == pacmanBlock.getRow() - 1 && block.getCol() == pacmanBlock.getCol()).findFirst().ifPresent(block -> block.setWall(false));
                   }
               }
               valueRemoveWall--;
           }
       }

       else if (speedChosen&&spaceBarActive&&valueSpeed>0&&!breakTime) {
           if (timeStartSpeed == 0){
               timeStartSpeed = System.currentTimeMillis();
               MOVE_DISTANCE=2;
               speed=true;
               valueSpeed--;
               leftPanel.setColor(1,Color.YELLOW);
           }
       } else if (invincibleChosen&&spaceBarActive&&valueInvincible>0&&!breakTime) {
           if (timeStartInvincible == 0) {
               timeStartInvincible = System.currentTimeMillis();
               invincible = true;
               valueInvincible--;
               leftPanel.setColor(2, Color.YELLOW);
           }
       }
        if (timeStartSpeed!=0&&((System.currentTimeMillis() - timeStartSpeed)/1000>=20)){
           timeStartSpeed=0;
           MOVE_DISTANCE=1;
           speed=false;
            leftPanel.setColor(1,Color.BLACK);
       }
        if (timeStartInvincible!=0&&((System.currentTimeMillis() - timeStartInvincible)/1000>=20)){
            timeStartInvincible=0;
            invincible=false;
            leftPanel.setColor(2,Color.BLACK);
        }
        if (isOnPoint && !pacmanBlock.isSpawn() && pacmanBlock.isHasPoint()){
            switch (MapDeck.grid[pacmanBlock.getRow()][pacmanBlock.getCol()]){
                case 15:
                    valueSpeed++;
                    break;
                case 16:
                    valueTeleport++;
                    break;
                case 17:
                    valueInvincible++;
                    break;
                case 21:
                    if(lives < 3){
                        lives++;
                    }
                    break;
                case 22:
                    valueRemoveWall++;
                    break;
                case 33:
                   setRun();
                    break;
                case 51:
                    setRun();
                    if(lives < 3){
                        lives++;
                    }
                    break;
                case 52:
                    setRun();
                    valueRemoveWall++;
                    break;
                case 45:
                    setRun();
                    valueSpeed++;
                    break;
                case 46:
                    setRun();
                    valueTeleport++;
                    break;
                case 47:
                    setRun();
                    valueInvincible++;
                    break;
                default:
            }
            MapDeck.grid[pacmanBlock.getRow()][pacmanBlock.getCol()] = 0;
            pacmanBlock.setHasPoint(false);
            ++score;
        }
        if (isOnPoint && !pacmanBlock.isSpawn()){
            switch (MapDeck.grid[pacmanBlock.getRow()][pacmanBlock.getCol()]){
                case 12:
                    valueSpeed++;
                    break;
                case 13:
                    valueTeleport++;
                    break;
                case 14:
                    valueInvincible++;
                    break;
                case 18:
                    if(lives < 3){
                        lives++;
                    }
                    break;
                case 19:
                    valueRemoveWall++;
                    break;
                default:
            }
            MapDeck.grid[pacmanBlock.getRow()][pacmanBlock.getCol()] = 0;
            pacmanBlock.setHasPoint(false);
        }

        if (y < 0) {

            y = mapDeck.getHeight() - (CHARACTER_SIZE+5);
        } else if (y + CHARACTER_SIZE +5 > mapDeck.getHeight()) {

            y = 0;
        }


        if (x + CHARACTER_SIZE +5 > mapDeck.getWidth()) {

            x = 5;
        } else if (x < 0) {

            x = mapDeck.getWidth() - (CHARACTER_SIZE+5);
        }
        updateMouthAngle();
        updateRotationAngle();


    }

    public void updateMouthAngle() {

        mouthAngle += 1;
        if (mouthAngle > 45 || mouthAngle < 15) {
            mouthAngle = 15;
        }
    }

    public void updateRotationAngle() {

        switch (direction) {
            case KeyEvent.VK_A:
                rotationAngle = 180;
                break;
            case KeyEvent.VK_D:
                rotationAngle = 0;
                break;
            case KeyEvent.VK_W:
                rotationAngle = 270;
                break;
            case KeyEvent.VK_S:
                rotationAngle = 90;
                break;
        }
    }
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_A:
                unSetTheMove(true,false,false,false);
                direction=keyCode;
                break;
            case KeyEvent.VK_D:
                unSetTheMove(false,true,false,false);
                direction=keyCode;
                break;
            case KeyEvent.VK_W:
                unSetTheMove(false,false,false,true);
                direction=keyCode;
                break;
            case KeyEvent.VK_S:
                unSetTheMove(false,false,true,false);
                direction=keyCode;
                break;
            case KeyEvent.VK_SPACE:
                spaceBarActive=true;
                break;
        }
        moving=true;
    }
   public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_SPACE:
                spaceBarActive=false;
                break;
        }
    }

    public synchronized Block getPacmanBlock() {
        return pacmanBlock;
    }

    public int getLives() {
        return lives;
    }
    public void loseLife() {
        lives--;
        resetPosition();
        blockHandler.getBlocks().stream()
                .filter(a -> (a.getRow() == (y / MapDeck.CELL_SIZE) && a.getCol() == (x / MapDeck.CELL_SIZE) ) && !a.isWall())
                .findFirst()
                .ifPresent(currentBlock -> pacmanBlock = currentBlock);
        setMoving(false);
        unSetTheMove(false,false,false,false);
        MOVE_DISTANCE=1;
        spaceBarActive=false;
        invincible=false;
        timeStartSpeed=0;
        timeStartInvincible=0;
        leftPanel.setColor(1,Color.BLACK);
        leftPanel.setColor(2,Color.BLACK);
        breakTime=true;

    }

    public void resetLives(){
        lives =3;
    }
    public void resetScore(){
        this.score=0;
 }
    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }
    public void unSetTheMove(boolean movingLeft, boolean movingRight, boolean movingDown,boolean movingUp){
        this.movingLeft=movingLeft;
        this.movingRight=movingRight;
        this.movingDown=movingDown;
        this.movingUp=movingUp;

    }

    public int getScore() {
        return score;
    }


    public boolean isRemoveWallChosen() {
        return removeWallChosen;
    }

    public void setRemoveWallChosen(boolean removeWallChosen) {
        this.removeWallChosen = removeWallChosen;
    }

    public boolean isSpeedChosen() {
        return speedChosen;
    }

    public void setSpeedChosen(boolean speedChosen) {
        this.speedChosen = speedChosen;

    }

    public boolean isTeleportChoosen() {
        return teleportChoosen;
    }

    public void setTeleportChoosen(boolean teleportChoosen) {
        this.teleportChoosen = teleportChoosen;
    }

    public boolean isInvincibleChosen() {
        return invincibleChosen;
    }

    public void setInvincibleChosen(boolean invincibleChosen) {
        this.invincibleChosen = invincibleChosen;
    }


    public boolean isInvincible() {
        return invincible;
    }

    public boolean isSpaceBarActive() {
        return spaceBarActive;
    }

    public void setPacmanBlock(Block pacmanBlock) {
        this.pacmanBlock = pacmanBlock;
    }

    public int getValueRemoveWall() {
        return valueRemoveWall;
    }

    public int getValueSpeed() {
        return valueSpeed;
    }

    public int getValueTeleport() {
        return valueTeleport;
    }

    public int getValueInvincible() {
        return valueInvincible;
    }

    public void teleportUsed(){
        valueTeleport--;
    }

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }

    public boolean isSpeed() {
        return speed;
    }

    public void setSpeed(boolean speed) {
        this.speed = speed;
    }

    public void unSetAll(){
        this.removeWallChosen=false;
        this.speedChosen=false;
        this.teleportChoosen=false;
        this.invincibleChosen=false;
    }

    public void setLeftPanel(LeftPanel2 leftPanel) {
        this.leftPanel = leftPanel;
    }

    public void setGhosts(ArrayList<AbstractGhost> ghosts) {
        this.ghosts = ghosts;
    }
    private void setRun(){
        ghosts.forEach(ghost ->{
            if (!ghost.isStateDead()) {
                ghost.setStateScatter(false);
                ghost.setStateChase(false);
                if (!ghost.isStateRun()) {
                    ghost.setStateRun(true);
                    ghost.setPath(null);
                }

            }
        } );
    }

    public boolean isBreakTime() {
        return breakTime;
    }

    public void setBreakTime(boolean breakTime) {
        this.breakTime = breakTime;
    }
}
