package hw3.hash;

import java.util.ArrayList;
import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        List<Oomage>[] buckets = new List[M];
        int N = oomages.size();
        for (int i = 0; i < M; i++) {
            buckets[i] = new ArrayList<>();
        }
        for (Oomage o : oomages) {
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            buckets[bucketNum].add(o);
        }
        for (List<Oomage> i : buckets) {
            if (i.size() < (N / 50) || i.size() > (N / 2.5)) {
                return false;
            }
        }
        return true;
    }
}
