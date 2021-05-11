import java.util.ArrayList;
import java.util.Objects;

/**
 * Class Node represents a node (a place on the desk)
 */
public class Node {

//    Coordinates of this node
    private final int xCoordinate;
    private final int yCoordinate;
//    Cost passing this node
    private int expense;
//    This node's father!
    private Node ancestor;
//    This node's neighbours
    private Node east;
    private Node north;
    private Node west;
    private Node south;
//    Boolean determining if this node's an obstacle
    private boolean obstacle;
    private int depth;
//    The value of the objective function for this node
    private int f;

    /**
     * Instantiates this node
     * @param x The x coordinate of this node
     * @param y The y coordinate of this node
     */
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

    /**
     * Sets the east neighbour of this node
     * @param east The east node
     */
    public void setEast(Node east) {
        this.east = east;
    }

    /**
     * Sets the north neighbour of this node
     * @param north The north node
     */
    public void setNorth(Node north) {
        this.north = north;
    }

    /**
     * Sets the west neighbour of this node
     * @param west The west node
     */
    public void setWest(Node west) {
        this.west = west;
    }

    /**
     * Sets the south neighbour of this node
     * @param south The south node
     */
    public void setSouth(Node south) {
        this.south = south;
    }

    /**
     * Sets the father of this node
     * @param ancestor The father node
     */
    public void setAncestor(Node ancestor) {
        this.ancestor = ancestor;
    }

    /**
     * Sets the expense for passing this node
     * @param expense The expense
     */
    public void setExpense(int expense) {
        this.expense = expense;
    }

    /**
     * Sets obstacle state of this node
     * @param obstacle Boolean determining the obstacle state of this node
     */
    public void setObstacle(boolean obstacle) {
        this.obstacle = obstacle;
    }

    /**
     * Sets the objective function for this node
     * @param f The objective function for this node
     */
    public void setF(int f) {
        this.f = f;
    }

    /**
     * @return The father of this node
     */
    public Node getAncestor() {
        return ancestor;
    }

    /**
     * @return x coordinate of this node
     */
    public int getX() {
        return xCoordinate;
    }

    /**
     * @return y coordinate of this node
     */
    public int getY() {
        return yCoordinate;
    }

    /**
     * @return the expense of passing this node
     */
    public int getExpense() {
        return expense;
    }

    /**
     * @return The value of the objective function for this node
     */
    public int getF() {
        return f;
    }

    /**
     * Sets this node's depth while performing search
     * @param depth The search depth
     */
    public void setDepth(int depth) {
        this.depth = depth;
    }

    /**
     * @return The depth of this node
     */
    public int getDepth() {
        return depth;
    }

    /**
     * This method returns the opposite node to the given node with respect to this node
     * @return The opposite-sided node
     */
    public Node getOppositeOf(Node node) {
        if (node.equals(east))
            return west;
        if (node.equals(north))
            return south;
        if (node.equals(west))
            return east;
        return north;
    }

    /**
     * @return An ArrayList containing the non-obstacle neighbours of this node
     */
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

    /**
     * Returns the direction to which the given node resides with regard
     * to this node
     * @param node The given node
     * @return String representing the direction
     */
    public String relativeDirectionOf(Node node) {
        if (node.equals(east)) return "R";
        if (node.equals(north)) return "U";
        if (node.equals(west)) return "L";
        if (node.equals(south)) return "D";
        return "NA";
    }

    /**
     * @return True if this node is an obstacle
     */
    public boolean isObstacle() {
        return obstacle;
    }

    /**
     * Checks if the given object is actually this node
     * @param o The given object to be compared
     * @return True if o is of type Node and has the same coordinates as this node
     */
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

    /**
     * @return A String representation of this node including its coordinates
     */
    @Override
    public String toString() {
        return  xCoordinate +
                "," + yCoordinate;
    }
}
