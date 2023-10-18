package cn.lp.controller;

/**
 * @author lipeng
 * @since 1.0
 */
public enum Test {

    SL1(new int[]{1, 2, 3, 4}),
    SL2(new int[]{5, 6, 7, 8}),
    SL3(new int[]{9, 10, 11, 12});

    private final int arr[];

     Test(int arr[]){
        this.arr=arr;
    }

    public int[] getArr() {
        return arr;
    }
}
