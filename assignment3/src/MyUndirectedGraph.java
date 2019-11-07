//package assignment3AADS.assignment3.generic;
import java.util.*;

public class MyUndirectedGraph<T> implements A3Graph<T> {
    HashMap<T, Vertex> vertexMap;
    private int currentVertices;
    private Vertex<T>[] vertexArray;

    public MyUndirectedGraph () {
        currentVertices = 0;
        int DEFAULT_SIZE = 11;
        vertexArray = new Vertex[DEFAULT_SIZE];
        vertexMap = new HashMap<>();
    }

    public MyUndirectedGraph(int size) {
        currentVertices = 0;
        vertexArray = new Vertex[ size ];
        vertexMap = new HashMap<>();
    }

    @Override
    public void addVertex(T type) {
        Vertex<T> newVertex = new Vertex<>(type);

        vertexArray[ currentVertices ] = newVertex;
        currentVertices++;

        if ( !vertexMap.containsKey( type ) ) {
            vertexMap.put( type, newVertex );
        }

        if ( currentVertices == vertexArray.length ) {
            enlargeGraph();
        }
    }

    @Override
    public void addEdge(T sourceVertex, T targetVertex) {
        if ( sourceVertex.equals(targetVertex) ) {
            return;
        }

        Vertex source = vertexMap.get(sourceVertex);
        Vertex target = vertexMap.get(targetVertex);

        if ( source != null && target != null ) {
            if ( !source.adjacencyList.contains( target ) ) {
                source.adjacencyList.add(target);
                target.adjacencyList.add(source);

                source.degree++;
                target.degree++;
            }
        }
    }

    private void enlargeGraph() {
        int newSize = ( vertexArray.length * 2 ) + 1;
        Vertex<T>[] newVertexArray = new Vertex[ newSize ];

        for (int i = 0; i < currentVertices ; i++) {
            newVertexArray[ i ] = vertexArray[ i ];
        }
        vertexArray = newVertexArray;
    }

    public void clearVertices () {
        for (int i = 0; i < currentVertices ; i++) {
            vertexArray[ i ].visited = false;
        }
    }

    public void printGraph() {
        for(int i = 0; i < currentVertices; i++) {
            System.out.print("( "+ vertexArray[ i ].element + " ):");

            for (Iterator<Vertex> j = vertexArray[ i ].adjacencyList.iterator() ; j.hasNext() ; ) {
                Vertex x = j.next();
                System.out.print(" => " + x.element);
            }
            System.out.println();
        }
    }

    @Override
    public List<List<T>> connectedComponents() {
        if (currentVertices == 0) {
            return null;
        }
        clearVertices();

        List<List<T>> componentsList = new LinkedList<>();
        int currentComponent = 0;

        for (int i = 0; i < currentVertices ; i++) {

            if ( !vertexArray[ i ].visited ) {
                System.out.println("Component no " + currentComponent);

                LinkedList<T> newComponent = new LinkedList<>();
                componentsList.add( newComponent );
                dfs( vertexArray[ i ], newComponent );

                currentComponent++;
                System.out.println();
            }
        }
        return componentsList;
    }

    private void dfs(Vertex<T> v, List<T> componentsList ) {
        v.visited = true;

        for (Iterator<Vertex> i = v.adjacencyList.iterator(); i.hasNext() ; ) {
            Vertex<T> x = i.next();
            if ( !x.visited ) {
                dfs( x, componentsList );
            }
        }
        if ( componentsList != null ) {
            componentsList.add(v.element);
        }
//        System.out.print(v.element + " ");
    }

    @Override
    public boolean isConnected() {
        if (currentVertices == 0) {
            return true;
        }
        clearVertices();
        int componentCounter = 0;

        for (int i = 0; i < currentVertices ; i++) {
            if ( !vertexArray[ i ].visited ) {
                dfs( vertexArray[ i ], null );
                componentCounter++;
            }

            if ( componentCounter > 1 ) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isAcyclic() {
        clearVertices();
        for (int i = 0; i < currentVertices ; i++) {
            if ( !vertexArray[ i ].visited ) {
                if ( DFS( vertexArray[ i ] ) ) {
                    return false;                       // found a cycle!!!
                }
            }
        }
        return true;
    }

    private boolean DFS (Vertex v) {
        v.visited = true;

        for (Iterator i = v.adjacencyList.iterator(); i.hasNext() ; ) {
            Vertex x = (Vertex) i.next();
            if ( v.parent != null ) {
                if ( x.visited && !v.parent.equals(x.element) ) {
                    return true;
                }
            }
            if ( !x.visited ) {
                x.parent = v.element;
                return DFS( x );
            }
        }
        return false;
    }

    private int numOfConnectedComponents () {
        if (currentVertices == 0) {
            return 0;
        }
        clearVertices();
        int componentCounter = 0;

        for (int i = 0; i < currentVertices ; i++) {
            if ( !vertexArray[ i ].visited ) {
                componentCounter++;
                dfs( vertexArray[ i ], null );
            }
        }
        return componentCounter;
    }

    @Override
    public boolean hasEulerPath() {
        if ( currentVertices == 0 ) {
            return false;
        }
        if ( currentVertices == 1 ) {
            return true;
        }

        int oddDegreeCounter = 0;
        int componentsNumber = numOfConnectedComponents();

        if ( componentsNumber == 1 ) {                                  // graph is connected

            for (int i = 0; i < currentVertices ; i++) {
                if ( vertexArray[ i ].degree % 2 == 1 ) {             // has a vertex with an odd indegree
                    oddDegreeCounter++;
                }
                if ( oddDegreeCounter > 2 ) {
                    return false;
                }
            }
            return true;
        }
        else {
            int zeroDergeeCounter = 0;

            for (int i = 0; i < currentVertices ; i++) {
                if ( vertexArray[ i ].degree % 2 == 1 ) {             // has a vertex with an odd indegree
                    oddDegreeCounter++;
                }
                if ( vertexArray[ i ].degree == 0 ) {
                    zeroDergeeCounter++;
                }
                if ( oddDegreeCounter > 2 ) {
                    return false;
                }
            }
            if ( zeroDergeeCounter == (componentsNumber) || zeroDergeeCounter == (componentsNumber - 1) ) {        // we consider only edges
                return true;
            }
            return false;
        }
    }

    private MyUndirectedGraph<T> cloneGraph () {
        MyUndirectedGraph<T> copy = new MyUndirectedGraph<>( currentVertices );

        for (int i = 0; i < currentVertices ; i++) {
            copy.addVertex((T) vertexArray[ i ].element);
        }
        for (int i = 0; i < currentVertices ; i++) {                            // for each vertex

            Iterator<Vertex> iter = vertexArray[ i ].adjacencyList.iterator();

            while ( iter.hasNext() ) {                                          // copy its neighbor list
                Vertex<T> v = iter.next();

                copy.addEdge( vertexArray[ i ].element, v.element );
            }
        }
        return copy;
    }

    private Vertex<T> removeEdge ( Vertex<T> vertex ) {
        if ( vertex.degree != 0 ) {

            Vertex<T> neighbour = vertex.adjacencyList.removeFirst();               // we need to remove it from BOTH lists !!!
            neighbour.adjacencyList.remove( vertex );

            vertex.degree--;
            neighbour.degree--;

            return neighbour;
        }
        else {
            return null;
        }
    }

    @Override
    public List<T> eulerPath() {
        if ( !hasEulerPath() ) {
            return null;
        }

        if ( currentVertices == 1 ) {                                       // if only vertex is an izolated one
            List<T> l = new LinkedList<>();
            l.add( vertexArray[ 0 ].element );
            return l;
        }

        Vertex<T> curr = null;
        Vertex<T> firstNonZero = null;
        int oddDegreeCounter = 0;

        MyUndirectedGraph<T> copiedGraph = cloneGraph();

        for (int i = 0; i < currentVertices ; i++) {
            if ( firstNonZero == null && copiedGraph.vertexArray[ i ].degree != 0 ) {
                firstNonZero = copiedGraph.vertexArray[ i ];
            }
            if ( copiedGraph.vertexArray[ i ].degree % 2 == 1 ) {             // has a vertex with an odd indegree
                oddDegreeCounter++;
                if ( curr == null ) {
                    curr = copiedGraph.vertexArray[ i ];
                }
            }
            if ( oddDegreeCounter > 2 ) {
                return null;
            }
        }

        if ( curr == null ) {
            curr = firstNonZero;
        }
        if ( curr == null ) {
            curr = copiedGraph.vertexArray[ 0 ];
        }

        Stack<Vertex<T>> stack = new Stack<>();
        List<T> eulerPath = new LinkedList<>();

        while ( !stack.isEmpty() || curr.degree != 0 ) {              // there are some neighbours

            if ( curr.degree == 0 ) {                                // has no neighbours
                eulerPath.add( curr.element );
                curr = stack.pop();
            }
            else {
                stack.push( curr );

                curr = copiedGraph.removeEdge( curr );              // removes edge and returns neighbour
            }
        }
        eulerPath.add( curr.element );

        return eulerPath;
    }

}