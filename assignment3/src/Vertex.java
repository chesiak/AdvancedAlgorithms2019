import java.util.LinkedList;
import java.util.List;

class Vertex <T> {
    public T element;
    public T parent;
    public boolean visited;
    public Colours colour;
    public int degree;
    public int distance;
    public LinkedList<Vertex> adjacencyList;


    // 1-white, 2- gray, 3- black

    public Vertex (T e) {
        element = e;
        parent = null;
        visited = false;
        colour = Colours.WHITE;
        distance = -1;
        degree = 0;
        adjacencyList = new LinkedList<Vertex>();
    }
}