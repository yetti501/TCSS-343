import java.util.Random;

public class Sample {
        int n;
        int m;
        int startx;
        int starty;
        int solx = 0;
        int soly = 0;
        boolean debug;
        boolean[][] north;
        boolean[][] east;
        boolean[][] south;
        boolean[][] west;
        boolean[][] visited;
        boolean done = false;
        Random RANDOM = new Random();
        int timedelay = 100;

        public Sample(int n, int m, boolean debug){
            System.out.println("MAZE");
            this.n = n;
            this.m = m;
            this.debug = debug;
            StdDraw.setCanvasSize(n*30, m*30);
            StdDraw.setXscale(0, n+2);
            StdDraw.setYscale(0, m+2);

            // Create border cells and mark as visited
            visited = new boolean[n+2][m+2];
            for(int x = 0; x < n+2; x++) {
                visited[x][0] = true;
                visited[x][m+1] = true;
            }
            for(int y = 0; y < m+2; y++){
                visited[0][y] = true;
                visited[n+1][y] = true;
            }

            for(int x = 1; x <= n; x++) {
                for(int y = 1; y <= m; y++) {
                    visited[x][y] = false;
                }
            }

            // initialize all walls as present
            north = new boolean[n+2][m+2];
            east = new boolean[n+2][m+2];
            south = new boolean[n+2][m+2];
            west = new boolean[n+2][m+2];

            for(int x = 0; x < n+2; x++){
                for(int y = 0; y < m+2; y++){
                    north[x][y] = true;
                    east[x][y] = true;
                    south[x][y] = true;
                    west[x][y] = true;
                }
            }

            // Select a random cell on the edge of the maze and set as the start point
            int r = RANDOM.nextInt(4);
            if(r == 0) {
                startx = RANDOM.nextInt(n)+1;
                starty = m;
            } else if(r == 1) {
                startx = n;
                starty = RANDOM.nextInt(m)+1;
            } else if(r == 2) {
                startx = RANDOM.nextInt(n)+1;
                starty = 1;
            } else if(r == 3) {
                startx = 1;
                starty = RANDOM.nextInt(m)+1;
            }

            // Choose a random solution on the edge of the maze that isn't the start
            do{
                r = RANDOM.nextInt(4);
                if(r == 0){
                    solx = RANDOM.nextInt(n)+1;
                    soly = m;
                } else if(r == 1){
                    solx = n;
                    soly = RANDOM.nextInt(m)+1;
                } else if(r == 2){
                    solx = RANDOM.nextInt(n)+1;
                    soly = 1;
                } else if(r == 3){
                    solx = 1;
                    soly = RANDOM.nextInt(m)+1;
                }
            } while(solx == startx && soly == starty);

            generate(startx, starty);

            for(int x = 1; x <= n; x++) {
                for(int y = 1; y <= m; y++) {
                    visited[x][y] = false;
                }
            }
        }

        private void generate(int x, int y) {
            System.out.println("GENERATE");
            if(debug) display();
            visited[x][y] = true;
            int r;
            // While there is an unvisited neighbor
            while(!visited[x][y+1] || !visited[x+1][y] || !visited[x][y-1] || !visited[x-1][y]) {
                System.out.println("HERE");
                while(true){
                    r = RANDOM.nextInt(4);
                    if(r == 0 && !visited[x][y+1]){
                        // move north
                        north[x][y] = false;
                        south[x][y+1] = false;
                        generate(x, y+1);
                        break;
                    }
                    if(r == 1 && !visited[x+1][y]){
                        // move east
                        east[x][y] = false;
                        west[x+1][y] = false;
                        generate(x+1, y);
                        break;
                    }
                    if(r == 2 && !visited[x][y-1]){
                        // move south
                        north[x][y-1] = false;
                        south[x][y] = false;
                        generate(x, y-1);
                        break;
                    }
                    if(r == 1 && !visited[x-1][y]){
                        // move west
                        east[x-1][y] = false;
                        west[x][y] = false;
                        generate(x-1, y);
                        break;
                    }
                }
            }
        }

        public void solve(int x, int y){
            System.out.println("SOLVE");
            if (x == 0 || y == 0 || x == n+1 || y == m+1) return;
            if (done || visited[x][y]) return;

            visited[x][y] = true;

            StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
            StdDraw.filledRectangle(x + 0.5, y + 0.5, 0.25, 0.25);
            StdDraw.show(timedelay);

            if(x == solx && y == soly) done = true;

            if(!north[x][y]) solve(x, y+1);
            if(!east[x][y]) solve(x+1, y);
            if(!south[x][y]) solve(x, y-1);
            if(!west[x][y]) solve(x-1, y);

            if(done) return;

            StdDraw.setPenColor(StdDraw.PINK);
            StdDraw.filledRectangle(x + 0.5, y + 0.5, 0.45, 0.45);
            StdDraw.show(timedelay);
        }

        public void display(){
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.GREEN);
            StdDraw.filledRectangle(startx+0.5, starty+0.5, 0.5, 0.5);

            if(solx != 0 && soly != 0){
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.filledRectangle(solx+0.5, soly+0.5, 0.5, 0.5);
            }

            StdDraw.setPenColor(StdDraw.BLUE);
            for (int x = 1; x <= n; x++) {
                for (int y = 1; y <= m; y++) {
                    if (south[x][y]) StdDraw.line(x, y, x + 1, y);
                    if (north[x][y]) StdDraw.line(x, y + 1, x + 1, y + 1);
                    if (west[x][y])  StdDraw.line(x, y, x, y + 1);
                    if (east[x][y])  StdDraw.line(x + 1, y, x + 1, y + 1);
                }
            }
            StdDraw.show(timedelay);
        }
 }
