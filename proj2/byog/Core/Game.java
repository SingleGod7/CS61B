package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public int WIDTH;
    public int HEIGHT;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        String digit = "([0-9]+)";

        Pattern r = Pattern.compile(digit);
        Matcher m = r.matcher(input);
        m.find();
        long randomSeed = Long.parseLong(m.group(1));
        Random random = new Random(randomSeed);

        this.WIDTH = random.nextInt(50,200);
        this.HEIGHT = random.nextInt(50,200);


        TETile[][] world = new TETile[WIDTH][HEIGHT];

        WorldGenerator x = new WorldGenerator(world, randomSeed);

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
        x.worldGenerate();

        return world;
    }
}
