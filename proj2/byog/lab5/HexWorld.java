package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import javax.swing.text.Position;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final Random RANDOM = new Random();

    private static class Position {
        private int x;
        private int y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        Position(){}
    }

    public static TETile randomTile() {
        int x = RANDOM.nextInt(7);
        switch (x) {
            case 0: return Tileset.WATER;
            case 1: return Tileset.GRASS;
            case 2: return Tileset.WALL;
            case 3: return Tileset.FLOWER;
            case 4: return Tileset.FLOOR;
            case 5: return Tileset.SAND;
            case 6: return Tileset.TREE;
            case 7: return Tileset.MOUNTAIN;
            default: return Tileset.LOCKED_DOOR;
        }
    }

    public static void drawLine(TETile[][] world, Position P, int length, TETile texture) {
        for (int i = 0;i < length; i++) {
            world[P.x + i][P.y] = texture;
        }
    }

    public static Position calPosition(Position start, int line, int edge) {
        Position lineStart = new Position();
        if (line < edge) {
            lineStart.x = start.x - line;
        } else {
            lineStart.x = start.x - 2 * edge + 1 + line;
        }
        lineStart.y = start.y + line;
        return lineStart;
    }

    public static int calLength(int line, int edge) {
        if (line < edge) {
            return edge + 2*line;
        } else {
            return edge - 2 * (line % edge) + 2 * (edge - 1);
        }
    }

    public static void addHexagon(TETile[][] world, Position p, int edge, TETile t) {
        for (int i = 0; i < 2 * edge;i++) {
            drawLine(world, calPosition(p, i, edge), calLength(i, edge), t);
        }
    }

    public static void drawColumn(TETile[][] world, Position start, int edge, int cnt) {
        for (int i = 0;i < cnt; i++) {
            Position p = new Position(start.x, start.y - 2 * i * edge);
            TETile texture = randomTile();
            addHexagon(world, p, edge, texture);
        }
    }

    public static void drawTesselation(TETile[][] world, Position start, int edge) {
        int[] num = {3, 4, 5, 4, 3};
        for (int i = 0; i < 5; i++) {
            int offsetX = i * (edge * 2 - 1);
            int offsetY = (num[i] - 3) * edge;
            Position colStart = new Position(start.x + offsetX, start.y + offsetY);
            drawColumn(world, colStart, edge, num[i]);
        }
    }

    public static void main(String[] args) {
        int height = 50;
        int width = 50;

        TERenderer ter = new TERenderer();
        ter.initialize(width, height);

        TETile[][] world  = new TETile[width][height];
        Position a = new Position(13 ,30);
        /*initialize the world */
        for (int i = 0; i < width;i++) {
            for(int j = 0; j < width;j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
        drawTesselation(world, a, 3);

        ter.renderFrame(world);
    }
}
