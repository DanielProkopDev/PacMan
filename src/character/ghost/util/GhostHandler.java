package character.ghost.util;

import Game.GamePanel;
import Game.LeftPanel2;
import character.PacMan;
import character.ghost.*;
import map.handlers.BlockHandler;
import map.handlers.MapHandler;
import util.SpawnPoint;
import map.MapDeck;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class GhostHandler extends Thread{

    private final MapDeck mapDeck;


    private static GhostHandler instance=null;



    private final GhostRed ghostRed;

    private final GhostPink ghostPink;

    private final GhostOrange ghostOrange;

    private final GhostBlue ghostBlue;



    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

   private boolean setWaitState;

   private long waitTime;

   private long timeNow;

    private  Thread redGhostThread;

    private  Thread pinkGhostThread;

    private  Thread orangeGhostThread;

    private  Thread blueGhostThread;

    private Thread dropBoostThread;
    private final Runnable endGameCallback;
    private final PacMan pacMan;

    private volatile boolean run=true;

    private GamePanel gamePanel;

    private ArrayList<AbstractGhost> ghosts;

    private int threadSleep=4;
    private double scale;
    private boolean paused;

    private long timeStartSpeed=0;

    private long timeStartInvincible=0;

    private BlockHandler blockHandler;

    private LeftPanel2 leftPanel;


    public GhostHandler(Runnable endGameCallback, PacMan pacMan, GamePanel gamePanel, GhostRed ghostRed,GhostBlue ghostBlue,GhostPink ghostPink , GhostOrange ghostOrange,double scale, LeftPanel2 leftPanel){
        this.mapDeck = MapDeck.getInstance();
        this.ghostRed=ghostRed;
        this.ghostBlue=ghostBlue;
        this.ghostPink=ghostPink;
        this.ghostOrange=ghostOrange;
        this.gamePanel=gamePanel;
        this.ghosts=new ArrayList<>();
        this.leftPanel=leftPanel;
        ghosts.add(ghostRed);
        ghosts.add(ghostBlue);
        ghosts.add(ghostPink);
        ghosts.add(ghostOrange);
        ghostRed.setGhostHandler(this);
        ghostBlue.setGhostHandler(this);
        ghostPink.setGhostHandler(this);
        ghostOrange.setGhostHandler(this);
        setWaitState=false;
        this.endGameCallback=endGameCallback;
        this.pacMan=pacMan;
        instance=this;
        this.scale=scale;
        this.threadSleep= (int) (this.threadSleep/scale);
        if (threadSleep==13||threadSleep==12||threadSleep==11){
            threadSleep-=4;
        }else if (threadSleep==10){
            threadSleep-=1;
        }else if (threadSleep==9){
            threadSleep -= 3;
        }else if (threadSleep==7||threadSleep==8){
            threadSleep-=2;
        }


    }
    public static GhostHandler getInstance(Runnable endGameCallback, PacMan pacMan,GamePanel gamePanel, GhostRed ghostRed,GhostBlue ghostBlue,GhostPink ghostPink , GhostOrange ghostOrange,double scale, LeftPanel2 leftPanel){
        if (instance == null) {
            synchronized (GhostHandler.class) {
                if (instance == null) {
                    instance = new GhostHandler(endGameCallback,pacMan,gamePanel,ghostRed,ghostBlue,ghostPink,ghostOrange,scale,leftPanel);
                }
            }
        }
        return instance;
    }
    public static GhostHandler getInstance(){
        if (instance!=null){
            synchronized (GhostHandler.class){
                if (instance!=null){
                    return instance;
                }
            }
        }
        return null;
    }
    public void resetGhosts() {
        ghostRed.resetPosition();

        ghostRed.setStateDead(false);
        ghostRed.setPath(null);
        ghostPink.resetPosition();
        ghostPink.setStateDead(false);
        ghostPink.setPath(null);
        ghostOrange.resetPosition();
        ghostOrange.setStateDead(false);
        ghostOrange.setPath(null);
        ghostBlue.resetPosition();
        ghostBlue.setStateDead(false);
        ghostBlue.setPath(null);
    }
    private void createGhostThreads() {
        redGhostThread = createGhostThread(ghostRed, 6000, 20000);
        pinkGhostThread = createGhostThread(ghostPink, 16000, 11000);
        orangeGhostThread = createGhostThread(ghostOrange, 12000, 9000);
        blueGhostThread = createGhostThread(ghostBlue, 15000, 8000);
        dropBoostThread = createDropGhostThread(ghosts,5000);
    }

    private Thread createGhostThread(AbstractGhost ghost, long scatterTime, long chaseTime) {
        return new Thread(() -> {
            while (run) {
                lock.lock();

                    try {
                        if (!ghost.isStateRun()&&!ghost.isStateDead()) {
                            ghost.setStateChase(false);
                            ghost.setStateScatter(true);
                            ghost.setPath(null);
                        }
                        condition.await(scatterTime, TimeUnit.MILLISECONDS);

                        if (!this.pacMan.isInvincible()&&!ghost.isStateRun()&&!ghost.isStateDead()) {
                            ghost.setStateScatter(false);
                            ghost.setStateChase(true);
                            ghost.setPath(null);
                        }

                        if (System.currentTimeMillis() - timeNow >= waitTime) {
                            setWaitState = false;
                        }
                        if (setWaitState) {
                            condition.await(waitTime, TimeUnit.MILLISECONDS);
                        }
                        condition.await(chaseTime, TimeUnit.MILLISECONDS);

                    } catch (InterruptedException e) {
                        if (!run) break;
                    } finally {
                        lock.unlock();
                    }

            }
        });
    }

    private Thread createDropGhostThread(ArrayList<AbstractGhost> ghosts, long waitTime){
        return new Thread(()-> {
            Random random = new Random();
            lock.lock();

            try{
                condition.await(waitTime,TimeUnit.MILLISECONDS);
            }catch (InterruptedException e){
                if (!run) return;
            }finally {
                lock.unlock();
            }
            while (run) {
                lock.lock();
                try {
                    if (!setWaitState) {
                        ghosts.forEach(ghost -> {
                            if (!ghost.getCurrentblock().isSpawn() && !ghost.isStateDead() && !ghost.isStateRun()) {
                                if (random.nextInt(100) < 25) {
                                    ghost.dropGhost();
                                }
                            }
                        });
                    }
                    System.gc();
                    condition.await(waitTime, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    if (!run) break;
                } finally {
                    lock.unlock();
                }
            }

        });
    }
    @Override
    public void run() {
        createGhostThreads();

        redGhostThread.start();
        pinkGhostThread.start();
        orangeGhostThread.start();
        blueGhostThread.start();
        dropBoostThread.start();

        while (run) {
            lock.lock();

                try {
                    if (pacMan.getLives() < 1 || gamePanel.isCancelGame() || gamePanel.isWinGame()) {
                        pacMan.setMoving(false);
                        if (endGameCallback != null && !gamePanel.isWinGame()) {
                            run = false;
                            gamePanel.setRun(false);
                            condition.signalAll();
                            endGameCallback.run();
                        }
                        if (gamePanel.isWinGame()){
                            run = false;
                            gamePanel.setRun(false);
                        }
                        break;
                    }
                    if (pacMan.isMoving()) {
                        pacMan.move();
                    }
                    //niestety zorientowalem sie 2 dni przed oddaniem projektu ze rowniez musi tutaj sprawdzac czas wlaczenia ulepszenia inaczej jak pacman bedzie stac bez ruchu to bedziemy wiecznie neismiertelni - fakt ze w taki sposob nie wygramy bo liczy sie czas glownei a nie punkty. Aczkolwiek no jest to w pewien sposob bug
                    //ktory nie ma na nic wplywu ale bug
                    if (pacMan.isInvincible()){
                        if (timeStartInvincible == 0) {
                            timeStartInvincible = System.currentTimeMillis();
                        }
                    }
                    if (timeStartInvincible!=0&&((System.currentTimeMillis() - timeStartInvincible)/1000>=20)){
                        timeStartInvincible=0;
                        pacMan.setInvincible(false);
                        leftPanel.setColor(2,Color.BLACK);
                    }
                    if (pacMan.isSpeed()){
                        if (timeStartSpeed == 0) {
                            timeStartSpeed = System.currentTimeMillis();
                        }
                    }
                    if (timeStartSpeed!=0&&((System.currentTimeMillis() - timeStartSpeed)/1000>=20)){
                        timeStartSpeed=0;
                        pacMan.setSpeed(false);
                        pacMan.setMOVE_DISTANCE(1);
                        leftPanel.setColor(1,Color.BLACK);
                    }
                    ghostRed.move();
                    ghostPink.move();
                    ghostOrange.move();
                    ghostBlue.move();




                    Thread.sleep(threadSleep);
                    if (System.currentTimeMillis() - timeNow >= waitTime) {
                        setWaitState = false;
                    }

                } catch (InterruptedException e) {
                    if (!run) break;
                } finally {
                    lock.unlock();
                }

        }
    }
    public void sleepNow(long millis) {
        lock.lock();
        try {
            setWaitState=true;
            waitTime = millis;
            timeNow = System.currentTimeMillis();
            condition.await(millis,TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            pacMan.setBreakTime(false);
        }
    }

   public void sleepUntilNotified(){
        lock.lock();
        try{
            while (paused){
             condition.await();
            }
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
   }
   public void wakeUpNotified(){
        lock.lock();
        try{
            paused=false;
            condition.signalAll();
        }finally {
            lock.unlock();
        }
   }
   public void pause(){
        lock.lock();
        try{
            paused = true;
         sleepUntilNotified();
        }finally {
            lock.unlock();
        }
   }

    public void stopGame() {
        run = false;
        lock.lock();
        try {
            if (instance !=null) instance.interrupt();
            if (redGhostThread != null) redGhostThread.interrupt();
            if (pinkGhostThread != null) pinkGhostThread.interrupt();
            if (orangeGhostThread != null) orangeGhostThread.interrupt();
            if (blueGhostThread != null) blueGhostThread.interrupt();
            if (dropBoostThread != null) dropBoostThread.interrupt();
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void startNewGame() {
        if (instance != null) {
            instance.stopGame();
            try {
                instance.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            run = true;
            instance = new GhostHandler(endGameCallback, pacMan, gamePanel,ghostRed,ghostBlue,ghostPink,ghostOrange, scale,leftPanel); // Create a new instance
            instance.start();
        } else {
            throw new IllegalThreadStateException("Cannot start a new game while another is running.");
        }
    }
}
