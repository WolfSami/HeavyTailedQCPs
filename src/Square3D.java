import java.io.Serializable;
import java.util.ArrayList;

public class Square3D extends LatinSquare{
    private static int[][] rowColor; //rowColor[i][j] == k indicates that value j + 1 is in column k of row i.
    private static int[][] colColor; //colColor[i][j] == k indicates that value j + 1 is in row k of column i.
    public Square3D(int width, String elems) {
        super(width, elems);
        rowColor = new int[size][size];
        colColor = new int[size][size];
        String[] elements = elems.split(" ");
        for (int i = 0; i < width * width; i++) {
            int elem = Integer.parseInt(elements[i]);
            if (elem > 0) {
                System.out.println("building. \n" + i + " " + elem + '\n' + this);
                rowColor[i / width][elem - 1] = i % width + 1;
                colColor[i % width][elem - 1] = i / width + 1;
            }
        }
    }

    public Square3D(int width, String elems,String values){
        super(width,elems,values);
        rowColor = new int[size][size];
        colColor = new int[size][size];
        String[] elements = elems.split(" ");
        for (int i = 0; i < width * width; i++) {
            int elem = Integer.parseInt(elements[i]);
            if (elem > 0) {
                rowColor[i / width][Integer.parseInt(elements[i]) - 1] = i % width;
                colColor[i % width][Integer.parseInt(elements[i]) - 1] = i / width;
            }
        }
    }
    /**
     * Forms a Square3D from a LatinSquare.
     */
    public static Square3D formSquare(int width, LatinSquare ls){
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < width; i++){
            for (int j = 0; j < width; j++){
                s.append(ls.getElement(i,j)).append(" ");
            }
        }
        return new Square3D(width,s.toString());
    }

    @Override
    public void changeValue(int row, int col, int newVal) {
        int valAsIndex = valueToIndex.get(newVal);
        square[row][col] = newVal;
        rowColor[row][valAsIndex] = col + 1;
        colColor[col][valAsIndex] = row + 1;
    }

    @Override
    public void findAndRemove(int value) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (square[i][j] == value) {
                    square[i][j] = 0;
                    rowColor[i][value - 1] = 0;
                    colColor[j][value - 1] = 0;
                    return;
                }
            }
        }
    }


    public NeighborSet uniqueNeighbors_RowColor(int row, int col) {
        int[] seenValues = new int[size]; //seenValues[i] == 1 indicates the value that hashes to i has been seen.
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (!(rowColor[row][i]==0)) {
                if (!(seenValues[valueToIndex.get(rowColor[row][i])]==1)) {
                    seenValues[valueToIndex.get(rowColor[row][i])] = 1;
                    count++;
                }
            }
            if (!(rowColor[i][col]==0)) {
                if (!(seenValues[valueToIndex.get(rowColor[i][col])]==1)) {
                    seenValues[valueToIndex.get(rowColor[i][col])] = 1;
                    count++;
                }
            }
        }
        return new NeighborSet(seenValues,count);
    }
    public NeighborSet uniqueNeighbors_ColColor(int row, int col) {
        int[] seenValues = new int[size]; //seenValues[i] == 1 indicates the value that hashes to i has been seen.
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (!(colColor[row][i]==0)) {
                if (!(seenValues[valueToIndex.get(colColor[row][i])]==1)) {
                    seenValues[valueToIndex.get(colColor[row][i])] = 1;
                    count++;
                }
            }
            if (!(colColor[i][col]==0)) {
                if (!(seenValues[valueToIndex.get(colColor[i][col])]==1)) {
                    seenValues[valueToIndex.get(colColor[i][col])] = 1;
                    count++;
                }
            }
        }
        return new NeighborSet(seenValues,count);
    }
    /**
     * Repeatedly fills in tiles with only a single choice, using information from all 3 dimensions.
     * @return  tiedForMinList - the indices that are tied for the least number of choice after all propagation
     * is done. If it's empty, the square is solved.
     */
    public ArrayList<Index> propagate(EmptySet3D empty) throws NoSolution {
        System.out.println("propagate " + '\n' + this);

        ArrayList<Index> tiedForMinList = new ArrayList<>();
        int minDecisions = 10000;
        for (Index s : empty.emptyIndices){ //s.row is row, s.col is col, iterate over actual vals (not indices)
            int row = s.row;
            int col = s.col;
            NeighborSet neighbors = uniqueNeighbors(row,col);
            int n = neighbors.size;
            if (n >= size) {
                System.out.println(s);
                System.out.println(neighbors);
                throw new NoSolution();
            }
            else if (n == size - 1){
                System.out.println("neighbors of " + row + "," + col + ": " + neighbors);
                for (int val : values){
                    if ((neighbors.neighbors[valueToIndex.get(val)] == 0)){
                        square[row][col] = val;
                        rowColor[row][valueToIndex.get(val)] = col + 1;
                        colColor[col][valueToIndex.get(val)] = row + 1;
                        System.out.println(empty.update(row,col,val));
                        break;
                    }
                }
                return propagate(empty);
            }
            else if (n >= size - minDecisions){
                if (n > size - minDecisions){
                    tiedForMinList = new ArrayList<>();
                    tiedForMinList.add(s);
                    minDecisions = size - n;
                }
                else tiedForMinList.add(s);
            }
        }
        for (Index s : empty.emptyIndices_RowColor){ // in this case, s.row is the row, s.col is the index of the
                                                        // value, and we are iterating over the cols.
            int row = s.row;
            int val = values[s.col];
            NeighborSet neighbors = uniqueNeighbors_RowColor(row,s.col);
            int n = neighbors.size;
            if (n >= size) {
                System.out.println(this);
                throw new NoSolution();
            }
            else if (n == size - 1){
                System.out.println("neighbors of " + row + "," + s.col + ": " + neighbors);
                for (int col = 0; col < size; col++){
                    if ((neighbors.neighbors[col] == 0)){
                        square[row][col] = val;
                        rowColor[row][s.col] = col + 1; //s.col is the index of the value
                        colColor[col][s.col] = row + 1;
                        empty.update(row,col,val);
                        break;
                    }
                }
                return propagate(empty);
            }
        }
        /*for (Index s : empty.emptyIndices_ColColor){ //Now s.row is the col, s.col is the index of the value, and we
                                                        // are iterating over the rows
            int col = s.row;
            int val = values[s.col];
            NeighborSet neighbors = uniqueNeighbors_ColColor(col,s.col);
            int n = neighbors.size;
            if (n >= size) throw new NoSolution();
            else if (n == size - 1){
                System.out.println("neighbors of " + s.row + "," + s.col + ": " + neighbors);
                for (int row = 0; row < size; row++){
                    if (neighbors.neighbors[row] == 0){
                        square[row][col] = val;
                        rowColor[row][s.col] = col + 1;
                        colColor[col][s.col] = row + 1;
                        empty.update(row,col,val);
                        break;
                    }
                }
                return propagate(empty);
            }
        }*/
        return tiedForMinList;
    }

    @Override
    public String toString(){
        StringBuilder a = new StringBuilder();
        StringBuilder b = new StringBuilder();
        StringBuilder c = new StringBuilder();
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                a.append(square[i][j]).append(" ");
                b.append(rowColor[i][j]).append(" ");
                c.append(colColor[i][j]).append(" ");
            }
            a.append('\n');
            b.append('\n');
            c.append('\n');
        }
        return a.toString() + '\n' + b.toString() + '\n' + c;
    }

    /**
     * Represents the empty vertices of a Latin Square.
     */
    public static class EmptySet3D extends EmptySet implements Serializable {
        //private TreeMap<String,Integer> emptyIndices;
        protected ArrayList<Index> emptyIndices_RowColor;
        protected ArrayList<Index> emptyIndices_ColColor;
        public EmptySet3D(Square3D ls, int solveSeed) {
            super(ls,solveSeed);
            emptyIndices_RowColor = new ArrayList<>();
            emptyIndices_ColColor = new ArrayList<>();
            for (int i = 0; i < ls.getSize(); i++) {
                for (int j = 0; j < ls.getSize(); j++) {
                    if (rowColor[i][j] == 0) {
                        emptyIndices_RowColor.add(new Index(i,j));
                    }
                    if (colColor[i][j] == 0) {
                        emptyIndices_ColColor.add(new Index(i,j));
                    }
                }
            }
        }

        public boolean update(int row, int col, int val){
            System.out.println("update " + row + " " + col + " " + val);
            System.out.println("empty: " + emptyIndices.toString());
            System.out.println(ls);
            int valAsIndex = ls.valueToIndex.get(val);
            boolean b1 = emptyIndices.remove(new Index(row,col));
            boolean b2 = emptyIndices_RowColor.remove(new Index(row,valAsIndex));
            boolean b3 = emptyIndices_ColColor.remove(new Index(col,valAsIndex));
            return ((b1 && b2) && b3);
        }

        public void add(Index index){emptyIndices.add(index);}

    }
}
