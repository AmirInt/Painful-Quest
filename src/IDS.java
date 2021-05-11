import java.util.ArrayList;

public class IDS extends Search {

    public IDS(Environment environment) {
        super(environment);
    }

    private int limit = environment.getHeight() * environment.getWidth();

    //This method finds the path that robot should pass to arrive the butter if exist otherwise it returns null
    public Path searchRobot(Node start, Node end, int limit) {
        for (int i = 0; i <= limit; i++) {
            boolean goalTest = limitedDFS(start, end);
            if (goalTest) {
                return new Path(end);
            }
        }
        return null;
    }

    //This method implements the limited version of DFS that should be called in IDS.
    //If we can reach the goal in the given limited depth it returns true otherwise it returns false
    private boolean limitedDFS(Node start, Node end) {
        int depth = 0;
        ArrayList<Node> explored = new ArrayList<>();
        ArrayList<Node> fringe = new ArrayList<>();
        fringe.add(start);

        while (depth <= limit) {
            if (fringe.isEmpty())
                return false;
            Node expanded = fringe.remove(fringe.size() - 1);
            depth = expanded.getDepth();
            if (depth == limit) {
                explored.add(expanded);
                continue;
            }
            ArrayList<Node> children = expanded.getNeighbours();
            for (Node child : children
            ) {
                child.setAncestor(expanded);
                child.setDepth(depth + 1);
                if (child.equals(end))
                    return true;
                if (!explored.contains(child))
                    fringe.add(child);
            }
            explored.add(expanded);
        }
        return false;
    }

    //This method finds the paths from butter to goal
    public ArrayList<Path> searchPlate(Node start, Node goal) {

        ArrayList<Path> paths = new ArrayList<>();
        ArrayList<Node> explored = new ArrayList<>();
        ArrayList<Node> fringe = new ArrayList<>();
        int depth = 0;
        for (int i = 0; i <= limit; i++) {
            fringe.add(start);
            while (depth <= i) {
                if (fringe.isEmpty())
                    break;
                Node expanded = fringe.remove(fringe.size() - 1);
                while (expanded.equals(goal) && !fringe.isEmpty())
                    expanded = fringe.remove(fringe.size() - 1);
                if (fringe.isEmpty())
                    break;
                depth = expanded.getDepth();
                if (depth == limit) {
                    explored.add(expanded);
                    continue;
                }
                ArrayList<Node> children = expanded.getNeighbours();
                for (Node child : children
                ) {
                    child.setAncestor(expanded);
                    child.setDepth(depth + 1);
                    if (child.equals(goal)){
                        Path path = new Path(goal);
                        if (!paths.contains(path))
                            paths.add(path);
                    }
                    if (!explored.contains(child))
                        fringe.add(child);
                }
                explored.add(expanded);
            }
        }
        return paths;
    }
}
