package lab11.graphs;

import java.util.ArrayDeque;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private Maze maze;
    private int s;
    private int t;
    private boolean findTarget;
    private ArrayDeque<Integer> searchDeque;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        this.maze = m;
        this.s = maze.xyTo1D(sourceX, sourceY);
        this.t = maze.xyTo1D(targetX, targetY);
        this.searchDeque = new ArrayDeque<>();
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        searchDeque.addLast(this.s);
        marked[this.s] = true;
        announce();
        while (!findTarget && !searchDeque.isEmpty()) {
            int dot = searchDeque.removeFirst();
            if (dot == this.t) {
                findTarget = true;
                break;
            }
            for (int x : maze.adj(dot)) {
                if (!marked[x]) {
                    searchDeque.addLast(x);
                    marked[x] = true;
                    edgeTo[x] = dot;
                    distTo[x] = distTo[dot] + 1;
                    announce();
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

