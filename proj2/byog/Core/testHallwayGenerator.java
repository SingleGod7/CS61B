package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class testHallwayGenerator {
    public static TETile[][] world = new TETile[100][50];
    public static HallwayGenerator x = new HallwayGenerator(world, 11);
    public static RoomGenerator y = new RoomGenerator(world, 11);

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(100,50);

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 50; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
        x.worldGrid();
        y.roomGenerator();
        y.renderRoomList();
        x.hallwayGenerate();
        ter.renderFrame(world);
    }
}
