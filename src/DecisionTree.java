/*
import java.util.ArrayList;

*/
/**
 * Represents the randomly-generated decision tree of a given latin square.
 *//*

public class DecisionTree {
    DecisionNode root;
    public DecisionTree(LatinSquare ls) {
        int min_decisions = 10000;
        String min_index = "0,0";
        for (int i = 0; i < ls.getSize(); i++) {
            for (int j = 0; j < ls.getSize(); j++)
                if (ls.getElement(i,j) == 0) {
                    int decisionsHere = ls.getSize() - uniqueNeighbors(ls,i,j).size();
                    if (decisionsHere < min_decisions) {
                        min_decisions = decisionsHere;
                        min_index = i + "," + j;
                    }
                }
        }
        int rlv = chooseRandomLegalValue(ls,min_index);
        LatinSquare ls_altered = ls;
        ls_altered.changeValue(Integer.parseInt(min_index.substring(0,min_index.indexOf(','))),
                Integer.parseInt(min_index.substring(min_index.indexOf(',') + 1)),rlv);
        root = new DecisionNode(min_index,value,buildCompleteChildArray(ls_altered,)
        root = new DecisionNode("root",-1,completeChildArray(ls));
    }


    */
/**
     * Builds the child array of the root of the tree. Represents all possible decisions made to solve
     * this latin square ls.
     * empty_indices: meant to reduce search time since we're using arrays. Of the form
     *                      ["i,j", "i,j",...]
     *//*

    private DecisionNode[] completeChildArray(LatinSquare ls,String[] empty_indices){
        DecisionNode[] root_children = new DecisionNode[empty_indices.length];
        for (String index : empty_indices) {
            LatinSquare ls_altered = ls;
            //ls_altered.changeValue();
        }
        return root_children;
    }
}
*/
