//package assignment3AADS.assignment3.generic.tests;
//import assignment3AADS.assignment3.generic.MySocialNetwork;

import java.util.List;
import java.util.Stack;

public class TestSocialNetwork {

    private static final String[] names = {"Alice","Bob", "Carol", "David", "Eve", "Grace", "Heidi", "Ivan", "Mallory", "Mark", "Neo", "Trent",};
    private static final String[] namesFiveteen = {"one","two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "13", "14", "15"};

    private MySocialNetwork<String> createSocialNetwork(){
        return new MySocialNetwork<String>();
    }

    public void testInsertSimple() {
        MySocialNetwork<String> network = createSocialNetwork();

        for(int i=0; i<names.length; i++) {
            network.addVertex(names[i]);
        }

        //Create circle of friends
        for(int i=0; i<names.length-1; i++) {
            network.addEdge(names[i],  names[i+1]);
        }
        network.addEdge(names[names.length-1], names[0]);

        int result = network.numberOfPeopleAtFriendshipDistance(names[0], 2);

        System.out.println("People at dist 0 = " + network.numberOfPeopleAtFriendshipDistance(names[0], 0));
        System.out.println("People at dist 5 = " + network.numberOfPeopleAtFriendshipDistance(names[0], 5));
        System.out.println("People at dist 2 = " + result);
        System.out.println("People at dist 6 = " + network.numberOfPeopleAtFriendshipDistance(names[0], 6));
    }

    public void testListOfPossibleFriends () {
        MySocialNetwork<String> network = createSocialNetwork();

        for(int i=0; i<namesFiveteen.length; i++) {
            network.addVertex(namesFiveteen[i]);
        }

        network.addEdge("ten","seven");
        network.addEdge("ten","eight");
        network.addEdge("ten","nine");
        network.addEdge("eleven","seven");

        network.addEdge("five","seven");
        network.addEdge("five","eight");
        network.addEdge("five","nine");
        network.addEdge("five","twelve");
        network.addEdge("five","15");
        network.addEdge("five","six");
        network.addEdge("five","four");
        network.addEdge("five","two");
        network.addEdge("five","one");

        network.addEdge("one","three");
        network.addEdge("one","two");
        network.addEdge("one","four");

        network.addEdge("two","three");
        network.addEdge("four","three");
        network.addEdge("two","four");

        network.addEdge("six","14");
        network.addEdge("six","three");
        network.addEdge("13","three");

        List<String> l = network.possibleFriends("five");

        System.out.print("Possible friends of (five) = ");
        for (String s : l
             ) {
            System.out.print(s + ", ");
        }
        System.out.print("\n");

        System.out.println("num of people in dist 2 = " + network.numberOfPeopleAtFriendshipDistance("five",2));
        System.out.println("furthest dist for (5) = "+ network.furthestDistanceInFriendshipRelationships("five"));

    }
}