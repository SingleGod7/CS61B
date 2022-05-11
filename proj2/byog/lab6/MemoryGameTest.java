package byog.lab6;

import org.junit.Test;
import static org.junit.Assert.*;

public class MemoryGameTest {
    public MemoryGame x = new MemoryGame(1024,768, 100);

    //@Test
    public void testGenerateRandomString() {
        String a = x.generateRandomString(1);
        assertEquals(1, a.length());
        String b = x.generateRandomString(2);
        assertEquals(2,b.length());
    }

    //@Test
    public void testDrawFrame() {
        x.drawFrame("wo shi sha bi");
        x.drawFrame("ni cai shi");
        x.drawFrame("que shi");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //@Test
    public void testFlashSequence() {
        x.flashSequence("nishishenmeguanjun");
    }

    @Test
    public void testSolicitNCharsInput() {
        x.solicitNCharsInput(10);
    }
}
