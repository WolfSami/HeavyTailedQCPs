import java.io.Serializable;
import java.util.*;

abstract class IndexComparator implements Serializable, Comparator<Index>{

    LatinSquare ls;
    int seed;
    public IndexComparator(LatinSquare ls, int seed){

    }
    @Override
    public int compare(Index o1, Index o2) {
        int diff = (ls.uniqueNeighbors(o1.row,o1.col).size - (ls.uniqueNeighbors(o2.row,o2.col)).size);
        if (o1.equals(o2)) return 0;
        if (diff > 0) return 1;
        if (diff < 0) return -1;
        List<Index> list = new ArrayList<>();
        list.add(o1);
        list.add(o2);
        Collections.shuffle(list,new Random(seed));
        if (list.get(0).equals(o1)) return 1;
        return -1;
    }
}
