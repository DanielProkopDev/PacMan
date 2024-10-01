package character.ghost.util;

import map.MapDeck;

import java.util.*;

public class AAsterisk {

    private static volatile AAsterisk instance=null;
    public AAsterisk(){
    }
    public static AAsterisk getInstance(){
        if (instance==null){
            synchronized (AAsterisk.class){
                if (instance==null){
                    instance=new AAsterisk();
                }
            }
        }
        return instance;
    }

    int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    public static class Pair {
        public int first;
        public int second;
        public Pair(int first, int second){
            this.first = first;
            this.second = second;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Pair && this.first == ((Pair)obj).first && this.second == ((Pair)obj).second;
        }

        public int getFirst() {
            return first;
        }

        public int getSecond() {
            return second;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "first=" + first +
                    ", second=" + second +
                    '}';
        }
    }


    public static class Details {
        double value;
        int i;
        int j;

        public Details(double value, int i, int j) {
            this.value = value;
            this.i = i;
            this.j = j;
        }
    }


    public static class Cell {
        public Pair parent;

        public double f, g, h;
        Cell()
        {
            parent = new Pair(-1, -1);
            f = -1;
            g = -1;
            h = -1;
        }

        public Cell(Pair parent, double f, double g, double h) {
            this.parent = parent;
            this.f = f;
            this.g = g;
            this.h = h;
        }

        @Override
        public String toString() {
            return "Cell{" +
                    "parent=" + parent +
                    '}';
        }
    }


    boolean isValid(int rows, int cols,
                    Pair point)
    {
        if (rows > 0 && cols > 0) {
            return (point.first >= 0) && (point.first < rows)
                    && (point.second >= 0)
                    && (point.second < cols);
        }

        return false;
    }



    boolean isUnBlocked(int[][] grid, int rows, int cols,
                        Pair point)
    {
        return isValid( rows, cols, point)
                && grid[point.first][point.second] != 1 && grid[point.first][point.second] != 8 ;
    }


    boolean isDestination(Pair position, Pair dest)
    {
        return position == dest || position.equals(dest);
    }


    double calculateHValue(Pair src, Pair dest)
    {
        return Math.sqrt(Math.pow((src.first - dest.first), 2.0) + Math.pow((src.second - dest.second), 2.0));
    }


    private Stack<Pair> path;

   private Stack<Pair> tracePath(
            Cell[][] cellDetails,
            Pair dest)
    {


        path = new Stack<>();

        int row = dest.first;
        int col = dest.second;

        while (!(cellDetails[row][col].parent.first == row && cellDetails[row][col].parent.second == col)) {
            path.push(new Pair(row, col));
            Pair temp = cellDetails[row][col].parent;
            row = temp.first;
            col = temp.second;
        }

        return path;
    }



    public Stack<Pair> aStarSearch(int[][] grid,
                                       int rows,
                                       int cols,
                                       Pair src,
                                       Pair dest)
    {

        if (!isValid( rows, cols, src)) {
            return path;
        }


        if (!isValid( rows, cols, dest)) {
            return path;
        }


        if (!isUnBlocked(grid, rows, cols, src)
                || !isUnBlocked(grid, rows, cols, dest)) {

            return path;
        }


        if (isDestination(src, dest)) {

            return null;
        }


        boolean[][] closedList = new boolean[rows][cols];

        Cell[][] cellDetails = new Cell[rows][cols];

        int i, j;

        i = src.first;
        j = src.second;
        cellDetails[i][j] = new Cell();
        cellDetails[i][j].f = 0.0;
        cellDetails[i][j].g = 0.0;
        cellDetails[i][j].h = 0.0;
        cellDetails[i][j].parent = new Pair( i, j );





        PriorityQueue<Details> openList = new PriorityQueue<>((o1, o2) -> (int) Math.round(o1.value - o2.value));



        openList.add(new Details(0.0, i, j));

        while (!openList.isEmpty()) {
            Details p = openList.peek();

            i = p.i;
            j = p.j;


            openList.poll();
            closedList[i][j] = true;



            for (int[] dir : directions) {
                int newX = i + dir[0];
                int newY = j + dir[1];
                Pair neighbour = new Pair(newX, newY);
                if (isValid( rows, cols, neighbour)) {
                    if(cellDetails[neighbour.first] == null){ cellDetails[neighbour.first] = new Cell[cols]; }
                    if (cellDetails[neighbour.first][neighbour.second] == null) {
                        cellDetails[neighbour.first][neighbour.second] = new Cell();
                    }

                    if (isDestination(neighbour, dest)) {
                        cellDetails[neighbour.first][neighbour.second].parent = new Pair ( i, j );
                        return tracePath(cellDetails, dest);

                    }

                    else if (!closedList[neighbour.first][neighbour.second]
                            && isUnBlocked(grid, rows, cols, neighbour)) {
                        double gNew, hNew, fNew;
                        gNew = cellDetails[i][j].g + 1.0;
                        hNew = calculateHValue(neighbour, dest);
                        fNew = gNew + hNew;

                        if (cellDetails[neighbour.first][neighbour.second].f == -1
                                || cellDetails[neighbour.first][neighbour.second].f > fNew) {

                            openList.add(new Details(fNew, neighbour.first, neighbour.second));



                            cellDetails[neighbour.first][neighbour.second].g = gNew;
                            cellDetails[neighbour.first][neighbour.second].h = hNew;
                            cellDetails[neighbour.first][neighbour.second].f = fNew;
                            cellDetails[neighbour.first][neighbour.second].parent = new Pair( i, j );
                        }
                    }
                }
            }
        }

        return path;
    }

    public Stack<Pair> getPath() {
        return path;
    }

    public void setPath(Stack<Pair> path) {
        this.path = path;
    }
}