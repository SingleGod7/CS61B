package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);

        assertEquals(10, arb.capacity());
        assertTrue(arb.isEmpty());

        try {
            arb.dequeue();
        } catch (RuntimeException e) {
            e.getStackTrace();
        }


        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        arb.enqueue(4);
        arb.enqueue(5);
        arb.enqueue(6);

        assertEquals(1, (int) arb.peek());

        int x = arb.dequeue();

        assertEquals(1, x);
        assertEquals(2, (int) arb.peek());
        assertEquals(5, arb.fillCount);

        arb.enqueue(7);
        arb.enqueue(8);
        arb.enqueue(9);
        arb.enqueue(10);
        arb.enqueue(11);

        for (int s : arb) {
            System.out.print(s + "->");
        }
        try {
            arb.enqueue(12);
        } catch (RuntimeException e) {
            e.getStackTrace();
        }

        assertTrue(arb.isFull());
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
