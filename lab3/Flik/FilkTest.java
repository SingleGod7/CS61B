import org.junit.Test;
import static org.junit.Assert.*;

public class FilkTest {

    @Test
    public void testIsSameNumber() {
        assertFalse(Flik.isSameNumber(128, 128));
        assertTrue(Flik.isSameNumber(129, 129));
    }
}
