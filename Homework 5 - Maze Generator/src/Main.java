// Grant Schorbach
// TCSS 342 - Data Structures

public class Main {

    public static void main(String[] args) {
	    int x = 10;
	    int y = 5;

	    Maze maze = new Maze(x, y, true);
	    maze.solve(maze.startx, maze.starty);
    }
}
