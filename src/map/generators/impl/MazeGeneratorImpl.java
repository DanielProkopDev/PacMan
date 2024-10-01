package map.generators.impl;

import map.generators.MazeGenerator;

import java.util.Random;

public class MazeGeneratorImpl implements MazeGenerator {
    private int[][] grid;

    private final int rows;
    private final int cols;

    public MazeGeneratorImpl(int[][] grid, int rows, int cols){
        this.grid=grid;
        this.rows=rows;
        this.cols=cols;
    }
    @Override
    public int[][] generateMaze(int row, int col) {
        grid[row][col] = 0;

        int[] directions = {0, 1, 2, 3};
        shuffleArray(directions);
        for (int dir : directions) {
            int newRow = row;
            int newCol = col;
            switch (dir) {
                case 0:
                    newRow -= 2;
                    break;
                case 1:
                    newRow += 2;
                    break;
                case 2:
                    newCol -= 2;
                    break;
                case 3:
                    newCol += 2;
                    break;
            }
            if (isValid(newRow, newCol) && !isEdge(newRow, newCol)) {
                if (grid[newRow][newCol] == 1) {
                    int wallRow = (row + newRow) / 2;
                    int wallCol = (col + newCol) / 2;
                    grid[wallRow][wallCol] = 0;
                    generateMaze(newRow, newCol);
                }
            }
        }
        return grid;
    }
    private void shuffleArray(int[] arr) {
        Random rnd = new Random();
        for (int i = arr.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            int temp = arr[index];
            arr[index] = arr[i];
            arr[i] = temp;
        }
    }
    private boolean isEdge(int row, int col) {
        return row <= 0 || row >= rows - 1 || col <= 0 || col >= cols - 1;
    }
    private boolean isValid(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols && grid[row][col] == 1;
    }
}
