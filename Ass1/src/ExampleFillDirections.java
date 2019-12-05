public class ExampleFillDirections {

    private A2Direction[] array;
    private static A2Itinerary<A2Direction> itinerary;

    public static void main(String[] args) {

        ExampleFillDirections example = new ExampleFillDirections();
        example.fillSix();
        example.createItinerary();

        System.out.println("Height = " + itinerary.heightOfItinerary() );
        System.out.println("Width = " + itinerary.widthOfItinerary() );

        int[] temp = itinerary.getIntersections();

        for (int i = 0; i < temp.length ; i++) {
            System.out.print( temp[ i ] + " " );
        }

    }

    private void fillSix() {
        array = new A2Direction[10];

        array[0] = A2Direction.LEFT;
        array[1] = A2Direction.DOWN;
        array[2] = A2Direction.RIGHT;
        array[3] = A2Direction.DOWN;
        array[4] = A2Direction.LEFT;
        array[5] = A2Direction.UP;
        array[6] = A2Direction.LEFT;
        array[7] = A2Direction.UP;
        array[8] = A2Direction.RIGHT;
        array[9] = A2Direction.UP;

//        array[0] = A2Direction.LEFT;
//        array[1] = A2Direction.DOWN;
//        array[2] = A2Direction.DOWN;
//        array[3] = A2Direction.RIGHT;
//        array[4] = A2Direction.UP;
//        array[5] = A2Direction.LEFT;
    }

    private void createItinerary() {
        itinerary = new MyItinerary(array);
    }

}