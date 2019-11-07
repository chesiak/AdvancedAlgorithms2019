//package assignment3AADS.assignment3.generic;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MySocialNetwork<T> extends MyUndirectedGraph<T> implements A3SocialNetwork<T> {

    @Override
    public int numberOfPeopleAtFriendshipDistance(T vertex, int dist) {
        Vertex start = vertexMap.get( vertex );
        super.clearVertices();

        return BFS( start, dist )[0];
    }

    @Override
    public int furthestDistanceInFriendshipRelationships(T vertex) {
        Vertex start = super.vertexMap.get( vertex );
        super.clearVertices();

        return BFS( start, 0)[1];
    }

    private int[] BFS ( Vertex start, int dist ) {
        int[] result = new int[2];
        result[ 0 ] = 0;                        // numberOfFriendsAtDistanceCounter
        result [ 1 ] = 0;                       // furthestDistance

        start.visited = true;
        start.distance = 0;

        LinkedList<Vertex> queue = new LinkedList<>();
        queue.addFirst( start );

        while ( !queue.isEmpty() ) {
            Vertex v = queue.peekFirst();

            for (Iterator<Vertex> i = v.adjacencyList.iterator(); i.hasNext() ;) {
                Vertex neighbour = i.next();

                if ( !neighbour.visited ) {
                    neighbour.visited = true;
                    neighbour.distance = v.distance + 1;

                    if ( neighbour.distance == dist ) {
                        result[ 0 ]++;
                    }

                    if ( neighbour.distance > result[ 1 ] ) {
                        result[ 1 ] = neighbour.distance;
                    }

                    queue.addLast( neighbour );
                }
            }
            queue.removeFirst();
            v.visited = true;
        }
        return result;
    }

    @Override
    public List<T> possibleFriends(T vertex) {
        Vertex start = super.vertexMap.get( vertex );
        super.clearVertices();

        List<T> listOfPossibleFriends = new LinkedList<>();

        start.visited = true;
        start.distance = 0;

        LinkedList<Vertex> queue = new LinkedList<>();
        queue.addFirst( start );

        while ( !queue.isEmpty() ) {
            Vertex v = queue.peekFirst();

            for (Iterator<Vertex> i = v.adjacencyList.iterator(); i.hasNext() ;) {
                Vertex neighbour = i.next();

                if ( !neighbour.visited ) {
                    neighbour.visited = true;
                    neighbour.distance = v.distance + 1;

                    if ( neighbour.distance == 2 ) {
                        if (neighbour.colour == Colours.WHITE) {           // has one common friend with START
                            neighbour.colour = Colours.GRAY;
                            neighbour.visited = false;
                            queue.addLast( neighbour );
                        }
                        else if ( neighbour.colour == Colours.GRAY ) {      // has two common friends with START
                            neighbour.colour = Colours.BLACK;
                            neighbour.visited = false;
                        }
                        else if ( neighbour.colour == Colours.BLACK ) {     // has three common friends with START
                            listOfPossibleFriends.add((T) neighbour.element);
                        }
                    }
                    else {
                        queue.addLast( neighbour );
                    }
                }
            }
            queue.removeFirst();
            v.visited = true;
        }

        return listOfPossibleFriends;
    }

}
