import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("BackTracks6_1.csv.txt");
        FileWriter w = new FileWriter(file);
        for (int a = 6; a < 7; a++) {
            int[][] bts = countBacktracks(a);
            w.write( "" + a +'\n' + '\n');
            for (int i = 0; i < bts[0].length;i++){
                w.write(bts[0][i] + ",");
            }
            System.out.println(Arrays.toString(bts[0]));
            w.write("" + '\n' + '\n');
            for (int i = 0; i < bts[0].length;i++){
                w.write(bts[1][i] + ",");
            }
            w.write("" + '\n' + '\n');
            for (int i = 0; i < bts[0].length;i++){
                w.write(bts[2][i] + ",");
            }
            w.write('\n' + "------------------" + '\n');
        }
        w.close();
    }

    public static int[][] countBacktracks(int size){
        int[] backtracks = new int[100];
        int[] genSeeds = new int[backtracks.length];
        int[] solveSeeds = new int[backtracks.length];
        for (int i = 0; i < 100; i++){
            System.out.println(i);
            int rand = (int)(Math.random() * 100000);
            genSeeds[i] = rand;
            LatinSquare ht = BigSquares.buildHeavyTail(6,rand);
            Solve.backtracks = 0;
            try{
                int random = (int)(Math.random() * 100000);
                solveSeeds[i] = random;
                ArrayList<String> emptyIndices = new ArrayList<>();
                for (int k = 0; k < ht.getSize(); k++) {
                    for (int j = 0; j < ht.getSize(); j++){
                        if (ht.getElement(k,j) == 0) {
                            emptyIndices.add(k + "," + j);
                        }
                    }
                }
                System.out.println(Solve.btBacktracks(ht,emptyIndices,random));
                backtracks[i] = Solve.backtracks;
            }
            catch (NoSolution ns){
                System.err.println("ht has no solution?");
            }
        }
        return new int[][] {backtracks,genSeeds,solveSeeds};
    }
}
