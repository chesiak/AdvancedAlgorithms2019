//package assignment3AADS.assignment3.generic.tests;
//import assignment3AADS.assignment3.generic.A3Graph;
//import assignment3AADS.assignment3.generic.MyUndirectedGraph;

public class TestUndirectedGraph {

    private static final String[] verticesNames = { "A", "B", "C", "D", "E", "F", "G", "H", "I" };

    public void testCronstructGraph() {
        createUndirectedGraph();
    }

    public void testAddVerticesSimple() {
        A3Graph<String> graph = createUndirectedGraph();
        insertVertices(graph);

    }

    public void testAddEdgesSimple() {

        A3Graph<String> graph = populateVerticesGraph();
        populateEdges(graph);
    }

    public void testIsAcyclicSimple() {
        A3Graph<String> graph = populateGraphVerticesAndEdges();
        graph.isAcyclic();
    }

    public void testEulerSimple() {

        A3Graph<String> graph = populateGraphVerticesAndEdges();
        if (graph.hasEulerPath()) {
            graph.eulerPath();
        }

    }

    private A3Graph<String> populateGraphVerticesAndEdges() {

        A3Graph<String> graph = populateVerticesGraph();
        populateEdges(graph);
        return graph;
    }

    private A3Graph<String> createUndirectedGraph() {
        return new MyUndirectedGraph<String>();
    }

    private void insertVertices(A3Graph<String> graph) {
        for (int i = 0; i < verticesNames.length; i++) {
            graph.addVertex(verticesNames[i]);
        }
    }

    private A3Graph<String> populateVerticesGraph() {
        A3Graph<String> graph = createUndirectedGraph();
        insertVertices(graph);
        return graph;
    }

    private void populateEdges(A3Graph<String> graph) {

        // Connect vertices in sequence without the first
        for (int i = 1; i < verticesNames.length - 1; i++) {
            graph.addEdge(verticesNames[i], verticesNames[i + 1]);
        }

        // connect first element with all the rest
        for (int i = 1; i < verticesNames.length - 1; i++) {
            graph.addEdge(verticesNames[0], verticesNames[i]);
        }

        // Connect last element with second
        graph.addEdge(verticesNames[verticesNames.length - 1], verticesNames[1]);

    }

}