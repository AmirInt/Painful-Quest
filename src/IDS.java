import java.util.ArrayList;

public class IDS {

    private final Environment environment;

    public IDS(Environment environment) {
        this.environment = environment;
    }

    //This method finds and return the path that butter plate should pass if exist otherwise it returns null
    public Path searchPlate(Node start, Node goal, int limit) {
        for(int i = 0; i <= limit; i++) {
            boolean goalTest = limitedDFS(start, goal, limit);
            if (goalTest) {
                return new Path(goal);
            }
        }
        return null;
    }

    //This method implements the limited version of DFS that should be called in IDS.
    //If we can reach the goal in the given limited depth it returns true otherwise it returns false
    private boolean limitedDFS(Node start, Node goal, int limit) {
       int depth = 0;
       ArrayList<Node> explored = new ArrayList<>();
       ArrayList<Node> fringe = new ArrayList<>();
       fringe.add(start);

       while (depth <= limit) {
           if (fringe.isEmpty())
               return false;
           Node expanded = fringe.remove(fringe.size()-1);
           depth = expanded.getDepth();
           if (depth == limit) {
               explored.add(expanded);
               continue;
           }
           ArrayList<Node> children = expanded.getNeighbours();
           for (Node child: children
                ) {
               child.setAncestor(expanded);
               child.setDepth(depth+1);
               if (child.equals(goal))
                   return true;
               if (!explored.contains(child))
                   fringe.add(child);
           }
           explored.add(expanded);
       }
       return false;
    }

}
