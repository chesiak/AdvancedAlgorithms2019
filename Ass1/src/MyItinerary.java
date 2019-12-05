// Aleksandra Chesiak
// Patryk Staniszewski

enum A2Direction {
    UP,
    RIGHT,
    DOWN,
    LEFT
}

public class MyItinerary implements A2Itinerary<A2Direction> {
    private A2Direction[] table;
    private int[] intersections;

    private Plane highestLeftmost;
    private Plane highestRightmost;
    private Plane lowestLeftmost;
    private Plane lowestRightmost;

    public MyItinerary ( A2Direction[] arr ) {
        table = arr;

        highestLeftmost = new Plane();
        highestRightmost = new Plane();
        lowestLeftmost = new Plane();
        lowestRightmost = new Plane();

        travelNow();
    }

    private void travelNow () {
        Plane currentPosition = new Plane();
        PlaneHashTable roadMap = new PlaneHashTable();
        int[] tempIntersection = new int[ table.length ];

        roadMap.insert(currentPosition);                //  we add (0, 0) to hashMap
        int countIntersections = 0;

        int i = 0;
        while ( i < table.length ) {
            if ( table[ i ] == A2Direction.LEFT ) {
                currentPosition.ox--;

                if ( highestLeftmost.ox > currentPosition.ox ) {
                    highestLeftmost.ox = currentPosition.ox;
                }
                if ( lowestLeftmost.ox > currentPosition.ox ) {
                    lowestLeftmost.ox = currentPosition.ox;
                }
            }
            else if ( table[ i ] == A2Direction.RIGHT ) {
                currentPosition.ox++;

                if ( highestRightmost.ox < currentPosition.ox ) {
                    highestRightmost.ox = currentPosition.ox;
                }
                if ( lowestRightmost.ox < currentPosition.ox ) {
                    lowestRightmost.ox = currentPosition.ox;
                }
            }
            else if ( table[ i ] == A2Direction.UP ) {
                currentPosition.oy++;

                if ( highestRightmost.oy < currentPosition.oy ) {
                    highestRightmost.oy = currentPosition.oy;
                }
                if ( highestLeftmost.oy < currentPosition.oy ) {
                    highestLeftmost.oy = currentPosition.oy;
                }
            }
            else if ( table[ i ] == A2Direction.DOWN ){
                currentPosition.oy--;

                if ( lowestRightmost.oy > currentPosition.oy ) {
                    lowestRightmost.oy = currentPosition.oy;
                }
                if ( lowestLeftmost.oy > currentPosition.oy ) {
                    lowestLeftmost.oy = currentPosition.oy;
                }
            }

            if ( roadMap.contains(currentPosition) ) {
                tempIntersection[ countIntersections++ ] = i;
            }
            else {
                roadMap.insert( new Plane( currentPosition.ox, currentPosition.oy ) );
            }

            i++;
        }
        intersections = new int[ countIntersections ];
        for (int j = 0; j < countIntersections ; j++) {
            intersections[ j ] = tempIntersection[ j ];
        }
    }

    @Override
    public A2Direction[] rotateRight() {
        A2Direction[] result = table;
        for (int i = 0; i < result.length ; i++) {
            if ( result[ i ] == A2Direction.LEFT ) {
                result[ i ] = A2Direction.UP;
            }
            else if ( result[ i ] == A2Direction.RIGHT ) {
                result[ i ] = A2Direction.DOWN;
            }
            else if ( result[ i ] == A2Direction.UP ) {
                result[ i ] = A2Direction.RIGHT;
            }
            else {
                result[ i ] = A2Direction.LEFT;
            }
        }
        return result;
    }

    public int widthOfItinerary() {
        return  highestRightmost.ox - highestLeftmost.ox;
    }

    public int heightOfItinerary() {
        return highestLeftmost.oy - lowestLeftmost.oy;
    }

    public int[] getIntersections() {
        return intersections;
    }

}

class Plane {
    public int ox;
    public int oy;

    public Plane() {
        ox = 0;
        oy = 0;
    }

    public Plane ( int x, int y ) {
        ox = x;
        oy = y;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Plane)) return false;
        Plane plane = (Plane) o;
        return ox == plane.ox &&
                oy == plane.oy;
    }

    @Override
    public int hashCode() {
        int result;
        if ( ox < 0 ) {
            result = -37 * Integer.hashCode(ox) + 13;
        }
        else {
            result = 31 * Integer.hashCode(ox) + 13;
        }
        if ( oy < 0 ) {
            result += -37 * Integer.hashCode(oy) + 17;
        }
        else {
            result += 31 * Integer.hashCode(oy) + 17;
        }
        return result;
    }

    public void setPlane (Plane p ) {
        ox = p.ox;
        oy = p.oy;
    }
}

class PlaneHashTable {

    private static class HashEntry {
        public Plane element;
        public boolean isActive;

        public HashEntry(Plane e) {
            this(e, true);
        }

        public HashEntry(Plane e, boolean b) {
            element = e;
            isActive = b;
        }
    }

    private static final int DEFAULT_SIZE = 11;         // ile ??

    private HashEntry[] array;
    private int currentSize;

    public PlaneHashTable(double limit) {
        this(DEFAULT_SIZE);
    }

    public PlaneHashTable() {
        this(DEFAULT_SIZE);
    }

    PlaneHashTable(int size) {
        allocateArray(size);
        makeEmpty();
    }

    public void print () {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                System.out.print("- ");
            }
            else {
                if ( array[i].isActive ) {
                    System.out.print(array[i].element + " ");
                }
                else {
                    System.out.print("(" + array[i].element + ") ");
                }
            }
        }
    }

    public void makeEmpty() {
        currentSize = 0;
        for (int i = 0; i < array.length; i++) {
            array[ i ] = null;
        }
    }

    private void allocateArray(int arrSize) {
        array = new HashEntry[ arrSize ];           // wiekszy rozmiar ??
    }

    private boolean isActive(int currPosition) {
        if (currPosition < array.length)
            return array[currPosition] != null && array[currPosition].isActive;
        else
            return true;
    }

    private int findPositionInsert(Plane x) {
        int offset = 1;
        int currentPosition = x.hashCode();

        while ( currentPosition >= array.length ) {
            currentPosition -= array.length;
        }
        while ( currentPosition >= array.length || (array[currentPosition] != null && ( !array[currentPosition].element.equals(x) || array[currentPosition].isActive ) ) ) {
            currentPosition += offset;
            offset +=2;

            if (currentPosition >= array.length) {
                currentPosition -= array.length;
            }
            if (currentPosition >= array.length) {
                break;
            }
        }
        return currentPosition;
    }

    private int findPositionDelete( Plane x ) {
        int offset = 1;
        int currentPos = x.hashCode();

        if ( currentPos >= array.length ) {
            currentPos %= array.length;
        }

        while( array[ currentPos ] != null && !array[ currentPos ].isActive ) {
            currentPos += offset; // Compute ith probe
            offset += 2;
            if( currentPos >= array.length )
                currentPos -= array.length;
        }
        return currentPos;
    }

    private void rehash() {
        HashEntry[] oldArray = array;

        allocateArray( 2*array.length + 1 );
        currentSize = 0;

        for (int i = 0; i < oldArray.length; i++) {
            if ( oldArray[i] != null && oldArray[i].isActive ) {
                insert( oldArray[i].element );
            }
        }
    }

    public void insert(Plane element) {
        int currentPosition = findPositionInsert( element );

        if (isActive(currentPosition)) {              // insert failed
            rehash();
        }

        array[currentPosition] = new HashEntry(element, true);
        ++currentSize;

        if ( currentSize > array.length / 2 ) {
            rehash();
        }
    }

    public boolean contains(Plane element) {
        int currentPosition = findPositionDelete(element);

        if ( isActive(currentPosition) && array[currentPosition].element.equals(element) ) {
            return true;
        }
        else {
            return false;
        }
    }
}