// Grant Schorbach
// TCSS 342 - Data Structures

// A lot of my code is based of this code.
//https://algs4.cs.princeton.edu/41graph/Maze.java.html

import java.util.Random;


public class Maze {
    private int n;
    private int m;
    int startx;
    int starty;
    private int endx;
    private int endy;
    private boolean debug;
    private boolean [][] NORTH;
    private boolean [][] EAST;
    private boolean [][] SOUTH;
    private boolean [][] WEST;
    private boolean [][] visited;
    private boolean done = false;
    private Random RANDOM = new Random();
    int TimeDelay = 10;

    public Maze(int n, int m, boolean debug) {
        this.n = n;
        this.m = m;
        this.debug = debug;

        StdDraw.setCanvasSize(n * 30, m * 30);
        StdDraw.setXscale(0, n + 2);
        StdDraw.setYscale(0, m + 2);

        buildMaze();
        startPoint();
        endPoint();
    }
    private void buildMaze() {
        visited = new boolean[n+2][m+2];
        for (int x = 0; x < n + 2; x++) {
            visited[x][0] = true;
            visited[x][m + 1] = true;
        }
        for (int y = 0; y < m + 2; y++) {
            visited[0][y] = true;
            visited[n + 1][y] = true;
        }
        for (int x = 1; x <= n; x++) {
            for (int y = 1; y <= m; y++) {
                visited[x][y] = false;
            }
        }
        NORTH = new boolean[n + 2][m + 2];
        EAST = new boolean[n + 2][m + 2];
        SOUTH = new boolean[n + 2][m + 2];
        WEST = new boolean[n + 2][m + 2];
        for (int x = 0; x < n + 2; x++) {
            for (int y = 0; y < m + 2; y++) {
                NORTH[x][y] = true;
                EAST[x][y] = true;
                SOUTH[x][y] = true;
                WEST[x][y] = true;
            }
        }
    }

    private void startPoint() {
        //Random start point
        int random = RANDOM.nextInt(4);
        if (random == 0) {
            startx = RANDOM.nextInt(n) + 1;
            starty = m;
        } else if (random == 1) {
            startx = n;
            starty = RANDOM.nextInt(m + 1);
        } else if (random == 2) {
            startx = RANDOM.nextInt(n) + 1;
            starty = 1;
        } else if (random == 3) {
            startx = 1;
            starty = RANDOM.nextInt(m) + 1;
        }
    }

    private void endPoint() {
        int random;
        do {
            random = RANDOM.nextInt(4);
            if (random == 0) {
                endx = RANDOM.nextInt(n) + 1;
                endy = m;
            } else if (random == 1) {
                endx = n;
                endy = RANDOM.nextInt(m) + 1;
            } else if (random == 2) {
                endx = RANDOM.nextInt(n) + 1;
                endy = 1;
            } else if (random == 3) {
                endx = 1;
                endy = RANDOM.nextInt(m) + 1;
            }
        } while (endx == startx && endy == starty);

        generate(startx, starty);

        for (int x = 1; x <= n; x++) {
            for (int y = 1; y <= m; y++) {
                visited[x][y] = false;
            }
        }

    }

    private void generate(int x, int y){
        if(debug) {
            display();
        }
        visited[x][y] = true;
        int random;
        while(!visited[x][y+1] || !visited[x+1][y] || !visited[x][y-1] || !visited[x-1][y]) {
            while(true) {
                random = RANDOM.nextInt(4);
                if (random == 0 && !visited[x][y + 1]) {
                    NORTH[x][y] = false;
                    SOUTH[x][y+1] = false;
                    generate(x, y + 1);
                    break;
                }
                if (random == 1 && !visited[x + 1][y]) {
                    EAST[x][y] = false;
                    WEST[x+1][y] = false;
                    generate(x+1, y);
                    break;
                }
                if (random == 2 && !visited[x][y - 1]) {
                    NORTH[x][y-1] = false;
                    SOUTH[x][y] = false;
                    generate(x, y-1);
                    break;
                }
                if (random == 1 && !visited[x-1][y]) {
                    EAST[x-1][y] = false;
                    WEST[x][y] = false;
                    generate(x-1, y);
                    break;
                }
            }
        }

    }

    public void solve(int x, int y){
        if(x == 0 || y == 0 || x == n+1 || y == m+1){
            return;
        }
        if(done || visited[x][y]){
            return;
        }
        visited[x][y] = true;
        StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
        StdDraw.filledRectangle(x + 0.5,  y+0.5, 0.25,  0.25);
        StdDraw.show();
        if(x == endx && y == endy){
            done = true;
        }
        if(!NORTH[x][y]){
            solve(x, y+1);
        }
        if(!EAST[x][y]){
            solve(x+1, y);
        }
        if(!SOUTH[x][y]){
            solve(x, y-1);
        }
        if(!WEST[x][y]){
            solve(x-1, y);
        }
        if(done){
            return;
        }
        StdDraw.setPenColor(StdDraw.PINK);
        StdDraw.filledRectangle(x + 0.5,  y+0.5, 0.25,  0.25);
        StdDraw.show();
    }

    public void display(){
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.filledRectangle(startx+0.5, starty+0.5,  0.5, 0.5);
        if(endx != 0 && endy  != 0){
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.filledRectangle(endx+0.5,  endy+0.5, 0.5, 0.5);
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        for(int x = 1; x <= n; x++){
            for(int y = 1; y <= m; y++){
                if(SOUTH[x][y]) {
                    StdDraw.line(x, y, x+1, y);
                }
                if(NORTH[x][y]) {
                    StdDraw.line(x, y+1, x+1, y+1);
                }
                if(WEST[x][y]) {
                    StdDraw.line(x, y, x, y+1);
                }
                if(EAST[x][y]) {
                    StdDraw.line(x+1, y, x+1, y+1);
                }
            }
        }
        StdDraw.show(TimeDelay);

    }

}
