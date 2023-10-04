import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Represents an N X N Latin square.
 */
public class LatinSquare implements Cloneable, Serializable {

    protected int size; // The side-length of the square
    protected int[] values; // The set of values the square can hold. Cannot contain repeats. REQUIRED TO BE SORTED
    protected int[][] square; // The values currently in the square. Must not contain any repeats in
                            // a single row nor column.
    public HashMap<Integer,Integer> valueToIndex;

    /**
     * Creates a Latin square of size equal to "width" and whose state is determined by "square."
     * 0 < width
     * square must be of the form x_1 x_2 ... x_(width^2) where 0 <= x_i and
     * x_i = 0 represents an empty space.
     * Values is initialized to [1, 2, ... size]
     */
    public LatinSquare(int width, String elems) {
        size = width;
        values = new int[size];
        valueToIndex = new HashMap<>();
        for (int i = 1; i <= size; i++){
            values[i-1] = i;
            valueToIndex.put(i, i-1);
        }
        square = new int[size][size];
        String[] elements = elems.split(" ");
        for (int i = 0; i < width * width; i++) {
            square[i / width][i % width] = Integer.parseInt(elements[i]);
        }
    }

    /**
     * Same as previous constructor, but with arbitrary specified values. Vals of the form "a b c..."
     * Requires: vals are positive. number of vals = width.
     */
    public LatinSquare(int width, String elems, String vals) {
        size = width;
        values = new int[size];
        valueToIndex = new HashMap<>();
        String[] valsArray = vals.split(" ");
        for (int i = 0; i < size; i++){
            values[i] = Integer.parseInt(valsArray[i]);
            valueToIndex.put(Integer.parseInt(valsArray[i]),i);
        }
        square = new int[size][size];
        String[] elements = elems.split(" ");
        for (int i = 0; i < width * width; i++) {
            square[i / width][i % width] = Integer.parseInt(elements[i]);
        }
    }

    /**
     * Forms a LatinSquare out of an array of a^2 LatinSquares, each of whom have width b.
     * Requires: no latinsquares in the same row or column share values, and the total number of values
     * equals a * b.
     */
    public LatinSquare(int a, int b, LatinSquare[][] squares) {
        size = a * b;
        values = new int[size];
        valueToIndex = new HashMap<>();
        for (int i = 0; i < values.length; i++){
            values[i] = i+1;
            valueToIndex.put(i+1,i);
        }
        square = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                LatinSquare theSquareWeAreIn = squares[i/b][j/b];
                square[i][j] = theSquareWeAreIn.getElement(i % b, j % b);
            }
        }
    }

    /**
     * Checks if the following class invariant holds true.
     * For all elements of this latin square, if they have the same value, then they are not in the same
     * row, and they are not in the same column.
     *
     */
    public boolean classInv() {
        for (int i = 0; i < size; i++) {
            ArrayList<Integer> seenInRow = new ArrayList<>();
            ArrayList<Integer> seenInCol = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                if (seenInRow.contains(square[i][j]) && square[i][j] != 0) {
                    System.out.println("row repeat. row: " + i + " col: " + j + " val: " + square[i][j]);
                    return false;
                }
                seenInRow.add(square[i][j]);
                if (seenInCol.contains(square[j][i]) && square[j][i] != 0) {
                    System.out.println("col repeat. row: " + j + " col: " + i + " val: " + square[j][i]);
                    return false;
                }
                seenInCol.add(square[j][i]);
            }
        }
        return true;
    }

    /**
     * Checks if there are repeats in row i.
     * @return True if there are no repeats, and false otherwise.
     */
    public boolean repeat_in_row(int i) {
        ArrayList<Integer> seenInRow = new ArrayList<>();
        for (int j = 0; j < size; j++) {
            if (seenInRow.contains(square[i][j]) && square[i][j] != 0) return false;
            seenInRow.add(square[i][j]);
        }
        return true;
    }

    /**
     * Checks if there are repeats in col i.
     * @return True if there are no repeats, and false otherwise.
     */
    public boolean repeat_in_col(int j) {
        ArrayList<Integer> seenInCol = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (seenInCol.contains(square[i][j]) && square[i][j] != 0) return false;
            seenInCol.add(square[i][j]);
        }
        return true;
    }

    /**
     * Bijective function from [1, 2, ... size] to the values of this square.
     * @param num 1 <= num <= size
     */
    public int valHash(int num){
        return values[num - 1];
    }

    /**
     * Function from values of this square to [1, 2, ... size]
     * Returns -1 if not present in values.
     * @param val
     * @return
     */
    public int inverseValHash(int val){
        if (val <= size && values[val-1] == val) return val;
        int minIndex = 0;
        int maxIndex = size - 1;
        int index = (minIndex + maxIndex) / 2;
        while (true){
            System.out.println(Arrays.toString(values));
            if (values[index] == val) return index + 1;
            else if (minIndex == maxIndex) return -1;
            else if (val > values[index]){
                minIndex = index + 1;
                index = (minIndex + maxIndex) / 2;
            }
            else {
                maxIndex = index - 1;
                index = (minIndex + maxIndex) / 2;
            }
        }
    }

    public String toString() {
        String str = "";
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                str = str + square[i][j] + " ";
            str = str + '\n';
        }
        return str;
    }

    public int getSize() {
        return size;
    }

    public int getElement(int i, int j) {
        return square[i][j];
    }

    public int[] getValues() {
        int[] copy = Arrays.copyOf(values,values.length);
        return copy;
    }

    public void changeValue(int row, int col, int newVal) {
        square[row][col] = newVal;
    }

    public void findAndRemove(int value) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (square[i][j] == value) {
                    square[i][j] = 0;
                    return;
                }
            }
        }
    }

    /**
     * Returns the unique values in row i and col j of square ls.
     */
    public NeighborSet uniqueNeighbors(int row, int col) {
        int[] seenValues = new int[size]; //seenValues[i] == 1 indicates the value that hashes to i has been seen.
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (!(square[row][i]==0)) {
                if (!(seenValues[valueToIndex.get(square[row][i])]==1)) {
                    seenValues[valueToIndex.get(square[row][i])] = 1;
                    count++;
                }
            }
            if (!(square[i][col]==0)) {
                if (!(seenValues[valueToIndex.get(square[i][col])]==1)) {
                    seenValues[valueToIndex.get(square[i][col])] = 1;
                    count++;
                }
            }
        }
        return new NeighborSet(seenValues,count);
    }

    public boolean equals(LatinSquare other) {
        if (!(size == other.getSize())) return false;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                if (!(square[i][j] == other.getElement(i,j))) return false;
        }
        return true;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public ArrayList<String> buildSortedEmptyList(){
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++)
                if (getElement(i,j) == 0) {
                    list.add(i + "," + j);
                }
        }
        return list;
    }

    /**
     * Converts this LatinSquare to a pls-style file.
     */
    public void writePLS(String fileName) throws IOException {
        FileWriter w = new FileWriter(fileName);
        w.write("order " + size + '\n');
        for (int i = 0; i < size; i++){
            StringBuilder s = new StringBuilder();
            for (int j = 0; j < size; j++){
                /*if (square[i][j] <= 0 || square[i][j] > 10) {
                    if (square[i][j] > 100)
                        s.append("  " + (square[i][j] - 1));
                    else s.append("  " + (square[i][j] - 1));
                }
                else
                    s.append("   " + (square[i][j]-1));*/
                s.append("  " + (square[i][j]-1));
            }
            w.write(s.toString() + "\n");
        }
        w.close();
    }

    /**
     * Represents the empty vertices of a Latin Square.
     */
    public static class EmptySet implements Serializable {
        //private TreeMap<String,Integer> emptyIndices;
        protected LatinSquare ls;
        protected int seed;
        protected ArrayList<Index> emptyIndices;
        public int size;

        public EmptySet(LatinSquare ls, int solveSeed) {
            size = 0;
            this.ls = ls;
            emptyIndices = new ArrayList<>();
            for (int i = 0; i < ls.getSize(); i++) {
                for (int j = 0; j < ls.getSize(); j++) {
                    if (ls.getElement(i, j) == 0) {
                        emptyIndices.add(new Index(i,j));
                        size++;
                    }
                }
            }
        }

        public boolean remove(Index index){
            return emptyIndices.remove(index);
        }

        public void add(Index index){emptyIndices.add(index);}

    }
}
