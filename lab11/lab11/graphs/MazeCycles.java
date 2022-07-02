package lab11.graphs;

import edu.princeton.cs.algs4.Stack;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    public int[] pathTo;
    boolean ring = false;

    public MazeCycles(Maze m) {
        super(m);
        this.pathTo = new int[maze.V()];
        pathTo[0] = 0;
        for (int i = 0; i < maze.V(); i++) {
            pathTo[i] = 100;
        }
    }

    @Override
    public void solve() {
        dfs(0);
        announce();
    }

    // Helper methods go here
    private void dfs(int i) {
        marked[i] = true;
        for (int j : maze.adj(i)) {
            if (ring) {
                return;
            }
            if (marked[j]) {
                if (j != pathTo[i]) {
                    pathTo[j] = i;
                    edgeTo[j] = pathTo[j];
                    int loop = j;
                    int cur = i;
                    while (cur != loop) {
                        edgeTo[cur] = pathTo[cur];
                        cur = pathTo[cur];
                        System.out.println(cur);
                    }
                    ring = true;
                    return;
                }
            } else {
                pathTo[j] = i;
                dfs(j);
            }
        }
    }
}

