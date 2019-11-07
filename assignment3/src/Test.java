import java.util.LinkedList;
import java.util.List;

public class Test {

    public void testHasEuler () {
        MyUndirectedGraph<Integer> graph = new MyUndirectedGraph<>(5);

        for (int i = 0; i < 7 ; i++) {
            graph.addVertex(i + 1 );
        }

//        graph.addEdge(3, 4);
//        graph.addEdge(4,5);
//        graph.addEdge(5,2);
//        graph.addEdge(3,2);

        System.out.println("does ");
        graph.printGraph();
        System.out.println("have a euler trail?");

        System.out.print( graph.hasEulerPath() );
        List<Integer> l = graph.eulerPath();

        if (l != null) {
            for (Integer i:
                    l
                 ) {
                System.out.print( "=> "+ i );
            }
        }
    }

    public void testEulerPath () {
        MyUndirectedGraph<Integer> graph = new MyUndirectedGraph<>(5);
        MyUndirectedGraph<Integer> internetGraph = new MyUndirectedGraph<>(5);

        for (int i = 0; i < 5 ; i++) {
            graph.addVertex(i + 1 );
            internetGraph.addVertex( i + 1 );
        }
        internetGraph.addEdge(5, 1);
        internetGraph.addEdge(1, 2);
        internetGraph.addEdge(2, 3);
        internetGraph.addEdge(2, 4);
        internetGraph.addEdge(3, 4);


        graph.addEdge(1,2);
        graph.addEdge(1,4);
        graph.addEdge(1,3);
        graph.addEdge(1,5);

        graph.addEdge(5, 3);
        graph.addEdge(3, 2);
        graph.addEdge(3, 4);

        graph.addEdge(2, 4);

        List<Integer> l = internetGraph.eulerPath();

        System.out.println("Euler path for internet:");
        System.out.print( internetGraph.hasEulerPath() );
        internetGraph.printGraph();

        for (Integer i : l ) {
            System.out.print(i + " -> ");
        }

        l = graph.eulerPath();

        System.out.println("Euler path for:");
        System.out.print( graph.hasEulerPath() );
        graph.printGraph();

        for (Integer i : l ) {
            System.out.print(i + " -> ");
        }

    }

    public void testDirectedGraph () {
        MyDirectedGraph<Integer> directedGraph = new MyDirectedGraph<>();

        for (int i = 0; i < 5 ; i++) {
            directedGraph.addVertex( i );
        }

        directedGraph.addEdge(0,2);
        directedGraph.addEdge(1,0);
        directedGraph.addEdge(1, 3);
        directedGraph.addEdge(2, 1);


        directedGraph.printGraph();
        directedGraph.connectedComponents();

        System.out.println( directedGraph.isConnected() );
    }

    public void testUndirectedGraph () {
        MyUndirectedGraph <Integer> graph = new MyUndirectedGraph<>();

        for (int i = 0; i < 4 ; i++) {
            graph.addVertex( i );
        }

        graph.addEdge(0,1);
        graph.addEdge(0,2);
        graph.addEdge(1,3);


//        graph.connectedComponents();

        System.out.println( "is connected? " + graph.isConnected() );
        graph.connectedComponents();

        System.out.println("Printing graph");
        graph.printGraph();


    }
}
