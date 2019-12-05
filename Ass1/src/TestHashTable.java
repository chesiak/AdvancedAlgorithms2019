import java.util.Random;

class TestHashTable {

    private static final int NUM_INSERTIONS = 10;
    private static final double MAX_LOAD = 0.75;

    private MyHashTable<Integer> createTable() {
	    return new MyHashTable<Integer>(MAX_LOAD);
    }

    public void testInsertSimple() {
	A2HashTable<Integer> table = createTable();

        for(int i=0; i<NUM_INSERTIONS; i++) {
            table.insert(3 );
            table.print();
            System.out.println("");
        }
    }

//    public void testRemoveSimple() {
//        A2HashTable<Integer> table = createTable();
//
//        for(int i = 0; i <= NUM_INSERTIONS; i++) {
//            table.insert( 2);
//            table.print();
////            System.out.println("i = " + i);
//        }
//        System.out.println("Now deleting:");
//
//        for(int i = 0; i < NUM_INSERTIONS / 2; i++) {
//            table.delete(-i);
//            table.print();
//            System.out.println("");
//        }
//
//    }

    public static void main (String []args) {
        System.out.println("Hello");

        TestHashTable myTest = new TestHashTable();

      myTest.testInsertSimple();
//        myTest.testRemoveSimple();

        System.out.println("Bye");

    }

}
