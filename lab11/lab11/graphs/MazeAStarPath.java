package lab11.graphs;

import edu.princeton.cs.algs4.MinPQ;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private int targetX;
    private int targetY;
    private MinPQ<astarHelper> min;
    private int[] roadTo;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        roadTo = new int[maze.V()];
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        this.targetX = targetX;
        this.targetY = targetY;
        distTo[s] = 0;
        edgeTo[s] = s;
        roadTo[s] = s;

        min = new MinPQ<>();
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        return Math.abs(maze.toX(v) - targetX) + Math.abs(maze.toY(v) - targetY);
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        min.insert(new astarHelper(s));
        marked[s] = true;
        while (!min.isEmpty()) {
            astarHelper a = min.delMin();
            edgeTo[a.pos] = roadTo[a.pos];
            announce();
            if (a.pos == t) {
                break;
            }
            for (int i : maze.adj(a.pos)) {
                if (!marked[i]) {
                    min.insert(new astarHelper(i));
                    marked[i] = true;
                    roadTo[i] = a.pos;
                }
            }
        }
    }

    private class astarHelper implements Comparable<astarHelper> {
        public int pos;
        public int estimateDis;

        astarHelper(int v) {
            this.pos = v;
            this.estimateDis = h(v);
        }

        @Override
        public int compareTo(astarHelper o) {
            return this.estimateDis - o.estimateDis;
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

