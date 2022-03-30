package algos.sorting;

import java.util.Random;
import org.apache.commons.lang3.time.StopWatch;
import static algos.Program.*;
import lombok.val;
import algos.utils.StackInts;

public class Quicksort {
    public static void run(int len) {
        // val tst = new int[] {6, -39, -41, -21, 20, -44, 25, 41, 2, -35, 37, -42, 23, -14, 35, -6, 3, 41, -29, 10 };
        // val sorted1 = new int[tst.length];
        // System.arraycopy(tst, 0, sorted1, 0, tst.length);
        // sortInPlace(sorted1);

        // l("output");
        // for (int i : sorted1) {
        //     System.out.print(i + " " );
        // }
        // l("");


        // val inp = generateInput(20);
        // val sorted1 = new int[20];
        // System.arraycopy(inp, 0, sorted1, 0, 20);


        // sortInPlace(sorted1);

        // boolean isSorted = checkSortedness(sorted1);
        // l(isSorted ? "Array was sorted successfully " : "Error: array wasn't sorted");
        // if (!isSorted) {
        //     l("input");
        //     for (int i : inp) {
        //         System.out.print(i + " " );
        //     }
        //     l("");

        //     l("output");
        //     for (int i : sorted1) {
        //         System.out.print(i + " " );
        //     }
        //     l("");
        // }

        if (len < 2) {
            l("Length must be at least 2");
            return;
        }
        val inp = generateInput(len);
        boolean isSorted;

        val sorted2 = new int[len];
        System.arraycopy(inp, 0, sorted2, 0, len);

        val sorted0 = new int[len];
        System.arraycopy(inp, 0, sorted0, 0, len);
        quicksort(sorted0);

        val sw2 = new StopWatch();
        sw2.start();
        quicksortRecursive(sorted2, 0, len - 1);
        sw2.stop();
        isSorted = checkSortedness(sorted2);
        l(isSorted ? ("Array was recursively quicksorted successfully in " + sw2.toString()) : "Error: array wasn't insertion-sorted");



        val sorted1 = new int[len];
        System.arraycopy(inp, 0, sorted1, 0, len);

        val sw1 = new StopWatch();
        sw1.start();
        quicksort(sorted1);
        sw1.stop();
        isSorted = checkSortedness(sorted1);
        l(isSorted ? ("Array was quicksorted successfully in " + sw1.toString()) : "Error: array wasn't sorted");





        val sorted3 = new int[len];
        System.arraycopy(inp, 0, sorted3, 0, len);

        val sw3 = new StopWatch();
        sw3.start();
        quicksortPure(sorted3);
        sw3.stop();
        isSorted = checkSortedness(sorted3);
        l(isSorted ? ("Array was purely quicksorted successfully in " + sw3.toString()) : "Error: array wasn't insertion-sorted");
    }


    public static void quicksort(int[] inp) {
        val backtrack = new StackInts();
        int left = 0;
        int right = inp.length - 1;
        backtrack.push(left, right);

        while (backtrack.hasValues()) {
            right = backtrack.popOne();
            left = backtrack.popOne();

            do {
                if (right - left == 1) {
                    if (inp[right] < inp[left]) {
                        swap(inp, left, right);
                    }
                    break;
                }
                if (right - left < 9) {
                    insertionSort(inp, left, right);
                    break;
                }

                int newSplit = partition(inp, left, right);
                if (newSplit > left) {
                    if (newSplit < right) {
                        backtrack.push(newSplit + 1, right);
                        right = newSplit;
                    } else {
                        right -= 1;
                    }
                } else {
                    left += 1;
                }
            } while (true);
        }
    }


    public static void quicksortPure(int[] inp) {
        val backtrack = new StackInts();
        int left = 0;
        int right = inp.length - 1;
        backtrack.push(left, right);

        while (backtrack.hasValues()) {
            right = backtrack.popOne();
            left = backtrack.popOne();

            do {
                if (right - left == 1) {
                    if (inp[right] < inp[left]) {
                        swap(inp, left, right);
                    }
                    break;
                } else if (right <= left) {
                    break;
                }

                int newSplit = partition(inp, left, right);

                if (newSplit > left) {
                    if (newSplit < right) {
                        backtrack.push(newSplit + 1, right);
                        right = newSplit;
                    } else {
                        right -= 1;
                    }
                } else {
                    left += 1;
                }
            } while (true);
        }
    }

    public static void quicksortRecursive(int[] inp, int left, int right) {
        do {
            if (right - left == 1) {
                if (inp[right] < inp[left]) {
                    swap(inp, left, right);
                }
                break;
            }
            if (right - left < 9) {
                insertionSort(inp, left, right);
                break;
            }


            int newSplit = partition(inp, left, right);

            if (newSplit > left) {
                if (newSplit < right) {
                    quicksortRecursive(inp, newSplit + 1, right);
                    right = newSplit;
                } else {
                    right -= 1;
                }
            } else {
                left += 1;
            }
        } while (true);
    }

    /**
     * Partitions elements in [left; right]. @pivot is a value that must occur at least once within this slice of the array.
     */
    private static int partition(int[] inp, int left, int right) {
        int candidate1 = inp[left];
        int candidate2 = inp[left + (right - left) / 2];
        int candidate3 = inp[right];
        int pivot = candidate1;
        if ((candidate2 > candidate1 && candidate2 < candidate3) || (candidate2 > candidate3 && candidate2 < candidate1)) {
            pivot = candidate2;
        } else if ((candidate3 > candidate1 && candidate3 < candidate2) || (candidate3 > candidate2 && candidate3 < candidate1)) {
            pivot = candidate3;
        }
        //l("partition left = " + left + ", right = " + right + ", pivot = " + pivot);
        int i = left;
        int j = right;
        while (true) {
            while (i <= right && inp[i] < pivot) {
                ++i;
            }
            while (j >= left && inp[j] > pivot) {
                --j;
            }
            if (i < j) {
                swap(inp, i, j);
                ++i;
                --j;
            } else {
                //IntStream.iterate(left, x -> x + 1).limit(right - left + 1).forEach(x -> System.out.print( inp[x] + " "));
                //l("j = " + j);

                return j;
            }
            //l("end of while, i = " + i + ", j = " + j);

        }

    }

    public static void insertionSort(int[] inp, int left, int right) {
        for (int i = left + 1; i <= right; ++i) {
            int curr = inp[i];
            if (inp[i - 1] > curr) {
                //swap(inp, i - 1, i);
                int tmp = inp[i - 1];
                inp[i - 1] = inp[i];
                inp[i] = tmp;

                for (int j = i - 1; j > left; --j) {
                    curr = inp[j];
                    if (inp[j - 1] < curr) break;

                    //swap(inp, j - 1, j);
                    int tmp2 = inp[j - 1];
                    inp[j - 1] = inp[j];
                    inp[j] = tmp2;
                }
            }
        }
    }

    private static boolean checkSortedness(int[] inp) {
        int prev = inp[0];
        for (int i = 1; i < inp.length; ++i) {
            if (inp[i] < prev) return false;
            prev = inp[i];
        }
        return true;
    }

    private static void swap(int[] inp, int i, int j) {
        int tmp = inp[i];
        inp[i] = inp[j];
        inp[j] = tmp;
    }


    public static int[] generateInput(int len) {
        val rnd = new Random();
        return rnd.ints(-50, 50).limit(len).toArray();
    }

}
