package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayDeque;
import java.util.Random;

public class WorldGenerator {
    private TETile[][] world;
    private int worldHeight;
    private int worldWidth;
    private Random random;

    WorldGenerator(TETile[][] world, int randomSeed) {
        this.world = world;
        this.worldWidth = world.length;
        this.worldHeight = world[0].length;
        this.random = new Random(randomSeed);
    }

    public void worldGenerate() {
        RoomGenerator rooms = new RoomGenerator(world, random.nextInt());
        HallwayGenerator hallway = new HallwayGenerator(world, random.nextInt());
        do {
            hallway.worldGrid();
            rooms.roomGenerator();
            rooms.renderRoomList();
            hallway.hallwayGenerate();
        }while (!hallway.isConnected());
        connectRoomWithHallway(rooms);
        fillDeadEnd();
        removeRedundantWall();
        removeMark();

    }

    public void connectRoomWithHallway(RoomGenerator rooms) {
        for (RoomGenerator.Room i : rooms.roomList) {
            removeWall(i);
        }
    }

    public void removeWall(RoomGenerator.Room room) {
        while (true) {
            int direction = random.nextInt(4);
            int mvX, mvY;
            switch (direction) {
                case 0://left
                    mvX = room.coordinate.x;
                    mvY = room.coordinate.y + random.nextInt(room.height - 2) + 1;
                    if (!removeable(mvX, mvY, direction)) {
                        continue;
                    }
                    world[mvX][mvY] = Tileset.FLOOR;
                    if (world[mvX - 1][mvY] == Tileset.FLOOR) {
                        return;
                    }
                    world[mvX - 1][mvY] = Tileset.FLOOR;
                    return;
                case 1://right
                    mvX = room.coordinate.x + room.width - 1;
                    mvY = room.coordinate.y + random.nextInt(room.height - 2) + 1;
                    if (!removeable(mvX, mvY, direction)) {
                        continue;
                    }
                    world[mvX][mvY] = Tileset.FLOOR;
                    if (world[mvX + 1][mvY] == Tileset.FLOOR) {
                        return;
                    }
                    world[mvX + 1][mvY] = Tileset.FLOOR;
                    return;
                case 2://down
                    mvX = room.coordinate.x + random.nextInt(room.width - 2) + 1;
                    mvY = room.coordinate.y;
                    if (!removeable(mvX, mvY, direction)) {
                        continue;
                    }
                    world[mvX][mvY] = Tileset.FLOOR;
                    if (world[mvX][mvY - 1] == Tileset.FLOOR) {
                        return;
                    }
                    world[mvX][mvY - 1] = Tileset.FLOOR;
                    return;
                case 3://up
                    mvX = room.coordinate.x + random.nextInt(room.width - 2) + 1;
                    mvY = room.coordinate.y + room.height - 1;
                    if (!removeable(mvX, mvY, direction)) {
                        continue;
                    }
                    world[mvX][mvY] = Tileset.FLOOR;
                    if (world[mvX][mvY + 1] == Tileset.FLOOR) {
                        return;
                    }
                    world[mvX][mvY + 1] = Tileset.FLOOR;
                    return;
                default:
                    return;
            }
        }
    }

    private boolean removeable(int x, int y, int direction) {
        int[][] directionNext = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int nextX = x + directionNext[direction][0];
        int nextY = y + directionNext[direction][1];
        Position next = new Position(nextX, nextY);

        int nNextX = nextX + directionNext[direction][0];
        int nNextY = nextY + directionNext[direction][1];
        Position nNext = new Position(nNextX, nNextY);

        if (!isValidPosition(nNext) || !isValidPosition(next)) {
            return false;
        }

        if (world[nextX][nextY] == Tileset.NOTHING) {
            return false;
        }
        if (world[nextX][nextY] == Tileset.WALL && world[nNextX][nNextY] == Tileset.WALL) {
            return false;
        }
        return true;
    }

    private void fillDeadEnd() {
        ArrayDeque<Position> deadEnd = new ArrayDeque<>();
        for (int i = 0; i < worldWidth; i++) {
            for (int j = 0; j < worldHeight; j++) {
                if (world[i][j] == Tileset.FLOOR) {
                    Position p = new Position(i, j);
                    if (isDeadEnd(p)) {
                        deadEnd.addLast(p);
                    }
                }
            }
        }
        removeRecursive(deadEnd);
    }

    private void removeRecursive(ArrayDeque<Position> deadEnd) {
        while (deadEnd.size() != 0) {
            Position remove = deadEnd.removeFirst();
            world[remove.x][remove.y] = Tileset.WALL;
            ArrayDeque<Position> positionAround = new ArrayDeque<>();
            positionAround.addLast(new Position(remove.x, remove.y - 1));
            positionAround.addLast(new Position(remove.x, remove.y + 1));
            positionAround.addLast(new Position(remove.x - 1, remove.y));
            positionAround.addLast(new Position(remove.x + 1, remove.y));
            for (Position p : positionAround) {
                if (isDeadEnd(p) && world[p.x][p.y] == Tileset.FLOOR) {
                    deadEnd.addLast(p);
                }
            }
        }
    }

    private boolean isDeadEnd(Position p) {
        int[][] next = {{1, 0},
                {-1, 0},
                {0, 1},
                {0, -1}};
        int cnt = wallCounter(p, next);
        return cnt >= 3;
    }

    private int wallCounter(Position p, int[][] direction) {
        int cnt = 0;
        int upperBound = worldHeight - 2 + worldWidth % 2;
        int rightBound = worldWidth - 2 + worldWidth % 2;
        for (int[] dir : direction){
            Position next = new Position(p.x + dir[0], p.y + dir[1]);
            if (!isValidPosition(next)) {
                continue;
            }
            if (world[next.x][next.y] == Tileset.WALL) {
                cnt += 1;
            }
        }
        return cnt;
    }

    private boolean isValidPosition(Position p) {
        int upperBound = worldHeight - 2 + worldWidth % 2;
        int rightBound = worldWidth - 2 + worldWidth % 2;
        if (p.x < 0 || p.x > rightBound || p.y < 0 || p.y > upperBound) {
            return false;
        }
        return true;
    }

    private void removeRedundantWall() {
        int[][] next = {{-1, 1}, {-1, -1}, {1, 1}, {1, -1}};
        ArrayDeque<Position> removeWall = new ArrayDeque<>();
        for (int i = 0; i < worldWidth; i++) {
            for (int j = 0; j < worldHeight; j++) {
                if (world[i][j] == Tileset.WALL && isRedundant(new Position(i, j), next)) {
                    removeWall.addLast(new Position(i, j));
                }
            }
        }

        for(Position p : removeWall) {
            world[p.x][p.y] = Tileset.NOTHING;
        }
    }

    private boolean isRedundant(Position p, int[][] direction) {
        int cntValidPlace = 0;
        for (int[] next : direction) {
            if (isValidPosition(new Position(p.x + next[0], p.y + next[1]))) {
                cntValidPlace += 1;
            }
        }
        return wallCounter(p, direction) == cntValidPlace;
    }

    private void removeMark() {
        for (int i = 0; i < worldWidth; i++) {
            for (int j = 0; j < worldHeight; j++) {
                if (world[i][j] == Tileset.WATER) {
                    world[i][j] = Tileset.FLOOR;
                    }
                }
            }
    }
}
