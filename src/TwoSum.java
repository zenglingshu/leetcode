import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Given an array of integers, find two numbers such that they add up to a specific target number.

   The function twoSum should return indices of the two numbers such that they add up to the target, where index1 must be less than index2. Please note that your returned answers (both index1 and index2) are not zero-based.

   You may assume that each input would have exactly one solution.

   Input: numbers={2, 7, 11, 15}, target=9

   Output: index1=1, index2=2
 * </pre>
 * Created by lingshu on 15/10/7.
 */
public class TwoSum {
    //O(n^2) 两层循环，挨着遍历所有的元素，直到两个相加等于target
    public static int[] twoSum(int[] nums, int target) {
        int[] result = new int[2];
        for (int k = 0; k < nums.length - 1; k ++){
            for (int j = k+1; j < nums.length; j ++){
                if (nums[k] + nums[j] == target){
                    result[0] = k+1;
                    result[1] = j+1;
                    break;
                }
            }
        }
        return result;
    }
    //O(nlogn), 先排序，然后再同时从左和从右往中间遍历

    //O(n)，一层循环，对遍历到的元素，从map中查找target与该元素的差，如果查到，那么结束
    public static int[] twoSum2(int[] nums, int target){
        int[] result = new int[2];
        Map<Integer, Integer> map = new HashMap<>();
        for (int k = 0; k < nums.length; k ++){
            map.put(nums[k], k);
        }
        for (int k = 0; k < nums.length; k ++){
            if (map.get(target - nums[k]) != null){
                result[0] = k + 1;
                result[1] = map.get(target - nums[k]) + 1;
                break;
            }
        }
        return result;
    }

    public static void main(String[] args){
        System.out.println(Arrays.toString(twoSum2(new int[]{-3,4,3,90}, 0)));
    }
}
