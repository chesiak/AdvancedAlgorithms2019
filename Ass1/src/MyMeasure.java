public class MyMeasure implements A2Measure {

    public MyMeasure () {
    }

    public void print ( int[] arr ) {
        for (int i = 0; i < arr.length ; i++) {
            System.out.print( arr[i] + " ");
        }
        System.out.println("");
    }

    @Override
    public boolean isSameCollection(int[] array1, int[] array2) {
        if (array1.length != array2.length ) {
            return false;
        }
        else {
            MinHeap myHeap1 = new MinHeap( array1 );
            MinHeap myHeap2 = new MinHeap( array2 );

            for (int i = 0; i < array1.length ; i++) {
                if ( myHeap1.removeMin() != myHeap2.removeMin() ) {
                    return false;
                }
            }
            return true;
        }
    }

    @Override
    public int minDifferences(int[] array1, int[] array2) {
        if ( array1.length == array2.length ) {

            MinHeap myHeap1 = new MinHeap( array1 );
            MinHeap myHeap2 = new MinHeap( array2 );

            int sum = 0;

            for (int i = 0; i < array1.length ; i++) {
                sum += (int) Math.pow(( myHeap2.removeMin() - myHeap1.removeMin() ), 2);
            }
            return sum;
        }

        return 0;
    }

    @Override
    public int[] getPercentileRange(int[] arr, int lower, int upper) {
        if ( lower <= upper ) {
            int inputSize = arr.length;

            float range = (float) upper / 100;
            float outputSize = range * inputSize;

            range = (float) lower / 100;
            float numOfLowerFloat = range * inputSize;
            int numOfLower = (int) numOfLowerFloat;
            outputSize -= numOfLower;

            if ( outputSize > 0 ) {
                int newSize = (int) outputSize;           // this many elements on output

                MinHeap myheap = new MinHeap( arr );
                int[] result = new int [ newSize ];

                for (int i = 0; i < (int) (range * inputSize); i++) {     // these elements we do not want
                    myheap.removeMin();
                }

                for (int i = 0; i < newSize; i++) {
                    result[ i ] = myheap.removeMin();
                }
                return result;
            }
            else {                      // ZERO elements to print
                return new int[0];
            }
            //  elements > lower BUT elements <= upper
        }
        return new int[0];
    }
}

class MinHeap {
    private int size;
    private int currentEnd;
    public int[] heapArray;

    public MinHeap( int size ) {
        this.size = size;
        currentEnd = 0;
        heapArray = new int[size];
    }

    public MinHeap ( int[] arr ) {
        size = arr.length;
        heapArray = new int[size];
        currentEnd = 0;

        for (int i = 0; i < size ; i++) {
            insert( arr[ i ] );
        }
    }

    public void swap ( int[] array, int i, int j ) {
        int temp = array[ i ];
        array[ i ] = array[ j ];
        array[ j ] = temp;
    }

    public void insert ( int element ) {
        if ( currentEnd < size ) {
            heapArray[ currentEnd ] = element;
            upHeap( currentEnd );
            currentEnd++;
        }
    }

    public int removeMin () {
        int result = heapArray[ 0 ];

        if ( currentEnd > 0 ) {
            heapArray[ 0 ] = heapArray[ currentEnd - 1 ];
            currentEnd--;

            downHeap( 0 );
        }
        else {
            // out array is empty - nothing to remove
        }
        return result;
    }

    public void downHeap ( int hole ) {
        int child;
//        int n = heapArray.length;
        int n = currentEnd;

        int temp = heapArray[ hole ];

        while ( hole < n/2 ) {
            child = 2 * hole + 1;
            if ( child < n - 1 && heapArray[ child ] > heapArray[ child + 1 ] ) {
                child++;                    // we choose element with smaller value
            }
            if ( temp <= heapArray[ child ] ) {
                break;
            }

            heapArray[ hole ] = heapArray[ child ];
            hole = child;
        }

        heapArray[ hole ] = temp;
    }

    public void upHeap ( int hole ) {
        int parent = ( hole - 1 ) / 2;
        int temp = heapArray[ hole ];

        while ( hole > 0 && heapArray[ parent ] > temp ) {
            heapArray[ hole ] = heapArray[ parent ];

            hole = parent;
            parent = ( parent - 1 ) / 2;
        }
        heapArray[ hole ] = temp;
    }
}
