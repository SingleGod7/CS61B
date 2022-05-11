package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

public class HallwayGenerator {
    private TETile[][] world;
    private int worldHeight;
    private int worldWidth;
    private Random random;
    private Deque<Position> dotDeque;

    HallwayGenerator(TETile[][] world, long randomSeed) {
        this.world = world;
        this.worldWidth = world.length;
        this.worldHeight = world[0].length;
        this.dotDeque = new ArrayDeque<Position>();
        this.random = new Random(randomSeed);
    }

    public void worldGrid() {
        //make the world at odd size of height and width
        if (this.worldWidth % 2 == 0) {
            this.worldWidth -= 1;
        }

        if (this.worldHeight % 2 == 0) {
            this.worldHeight -= 1;
        }

        for (int i = 0; i < worldWidth; i++) {
            for (int j = 0; j < worldHeight; j++) {
                if (i % 2 == 0 || j % 2 == 0) {
                    world[i][j] = Tileset.WALL;
                } else {
                    world[i][j] = Tileset.NOTHING;
                }
            }
        }
    }

    public void hallwayGenerate() {
        while (true) {
            int startX = random.nextInt(worldWidth - 1);
            int startY = random.nextInt(worldHeight - 1);
            if (world[startX][startY] == Tileset.NOTHING) {
                dotDeque.addLast(new Position(startX, startY));
                break;
            }
        }

        while (dotDeque.size() != 0) {
            Position start = dotDeque.removeFirst();
            world[start.x][start.y] = Tileset.FLOOR;
            connectDot(start);
        }

    }

    public boolean isConnected() {
        for (int i = 0; i < worldWidth; i++) {
            for (int j = 0; j < worldHeight; j++) {
                if (world[i][j] == Tileset.NOTHING) {
                    return false;
                }
            }
        }

        return true;
    }

    private void connectDot(Position p) {
        int[][] next = {{2, 0}, {-2, 0}, {0, 2}, {0, -2}};
        boolean[] goThrough = {false, false, false, false};
        boolean connected = false;

        while (!allGoThrough(goThrough)) {
            int nextPos = random.nextInt(4);
            if (goThrough[nextPos]) {
                continue;
            } else {
                goThrough[nextPos] = true;
                int nextX = p.x + next[nextPos][0];
                int nextY = p.y + next[nextPos][1];
                //detect if it is out of space
                if (nextY > worldHeight - 1 || nextY < 0 || nextX < 0|| nextX > worldWidth - 1) {
                    continue;
                } else if (world[nextX][nextY] == Tileset.NOTHING) {
                    dotDeque.addLast(new Position(nextX, nextY));
                    world[nextX][nextY] = Tileset.TREE;
                } else if(world[nextX][nextY] == Tileset.FLOOR && !connected) {
                    connected = true;
                    world[(nextX + p.x) / 2][(nextY + p.y) / 2] = Tileset.FLOOR;
                }
            }
        }
    }

    private boolean allGoThrough(boolean[] goThrough) {
        for (boolean i : goThrough) {
            if (!i) {
                return false;
            }
        }
        return true;
    }
}
