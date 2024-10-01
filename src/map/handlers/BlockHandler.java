package map.handlers;

import map.MapDeck;

import java.util.ArrayList;

public class BlockHandler {


    private static volatile BlockHandler instance=null;

    private ArrayList<Block> blocks;

    public static BlockHandler getInstance(){
        if (instance==null){
            synchronized (BlockHandler.class){
                if (instance==null){
                    instance=new BlockHandler(MapDeck.getGrid());
                }
            }
        }
        return instance;
    }

    public BlockHandler(int[][] grid){
        blocks=new ArrayList<>();
        for (int i=0;i<grid.length;i++){
            for (int j=0; j<grid[0].length;j++){
                if (((i==0||i== grid.length-1||j==0||j==grid[0].length-1)&&grid[i][j]==1)||grid[i][j]==8){
                    blocks.add(new Block(i,j,MapDeck.CELL_SIZE,true,false,false,true));
                }
                else if (grid[i][j]==1){
                    blocks.add(new Block(i,j,MapDeck.CELL_SIZE,true));
                }
                else if (grid[i][j]==3||grid[i][j]==33){
                    blocks.add(new Block(i,j,MapDeck.CELL_SIZE,false,true));
                }
                else if (grid[i][j]==2){
                    blocks.add(new Block(i,j,MapDeck.CELL_SIZE,false,false,true));
                }
            }
        }
        for (Block block:blocks){
            block.addNeighbors(blocks);
        }
        instance=this;
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }
}
