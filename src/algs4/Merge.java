package algs4;

/**
 *  The {@code Merge} class provides static methods for sorting an
 *  array using mergesort.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/22mergesort">Section 2.2</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *  For an optimized version, see {@link MergeX}.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class Merge {

    // This class should not be instantiated.
    //private Merge() { }

    // stably merge a[lo .. mid] with a[mid+1 ..hi] using aux[lo .. hi]
    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi, Integer[] items, Integer[] aux1) {
        // precondition: a[lo .. mid] and a[mid+1 .. hi] are sorted subarrays
        assert isSorted(a, lo, mid);
        assert isSorted(a, mid+1, hi);

        // copy to aux[]
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
            aux1[k] = items[k];
        }

        // merge back to a[]
        int i = lo, j = mid+1, x = lo, y = mid+1;
        for (int k = lo; k <= hi; k++) {
            if(i > mid){
                a[k] = aux[j++];
                items[k] = aux1[y++];
            }else if (j > hi) {
                a[k] = aux[i++];
                items[k] = aux1[x++];
            }else if (less(aux[j], aux[i])){
                a[k] = aux[j++];
                items[k] = aux1[y++];
            }else{
                a[k] = aux[i++];
                items[k] = aux1[x++];
            }
        }

        // postcondition: a[lo .. hi] is sorted
        assert isSorted(a, lo, hi);
    }

    // mergesort a[lo..hi] using auxiliary array aux[lo..hi]
    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi, Integer[] items, Integer[] aux1) {
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid, items, aux1);
        sort(a, aux, mid + 1, hi, items, aux1);
        merge(a, aux, lo, mid, hi, items, aux1);
    }

    /**
     * Rearranges the array in ascending order, using the natural order.
     * @param a the array to be sorted
     */
    public static void sort(Comparable[] a, Integer[] items) {
        Comparable[] aux = new Comparable[a.length];
        Integer[] aux1 = new Integer[a.length];
        sort(a, aux, 0, a.length-1, items, aux1);
        assert isSorted(a);
    }


    /***************************************************************************
     *  Helper sorting function.
     ***************************************************************************/

    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    /***************************************************************************
     *  Check if array is sorted - useful for debugging.
     ***************************************************************************/
    private static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }
}

/******************************************************************************
 *  Copyright 2002-2016, Robert Sedgewick and Kevin Wayne.
 *
 *  This file is part of algs4.jar, which accompanies the textbook
 *
 *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
 *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
 *      http://algs4.cs.princeton.edu
 *
 *
 *  algs4.jar is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  algs4.jar is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
 ******************************************************************************/
