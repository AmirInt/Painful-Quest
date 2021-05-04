import java.util.ArrayList;
import java.util.Objects;

public class Node {

    private final int xCoordinate;
    private final int yCoordinate;
    private int expense;
    private Node ancestor;
    private Node east;
    private Node north;
    private Node west;
    private Node south;
    private boolean obstacle;
    private int depth;
    private int f;

    public Node(int x, int y) {
        xCoordinate = x;
        yCoordinate = y;
        expense = 0;
        ancestor = null;
        east = null;
        north = null;
        west = null;
        south = null;
        obstacle = false;
        depth = 0;
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

    public void setF(int f) {
        this.f = f;
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

    public int getF() {
        return f;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getDepth() {
        return depth;
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
        if (east != null && !east.isObstacle())
            neighbours.add(east);
        if (north != null && !north.isObstacle())
            neighbours.add(north);
        if (west != null && !west.isObstacle())
            neighbours.add(west);
        if (south != null && !south.isObstacle())
            neighbours.add(south);
        return neighbours;
    }

    public String relativeDirectionOf(Node node) {
        if (node.equals(east)) return "R";
        if (node.equals(north)) return "U";
        if (node.equals(west)) return "L";
        if (node.equals(south)) return "D";
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

    @Override
    public String toString() {
        return  xCoordinate +
                "," + yCoordinate;
    }
}
