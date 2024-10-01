package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Queue;

import Game.util.InvincibleRadioButton;
import Game.util.RemoveWallRadioButton;
import Game.util.SpeedRadioButton;
import Game.util.TeleportRadioButton;
import character.*;
import character.ghost.*;
import character.ghost.util.AAsterisk;
import map.MapDeck;
import map.handlers.BlockHandler;
import character.ghost.util.GhostHandler;
import map.handlers.MapHandler;
import util.Range.Range;
import util.SpawnPoint;

public class GamePanel extends JPanel implements KeyListener,MouseListener{
    private MapDeck mapDeck;

    private final PacMan pacMan;

    private final GhostHandler ghostHandler;

    private final GhostRed ghostRed;

    private final GhostPink ghostPink;

    private final GhostOrange ghostOrange;

    private final GhostBlue ghostBlue;

    private final BlockHandler blockHandler;

    private final Runnable endGameCallback;

    private final StatusPanel statusPanel;

    private boolean run=true;

    private Thread animationThread;

    private final Queue<SpawnPoint> spawnPoints;

    private SpawnPoint spawnPoint;

    private final MapHandler mapHandler;

    private final RightPanel2 rightPanel;

    private final LeftPanel2 leftPanel;

    private RemoveWallRadioButton removeWallRadioButton;
    private SpeedRadioButton speedRadioButton;
    private TeleportRadioButton teleportRadioButton;
    private InvincibleRadioButton invincibleRadioButton;

    private boolean cancelGame=false;

    private int threadSleep=10;


    private int cellSize;
    private int characterSize;
    private int teleportOffset=2;

    private boolean isInitialized=false;

    private int screenWidth;
    private int screenHeight;

    private double initialWidth;
    private double initialHeight;

    private RescaleUnit rescaleUnit;
    private boolean winGame;

    private final ArrayList<AbstractGhost> ghosts;

    public double widthScale=1.0;
    public double heightScale=1.0;
    public JPanel panel;
    public GamePanel(Runnable endGameCallback, StatusPanel statusPanel,RightPanel2 rightPanel, LeftPanel2 leftPanel,int width, int height, int scaledCharSize, int scaledCellSize,double sx, double sy ) {
        System.gc();
        this.endGameCallback = endGameCallback;
        this.statusPanel=statusPanel;
        this.rightPanel=rightPanel;
        this.leftPanel=leftPanel;
        this.setDoubleBuffered(true);
        this.cellSize=scaledCellSize;
        this.characterSize=scaledCharSize;
        this.initialWidth=(((double) width /50)*scaledCellSize)+20;
        this.initialHeight=(((double) height /50)*scaledCellSize)+20;
        this.rescaleUnit=RescaleUnit.getInstance();
        this.ghosts=new ArrayList<>();
        panel=this;


       Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.screenWidth = screenSize.width;
        this.screenHeight = screenSize.height;

        final int BASE_SCREEN_WIDTH = 3440;
        final int BASE_SCREEN_HEIGHT = 1440;
        final int BASE_THREAD_SLEEP =10;



        double widthScale = screenWidth / (double) BASE_SCREEN_WIDTH;
        double heightScale = screenHeight / (double) BASE_SCREEN_HEIGHT;
        double scale = Math.min(widthScale, heightScale);
        this.threadSleep=(int) (BASE_THREAD_SLEEP / scale);

        if (threadSleep==33||threadSleep==32||threadSleep==31){
            threadSleep-=8;
        }else if (threadSleep<=26&&threadSleep>=17){
            threadSleep-=5;
        }



        setPreferredSize(new Dimension(((width/50)*cellSize)+20, ((height/50)*cellSize)+20));
        setBackground(Color.BLACK);
        setFocusable(true);
      //  setLayout(null);         //to jest jedynie potrzebne jesli chcemy uruchomic przez paintComponent wsyzstkie elementy do rysowania, nie ma wplywu poza tym wiec zostawiam
        addKeyListener(this);
        requestFocusInWindow();



        mapDeck = new MapDeck(height/50,width/50,scaledCellSize,sx,sy);


        blockHandler=new BlockHandler(MapDeck.getGrid());
        mapHandler= new MapHandler();
        spawnPoints=mapHandler.getSpawnPoints();
        SpawnPoint pacmanSpawnPoint = mapHandler.getPacmanSpawnPoint(MapDeck.getGrid(),scaledCellSize);

        pacMan=new PacMan(pacmanSpawnPoint.getX(),pacmanSpawnPoint.getY(),1,KeyEvent.VK_RIGHT,scaledCharSize-(scaledCharSize/4)-1,mapDeck);
        this.statusPanel.setPacMan(pacMan);
        this.removeWallRadioButton=statusPanel.getRemoveWallRadioButton();
        this.speedRadioButton=statusPanel.getSpeedRadioButton();
        this.teleportRadioButton=statusPanel.getTeleportRadioButton();
        this.invincibleRadioButton = statusPanel.getInvincibleRadioButton();
        pacMan.setLeftPanel(leftPanel);

        spawnPoint = spawnPoints.poll();
        assert spawnPoint != null;
        ghostRed = new GhostRed(spawnPoint.getX(), spawnPoint.getY(), 1,scaledCharSize, mapDeck);
        spawnPoint = spawnPoints.poll();
        assert spawnPoint != null;
        ghostPink=new GhostPink(spawnPoint.getX(), spawnPoint.getY(),1,scaledCharSize, mapDeck);
        spawnPoint = spawnPoints.poll();
        assert spawnPoint != null;
        ghostOrange=new GhostOrange(spawnPoint.getX(), spawnPoint.getY(),1,scaledCharSize, mapDeck);
        spawnPoint = spawnPoints.poll();
        assert spawnPoint != null;
        ghostBlue=new GhostBlue(spawnPoint.getX(), spawnPoint.getY(),1,scaledCharSize, mapDeck);
        ghostHandler=new GhostHandler(endGameCallback,pacMan,this,ghostRed,ghostBlue,ghostPink,ghostOrange,scale,leftPanel);
        ghosts.add(ghostRed);
        ghosts.add(ghostPink);
        ghosts.add(ghostOrange);
        ghosts.add(ghostBlue);
        pacMan.setGhosts(ghosts);


        addMouseListener(this);




       ghostHandler.startNewGame();
        startAnimationThread();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (isInitialized) {
                    handleResize(getWidth(), getHeight());
                }
            }
        });

    }

    private void startAnimationThread() {
         animationThread = new Thread(() -> {
            while (run) {

                super.repaint();
                try {
                    statusPanel.update(pacMan.getLives(), pacMan.getScore(), pacMan.getValueRemoveWall(), pacMan.getValueSpeed(), pacMan.getValueTeleport(), pacMan.getValueInvincible(), mapDeck.getCount());
                    if (pacMan.getScore()==mapDeck.getCount()){
                        GameFrame.gameWon(pacMan.getScore(),statusPanel.getElapsedTime());
                        setWinGame(true);
                    }
                }catch (NullPointerException ne){
                    //ignore -  BUT NOT FOR DEVELOPMENT
                }


                try {
                    Thread.sleep(threadSleep);
                } catch (InterruptedException e) {
                    if (!run) break;
                }
            }
        });
        animationThread.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
    try {
    // rysuj elementy gry
    mapDeck.draw((Graphics2D) g);
    pacMan.draw(g);
    ghostRed.draw(g);
    ghostPink.draw(g);
    ghostOrange.draw(g);
    ghostBlue.draw(g);
       }catch (NullPointerException ne){
        //ignore - BUT NOT FOR DEVELOPMENT(REMOVE THIS TRY AND CATCH) and remove way to ending game if you dont want exception at the end- doesn't affect the gameplay though
    }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P) {
            try {
                ghostHandler.sleepNow(10000);
                System.out.println(ghostHandler.getState());
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            setCancelGame(true);
        }
        if (e.getKeyCode() == KeyEvent.VK_1 ||e.getKeyCode() == KeyEvent.VK_2 ||e.getKeyCode() == KeyEvent.VK_3 ||e.getKeyCode() == KeyEvent.VK_4) {
         switch (e.getKeyCode()){
             case KeyEvent.VK_1:
                 removeWallRadioButton.keyPressed(e);
                 break;
             case KeyEvent.VK_2:
                 speedRadioButton.keyPressed(e);
                 break;
             case KeyEvent.VK_3:
                 teleportRadioButton.keyPressed(e);
                 break;
             case KeyEvent.VK_4:
                 invincibleRadioButton.keyPressed(e);
                 break;

             default:
         }
        } else {
            pacMan.keyPressed(e);
        }
        rightPanel.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
      pacMan.keyReleased(e);
      rightPanel.keyReleased(e);
    }
    public void handleResize(double width, double height) {
        double widthScale = width / initialWidth;
        double heightScale = height / initialHeight;
        this.widthScale = widthScale;
        this.heightScale = heightScale;
        mapDeck.setSx(widthScale);
        mapDeck.setSy(heightScale);
        rescaleUnit.rescale(widthScale, heightScale);


    }

    public boolean isRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }


    public boolean isCancelGame() {
        return cancelGame;
    }

    public void setCancelGame(boolean cancelGame) {
        this.cancelGame = cancelGame;
        try {
            if (cancelGame) {
                this.mapDeck = null;
                this.blockHandler.getBlocks().clear();
                AAsterisk.getInstance().setPath(null);
                System.gc();
            }
        }catch (NullPointerException ne){
            //ignore - but not for development
        }
    }
    public void setWinGame(boolean winGame){
        this.winGame=winGame;
        try{
            if (winGame){
                this.mapDeck = null;
                this.blockHandler.getBlocks().clear();
                AAsterisk.getInstance().setPath(null);
                System.gc();
            }
        }catch (NullPointerException ne){
            //ignore = not for development
        }
    }

    public void setInitialized(boolean initialized) {
        isInitialized = initialized;
    }


    public boolean isWinGame() {
        return winGame;
    }

    public double getWidthScale() {
        return widthScale;
    }

    public double getHeightScale() {
        return heightScale;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (pacMan.isTeleportChoosen()&&pacMan.isSpaceBarActive()&&pacMan.getValueTeleport()>0){
            int x = (int)(e.getX()/this.widthScale);
            int y = (int)(e.getY()/this.heightScale);
            int row=(y/MapDeck.CELL_SIZE);
            int col=(x/MapDeck.CELL_SIZE);
            if (!mapDeck.isWall(row,col)&&!pacMan.isBreakTime()){
                blockHandler.getBlocks().stream().filter(block -> block.getRow()==row && block.getCol()==col).findFirst().ifPresent(pacMan::setPacmanBlock);
                pacMan.setX(pacMan.getPacmanBlock().getX()+teleportOffset);
                pacMan.setY(pacMan.getPacmanBlock().getY()+teleportOffset);
                pacMan.setRangeX(new Range(pacMan.getX(), pacMan.getX()+ pacMan.getCHARACTER_SIZE()));
                pacMan.setRangeY(new Range(pacMan.getY(), pacMan.getY()+ pacMan.getCHARACTER_SIZE()));
                pacMan.unSetTheMove(false,false,false,false);
                pacMan.setMoving(false);
                pacMan.teleportUsed();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public GhostHandler getGhostHandler() {
        return ghostHandler;
    }
}

