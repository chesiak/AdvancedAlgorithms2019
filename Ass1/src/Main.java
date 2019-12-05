class Main {

    public static void main (String []args) {
        System.out.println("Hello");

       int[] arr1 = { 10, 1, 7, 10 };
       int[] arr2 = { 1, 10, 7, 9 };

       int[] arr3 = { 2, 5, 3, 9 };
       int[] arr4 = { 15, 12, 1, 3 };

       int[] arr5 = { 20, 16, 2, 4, 10, 6, 12, 8, 14, 18 };

       int [] empty1 = new int [0];
       int [] empty2 = new int [0];

       int [] p1 = { 9, 8, 7, 6, 1, 2, 3, 4 };
       int [] p2 = { 0, 0, 0, 0, 0, 0, 0, 0 };

       MyMeasure myMeasure = new MyMeasure();

       System.out.println("czy sa the same? " + myMeasure.isSameCollection(p1, p2) );

//       System.out.println("czy sa the same? " + myMeasure.isSameCollection(empty1, empty2) );

       int result = myMeasure.minDifferences( p2, p1 );
       System.out.println("E = " + result );

       result = myMeasure.minDifferences( empty1, empty2 );
       System.out.println("E = " + result );

       int [] temp;
//       arr5 = p1;
       temp = myMeasure.getPercentileRange( p1, 79, 79);
       System.out.print(" 0-10 \n" );
       myMeasure.print(temp);
       myMeasure.print( myMeasure.getPercentileRange(arr5, 0, 10) );

        System.out.println(" 10-20 " );
        myMeasure.print( myMeasure.getPercentileRange(arr5, 10, 20) );

        System.out.println(" 10-50 " );
        myMeasure.print( myMeasure.getPercentileRange(arr5, 10, 50) );

        System.out.println(" 60-70 " );
        myMeasure.print( myMeasure.getPercentileRange(arr5, 60, 70) );

        System.out.println(" 0-100 " );
        myMeasure.print( myMeasure.getPercentileRange(arr5, 0, 100) );

    }
}