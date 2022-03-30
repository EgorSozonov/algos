package algos.utils;
import java.util.ArrayList;
import static algos.Program.*;


public class StackInts {
    private ArrayList<int[]> data;
    private int[] currArr;
    private int currArrInd;
    private int currInd;
    private static final int ARR_SZ = 200;

    public StackInts() {
        this.data = new ArrayList<>();
        this.currArr = new int[ARR_SZ];
        this.data.add(this.currArr);
        this.currArrInd = 0;
        this.currInd = -1;
    }

    public void push(int a, int b) {
        this.currInd += 1;
        if (this.currInd >= ARR_SZ) {
            this.currArr = new int[ARR_SZ];
            this.data.add(this.currArr);
            this.currArrInd += 1;
            this.currInd = 0;
        }
        this.currArr[this.currInd] = a;
        this.currInd += 1;
        this.currArr[this.currInd] = b;
    }

    public int popOne() {
        int result = this.currArr[this.currInd];
        this.currInd -= 1;
        if (this.currInd < 0 && this.currArrInd > 0) {
            this.currArrInd -= 1;
            this.currArr = this.data.get(this.currArrInd);
            this.currInd = ARR_SZ - 1;
        }
        return result;
    }

    public boolean hasValues() {
        return this.currInd >= 0;
    }
}
