import java.io.Serializable;

/**
 * Represents a tile in the latin square. Used to efficiently implement the select algorithm with a treeset and comparator.
 */
public class Index implements Serializable {
    int row;
    int col;

    public Index(int row, int col){
        this.row = row;
        this.col = col;
    }

    public boolean equals(Object other){
        Index i = (Index)other;
        if (row == i.row && col == i.col) return true;
        return false;
    }

    public String toString(){
        return "Index " + row + " " + col;
    }
}
