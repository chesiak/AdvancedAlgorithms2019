//package assignment3AADS.assignment3.generic;

import java.util.*;

public class MyDirectedGraph<T> implements A3Graph<T> {
    private int DEFAULT_SIZE = 11;
    private HashMap<T, Vertex> vertexMap;
    private int currentVertices;
    private Vertex[] vertexArray;

    public MyDirectedGraph () {
        currentVertices = 0;
        vertexArray = new Vertex[ DEFAULT_SIZE ];
        vertexMap = new HashMap<>();
    }

    private MyDirectedGraph ( int size ) {
        currentVertices = 0;
        vertexArray = new Vertex[ size ];
        vertexMap = new HashMap<>();
    }

    @Override
    public void addVertex(T type) {
        Vertex newVertex = new Vertex<>(type);
        vertexArray[ currentVertices ] = newVertex;
        currentVertices++;
        if ( !vertexMap.containsKey( type ) ) {
            vertexMap.put( type, newVertex );
        }

        if ( currentVertices == vertexArray.length ) {
            enlargeGraph();
        }
    }

    private void enlargeGraph() {
        int newSize = ( vertexArray.length * 2 ) + 1;
        Vertex[] newVertexArray = new Vertex[ newSize ];

        for (int i = 0; i < currentVertices ; i++) {
            newVertexArray[ i ] = vertexArray[ i ];
        }
        vertexArray = newVertexArray;
    }

    private void clearVertices () {
        for (int i = 0; i < currentVertices ; i++) {
            vertexArray[ i ].visited = false;
            vertexArray[ i ].colour = Colours.WHITE;
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

                target.degree++;
            }
        }
    }

    @Override
    public boolean isConnected() {
        if ( currentVertices > 0 ) {

            clearVertices();
            DFS( vertexArray[ 0 ] );                              // DFS for random element

            for (int i = 0; i < currentVertices ; i++) {
                if ( !vertexArray[ i ].visited ) {
                    return false;                       // there is a vertex not visited
                }
            }

            MyDirectedGraph<T> transposed = transpose();

            DFS( transposed.vertexArray[ 0 ] );         // same random element, but in transposed graph

            for (int i = 0; i < transposed.currentVertices ; i++) {
                if ( !transposed.vertexArray[ i ].visited ) {
                    return false;
                }
            }
            return true;                                // every vertex visited
        }
        return false;
    }

    private void DFS ( Vertex v ) {
        v.visited = true;

        Iterator<Vertex> i = v.adjacencyList.iterator();
        while ( i.hasNext() ) {
            Vertex x = i.next();
            if ( !x.visited ) {
                DFS( x );
            }
        }
    }

    @Override
    public boolean isAcyclic() {                                // returns true if there is no cycle in the graph
        clearVertices();
        for (int i = 0; i < currentVertices ; i++) {
            if ( vertexArray[ i ].colour == Colours.WHITE ) {
                if ( coloringDFS( vertexArray[ i ] ) ) {
                    return false;                                 // found a cycle!!!
                }
            }
        }
        return true;
    }

    private boolean coloringDFS (Vertex v) {
        v.colour = Colours.GRAY;

        for (Iterator<Vertex> i = v.adjacencyList.iterator(); i.hasNext() ; ) {
            Vertex x = i.next();
            if ( x.colour == Colours.GRAY ) {                               // we found a cycle
                return true;
            }
            if ( x.colour == Colours.WHITE && coloringDFS( x ) ) {         // found a cycle somewhere else
                return true;
            }
        }

        v.colour = Colours.BLACK;
        return false;
    }

    @Override
    public List<List<T>> connectedComponents() {
        if ( currentVertices < 1 ) {
            return null;
        }

        int count = 0;
        List<List<T>> componentsList = new LinkedList<>();
        Stack<Vertex> stack = new Stack<>();
        clearVertices();

        for (int i = 0; i < currentVertices ; i++) {
            if ( !vertexArray[ i ].visited ) {
                fillTheStack( vertexArray[ i ], stack );
            }
        }

        MyDirectedGraph<T> transposedGraph = transpose();
//        transposedGraph.clearVertices();
//        clearVertices();

        while ( !stack.isEmpty() ) {
            Vertex v = stack.pop();
            Vertex vInTransposed = transposedGraph.vertexMap.get(v.element);

            if ( !vInTransposed.visited ) {
                System.out.println("Component no " + count);
                componentsList.add( new LinkedList<>() );
                transposedGraph.componentDFS( vInTransposed, componentsList.get(count) );
                count++;                                         // here we have another component
                System.out.println();
            }
        }
        return componentsList;
    }

    private void componentDFS ( Vertex v, List<T> componentsList ) {
        v.visited = true;
        System.out.print(v.element + " ");
        componentsList.add((T) v.element);

        Iterator<Vertex> i = v.adjacencyList.iterator();
        while ( i.hasNext() ) {
            Vertex x = i.next();
            if ( !x.visited ) {
                componentDFS( x, componentsList );
            }
        }
    }

    private MyDirectedGraph<T> transpose () {
        MyDirectedGraph<T> transposedGraph = new MyDirectedGraph<>(vertexArray.length);

        for (int i = 0; i < currentVertices ; i++) {
            transposedGraph.addVertex((T) vertexArray[ i ].element);
        }

        for (int i = 0; i < currentVertices ; i++) {

            for (Iterator j = vertexArray[ i ].adjacencyList.iterator(); j.hasNext() ; ) {
                Vertex v = (Vertex) j.next();
                transposedGraph.addEdge( (T) v.element, (T) vertexArray[ i ].element );
            }
        }
        return transposedGraph;
    }

    private void fillTheStack ( Vertex v, Stack stack ) {               // similar to DFS
        v.visited = true;

        Iterator<Vertex> i = v.adjacencyList.iterator();
        while ( i.hasNext() ) {
            Vertex x = i.next();
            if ( !x.visited ) {
                fillTheStack( x, stack );
            }
        }
        stack.push( v );
    }

    public void printGraph() {
        for(int i = 0; i < currentVertices; i++) {
            System.out.print("( "+ vertexArray[ i ].element + " ):");
            for (Iterator j = vertexArray[ i ].adjacencyList.iterator(); j.hasNext() ; ) {
                Vertex x = (Vertex) j.next();
                System.out.print(" => " + x.element);
            }
            System.out.println();
        }
    }
}
