import java.util.ArrayList;
import java.util.Objects;

public class Node {

    private int xCoordinate;
    private int yCoordinate;
    private int expense;
    private Node ancestor;
    private Node east;
    private Node north;
    private Node west;
    private Node south;
    private boolean obstacle;

    public Node(int x, int y) {
        xCoordinate = x;
        yCoordinate = y;
        expense = 0;
        east = null;
        north = null;
        west = null;
        south = null;
    }

    public void setEast(Node east) {
        this.east = east;
    }

    public void setNorth(Node north) {
        this.north = north;
    }

    public void setWest(Node west) {
        this.west = west;
    }

    public void setSouth(Node south) {
        this.south = south;
    }

    public void setAncestor(Node ancestor) {
        this.ancestor = ancestor;
    }

    public void setExpense(int expense) {
        this.expense = expense;
    }

    public void setObstacle(boolean obstacle) {
        this.obstacle = obstacle;
    }

    public Node getAncestor() {
        return ancestor;
    }

    public int getX() {
        return xCoordinate;
    }

    public int getY() {
        return yCoordinate;
    }

    public int getExpense() {
        return expense;
    }

    public Node getEast() {
        return east;
    }

    public Node getWest() {
        return west;
    }

    public Node getNorth() {
        return north;
    }

    public Node getSouth() {
        return south;
    }

    public Node getOppositeOf(Node node) {
        if (node.equals(east))
            return west;
        if (node.equals(north))
            return south;
        if (node.equals(west))
            return east;
        return north;
    }

    public ArrayList<Node> getNeighbours() {
        ArrayList<Node> neighbours = new ArrayList<>();
        if (east != null)
            neighbours.add(east);
        if (east != null)
            neighbours.add(north);
        if (east != null)
            neighbours.add(west);
        if (east != null)
            neighbours.add(south);
        return neighbours;
    }

    public String relativeDirectionOf(Node node) {
        if (east.equals(node)) return "R";
        if (north.equals(node)) return "U";
        if (west.equals(node)) return "L";
        if (south. equals(node)) return "D";
        return "NA";
    }

    public boolean isObstacle() {
        return obstacle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return xCoordinate == node.xCoordinate && yCoordinate == node.yCoordinate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xCoordinate, yCoordinate);
    }

}
