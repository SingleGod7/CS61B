package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class testWorldGenerator {
    public static TETile[][] world = new TETile[100][50];

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(100, 50);

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 50; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }

        WorldGenerator z = new WorldGenerator(world, 1124311);

        z.worldGenerate();

        ter.renderFrame(world);
    }
}
