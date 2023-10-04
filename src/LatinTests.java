import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
public class LatinTests {
    public static void main(String[] args) {
        //squareConstructorTest1();
        //latinInvariantTest1();
        //squareAltConstructorTest1();
        //test4();
        //getRowStringTest1();
        //solveTest1();
        //findAndRemoveTest1();
        //removeIndexTest1();
        //neighborsTest();
        //changeValueTest();
        //bigSquaresTest();
        //bigSolveTest();
        //reallyBigSolve();
        //reallyBigSolve2();
        //bigSolveTest2();
        //solve10test1();
        //seedTest();
        //scrambleTest();
        //emptySetRandomTest();
        plstest();
        //alternateHTest();
        //listTestJunk();
        //valHashTest();
        //square3DTest();
    }
    private static LatinSquare ls1 = new LatinSquare(3,"1 2 3 3 1 2 2 3 1");
    private static LatinSquare ls2 = new LatinSquare(4,"1 2 3 4 1 2 3 4 1 2 3 4 1 2 3 4 1 2 3 4 ");
    private static LatinSquare ls3 = new LatinSquare(4, "0 0 1 3 2 0 0 1 4 1 0 0 0 2 3 4");
    private static LatinSquare ls4 = new LatinSquare(4,"3 0 0 0 0 3 1 0 0 4 0 0 2 0 0 0");
    private static LatinSquare ls5 = new LatinSquare(4,"0 0 0 3 0 2 0 0 2 3 0 0 4 0 0 0");
    private static LatinSquare ls6u = new LatinSquare(4,"0 0 0 0 4 0 2 3 0 3 0 0 0 1 0 0");
    private static LatinSquare ls7 = new LatinSquare(5,"0 0 2 0 0 0 0 4 0 3 0 2 3 0 0 2 0 0 0 0 0 5 0 0 0");
    private static void squareConstructorTest1() {
        LatinSquare ls = new LatinSquare(3,"1 2 3 3 1 2 2 3 1");
        System.out.println(ls.toString());
    }

    private static void plstest(){
        try {
            int rand = (int)(Math.random() * 100000);
            System.out.println(rand);
            LatinSquare ht = BigSquares.buildHeavyTail(9,rand);
            ht.writePLS("plstest.txt");
        }catch (IOException e){System.out.println("io exc " + e);}
    }

    private static void latinInvariantTest1() {
        System.out.println(ls1.classInv());
        System.out.println(ls2.classInv() + " " + ls2.repeat_in_col(2) + " " + ls1.repeat_in_col(0));
    }

    private static void squareAltConstructorTest1() {
        LatinSquare lsa = new LatinSquare(2,"1 2 2 1");
        LatinSquare lsb = new LatinSquare(2,"3 4 4 3");
        LatinSquare[][] arr = new LatinSquare[][] {{lsa, lsb},{lsb, lsa}};
        LatinSquare ls = new LatinSquare(2,2,arr);
        System.out.println(ls.toString());
    }

    private static void test4() {
        System.out.println(ls3.toString());
        System.out.println(ls3.classInv() + " " + ls3.repeat_in_col(0));
    }

    private static void listTestJunk(){
        ArrayList<Index> arr = new ArrayList<>();
        arr.add(new Index(1,1));
        arr.remove(new Index(1,1));
        System.out.println(arr.size());
    }


    private static void getRowStringTest1() {
        System.out.println(Solve.getRowString("43,12"));
        System.out.println(Solve.getColString("108,14"));
    }

    private static void solveTest1() {
        LatinSquare ls = new LatinSquare(3,"2 1 3 3 2 0 0 0 0");
        try {
            System.out.println(Solve.solve(ls7,41419).toString());
        }
        catch (NoSolution ns) {
            System.out.println("No soln.");
        }
    }

    private static void findAndRemoveTest1() {
        LatinSquare ls = new LatinSquare(3, "1 2 3 2 3 1 3 1 2");
        ls.findAndRemove(4);
        System.out.println(ls.toString());
    }

    private static void valHashTest(){
        Square3D ls = new Square3D(3, "1 2 3 0 0 0 0 2 2","1 2 3");
        System.out.println(ls.inverseValHash(2) + ", " + ls.valHash(1));
        Square3D ls2 = new Square3D(3, "0 0 0 0 0 0 0 0 0","1 14 18");
        System.out.println("14: " + ls2.inverseValHash(14));
        System.out.println("1: " + ls2.inverseValHash(1));
        System.out.println("18: " + ls2.inverseValHash(18));
        Square3D ls3 = new Square3D(4,"0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0","3 5 7 9");
        System.out.println("3: " + ls3.inverseValHash(3));
        System.out.println("5: " + ls3.inverseValHash(5));
        System.out.println("7: " + ls3.inverseValHash(7));
        System.out.println("9: " + ls3.inverseValHash(9));
    }

    private static void removeIndexTest1() {
        String[] arr = new String[] {
                "aa","bb","cc","dd"};
        String[] r = Solve.removeIndex(arr,0);
        for (String s : r)
            System.out.println(s);
    }

    private static void alternateHTest() {
        LatinSquare alt = BigSquares.buildAlternate(5,123213,2,true);
        System.out.println(alt);
        try{
            System.out.println(Solve.solve(alt,5858));
        }
        catch (NoSolution ns){
            System.out.println("no sol");
        }
    }

    private static void changeValueTest() {
        LatinSquare ls = new LatinSquare(3,"2 1 3 3 2 0 0 0 0");
        ls.changeValue(1,2,1);
        System.out.println(ls.toString());
    }

    private static void bigSolveTest() {
        LatinSquare ls = new LatinSquare(6,"1 32 33 34 35 36 0 0 0 0 0 0 " +
                "0 0 0 0 0 0 " +
                "0 0 0 0 0 0 " +
                "0 0 0 0 0 0 " +
                "0 0 0 0 0 0 ","1 32 33 34 35 36 ");
        try{System.out.println(Solve.solve(ls,4441));}
        catch (NoSolution ns) {System.out.println("boop");}
        LatinSquare lss = new LatinSquare(5,"1 5 6 7 8 0 0 0 0 0 0 0 " +
                "0 0 0 0 0 0 " +
                "0 0 0 0 0 0 0","1 5 6 7 8");
        try{System.out.println(Solve.solve(lss,2451));}
        catch (NoSolution ns) {System.out.println("boo");}
    }

    private static void reallyBigSolve() {
        try {
            StringBuilder s = new StringBuilder();
            File file = new File("HeavyTailSqs.txt");
            FileReader fr = new FileReader(file);
            BufferedReader rdr = new BufferedReader(fr);
            for (int i = 0; i < 36; i++){
                s.append(rdr.readLine()).append(" ");
            }
            LatinSquare ls = new LatinSquare(36,s.toString());
            System.out.println(ls);
            System.out.println(Solve.solve(ls,6363));
        }
        catch (IOException exc){
            System.out.println("ioexc" + exc);
        }
        catch (NoSolution ns) {
            System.out.println("ns " + ns);
        }
    }

    private static void reallyBigSolve2(){
        LatinSquare ht = BigSquares.buildHeavyTail(6,(int)(Math.random() * 100000));
            System.out.println(ht);
            try{
                System.out.println(Solve.solve(ht,(int)(Math.random() * 100000)));
            }
            catch (NoSolution ns) {

            }
    }

    private static void bigSolveTest2() {
        int n = 5;
        String s = "19 20 21 22 23 24 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0";
        String vals = "19 20 21 22 23 24";
        for (int i = 0; i < 10; i++) {
            try {
                LatinSquare ls = new LatinSquare(n + 1, s, vals);
                Solve.solve(ls,43567);
                //Solve.solve(new LatinSquare(n+1,s,vals));
            } catch (NoSolution ns) {
                System.out.println("boop");
                break;
            }
        }
    }

    /*private static void emptySetRandomTest(){
        LatinSquare ht = BigSquares.buildHeavyTail(5,1500);
        System.out.println(ht);
        for (int i = 0; i < 10; i++){
            int seed = (int)(Math.random() * 100000);
            System.out.println("seed: " + seed);
            EmptySet empty = new EmptySet(ht,seed);
            for (int j = 0; j < 5; j++){
                String removed = empty.remove().getKey();
                System.out.println("removal num: " + i + " is " + removed.toString() + ". It has UNs: "
                        + ht.uniqueNeighbors(Solve.getRowString(removed),Solve.getColString(removed)).size());
            System.out.println();}
        }
    }*/
    private static void bigSquaresTest() {
        long[] times = new long[100];
        for (int i = 0; i < 1; i++) {
            try {
                long startTime = System.nanoTime();
                LatinSquare ht = BigSquares.buildHeavyTail(4,457);
                System.out.println(ht);
                LatinSquare solved = Solve.solve(ht,131);
                System.out.println(solved);
                long timeElapsed = System.nanoTime() - startTime;
                times[i] = timeElapsed / 1000000;
            }
            catch (NoSolution e) {
                System.out.println("ns");
            }
        }
        System.out.println(Arrays.toString(times));
    }
    private static void neighborsTest() {
        LatinSquare ls = new LatinSquare(3,"1 2 3 0 1 2 2 3 0");
        int[][] neighbors = new int[10][10];
        for (int i = 0; i < 3; i ++) {
            for (int j = 0; j < 3; j++){
                neighbors[i][j] = ls.uniqueNeighbors(i, j).size;
                System.out.print(neighbors[i][j] + " ");
        }
            System.out.println();
        }
        System.out.println(ls);
    }

    private static void firstRowTest() {
        for (int i = 0; i < 1000; i++) {
            ArrayList<Integer> intList = new ArrayList<>();
            while(intList.size() < 4) {
                int rand = (int)(Math.random() * 30);

            }
        }
    }

    private static void solve10test1() {
        StringBuilder s = new StringBuilder();
        s.append("1 2 3 4 5 6 7 8 9 10 ");
        for (int i = 0; i < 90; i++) {
            s.append("0 ");
        }
        LatinSquare ls = new LatinSquare(10,s.toString());
        try{
            System.out.println(Solve.solve(ls,131167));
        }
        catch (NoSolution ns){
            System.err.println("no solution.");
        }
    }

    private static void seedTest(){
        LatinSquare ht = BigSquares.buildHeavyTail(6,(int)(Math.random() * 100000));
        try{
            Solve.backtracks = 0;
            ArrayList<String> emptyIndices = new ArrayList<>();
            for (int k = 0; k < ht.getSize(); k++) {
                for (int j = 0; j < ht.getSize(); j++){
                    if (ht.getElement(k,j) == 0) {
                        emptyIndices.add(k + "," + j);
                    }
                }
            }
            Solve.btBacktracks(ht,emptyIndices,(int)(Math.random() * 100000));
            System.out.println(Solve.backtracks);
        }
        catch (NoSolution ns){
            System.err.println("no solution.");
        }
    }

    private static void scrambleTest() {
        String nums = "1 2 3 4 5 6 7 8 9 10";
        for (int i = 0; i < 20; i++){
            System.out.println(BigSquares.scramble(nums,(int)(Math.random() * 100000)));
        }
    }

    private static void square3DTest() {
        Square3D ls = new Square3D(3,"1 2 0 0 0 1 0 0 0");
        LatinSquare ht = BigSquares.buildHeavyTail(6,232323);
        try {
            //System.out.print(Solve.solve3D(ls, (int) (Math.random() * 1991)));
            Solve.backtracks = 0;
            Square3D square = Square3D.formSquare(49,ht);
            System.out.println(Solve.solve3D(square,(int) (Math.random() * 1991)));
            System.out.println(Solve.backtracks);
        }
        catch (NoSolution ns){
            System.out.println("no solution");
        }
    }
}
