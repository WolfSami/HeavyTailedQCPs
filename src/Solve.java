import java.util.ArrayList;
import java.util.List;

/**
 * Contains methods intended to find solutions or prove unsatisfiability of latin squares.
 */
public class Solve {

    /**
     * Solves the give LatinSquare.
     */

    public static int backtracks; //used to store the number of backtracks in a run.

    /**
     * Solves LatinSquare ls and counts the number of backtracks.
     * @param ls The square to be solved.
     * @param empty_indices A list of the empty indices in the square.
     * @param solveSeed The seed to be used to pseudo-randomly solve the square.
     * @return a filled solution square, if it exists.
     * @throws NoSolution if there is no solutions square in this iteration or any of its children.
     */
    public static LatinSquare btBacktracks(LatinSquare ls, ArrayList<String> empty_indices, int solveSeed) throws NoSolution{
        if (empty_indices.size() == 0)
            return ls;
        int min_decisions = 10000;
        //System.out.println(ls.toString());
        String min_index = empty_indices.get(0);
        int min_index_index = 0;
        ArrayList<String> tiedForMinList = new ArrayList<>();
        for (int i = 0; i < empty_indices.size(); i++){
            int decisionsHere = ls.getSize() - ls.uniqueNeighbors(getRowString(empty_indices.get(i)),getColString(empty_indices.get(i))).size;
            if (decisionsHere <= min_decisions) {
                if (decisionsHere < min_decisions){
                    min_decisions = decisionsHere;
                    min_index = empty_indices.get(i);
                    min_index_index = i;
                    tiedForMinList = new ArrayList<>();
                    tiedForMinList.add(min_index);
                }
                else {
                    tiedForMinList.add(empty_indices.get(i));
                }
            }
        }
        //randomize choice if more than 1 min
        if (tiedForMinList.size() > 1) {
            int rand = solveSeed % tiedForMinList.size();
            min_index = tiedForMinList.get(rand);
            min_index_index = empty_indices.indexOf(min_index);
        }
        if (min_decisions == 0) {
            throw new NoSolution();
        }
        NeighborSet uniqueNeighborsAtNextChoice = ls.uniqueNeighbors(getRowString(min_index),getColString(min_index));
        List<Integer> values = BigSquares.scrambleToList(ls.getValues(),solveSeed);
        for (int val = 0; val < ls.getSize(); val++) {//run btsearch for all legal values of min_index. if none work, backtrack.
            if (uniqueNeighborsAtNextChoice.neighbors[ls.valueToIndex.get((values.get(val)))] == 0){ //if its a legal value choice
                LatinSquare ls_altered = lscopy(ls);
                ls_altered.changeValue(getRowString(min_index),getColString(min_index),values.get(val));
                ArrayList<String> empty_indices_altered = new ArrayList<>();
                empty_indices_altered.addAll(empty_indices);
                empty_indices_altered.remove(min_index_index);
                try {
                    return btBacktracks(ls_altered,empty_indices_altered,solveSeed);
                }
                catch (NoSolution ns){
                    //do nothing
                }
            }
        }
        backtracks++;
        throw new NoSolution(); //must be no solutions in children. return ls representing this.
    }
    public static LatinSquare solve(LatinSquare ls, int solveSeed) throws NoSolution{
        ArrayList<String> emptyIndices = new ArrayList<>();
        for (int i = 0; i < ls.getSize(); i++) {
            for (int j = 0; j < ls.getSize(); j++){
                if (ls.getElement(i,j) == 0) {
                    emptyIndices.add(i + "," + j);
                }
            }
        }
        LatinSquare solved = btSearch(ls,emptyIndices,solveSeed);
        return solved;
    }
    /**
     * Runs the back-track search algorithm as described by O. Carvalho.
     * ls: the latin square to the run the algorithm on.
     * empty_indices: the indices of empty cells. of the form ["i,j",...]
     */
    private static LatinSquare btSearch(LatinSquare ls, ArrayList<String> empty_indices, int solveSeed) throws NoSolution{
        if (empty_indices.size() == 0)
            return ls;
        //long selectionStartTime = System.nanoTime();
        int min_decisions = 10000;
        String min_index = empty_indices.get(0);
        int min_index_index = 0;
        ArrayList<String> tiedForMinList = new ArrayList<>();
        for (int i = 0; i < empty_indices.size(); i++){
            int decisionsHere = ls.getSize() - ls.uniqueNeighbors(getRowString(empty_indices.get(i)),getColString(empty_indices.get(i))).size;//neighbors[getRowString(empty_indices.get(i))][getColString(empty_indices.get(i))];
            if (decisionsHere <= min_decisions) {
                if (decisionsHere < min_decisions){
                    min_decisions = decisionsHere;
                    min_index = empty_indices.get(i);
                    min_index_index = i;
                    tiedForMinList = new ArrayList<>();
                    tiedForMinList.add(min_index);
                }
                else {
                    tiedForMinList.add(empty_indices.get(i));
                }
            }
        }
        //randomize choice if more than 1 variable tied for min choices.
        if (tiedForMinList.size() > 1) {
            int rand = (solveSeed % tiedForMinList.size());
            min_index = tiedForMinList.get(rand);
            min_index_index = empty_indices.indexOf(min_index);
        }
        //System.out.println("Selection time: " + (System.nanoTime() - selectionStartTime));
        if (min_decisions == 0) {
            //System.out.println("min dec is 0. index is " + min_index);
            //System.out.println(ls);
            throw new NoSolution();
        }
        NeighborSet uniqueNeighborsAtNextChoice = ls.uniqueNeighbors(getRowString(min_index),getColString(min_index));
        List<Integer> values = BigSquares.scrambleToList(ls.getValues(),solveSeed);
        for (int val = 0; val < ls.getSize(); val++) {//run btsearch for all legal values of min_index. if none work, backtrack.
            if (uniqueNeighborsAtNextChoice.neighbors[ls.valueToIndex.get(values.get(val))] == 0) { //if its a legal value choice
                //long copyStartTime = System.nanoTime();
                LatinSquare ls_altered = lscopy(ls);
                //`System.out`.println(ls_altered);
                //System.out.println("----------------");
                ls_altered.changeValue(getRowString(min_index),getColString(min_index),values.get(val));
                ArrayList<String> empty_indices_altered = new ArrayList<String>();
                empty_indices_altered.addAll(empty_indices);
                empty_indices_altered.remove(min_index_index);
                //System.out.println("copy time: " + (System.nanoTime() - copyStartTime));
                try {
                    LatinSquare resultOfSearch = btSearch(ls_altered,empty_indices_altered,solveSeed);
                    return resultOfSearch;
                }
                catch (NoSolution ns){
                    //do nothing
                }
            }
        }
        throw new NoSolution(); //must be no solutions in children.
    }


    public static String[] removeIndex(String[] arr, int i){
        String[] newArr = new String[arr.length - 1];
        boolean seenIt = false;
        for (int m = 0; m < arr.length - 1; m++) {
            if (!(m == i)) {
                if (!seenIt)
                    newArr[m] = arr[m];
                else
                    newArr[m - 1] = arr[m];
            }
            else
                seenIt = true;
        }
        return newArr;
    }
    public static int getRowString(String s) {return Integer.parseInt(s.substring(0,s.indexOf(',')));}
    public static int getColString(String s) {return Integer.parseInt(s.substring(s.indexOf(',') + 1));}

    public static LatinSquare lscopy(LatinSquare ls) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < ls.getSize(); i++){
            for (int j = 0; j < ls.getSize(); j++){
                s.append(ls.getElement(i,j)).append(" ");
            }
        }
        StringBuilder m = new StringBuilder();
        for (int i = 0; i < ls.getSize(); i++){
            m.append(ls.getValues()[i]).append(" ");
        }
        return new LatinSquare(ls.getSize(),s.toString(),m.toString());
    }
    public static Square3D lscopy3D(Square3D ls) {
        return (Square3D) DeepCopy.copy(ls);
    }

    /*public static LatinSquare btBacktracks(LatinSquare ls, EmptySet empty_indices, int solveSeed) throws NoSolution{
        if (empty_indices.size() == 0)
            return ls;
        //long selectionStartTime = System.nanoTime();
        Map.Entry<String,Integer> entry = empty_indices.remove();
        String min_index = entry.getKey();
        if (entry.getValue() == 0) {
            backtracks++;
            throw new NoSolution();
        }
        ArrayList<Integer> uniqueNeighborsAtNextChoice = ls.uniqueNeighbors(Solve.getRowString(min_index),Solve.getColString(min_index));
        List<Integer> values = BigSquares.scrambleToList(ls.getValues(),solveSeed);
        for (int val = 0; val < ls.getSize(); val++) {//run btsearch for all legal values of min_index. if none work, backtrack.
            if (!uniqueNeighborsAtNextChoice.contains(values.get(val))) { //if its a legal value choice
                //long copyStartTime = System.nanoTime();
                LatinSquare ls_altered = lscopy(ls);
                //if (Math.random() < 0.001)
                ls_altered.changeValue(Solve.getRowString(min_index),Solve.getColString(min_index),values.get(val));
                EmptySet empty_indices_altered = (EmptySet) DeepCopy.copy(empty_indices);
                try {
                    return btBacktracks(ls_altered,empty_indices_altered,solveSeed);
                }
                catch (NoSolution ns){
                    //do nothing
                }
            }
        }
        backtracks++;
        throw new NoSolution(); //must be no solutions in children. return ls representing this.
    }*/
    /**
     * Returns the solved version of the latin square if it is possible, or [-1] otherwise.
     */
    /*public static LatinSquare solve(LatinSquare ls, int solveSeed) throws NoSolution{
        EmptySet emptyIndices = new EmptySet(ls,solveSeed);
        return btSearch(ls,emptyIndices, solveSeed);
    }*/

    /**
     * Runs the back-track search algorithm as described by O. Carvalho.
     * ls: the latin square to the run the algorithm on.
     * empty_indices: the indices of empty cells. of the form ["i,j",...]
     */
    /*private static LatinSquare btSearch(LatinSquare ls, EmptySet empty_indices, int solveSeed) throws NoSolution{
        if (empty_indices.size() == 0)
            return ls;
        //long selectStartTime = System.nanoTime();
        Map.Entry<String,Integer> entry = empty_indices.remove();
        String min_index = entry.getKey();
        if (entry.getValue() == 0) {
            throw new NoSolution();
        }
        ArrayList<Integer> uniqueNeighborsAtNextChoice = ls.uniqueNeighbors(Solve.getRowString(min_index),Solve.getColString(min_index));
        List<Integer> values = BigSquares.scrambleToList(ls.getValues(),solveSeed);
        for (int val = 0; val < ls.getSize(); val++) {//run btsearch for all legal values of min_index. if none work, backtrack.
            if (!uniqueNeighborsAtNextChoice.contains(values.get(val))) { //if its a legal value choice
                LatinSquare ls_altered = lscopy(ls);
                ls_altered.changeValue(Solve.getRowString(min_index),Solve.getColString(min_index), values.get(val));
                EmptySet empty_indices_altered = new EmptySet(ls_altered,solveSeed,empty_indices);
                try {
                    return btSearch(ls_altered,empty_indices_altered,solveSeed);
                }
                catch (NoSolution ns){
                    //do nothing
                }
            }
        }
        throw new NoSolution(); //must be no solutions in children.
    }*/

    public  static LatinSquare solve3D(Square3D ls, int solveSeed) throws NoSolution{
        Square3D.EmptySet3D empty = new Square3D.EmptySet3D(ls,solveSeed);
        LatinSquare solved = btSearch3D(ls,empty,solveSeed);
        return solved;
    }

    public static LatinSquare btSearch3D(Square3D ls, Square3D.EmptySet3D emptyIndices, int solveSeed) throws NoSolution{
        if (emptyIndices.size == 0) return ls;
        long propStartTime = System.nanoTime();
        ArrayList<Index> tiedForMinList = ls.propagate(emptyIndices);
        System.out.println("propagation time: " + (System.nanoTime()-propStartTime));
        if (tiedForMinList.size() == 0) return ls;
        Index min_index = tiedForMinList.get(0);
        if (tiedForMinList.size() > 1){
            int rand = (int)(Math.random() * 1000000) % tiedForMinList.size();
            min_index = tiedForMinList.get(rand);
        }

        NeighborSet uniqueNeighborsAtNextChoice = ls.uniqueNeighbors(min_index.row, min_index.col);
        List<Integer> values = BigSquares.scrambleToList(ls.getValues(),solveSeed);
        for (int val = 0; val < ls.getSize(); val++) {//run btsearch for all legal values of min_index. if none work, backtrack.
            if (uniqueNeighborsAtNextChoice.neighbors[ls.valueToIndex.get(values.get(val))] == 0) { //if its a legal value choice
                long copyStartTime = System.nanoTime();
                Square3D ls_altered = lscopy3D(ls);
                System.out.println(ls_altered);
                //System.out.println("----------------");
                ls_altered.changeValue(min_index.row, min_index.col, values.get(val));
                Square3D.EmptySet3D empty_indices_altered = (Square3D.EmptySet3D) DeepCopy.copy(emptyIndices);//scuffed
                empty_indices_altered.update(min_index.row, min_index.col, values.get(val));
                System.out.println("copy time: " + (System.nanoTime() - copyStartTime));
                try {
                    return btSearch3D(ls_altered,empty_indices_altered,solveSeed);
                }
                catch (NoSolution ns){
                    //do nothing
                }
            }
        }
        backtracks++;
        throw new NoSolution(); //must be no solutions in children.
    }
}
