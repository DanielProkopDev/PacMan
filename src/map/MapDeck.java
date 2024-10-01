package map;


import map.generators.MapGenerator;
import map.generators.impl.MapGeneratorImpl;
import util.points.*;
import util.points.Point;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;


//0-puste,1-sciana,2-spawn,3-punkt,4-zycie,5-buf,6-skil,7-duchy uciekaja,8-sciany spawn, 9-drzwi , 18-heart, 19-removeWall , 12-speed, 13-teleport, 14-invincible , 33-runPoint  -> dla przykladu jesli komorka ma punkt do zebrania czyli ma wartosc 3 a duch upusci boost na przyklad heart, wartosc komorki bedzie =21




public class MapDeck extends AbstractMap{

    private final GradientPaint wallColor;

    private final Point point;
    private final RunPoint runPoint;

    private final HeartPoint heartPoint;
    private final RemoveWallPoint removeWallPoint;
    private final SpeedPoint speedPoint;
    private final TeleportPoint teleportPoint;
    private final InvinciblePoint invinciblePoint;

    private int count=0;

    private MapGenerator mapGenerator;

    private JPanel gamePanel;

    private static volatile MapDeck instance = null;

    private double sx=1.0;
    private double sy=1.0;

    public MapDeck(int rows, int cols, int cellSize, double sX, double sY) {
        super(rows,cols,cellSize);
        this.mapGenerator= new MapGeneratorImpl(rows,cols);
        MapDeck.grid = mapGenerator.generateMap();
        mapGenerator=null;
        wallColor= getRandomColor();
        point=new Point();
        runPoint=new RunPoint();
        heartPoint=new HeartPoint();
        removeWallPoint=new RemoveWallPoint();
        speedPoint=new SpeedPoint();
        teleportPoint=new TeleportPoint();
        invinciblePoint=new InvinciblePoint();
        checkMap(grid);
        this.sx=sX;
        this.sy=sY;
        instance=this;
    }

    public static MapDeck getInstance(int rows, int cols, int cellSize,double sX, double sY){
        if (instance==null){
            synchronized (MapDeck.class){
                if (instance==null){
                    instance=new MapDeck(rows,cols,cellSize,sX,sY);
                }
            }
        }
        return instance;
    }
    public static MapDeck getInstance(){
        if (instance!=null){
            synchronized (MapDeck.class){
                if (instance!=null){
                    return instance;
                }
            }
        }
        return null;
    }


    private void checkMap(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;


        int midRow1 = rows / 2 - 1;
        int midRow2 = midRow1 + 1;


        int midRow = rows / 2;
        int midCol = cols / 2;
        int spaceWidth = 6;
        int spaceHeight = 5;
        int spaceStartRow = midRow-2;
        int spaceStartCol = midCol-2;
        for (int i = spaceStartRow; i < spaceStartRow + spaceHeight; i++) {
            for (int j = spaceStartCol; j < spaceStartCol + spaceWidth; j++) {
                grid[i][j] = 2;
                if (grid[i][j-1]==1){
                    grid[i][j-1]=0;
                }
                if (grid[i][j+1]==1){
                    grid[i][j+1]=0;
                }
                if (grid[i-1][j]==1){
                    grid[i-1][j]=0;
                }
                if (grid[i+1][j]==1){
                    grid[i+1][j]=0;
                }

            }
        }
        for (int i = spaceStartRow; i < spaceStartRow + spaceHeight; i++) {
            for (int j = spaceStartCol; j < spaceStartCol + spaceWidth; j++) {
                if ((grid[i][j+1]==2&&grid[i][j-1]==2&&grid[i+1][j]==0&&grid[i-1][j]==2)||(grid[i][j+1]==2&&grid[i][j-1]==2&&grid[i-1][j]==0&&grid[i+1][j]==2)){
                    grid[i][j] = 3;
                }
            }
        }
        for (int i = spaceStartRow; i < spaceStartRow + spaceHeight; i++) {
            for (int j = spaceStartCol; j < spaceStartCol + spaceWidth; j++) {
                if ((grid[i-1][j]==0&&grid[i+1][j]==2)&&grid[i][j]==2||(grid[i+1][j]==0&&(grid[i-1][j]==2||grid[i-1][j]==8)&&grid[i][j]==2)||(grid[i][j-1]==2&&grid[i][j+1]==0&&grid[i][j]==2)||
                        (grid[i][j-1]==0&&grid[i][j+1]==2&&grid[i][j]==2)){
                    grid[i][j] = 8;
                }

            }
        }

        //Ponizej sa warunku sprawdzajace czy mamy przejscia na dole gorze prawo i lewo, w dodatku sprawdzamy czy wszystkie komorki+1 oraz komorki.lenght()-2 sa bez scian i dolne i gorne analogicznie
        if(grid[midRow1][0] != 0){
            grid[midRow1][0]=0;
            grid[midRow2][0]=0;
            grid[midRow1][2]=0;
            grid[midRow2][2]=0;
        }
        if (grid[midRow1][cols-1]!=0){
            grid[midRow1][cols-1]=0;
            grid[midRow2][cols-1]=0;

            grid[midRow2][cols-3]=0;
        }

        if (grid[0][(cols/2)-1]!=0){
            grid[0][(cols/2)-1]=0;
        }
        if (grid[0][cols/2]!=0){
            grid[0][cols/2]=0;
        }
        if (grid[rows-1][(cols/2)-1]!=0){
            grid[rows-1][(cols/2)-1]=0;
        }
        if (grid[rows-1][cols/2]!=0){
            grid[rows-1][cols/2]=0;
        }
        for (int i=1;i<rows-1;i++){
            if (grid[i][cols-2] !=0){
                grid[i][cols-2] =0;
            }
            if (grid[i][1]!=0){
                grid[i][1]=0;
            }
        }
        for (int j = 1 ; j<cols-1;j++){
            if (grid[1][j]!=0){
                grid[1][j]=0;
            }
            if (grid[rows-2][j]!=0){
                grid[rows-2][j]=0;
            }
            if (grid[rows-3][j]!=0){
                grid[rows-3][j]=0;
            }
        }
//Powyzej
        grid[1][cols/2]=33;
        grid[rows-2][3]=33;
        grid[rows-2][cols-4]=33;
        grid[1][cols-2]=33;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if ((grid[i][j] == 0)) {
                    grid[i][j] = 3;
                }
            }
        }
      boolean check=  checkGrid();



    }

    private boolean checkGrid(){

        switch (rows) {
            case 16:
                switch (cols) {
                    case 16:
                        checkCount();
                        while (count < 145){
                            for (int i = 1; i < rows-1; i++) {
                                if (count == 145){
                                    break;
                                }
                                for (int j = 1; j < cols-1; j++) {
                                    if (count == 145){
                                        break;
                                    }

                                    deadEndsBelow(i, j);
                                }

                            }
                        }
                        while (count >145){
                            for (int i = 1; i < rows-1; i++) {
                                if (count == 145){
                                    break;
                                }
                                for (int j = 1; j < cols-1; j++) {
                                    if (count == 145){
                                        break;
                                    }

                                    deadEndsAbove(i, j);
                                }

                            }
                        }
                        return count == 145;
                    default:
                        return false;
                }
            case 20:
                switch (cols) {
                    case 20:
                        checkCount();
                        while (count < 225){
                            for (int i = 1; i < rows-1; i++) {
                                if (count == 225){
                                    break;
                                }
                                for (int j = 1; j < cols-1; j++) {
                                    if (count == 225){
                                        break;
                                    }

                                    deadEndsBelow(i, j);
                                }

                            }
                        }
                        while (count >225){
                            for (int i = 1; i < rows-1; i++) {
                                if (count == 225){
                                    break;
                                }
                                for (int j = 1; j < cols-1; j++) {
                                    if (count == 225){
                                        break;
                                    }

                                    deadEndsAbove(i, j);
                                }

                            }
                        }
                        return count ==225;
                    default:
                        return false;
                }
            case 24:
                switch (cols) {
                    case 24:
                        checkCount();
                        while (count < 325){
                            for (int i = 1; i < rows-1; i++) {
                                if (count == 325){
                                    break;
                                }
                                for (int j = 1; j < cols-1; j++) {
                                    if (count == 325){
                                        break;
                                    }

                                    deadEndsBelow(i, j);
                                }

                            }
                        }
                        while (count >325){
                            for (int i = 1; i < rows-1; i++) {
                                if (count == 325){
                                    break;
                                }
                                for (int j = 1; j < cols-1; j++) {
                                    if (count == 325){
                                        break;
                                    }

                                    deadEndsAbove(i, j);
                                }

                            }
                        }
                        return count ==325;
                    case 28:
                        checkCount();
                        while (count < 370){
                            for (int i = 1; i < rows-1; i++) {
                                if (count == 370){
                                    break;
                                }
                                for (int j = 1; j < cols-1; j++) {
                                    if (count == 370){
                                        break;
                                    }

                                    deadEndsBelow(i, j);
                                }

                            }
                        }
                        while (count >370){
                            for (int i = 1; i < rows-1; i++) {
                                if (count == 370){
                                    break;
                                }
                                for (int j = 1; j < cols-1; j++) {
                                    if (count == 370){
                                        break;
                                    }

                                    deadEndsAbove(i, j);
                                }

                            }
                        }
                        return count == 370;
                    case 32:
                       checkCount();
                        while (count < 420){
                            for (int i = 1; i < rows-1; i++) {
                                if (count == 420){
                                    break;
                                }
                                for (int j = 1; j < cols-1; j++) {
                                    if (count == 420){
                                        break;
                                    }

                                    deadEndsBelow(i, j);
                                }

                            }
                        }
                        while (count >420){
                            for (int i = 1; i < rows-1; i++) {
                                if (count == 420){
                                    break;
                                }
                                for (int j = 1; j < cols-1; j++) {
                                    if (count == 420){
                                        break;
                                    }

                                    deadEndsAbove(i, j);
                                }

                            }
                        }
                        return count == 420;
                    default:
                        return false;
                }

            default:
                return false;
        }


    }

    private void deadEndsBelow(int i, int j) {
        if((grid[i][j]==1&&grid[i+1][j]==3&&grid[i-1][j]==3&&grid[i][j-1]==1 &&grid[i][j+1]==1&&grid[i-1][j-1]==1&&grid[i-1][j+1]==1)
                ||(grid[i][j]==1&&grid[i][j-1]==3&&grid[i][j+1]==3&&grid[i-1][j]==1&&grid[i+1][j]==1) || (grid[i][j]==1&&grid[i+1][j]==3&&grid[i-1][j]==3&&grid[i][j-1]==1 &&grid[i][j+1]==1&&grid[i+1][j-1]==1&&grid[i+1][j+1]==1)){

            grid[i][j]=3;
            count++;

        }
    }
    private void deadEndsAbove(int i, int j) {
        if(((grid[i][j]==3 && grid[i-1][j]==3 && grid[i][j-1]==3 && grid[i][j+1]==3 && grid[i+1][j]==3)&&(i>2&&i<grid.length-2&&j>2&&j<grid[i].length-2))&&(grid[i-1][j-1]!=8&&grid[i-1][j+1]!=8&&grid[i+1][j+1]!=8&&grid[i+1][j-1]!=8&&grid[i][j]!=30&&grid[i][j]!=33)){

            grid[i][j]=1;
            count--;

        }
    }

    private void checkCount(){
        for (int i =0;i< grid.length;i++){
            for (int j =0;j<grid[i].length;j++){
                if (grid[i][j]==3||grid[i][j]==33){
                    count++;
                }
            }
        }
    }


    public void draw(Graphics2D g2d) {
        g2d.scale(sx,sy);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int cellX = j * CELL_SIZE;
                int cellY = i * CELL_SIZE;

                if (grid[i][j] == 1) {


                    g2d.setPaint(wallColor);

                    g2d.fillRect(cellX + 1, cellY + 1, CELL_SIZE , CELL_SIZE );

                    g2d.setColor(Color.WHITE);
                    if (i > 0 && (grid[i - 1][j] == 0 || grid[i - 1][j] == 3 || grid[i - 1][j] == 12 || grid[i - 1][j] == 13 || grid[i - 1][j] == 14 || grid[i - 1][j] == 15 || grid[i - 1][j] == 16 || grid[i - 1][j] == 17  || grid[i - 1][j] == 18|| grid[i - 1][j] == 19|| grid[i - 1][j] == 21|| grid[i - 1][j] == 22 || grid[i - 1][j] == 33
                            || grid[i - 1][j] == 45|| grid[i - 1][j] == 46|| grid[i - 1][j] == 47|| grid[i - 1][j] == 51|| grid[i - 1][j] == 52 )) {
                        g2d.fillRect(cellX, cellY, CELL_SIZE, 2); // Górna krawędź
                    }
                    if (i < rows - 1 && (grid[i + 1][j] == 0 || grid[i + 1][j] == 3|| grid[i + 1][j] == 12 || grid[i + 1][j] == 13 || grid[i + 1][j] == 14 || grid[i + 1][j] == 15 || grid[i + 1][j] == 16 || grid[i + 1][j] == 17  || grid[i + 1][j] == 18|| grid[i + 1][j] == 19|| grid[i + 1][j] == 21|| grid[i + 1][j] == 22 || grid[i + 1][j] == 33
                            || grid[i + 1][j] == 45|| grid[i + 1][j] == 46|| grid[i + 1][j] == 47|| grid[i + 1][j] == 51|| grid[i + 1][j] == 52 )) {
                        g2d.fillRect(cellX, cellY + CELL_SIZE - 2, CELL_SIZE, 2); // Dolna krawędź
                    }
                    if (j > 0 && (grid[i][j - 1] == 0 || grid[i][j - 1] == 3|| grid[i][j - 1] == 12 || grid[i][j - 1] == 13 || grid[i][j - 1] == 14 || grid[i][j - 1] == 15 || grid[i][j - 1] == 16 || grid[i][j - 1] == 17  || grid[i][j - 1] == 18|| grid[i][j - 1] == 19|| grid[i][j - 1] == 21|| grid[i][j - 1] == 22 || grid[i][j - 1] == 33
                            || grid[i][j-1] == 45|| grid[i][j-1] == 46|| grid[i][j-1] == 47|| grid[i][j-1] == 51|| grid[i][j-1] == 52)) {
                        g2d.fillRect(cellX, cellY, 2, CELL_SIZE); // Lewa krawędź
                    }
                    if (j < cols - 1 && (grid[i][j + 1] == 0 || grid[i][j + 1] == 3|| grid[i][j + 1] == 12 || grid[i][j + 1] == 13 || grid[i][j + 1] == 14 || grid[i][j + 1] == 15 || grid[i][j + 1] == 16 || grid[i][j + 1] == 17  || grid[i][j + 1] == 18|| grid[i][j + 1] == 19|| grid[i][j + 1] == 21|| grid[i][j + 1] == 22 || grid[i][j + 1] == 33
                            || grid[i][j+1] == 45|| grid[i][j+1] == 46|| grid[i][j+1] == 47|| grid[i][j+1] == 51|| grid[i][j+1] == 52)) {
                        g2d.fillRect(cellX + CELL_SIZE - 2, cellY, 2, CELL_SIZE); // Prawa krawędź
                    }
                }
                //Rysowanie kulek
                if (grid[i][j] == 3 ){
                    point.draw(g2d,cellX,cellY,CELL_SIZE);
                }
                if (grid[i][j] == 21){
                    heartPoint.draw(g2d,cellX,cellY,CELL_SIZE);
                    point.draw(g2d,cellX,cellY,CELL_SIZE);
                }
                if (grid[i][j] == 22){
                    removeWallPoint.draw(g2d,cellX,cellY,CELL_SIZE);
                    point.draw(g2d,cellX,cellY,CELL_SIZE);
                }
                if (grid[i][j] == 15){
                    speedPoint.draw(g2d,cellX,cellY,CELL_SIZE);
                    point.draw(g2d,cellX,cellY,CELL_SIZE);
                }
                if (grid[i][j] == 16){
                    teleportPoint.draw(g2d,cellX,cellY,CELL_SIZE);
                    point.draw(g2d,cellX,cellY,CELL_SIZE);
                }
                if (grid[i][j] == 17){
                    invinciblePoint.draw(g2d,cellX,cellY,CELL_SIZE);
                    point.draw(g2d,cellX,cellY,CELL_SIZE);
                }
                if (grid[i][j] == 18){
                    heartPoint.draw(g2d,cellX,cellY,CELL_SIZE);
                }
                if (grid[i][j] == 19){
                    removeWallPoint.draw(g2d,cellX,cellY,CELL_SIZE);
                }
                if (grid[i][j] == 12){
                    speedPoint.draw(g2d,cellX,cellY,CELL_SIZE);
                }
                if (grid[i][j] == 13){
                    teleportPoint.draw(g2d,cellX,cellY,CELL_SIZE);
                }
                if (grid[i][j] == 14){
                    invinciblePoint.draw(g2d,cellX,cellY,CELL_SIZE);
                }
                if (grid[i][j]==33){
                    runPoint.draw(g2d,cellX,cellY,CELL_SIZE);
                }
                if (grid[i][j] == 51){
                    heartPoint.draw(g2d,cellX,cellY,CELL_SIZE);
                    runPoint.draw(g2d,cellX,cellY,CELL_SIZE);
                }
                if (grid[i][j] == 52){
                    removeWallPoint.draw(g2d,cellX,cellY,CELL_SIZE);
                    runPoint.draw(g2d,cellX,cellY,CELL_SIZE);
                }
                if (grid[i][j] == 45){
                    speedPoint.draw(g2d,cellX,cellY,CELL_SIZE);
                    runPoint.draw(g2d,cellX,cellY,CELL_SIZE);
                }
                if (grid[i][j] == 46){
                    teleportPoint.draw(g2d,cellX,cellY,CELL_SIZE);
                    runPoint.draw(g2d,cellX,cellY,CELL_SIZE);
                }
                if (grid[i][j] == 47){
                    invinciblePoint.draw(g2d,cellX,cellY,CELL_SIZE);
                    runPoint.draw(g2d,cellX,cellY,CELL_SIZE);
                }
                //Drzwi do spawn
                if (grid[i][j]==9){
                    g2d.setColor(Color.blue);
                    if (i > 0 && (grid[i - 1][j] == 0 || grid[i - 1][j] == 3)) {
                        g2d.fillRect(cellX, cellY, CELL_SIZE, 5); // Górna krawędź
                    }
                    if (i < rows - 1 && (grid[i + 1][j] == 0 || grid[i + 1][j] == 3 )) {
                        g2d.fillRect(cellX, cellY + CELL_SIZE - 2, CELL_SIZE, 5); // Dolna krawędź
                    }
                    if (j > 0 && (grid[i][j - 1] == 0 || grid[i][j - 1] == 3)) {
                        g2d.fillRect(cellX, cellY, 5, CELL_SIZE); // Lewa krawędź
                    }
                    if (j < cols - 1 && (grid[i][j + 1] == 0 || grid[i][j + 1] == 3 )) {
                        g2d.fillRect(cellX + CELL_SIZE - 2, cellY, 5, CELL_SIZE); // Prawa krawędź
                    }
                }
                //Sciany spawn
                if (grid[i][j]==8){
                    g2d.setPaint(wallColor);
                    if (i > 0 && (grid[i - 1][j] == 0 || grid[i - 1][j] == 3|| grid[i - 1][j] == 12 || grid[i - 1][j] == 13 || grid[i - 1][j] == 14 || grid[i - 1][j] == 15 || grid[i - 1][j] == 16 || grid[i - 1][j] == 17  || grid[i - 1][j] == 18|| grid[i - 1][j] == 19|| grid[i - 1][j] == 21|| grid[i - 1][j] == 22  )) {
                        g2d.fillRect(cellX, cellY, CELL_SIZE, 5); // Górna krawędź
                    }
                    if (i < rows - 1 && (grid[i + 1][j] == 0 || grid[i + 1][j] == 3|| grid[i + 1][j] == 12 || grid[i + 1][j] == 13 || grid[i + 1][j] == 14 || grid[i + 1][j] == 15 || grid[i + 1][j] == 16 || grid[i + 1][j] == 17  || grid[i + 1][j] == 18|| grid[i + 1][j] == 19|| grid[i + 1][j] == 21|| grid[i + 1][j] == 22)) {
                        g2d.fillRect(cellX, cellY + CELL_SIZE - 2, CELL_SIZE, 5); // Dolna krawędź
                    }
                    if (j > 0 && (grid[i][j - 1] == 0 || grid[i][j - 1] == 3|| grid[i][j - 1] == 12 || grid[i][j - 1] == 13 || grid[i][j - 1] == 14 || grid[i][j - 1] == 15 || grid[i][j - 1] == 16 || grid[i][j - 1] == 17  || grid[i][j - 1] == 18|| grid[i][j - 1] == 19|| grid[i][j - 1] == 21|| grid[i][j - 1] == 22)) {
                        g2d.fillRect(cellX, cellY, 5, CELL_SIZE); // Lewa krawędź
                    }
                    if (j < cols - 1 && (grid[i][j + 1] == 0 || grid[i][j + 1] == 3|| grid[i][j + 1] == 12 || grid[i][j + 1] == 13 || grid[i][j + 1] == 14 || grid[i][j + 1] == 15 || grid[i][j + 1] == 16 || grid[i][j + 1] == 17  || grid[i][j + 1] == 18|| grid[i][j + 1] == 19|| grid[i][j + 1] == 21|| grid[i][j + 1] == 22)) {
                        g2d.fillRect(cellX + CELL_SIZE - 2, cellY, 5, CELL_SIZE); // Prawa krawędź
                    }
                }
            }

        }
    }
    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g.create();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int cellX = j * CELL_SIZE;
                int cellY = i * CELL_SIZE;

                if (grid[i][j] == 1) {

                    g2d.setPaint(wallColor);

                    g2d.fillRect(cellX + 1, cellY + 1, CELL_SIZE , CELL_SIZE );

                    g2d.setColor(Color.WHITE);
                    if (i > 0 && (grid[i - 1][j] == 0 || grid[i - 1][j] == 3 )) {
                        g2d.fillRect(cellX, cellY, CELL_SIZE, 2); // Górna krawędź
                    }
                    if (i < rows - 1 && (grid[i + 1][j] == 0 || grid[i + 1][j] == 3)) {
                        g2d.fillRect(cellX, cellY + CELL_SIZE - 2, CELL_SIZE, 2); // Dolna krawędź
                    }
                    if (j > 0 && (grid[i][j - 1] == 0 || grid[i][j - 1] == 3)) {
                        g2d.fillRect(cellX, cellY, 2, CELL_SIZE); // Lewa krawędź
                    }
                    if (j < cols - 1 && (grid[i][j + 1] == 0 || grid[i][j + 1] == 3)) {
                        g2d.fillRect(cellX + CELL_SIZE - 2, cellY, 2, CELL_SIZE); // Prawa krawędź
                    }
                }
                //Rysowanie kulek
                if (grid[i][j] == 3 && j < grid[i].length-1 && j > 0 && i > 0 && i < grid.length-1){
                    point.draw(g2d,cellX,cellY,CELL_SIZE);
                }
                //Drzwi do spawn
                if (grid[i][j]==9){
                    g2d.setColor(Color.blue);
                    if (i > 0 && (grid[i - 1][j] == 0 || grid[i - 1][j] == 3 )) {
                        g2d.fillRect(cellX, cellY, CELL_SIZE, 5); // Górna krawędź
                    }
                    if (i < rows - 1 && (grid[i + 1][j] == 0 || grid[i + 1][j] == 3)) {
                        g2d.fillRect(cellX, cellY + CELL_SIZE - 2, CELL_SIZE, 5); // Dolna krawędź
                    }
                    if (j > 0 && (grid[i][j - 1] == 0 || grid[i][j - 1] == 3)) {
                        g2d.fillRect(cellX, cellY, 5, CELL_SIZE); // Lewa krawędź
                    }
                    if (j < cols - 1 && (grid[i][j + 1] == 0 || grid[i][j + 1] == 3)) {
                        g2d.fillRect(cellX + CELL_SIZE - 2, cellY, 5, CELL_SIZE); // Prawa krawędź
                    }
                }
                //Sciany spawn
                if (grid[i][j]==8){
                    g2d.setPaint(wallColor);
                    if (i > 0 && (grid[i - 1][j] == 0 || grid[i - 1][j] == 3 )) {
                        g2d.fillRect(cellX, cellY, CELL_SIZE, 5); // Górna krawędź
                    }
                    if (i < rows - 1 && (grid[i + 1][j] == 0 || grid[i + 1][j] == 3)) {
                        g2d.fillRect(cellX, cellY + CELL_SIZE - 2, CELL_SIZE, 5); // Dolna krawędź
                    }
                    if (j > 0 && (grid[i][j - 1] == 0 || grid[i][j - 1] == 3)) {
                        g2d.fillRect(cellX, cellY, 5, CELL_SIZE); // Lewa krawędź
                    }
                    if (j < cols - 1 && (grid[i][j + 1] == 0 || grid[i][j + 1] == 3)) {
                        g2d.fillRect(cellX + CELL_SIZE - 2, cellY, 5, CELL_SIZE); // Prawa krawędź
                    }
                }
            }
        }
        super.paintComponent(g);
        g2d.dispose();
    }

    private GradientPaint getRandomColor() {
        Random rand = new Random();
        int r1 = rand.nextInt(256);
        int g1 = rand.nextInt(256);
        int b1 = rand.nextInt(256);

        int r2 = rand.nextInt(256);
        int g2 = rand.nextInt(256);
        int b2 = rand.nextInt(256);

        return new GradientPaint(0, 0, new Color(r1, g1, b1), 0, (rows * CELL_SIZE), new Color(r2, g2, b2));
    }

    public boolean isWall(int row, int col) {
        return ((grid[row][col] == 1)||(grid[row][col]==8)||(grid[row][col]==9));
    }

    public boolean isPoint(int row, int col) {
        return grid[row][col] == 3;
    }

    public boolean isEnd(int row, int col) {
        return row< grid.length&&col<grid[0].length;
    }

    public int getWidth() {
        return cols * CELL_SIZE;
    }

    public int getHeight() {
        return rows * CELL_SIZE;
    }

    public void setGamePanel(JPanel gamePanel) {
        this.gamePanel = gamePanel;
    }


    public void setSx(double sx) {
        this.sx = sx;
    }

    public void setSy(double sy) {
        this.sy = sy;
    }

    public int getCount() {
        return count;
    }

    //no pasuej ja wkoncu zrobic jak znajde czas checkValue()
    private boolean checkValue(int i, int j){
        return i > 0 && (grid[i - 1][j] == 0 || grid[i - 1][j] == 3 || grid[i - 1][j] == 12 || grid[i - 1][j] == 13 || grid[i - 1][j] == 14 || grid[i - 1][j] == 15 || grid[i - 1][j] == 16 || grid[i - 1][j] == 17  || grid[i - 1][j] == 18|| grid[i - 1][j] == 19|| grid[i - 1][j] == 21|| grid[i - 1][j] == 22 || grid[i-1][j]==33 );
    }
}


