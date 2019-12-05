//Aleksandra Chesiak
//Patryk Staniszewski

class MyHashTable<AnyType> implements A2HashTable<AnyType> {

    private static class HashEntry<AnyType> {

        public AnyType element;
        public boolean isActive;

        public HashEntry(AnyType e) {
            this(e, true);
        }

        public HashEntry(AnyType e, boolean b) {
            element = e;
            isActive = b;
        }
    }

    private static final int DEFAULT_SIZE = 11;

    private HashEntry<AnyType>[] array;
    private int currentSize;
    private double loadFactorLimit;

    public MyHashTable(double limit) {
        this(DEFAULT_SIZE);
        loadFactorLimit = limit;
    }

    public MyHashTable() {
        this(DEFAULT_SIZE);
    }

    MyHashTable(int size) {
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
        System.out.println();
    }

    public void makeEmpty() {
        currentSize = 0;
        for (int i = 0; i < array.length; i++) {
            array[ i ] = null;
        }
    }

    private void allocateArray(int arrSize) {
        array = new HashEntry[ arrSize ];
    }

    private boolean isActive(int currPosition) {
        if (currPosition < array.length)
            return array[currPosition] != null && array[currPosition].isActive;
        else
            return true;
    }

    private int findPositionInsert(AnyType x) {
        int offset = 1;
        int currentPosition = x.hashCode();

        if ( currentPosition >= array.length ) {
            currentPosition %= array.length;
        }
        if ( currentPosition < 0 ) {
            currentPosition = 0 - currentPosition;
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

    private int findPositionDelete( AnyType x ) {
        int offset = 1;
        int currentPosition = x.hashCode();
        // int currentPos = myhash(x);

        if ( currentPosition >= array.length ) {
            currentPosition %= array.length;
        }
        if ( currentPosition < 0 ) {
            currentPosition = 0 - currentPosition;
        }

        while( array[ currentPosition ] != null && !array[ currentPosition ].isActive ) {
            currentPosition += offset; // Compute ith probe
            offset += 2;
            if( currentPosition >= array.length )
                currentPosition -= array.length;
        }
        return currentPosition;
    }

    private void rehash() {
        HashEntry<AnyType>[] oldArray = array;

        allocateArray( 2*array.length+1 );
        currentSize = 0;

        for (int i = 0; i < oldArray.length; i++) {
            if ( oldArray[i] != null && oldArray[i].isActive ) {
                insert( oldArray[i].element );
            }
        }
    }

    private double getCurrentLoadFactor() {
        return (double) currentSize / array.length;
    }

    public void insert(AnyType element) {
        int currentPosition = findPositionInsert( element );

        if (isActive(currentPosition)) {              // insert failed
            rehash();
        }

        array[currentPosition] = new HashEntry<>(element, true);

        ++currentSize;

        if (getCurrentLoadFactor() > loadFactorLimit) {
            rehash();
        }
    }

    public void delete(AnyType element) {
        int currentPos = findPositionDelete(element);

        if ( isActive(currentPos) && array[currentPos].element.equals(element) ) {
            array[ currentPos ].isActive = false;
        }
    }

    public boolean contains(AnyType element) {
        int currentPosition = findPositionDelete(element);

        if ( isActive(currentPosition) && array[currentPosition].element.equals(element) ) {
            return true;
        }
        else {
            return false;
        }
    }

    public int getLengthOfArray() {
        return array.length;
    }
}