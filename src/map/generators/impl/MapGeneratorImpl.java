package map.generators.impl;

import map.generators.MapGenerator;

public class MapGeneratorImpl implements MapGenerator {
    private int[][] grid;

    private final int rows;
    private final int cols;

    private MazeGeneratorImpl mazeGenerator;

    public MapGeneratorImpl(int rows, int cols){
        this.rows=rows;
        this.cols=cols;
        this.grid=new int[rows][cols];
        this.mazeGenerator=new MazeGeneratorImpl(grid,rows,cols);
    }
    @Override
    public int[][] generateMap() {
        // Wypełnienie mapy ścianami
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = 1;
            }
        }




        int midRow = rows / 2;
        int midCol = cols / 2;



        int startRow = midRow % 2 == 0 ? midRow - 2 : midRow;
        int startCol = midCol % 2 == 0 ? midCol - 2 : midCol;
        grid=mazeGenerator.generateMaze(startRow, startCol);



        return grid;
    }
}
