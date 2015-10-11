import java.util.Arrays;

/**
 * 二分排序
 *
 * Created by lingshu on 15/10/11.
 */
public class PartitionSort {
    /**
     * 交换数组中第i和第j位的数
     */
    private static void change(int[] arrays, int i, int j){
        int temp = arrays[i];
        arrays[i] = arrays[j];
        arrays[j] = temp;
    }

    /**
     * 递归：以数组中第一个数为基准，让数组中小于第一个数的在左边，大于第一个数的在右边
     * @param arrays 待排序的数组
     * @param start 数组的第一个元素的下标
     * @param end 数组最后一个元素的下标
     */
    public static void partition(int[] arrays, int start, int end){
        // 结束条件：partition时，start大于等于end
        if (start >= end){
            return;
        }
        // 以数组中第一个数为基准，让数组中小于第一个数的在左边，大于第一个数的在右边
        int i = start + 1;
        int j = end;
        while (i <= j){
            // 如果第一个数在当前的i和j下标的两个数中间，那么交换i和j下标的两个数，并且i和j两个index分别右移和左移
            if (arrays[i] > arrays[start] && arrays[j] < arrays[start]){
                change(arrays, i, j);
                i ++;
                j --;
                continue;
            }
            // 如果当前数小于等于第一个数，那么index右移
            if (arrays[i] <= arrays[start]){
                i ++;
            }
            // 如果当前数大于等于第一个数，那么index左移
            if (arrays[j] >= arrays[start]){
                j --;
            }

        }
        // 调整第一个数和j下标下位置数的顺序：j下标下的数肯定不会比基准数大（如果大的话，肯定会左移）
        change(arrays, start, j);
        // 递归执行
        partition(arrays, start, j - 1);
        partition(arrays, j + 1, end);
    }

    public static void main(String[] args){
        int[] arrays = new int[]{3,5,5,3,2,7,9,0};
        partition(arrays, 0, arrays.length-1);
        System.out.println(Arrays.toString(arrays));
    }
}
