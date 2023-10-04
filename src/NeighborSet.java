import java.util.Arrays;

/**
 * Represents the neighbor set of some index.
 */
public class NeighborSet {
    int[] neighbors;
    int size;
    public NeighborSet(int[] neighbors,int size){
        this.neighbors = neighbors;
        this.size = size;
    }

    public String toString(){
        return "" + size + " " + Arrays.toString(neighbors);
    }
}
