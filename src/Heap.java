import java.util.HashMap;

public class Heap {

    private final Node[] nodes;

    private int size;
    private final HashMap<Node, Integer> indices;

    public Heap(int n) {
        nodes = new Node[n];
        size = 0;
        indices = new HashMap<>();
    }

    public Node getTop() {
        if (size == 0)
            return null;
        Node top = nodes[0];
        delete(nodes[0]);
        return top;
    }

    public void add(Node newNode) {
        nodes[size] = newNode;
        indices.put(nodes[size], size);
        sortUp(size);
        ++size;
    }

    public void delete(Node poorNode) {
        int index = indices.get(poorNode);
        Node inter = nodes[index];
        nodes[index] = nodes[size - 1];
        nodes[size - 1] = inter;
        indices.put(nodes[index], index);
        indices.put(nodes[size - 1], size - 1);
        --size;
        if((index + 1) / 2 - 1 >= 0 && nodes[index].getF() < nodes[(index + 1) / 2 - 1].getF()) {
            sortUp(index);
        }
        else {
            sortDown(index);
        }
    }

    private void sortUp(int index) {
        while ((index + 1) / 2 - 1 >= 0 && nodes[index].getF() < nodes[(index + 1) / 2 - 1].getF()) {
            Node inter = nodes[index];
            nodes[index] = nodes[(index + 1) / 2 - 1];
            nodes[(index + 1) / 2 - 1] = inter;
            indices.put(nodes[index], index);
            indices.put(nodes[(index + 1) / 2 - 1], (index + 1) / 2 - 1);
            index = (index + 1) / 2 - 1;
        }
    }

    private void sortDown(int index) {
        int left = (index + 1) * 2 - 1;
        int right = (index + 1) * 2;
        int smallest = index;
        if(left < size && nodes[smallest].getF() > nodes[left].getF())
            smallest = left;
        if(right < size && nodes[smallest].getF() > nodes[right].getF())
            smallest = right;
        if(smallest != index) {
            Node inter = nodes[index];
            nodes[index] = nodes[smallest];
            nodes[smallest] = inter;
            indices.put(nodes[index], index);
            indices.put(nodes[smallest], smallest);
            sortDown(smallest);
        }
    }

    public Node contains(Node sought) {
        for (int i = 0; i < size; ++i) {
            if (sought.equals(nodes[i]))
                return nodes[i];
        }
        return null;
    }

//    public void resetNodes() {
//        size = indices.size();
//        for (Node node :
//                nodes) {
//            node.setVisited(false);
//        }
//    }
}
