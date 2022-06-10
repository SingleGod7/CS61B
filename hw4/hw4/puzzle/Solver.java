package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.HashMap;
import java.util.Deque;
import java.util.ArrayDeque;

public class Solver {
    private MinPQ<SearchNode> aStarSearch;
    private WorldState initState;
    private SearchNode solutionNode;
    private HashMap<WorldState, Integer> estimateSame;


    public Solver(WorldState initial) {
        this.initState = initial;
        this.aStarSearch = new MinPQ<>();
        this.estimateSame = new HashMap<>();

        SearchNode init = new SearchNode(initState, 0, null);
        aStarSearch.insert(init);
        SearchNode bMS = aStarSearch.delMin();
        estimateSame.put(bMS.curState, bMS.distance2goal);

        while (!bMS.curState.isGoal()) {
            for (WorldState i : bMS.curState.neighbors()) {
                int distance2goal = i.estimatedDistanceToGoal() + bMS.moveStep + 1;
                if (estimateSame.containsKey(i) && estimateSame.get(i) <= distance2goal) {
                    continue;
                }
                aStarSearch.insert(new SearchNode(i, bMS.moveStep + 1, bMS));
            }
            System.out.println(bMS.curState.estimatedDistanceToGoal());
            bMS = aStarSearch.delMin();
            estimateSame.put(bMS.curState, bMS.distance2goal);
        }
        solutionNode = bMS;
    }

    public int moves() {
        return solutionNode.moveStep;
    }

    public Iterable<WorldState> solution() {
        Deque<WorldState> solution = new ArrayDeque<>();
        SearchNode iterNode = solutionNode;
        while (iterNode != null) {
            solution.addFirst(iterNode.curState);
            iterNode = iterNode.previousSearchNode;
        }
        return solution;
    }

    /** class of search node */
    private class SearchNode implements Comparable<SearchNode> {
        private WorldState curState;
        private int moveStep;
        private SearchNode previousSearchNode;
        private int distance2goal;

        SearchNode(WorldState worldState, int mv, SearchNode preNode) {
            this.curState = worldState;
            this.moveStep = mv;
            this.previousSearchNode = preNode;
            distance2goal = moveStep + curState.estimatedDistanceToGoal();
        }

        public int compareTo(SearchNode other) {
            return this.distance2goal - other.distance2goal;
        }
    }
}
