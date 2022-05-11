package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class testRoomGenerator {
    public static TETile[][] world = new TETile[100][50];
    public static RoomGenerator x = new RoomGenerator(world, 111231);

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(100,50);
        for (int i = 0;i < 100; i++){
            for (int j = 0;j < 50;j++){
                world[i][j] = Tileset.NOTHING;
            }
        }
        x.roomGenerator();
        x.renderRoomList();

        ter.renderFrame(world);
    }

}
