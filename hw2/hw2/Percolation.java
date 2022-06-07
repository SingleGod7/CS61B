package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF percolationGraph;
    private WeightedQuickUnionUF fullGraph;
    private boolean[][] openGraph;
    private int N;
    private int openCnt;

    public Percolation(int N){
        percolationGraph = new WeightedQuickUnionUF(N * N);
        fullGraph = new WeightedQuickUnionUF(N * N);
        this.N = N;
        this.openGraph = new boolean[N][N];

        //init the openGraph
        for (int i = 0 ; i < N; i++) {
            for (int j = 0; j < N; j++) {
                openGraph[i][j] = false;
            }
        }

        //union the bottom and top
        for (int i = 0; i < N; i++) {
            percolationGraph.union(0, i);
            fullGraph.union(0, i);
            fullGraph.union((N - 1) * N, (N - 1) * N + i);
        }
    }

    public void open(int row, int col) {
        checkValidCoordinate(row, col);
        openGraph[row][col] = true;
        openCnt += 1;
        int[] direction = {-1, 1};
        int index = row * N + col;
        for (int i : direction) {
            if (row + i >= 0 && row + i < N && openGraph[row + i][col]) {
                percolationGraph.union(index, index + N * i);
                fullGraph.union(index, index + N * i);
            }
            if (col + i >= 0 && col + i < N && openGraph[row][col + i]) {
                percolationGraph.union(index, index + i);
                fullGraph.union(index, index + i);
            }
        }
    }

    public boolean isOpen(int row, int col) {
        checkValidCoordinate(row, col);
        return openGraph[row][col];
    }

    public boolean isFull(int row, int col) {
        if (row == 0) {
            return openGraph[row][col];
        } else if (row == N - 1){
            return fullGraph.connected(0, row * N + col) && openGraph[row][col] && percolationGraph.connected(0, row * N + col);
        } else {
            return fullGraph.connected(0, row * N + col) && percolationGraph.connected(0, row * N + col);
        }
    }

    public int numberOfOpenSites() {
        return this.openCnt;
    }

    public boolean percolates() {
        return fullGraph.connected(0, N * (N - 1));
    }

    /** helper function */
    private void checkValidCoordinate(int row, int col) {
        checkValidIndex(row);
        checkValidIndex(col);
    }

    private void checkValidIndex(int index) {
        if (index < 0) {
            throw new java.lang.IllegalArgumentException();
        }
        if (index > (N - 1)) {
            throw new java.lang.IndexOutOfBoundsException();
        }
    }

    public static void main(String[] args) {

    }
}
