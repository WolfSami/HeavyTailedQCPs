import java.io.Serializable;

/**
 * Represents the empty vertices of a Latin Square.
 */
/*public class EmptySet implements Serializable {
    //private TreeMap<String,Integer> emptyIndices;
    private LatinSquare ls;
    int seed;

    public EmptySet(LatinSquare ls, int solveSeed) {
        for (int i = 0; i < ls.getSize(); i++) {
            for (int j = 0; j < ls.getSize(); j++) {
                if (ls.getElement(i, j) == 0) {
                    list.add(i + "," + j);
                }
            }
        }
    }
}















    /*public EmptySet(LatinSquare ls, int solveSeed){
        this.ls = ls;
        seed = solveSeed;
        class IndexComparator implements Serializable, Comparator<String>{

            @Override
            public int compare(String o1, String o2) {
                int diff = (ls.uniqueNeighbors(Solve.getRowString(o1),Solve.getColString(o1)).size() - (ls.uniqueNeighbors(Solve.getRowString(o2),Solve.getColString(o2))).size());
                if (o1.equals(o2)) return 0;
                if (diff > 0) return -1;
                if (diff < 0) return 1;
                List<String> list = new ArrayList<>();
                double random = (new Random(seed)).nextDouble() * 20;
                if (random < 10){
                    if (Solve.getRowString(o1) < Solve.getRowString(o2))
                        return 1;
                    else if (Solve.getRowString(o2) < Solve.getRowString(o1))
                        return -1;
                    else if (random % 10 < 5){
                        if (Solve.getColString(o1) < Solve.getColString(o2))
                            return 1;
                        else if (Solve.getColString(o2) < Solve.getColString(o1))
                            return -1;
                    }
                    else{
                        if (Solve.getColString(o1) < Solve.getColString(o2))
                            return -1;
                        else if (Solve.getColString(o2) < Solve.getColString(o1))
                            return 1;
                    }
                    System.err.println("Equals didn't work. Indices equal.");
                    System.err.println("random = " + random);
                    System.err.println("o1 = " + o1 + " o2 = " + o2);
                    return 0;
                }
                else{
                    if (Solve.getRowString(o1) < Solve.getRowString(o2))
                        return -1;
                    else if (Solve.getRowString(o2) < Solve.getRowString(o1))
                        return 1;
                    else if (random % 10 < 5){
                        if (Solve.getColString(o1) < Solve.getColString(o2))
                            return -1;
                        else if (Solve.getColString(o2) < Solve.getColString(o1))
                            return 1;
                    }
                    else if (Solve.getColString(o1) < Solve.getColString(o2))
                        return 1;
                    else if (Solve.getColString(o2) < Solve.getColString(o1))
                        return -1;
                    System.err.println("Equals didn't work. Indices equal.");
                    return 0;
                }
            }
        }
        Comparator<String> comp = new IndexComparator();
        emptyIndices = new TreeMap<>(comp);
        List<String> list = new ArrayList<>(ls.getSize() * ls.getSize());
        for (int i = 0; i < ls.getSize();i++){
            for (int j = 0; j < ls.getSize();j++){
                if (ls.getElement(i,j) == 0){
                    list.add(i+","+j);
                }
            }
        }
        Collections.shuffle(list,new Random(seed));
        for (String ind : list){
            emptyIndices.put(ind,ls.uniqueNeighbors(Solve.getRowString(ind),Solve.getColString(ind)).size());
        }
        //System.out.println(this.toString());
    }*/

    /*public EmptySet(LatinSquare ls, int solveSeed, EmptySet other){
        this.ls = ls;
        seed = solveSeed;
        emptyIndices = other.copyTree();
    }*/

    /**
     * Returns the Index with the fewest unique neighbors.
     */
    /*
    public Map.Entry<String, Integer> remove(){
        Map.Entry<String, Integer> entry = emptyIndices.pollFirstEntry();
        //remove all elements in the same row and col as this one. add them back with updated priority.
        for (int i = 0; i < Solve.getRowString(entry.getKey()); i++){
            Integer val = emptyIndices.remove(i + "," + Solve.getColString(entry.getKey()));
            if (Objects.nonNull(val))
                emptyIndices.put(i + "," + Solve.getColString(entry.getKey()),val + 1);
        }
        for (int i = Solve.getRowString(entry.getKey() + 1); i < ls.getSize(); i++){
            Integer val = emptyIndices.remove(i + "," + Solve.getColString(entry.getKey()));
            if (Objects.nonNull(val))
                emptyIndices.put(i + "," + Solve.getColString(entry.getKey()),val + 1);
        }
        for (int i = 0; i < Solve.getColString(entry.getKey()); i++){
            Integer val = emptyIndices.remove(Solve.getRowString(entry.getKey()) + "," + i);
            if (Objects.nonNull(val))
                emptyIndices.put(Solve.getRowString(entry.getKey()) + "," + i,val + 1);
        }
        for (int i = 0; i < Solve.getRowString(entry.getKey()); i++){
            Integer val = emptyIndices.remove(Solve.getRowString(entry.getKey()) + "," + i);
            if (Objects.nonNull(val))
                emptyIndices.put(Solve.getRowString(entry.getKey()) + "," + i,val + 1);
        }
        return entry;
    }

    public TreeMap<String,Integer> copyTree(){
        return (TreeMap<String,Integer>) DeepCopy.copy(emptyIndices);
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("EmptySet. \n");
        s.append(emptyIndices.toString() + '\n');
        s.append(ls.toString() + '\n');
        s.append("----------");
        return s.toString();
    }*/
    /*
    public boolean lessThan(String index1,String index2, int seed){
        int row1 = Solve.getRowString(index1);
        int col1 = Solve.getColString(index1);
        int row2 = Solve.getRowString(index2);
        int col2 = Solve.getColString(index2);
        return true;
    }*/
