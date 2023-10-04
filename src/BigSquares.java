import java.util.*;

/**
 * Builds and stores specific big Latin squares.
 */
public class BigSquares {
    public static LatinSquare buildHeavyTail(int n, int genSeed) {
        LatinSquare[][] arr = new LatinSquare[n+1][n+1];
        for (int i = 0; i < n; i++) { //builds the first n rows of the n + 1 sized matrix
            for (int j = 0; j < n; j++) { //builds the first n cols
                int d = diagonalValue(i,j,n+1);
                if (d == -1){
                    StringBuilder s = new StringBuilder();
                    for (int a = 0; a < (n + 1) * (n + 1); a++) { //since we are on the special -1 diag, make this entry all 0s.
                        s.append("0 ");
                    }
                    LatinSquare emptyls = new LatinSquare(n + 1, s.toString());
                    arr[i][j] = emptyls;
                }
                else {
                    if (d >= 0){
                        StringBuilder s = new StringBuilder();
                        for (int a = 0; a < n + 1; a++) {
                            s.append((n + 3) + (d * (n + 1)) + a).append(" ");
                        }
                        String vals = scramble(s.toString(),genSeed);
                        StringBuilder y = new StringBuilder();
                        y.append(vals);
                        for (int a = n + 1; a < (n + 1) * (n + 1); a++) {
                            y.append("0 ");
                        }
                        try {
                            LatinSquare ls = new LatinSquare(n + 1, y.toString(), vals);
                            LatinSquare solved = Solve.solve(ls,genSeed);
                            arr[i][j] = solved;
                        }
                        catch (NoSolution ns) {
                        }
                    }
                    else {
                        StringBuilder s = new StringBuilder();
                        for (int a = 0; a < n + 1; a++) {
                            s.append((n * n) + n + 1 + ((d+2) * (n + 1)) + a).append(" ");
                        }
                        String vals = scramble(s.toString(),genSeed);
                        s = new StringBuilder();
                        s.append(vals);
                        for (int a = n + 1; a < (n + 1) * (n + 1); a++) {
                            s.append("0 ");
                        }
                        try {
                            LatinSquare ls = new LatinSquare(n + 1, s.toString(), vals);
                            LatinSquare solved = Solve.solve(ls,genSeed);
                            arr[i][j] = solved;
                        }
                        catch (NoSolution ns) {
                        }
                    }
                }
            }
        }
        //Now deal with last row except bottom right
        for (int i = 0; i < n; i++) {
            StringBuilder s = new StringBuilder();
            s.append(i + 1).append(" ");
            for (int a = 0; a < n; a++) {
                s.append((n + 3) + (i * (n + 1)) + a).append(" ");
            }
            String vals = scramble(s.toString(),genSeed);
            s = new StringBuilder();
            s.append(vals);
            for (int a = n + 1; a < (n + 1) * (n + 1); a++) {
                s.append("0 ");
            }
            try {
                LatinSquare ls = new LatinSquare(n + 1, s.toString(), vals);
                LatinSquare solved = Solve.solve(ls,genSeed);
                solved.findAndRemove(i+1);
                arr[n][i] = solved;
            }
            catch (NoSolution ns) {
                //System.out.println(s.toString());
                //System.out.println(vals);
                //System.out.println(5 + " " + n + " " + i);
                System.err.println("5: Theorem failed or bug.");
            }
        }
        //Now deal with last col
        for (int j = 0; j < n; j++) {
            StringBuilder s = new StringBuilder();
            s.append(n - j).append(" ");
            for (int a = 0; a < n; a++) {
                s.append((n + 3) + (j * (n + 1)) + a).append(" ");
            }
            String vals = scramble(s.toString(),genSeed);
            s = new StringBuilder();
            s.append(vals);
            for (int a = n + 1; a < (n + 1) * (n + 1); a++) {
                s.append("0 ");
            }
            try {
                LatinSquare sq = new LatinSquare(n + 1, s.toString(), vals);
                //System.out.println(sq.toString() + vals + "." );
                LatinSquare ls = Solve.solve(sq,genSeed);
                ls.findAndRemove(j);
                arr[j][n] = ls;
            }
            catch (NoSolution ns) {
                //System.out.println(s.toString());
                //System.out.println(vals);
                //System.out.println(1 + " " + j + " " + n);
                System.err.println("1: Theorem failed or bug.");
            }
        }
        //Now deal with bottom right
        StringBuilder s = new StringBuilder();
        s.append(n + 1).append(" ");
        s.append(n+2).append(" ");
        for (int a = 0; a < n - 1; a++) {
            s.append(2*n + 3 + (a * (n + 1))).append(" ");
        }
        String vals = scramble(s.toString(),genSeed);
        s = new StringBuilder();
        s.append(vals);
        for (int a = n + 1; a < (n + 1) * (n + 1); a++) {
            s.append("0 ");
        }
        try {
            LatinSquare ls = new LatinSquare(n + 1, s.toString(), vals);
            LatinSquare solved = Solve.solve(ls,genSeed);
            arr[n][n] = solved;
        }
        catch (NoSolution ns) {
            System.err.println("2: Theorem failed or bug.");
        }
        return new LatinSquare(n + 1, n + 1,arr);
    }

    /**
     * Trying to make Carvalho's model more heavy-tailed.
     * @param n the size parameter
     * @param genSeed the seed for generation
     * @param numHoles number of empty cells in the bottom row of squares
     * @return an alternate heavy-tailed latin square
     */
    public static LatinSquare buildAlternate(int n, int genSeed, int numHoles, boolean twoPerSquare){
        LatinSquare[][] arr = new LatinSquare[n+1][n+1];
        for (int i = 0; i < n; i++) { //builds the first n rows of the n + 1 sized matrix
            for (int j = 0; j < n; j++) { //builds the first n cols
                int d = diagonalValue(i,j,n+1);
                if (d == -1){
                    StringBuilder s = new StringBuilder();
                    for (int a = 0; a < (n + 1) * (n + 1); a++) { //since we are on the special -1 diag, make this entry all 0s.
                        s.append("0 ");
                    }
                    LatinSquare emptyls = new LatinSquare(n + 1, s.toString());
                    arr[i][j] = emptyls;
                }
                else {
                    if (d >= 0){
                        StringBuilder s = new StringBuilder();
                        for (int a = 0; a < n + 1; a++) {
                            s.append((n + 3) + (d * (n + 1)) + a).append(" ");
                        }
                        String vals = scramble(s.toString(),genSeed);
                        StringBuilder y = new StringBuilder();
                        y.append(vals);
                        for (int a = n + 1; a < (n + 1) * (n + 1); a++) {
                            y.append("0 ");
                        }
                        try {
                            LatinSquare ls = new LatinSquare(n + 1, y.toString(), vals);
                            LatinSquare solved = Solve.solve(ls,genSeed);
                            arr[i][j] = solved;
                        }
                        catch (NoSolution ns) {
                        }
                    }
                    else {
                        StringBuilder s = new StringBuilder();
                        for (int a = 0; a < n + 1; a++) {
                            s.append((n * n) + n + 1 + ((d+2) * (n + 1)) + a).append(" ");
                        }
                        String vals = scramble(s.toString(),genSeed);
                        s = new StringBuilder();
                        s.append(vals);
                        for (int a = n + 1; a < (n + 1) * (n + 1); a++) {
                            s.append("0 ");
                        }
                        try {
                            LatinSquare ls = new LatinSquare(n + 1, s.toString(), vals);
                            LatinSquare solved = Solve.solve(ls,genSeed);
                            arr[i][j] = solved;
                        }
                        catch (NoSolution ns) {
                        }
                    }
                }
            }
        }
        //Now deal with last row except bottom right
        for (int i = 0; i < n; i++) {
            StringBuilder s = new StringBuilder();
            s.append(i + 1).append(" ");
            for (int a = 0; a < n; a++) {
                s.append((n + 3) + (i * (n + 1)) + a).append(" ");
            }
            String vals = scramble(s.toString(),genSeed);
            s = new StringBuilder();
            s.append(vals);
            for (int a = n + 1; a < (n + 1) * (n + 1); a++) {
                s.append("0 ");
            }
            try {
                LatinSquare ls = new LatinSquare(n + 1, s.toString(), vals);
                LatinSquare solved = Solve.solve(ls,genSeed);
                if (i < numHoles) {
                    solved.findAndRemove(i + 1);
                    if (twoPerSquare)
                        if (!(solved.getElement(0, 0) == 0))
                            solved.changeValue(0, 0, 0);
                        else solved.changeValue(0, 1, 0);
                }
                arr[n][i] = solved;
            }
            catch (NoSolution ns) {
                //System.out.println(s.toString());
                //System.out.println(vals);
                //System.out.println(5 + " " + n + " " + i);
                System.err.println("5: Theorem failed or bug.");
            }
        }
        //Now deal with last col
        for (int j = 0; j < n; j++) {
            StringBuilder s = new StringBuilder();
            s.append(n - j).append(" ");
            for (int a = 0; a < n; a++) {
                s.append((n + 3) + (j * (n + 1)) + a).append(" ");
            }
            String vals = scramble(s.toString(),genSeed);
            s = new StringBuilder();
            s.append(vals);
            for (int a = n + 1; a < (n + 1) * (n + 1); a++) {
                s.append("0 ");
            }
            try {
                LatinSquare sq = new LatinSquare(n + 1, s.toString(), vals);
                //System.out.println(sq.toString() + vals + "." );
                LatinSquare ls = Solve.solve(sq,genSeed);
                ls.findAndRemove(j);
                arr[j][n] = ls;
            }
            catch (NoSolution ns) {
                //System.out.println(s.toString());
                //System.out.println(vals);
                //System.out.println(1 + " " + j + " " + n);
                System.err.println("1: Theorem failed or bug.");
            }
        }
        //Now deal with bottom right
        StringBuilder s = new StringBuilder();
        s.append(n + 1).append(" ");
        s.append(n+2).append(" ");
        for (int a = 0; a < n - 1; a++) {
            s.append(2*n + 3 + (a * (n + 1))).append(" ");
        }
        String vals = scramble(s.toString(),genSeed);
        s = new StringBuilder();
        s.append(vals);
        for (int a = n + 1; a < (n + 1) * (n + 1); a++) {
            s.append("0 ");
        }
        try {
            LatinSquare ls = new LatinSquare(n + 1, s.toString(), vals);
            LatinSquare solved = Solve.solve(ls,genSeed);
            arr[n][n] = solved;
        }
        catch (NoSolution ns) {
            System.err.println("2: Theorem failed or bug.");
        }
        return new LatinSquare(n + 1, n + 1,arr);
    }

    /**
     * Cells on main diag have d = 0.
     * Top left cell has d = -(size - 1) = -N
     * Bottom Right cell has d = (size - 1) = N
     */
    private static int diagonalValue(int row, int col, int size) {
        return row + col - (size - 1);
    }

    public static String scramble(String s, int seed){
        List<Integer> intList = new ArrayList<>();
        int previousSpace = -1;
        for (int i = 0; i < s.length(); i++){
            if (s.charAt(i) == ' '){
                intList.add(Integer.parseInt(s.substring(previousSpace + 1,i)));
                previousSpace = i;
            }
        }
        Collections.shuffle(intList,new Random(seed));
        StringBuilder b = new StringBuilder();
        for (Integer num : intList){
            b.append(num + " ");
        }
        return b.toString();
    }

    public static List<Integer> scrambleToList(int[] arr, int seed){
        List<Integer> intList = new ArrayList<>();
        for (int i = 0; i < arr.length; i++){
            intList.add(arr[i]);
        }
        Collections.shuffle(intList,new Random(seed));
        return intList;
    }
}
