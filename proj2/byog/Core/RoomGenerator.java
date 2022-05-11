package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RoomGenerator {
    private TETile[][] world;
    private int worldWidth;
    private int worldHeight;
    public List<Room> roomList;
    private Random random;

    public RoomGenerator(TETile[][] world, long randomSeed) {
        this.world = world;
        this.random = new Random(randomSeed);
        this.worldWidth = world.length;
        this.worldHeight = world[0].length;
        this.roomList = new ArrayList<>();
    }

    public static class Room {
        //Room start at lower left corner
        public Position coordinate;
        public int width;
        public int height;

        Room(Position p, int width, int height) {
            this.coordinate = p;
            this.width = width;
            this.height = height;
        }
    }

    public void roomGenerator() {
        int times = 100;
        for (int i = 0; i < times; i++) {
            Room newRoom = randomRoom();
            if (isValidRoom(newRoom)) {
                roomList.add(newRoom);
            }
        }
    }

    private Room randomRoom() {
        Position randomPos = new Position(random.nextInt(worldWidth), random.nextInt(worldHeight));
        int randomWidth = random.nextInt(this.worldWidth / 5);
        int randomHeight = random.nextInt(this.worldHeight / 5);

        return new Room(randomPos, randomWidth, randomHeight);
    }

    private boolean isValidRoom(Room r) {
        //find if it is out of the map
        if (r.coordinate.x + r.width - 1 >= worldWidth) {
            return false;
        } else if (r.coordinate.y + r.height - 1 >= worldHeight) {
            return false;
        //remove the room like hallway
        } else if (r.height < 4 || r.width < 4) {
            return false;
        }

        //find if is overlap
        for (Room x : roomList) {
            if (isOverlap(x, r)) {
                return false;
            }
        }
        return true;
    }

    private boolean isOverlap(Room r1, Room r2) {
        boolean hflag = false;
        boolean vflag = false;
        int hDistance = Math.abs(r1.coordinate.x - r2.coordinate.x);
        int vDistance = Math.abs(r1.coordinate.y - r2.coordinate.y);

        if (r1.coordinate.x < r2.coordinate.x) {
            if (r1.width > hDistance) {
                hflag = true;
            }
        } else {
            if (r2.width > hDistance) {
                hflag = true;
            }
        }

        if (r1.coordinate.y < r2.coordinate.y) {
            if (r1.height > vDistance) {
                vflag = true;
            }
        } else {
            if (r2.height > vDistance) {
                vflag = true;
            }
        }
        return hflag && vflag;
    }

    public void renderRoomList() {
        for (Room i : roomList) {
            renderRoom(i);
        }
    }

    private void renderRoom(Room r) {
        int rightBound = r.coordinate.x + r.width - 1;
        int leftBound = r.coordinate.x;
        int lowerBound = r.coordinate.y;
        int upperBound = r.coordinate.y + r.height - 1;

        for (int i = lowerBound; i <= upperBound; i++) {
            for (int j = leftBound; j <= rightBound; j++) {
                if (i == lowerBound || i == upperBound || j == leftBound || j == rightBound) {
                    //render the wall of the room
                    world[j][i] = Tileset.WALL;
                } else {
                    //render the rest line
                    world[j][i] = Tileset.WATER;
                }
            }
        }
    }
}
