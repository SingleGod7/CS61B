package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;
import java.util.StringJoiner;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        this.rand = new Random(seed);
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        StringBuilder randomString = new StringBuilder();
        Random a = new Random();
        for (int i = 0; i < n; i++) {
            char randomCharacter = CHARACTERS[a.nextInt(25)];
            randomString.append(randomCharacter);
        }
        return randomString.toString();
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
        StdDraw.clear(Color.BLACK);
        if (!gameOver) {
            Font uiFont = new Font("Monaco", Font.ITALIC, 20);
            StdDraw.setFont(uiFont);
            StdDraw.textLeft(1, this.height - 1, "Round:" + this.round);
            StdDraw.text(this.width / 2, this.height - 1, this.playerTurn ? "Type!" : "Watch!");
            StdDraw.textRight(this.width - 1, this.height - 1, "Shiny <3!");
            StdDraw.line(0, this.height - 2, this.width, this.height - 2);
        }

        Font displayFont = new Font("宋体", Font.BOLD, 30);
        StdDraw.setFont(displayFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(this.width / 2, this.height /2, s);
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        for (int i = 0; i < letters.length(); i++) {
            drawFrame(letters.substring(i, i + 1));
            StdDraw.pause(1000);
            drawFrame("");
            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        StringBuilder sb = new StringBuilder();
        while (n != 0) {
            if (StdDraw.hasNextKeyTyped()) {
                char charNow = StdDraw.nextKeyTyped();
                sb.append(charNow);

                drawFrame(sb.toString());

                n--;
            }
        }
        StdDraw.pause(1000);
        return sb.toString();
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        this.round = 1;
        this.gameOver = false;
        this.playerTurn = false;
        //TODO: Establish Game loop
        while (!gameOver) {
            playerTurn = false;
            drawFrame("Round:" + this.round + " GL!HF!");
            StdDraw.pause(1000);

            String generateString = generateRandomString(this.round);
            flashSequence(generateString);

            playerTurn = true;
            String inputString = solicitNCharsInput(this.round);

            if (generateString.equals(inputString)) {
                drawFrame("Well Done! Go on!");
                this.round += 1;
                StdDraw.pause(1000);
            } else {
                drawFrame("Sorry! You're wrong!\n Final level:" + this.round);
                gameOver = true;
            }
        }
    }

}
