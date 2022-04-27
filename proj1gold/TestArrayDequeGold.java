import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    @Test
    public void testStudentArray() {
        StudentArrayDeque<Integer> x = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> y = new ArrayDequeSolution<>();

        StringBuffer msg = new StringBuffer();

        //Test random operation
        for (Integer i = 0; i < 500; i++) {
            double probability = StdRandom.uniform();
            if (probability > 0.75) {
                x.addFirst(i);
                y.addFirst(i);
                msg.append("addFirst(" + i + ")\n");
                assertEquals(msg.toString(), y.get(0), x.get(0));
            } else if (probability > 0.5) {
                x.addLast(i);
                y.addLast(i);
                Integer last = y.size() - 1;
                msg.append("addLast(" + i + ")\n");
                assertEquals(msg.toString(), y.get(last), x.get(last));
            } else if (probability > 0.25 && y.size() > 0) {
                msg.append("removeLast()\n");
                assertEquals(msg.toString(), y.removeLast(), x.removeLast());
            } else if (y.size() > 0) {
                msg.append("removeFirst()\n");
                assertEquals(msg.toString(), y.removeFirst(), x.removeFirst());
            }
        }

    }
}
