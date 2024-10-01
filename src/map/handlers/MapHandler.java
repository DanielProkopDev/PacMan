package map.handlers;

import util.DoorPoint;
import util.SpawnPoint;

import java.util.*;
import map.MapDeck;

public class MapHandler {

    private static volatile MapHandler instance=null;
    private Queue<SpawnPoint> spawnPoints;

    private Queue<DoorPoint> doorPoints;
    public MapHandler(){
        spawnPoints=findSpawnPoints(MapDeck.grid, MapDeck.CELL_SIZE);
        doorPoints=findDoorPoints(MapDeck.grid, MapDeck.CELL_SIZE);
        instance=this;
    }
    public static MapHandler getInstance(){
        if (instance==null){
            synchronized (MapHandler.class){
                if (instance==null){
                    instance=new MapHandler();
                }
            }
        }
        return instance;
    }


    public Queue<SpawnPoint> findSpawnPoints(int[][] grid, int cellSize) {
        Queue<SpawnPoint> spawnPoints =new ArrayDeque<>();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 2) {

                    int x = j * cellSize;
                    int y = i * cellSize;
                    spawnPoints.add(new SpawnPoint(x, y));
                }
            }
        }
        return spawnPoints;
    }

    public SpawnPoint getPacmanSpawnPoint(int[][] grid,int cellSize){
        int x,y;
        x=0;
        y=0;

        for (int i=1;i<grid.length;i++){
            if (grid[i][1]==3){
                x = (i*cellSize)+4;
                y = (i*cellSize)+4;
                return new SpawnPoint(x,y);
            }
        }
        return null;
    }

    public Queue<DoorPoint> findDoorPoints(int[][] grid, int cellSize) {
        Queue<DoorPoint> doorPoints =new ArrayDeque<>();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 9) {
                    int x = j * cellSize;
                    int y = i * cellSize;
                    doorPoints.add(new DoorPoint(x, y));
                }
            }
        }
        return doorPoints;
    }

    public Queue<SpawnPoint> getSpawnPoints() {
        return spawnPoints;
    }
}
