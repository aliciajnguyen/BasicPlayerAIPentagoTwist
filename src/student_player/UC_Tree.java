package student_player;

import java.util.Collections;
import java.util.Comparator;

/*The upper confidence tree calculations for our MCTree Seach Tree
*
* External Code Sources (also in Sources Cited): https://www.baeldung.com/java-monte-carlo-tree-search
*/
public class UC_Tree {
    public static double calcUCTValue(int totalVisit, double nodeWinScore, int nodeVisit) {
      if (nodeVisit == 0) {
        return Integer.MAX_VALUE;
      }
      double UCTValue = ((double) nodeWinScore / (double) nodeVisit) 
        + 1.41 * Math.sqrt(Math.log(totalVisit) / (double) nodeVisit);

      return UCTValue;
    }

    public static MC_Node findBestNodeWithUCT(MC_Node node) {
      int parentVisit = node.getState().getVisitCount();

      for (MC_Node n : node.getChildren()){
        n.setUCTValue(calcUCTValue(parentVisit, n.getState().getWinScore(), n.getState().getVisitCount()));
      }

      //https://www.baeldung.com/java-8-comparator-comparing
      MC_Node bestNode = Collections.max(node.getChildren(), Comparator.comparing(c -> c.getUCTValue()));
      
      return bestNode;
    }
}