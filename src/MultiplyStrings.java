/**
 * <pre>
 *     Given two numbers represented as strings, return multiplication of the numbers as a string.
 *
 *     Note: The numbers can be arbitrarily large and are non-negative.
 * </pre>
 * Created by lingshu on 15/10/18.
 */
public class MultiplyStrings {
    /**
     * 算法描述：将num2中每一位（从最后一位开始）和num1相乘，放在数组的一行中（考虑进位）。之后再将数组中的每一行相加（考虑进位）。
     */
    public String multiply(String num1, String num2) {
        if (num1 == null || "".equals(num1.trim())){
            return num2;
        }
        if (num2 == null || "".equals(num2.trim())){
            return num1;
        }
        char[] num1CharArray = num1.toCharArray();
        char[] num2CharArray = num2.toCharArray();
        // 二维数组的每一行表示num2中每一位和num1中所有位相乘的结果。第一行为num2最后一位和num1中所有位相乘的结果。
        // 如 19 * 325，在数组中，最后一行为5和19的乘积，第一行为3和19的乘积
        int[][] resultArr = new int[num2CharArray.length][num1CharArray.length + num2CharArray.length];
        // p表示有遍历到的行，q表示每一行中遍历到的列
        int p = num2CharArray.length - 1, q = num1CharArray.length + num2CharArray.length - 1;
        for (int j = num2CharArray.length - 1; j >= 0; j --){
            for (int i = num1CharArray.length - 1; i >= 0; i --){
                int multiply = Integer.valueOf(String.valueOf(num2CharArray[j])) * Integer.valueOf(String.valueOf(num1CharArray[i]));
                resultArr[p][q] += multiply % 10;
                resultArr[p][q-1] += multiply / 10;
                q --;
            }
            p --;
            q = num1CharArray.length + j - 1;
        }
        // 存放二维数组中每列相加的值，result[1]为resultArr中第1列上所有行的值相加，有进位时，将进位的值写入result[0]中
        int[] result = new int[num1CharArray.length + num2CharArray.length];
        // 每一列所有行相加之后是否需要向前进位
        boolean needCarry;
        // 从最后一列开始，每一行的值相加
        for (int j = num1CharArray.length + num2CharArray.length - 1; j >= 0; j --){
            for (int i = num2CharArray.length - 1; i >= 0; i --){
                result[j] += resultArr[i][j];
            }
            needCarry = result[j] > 9;
            // 将进位写入前一个值
            if (needCarry){
                result[j - 1] = result[j] / 10;
            }
            result[j] = result[j] % 10;
        }
        // 将数组中的数拼接成字符串，需要将数字前面的0去掉
        String sres = "";
        boolean skip = true;
        for (int value : result){
            if (value == 0 && skip){
                continue;
            }
            skip = false;
            sres += value;
        }
        // 如果转换后sres仍为空串，那么说明都是0
        if ("".equals(sres)){
            return "0";
        }
        return sres;
    }

    public static void main(String[] args){
        System.out.println(new MultiplyStrings().multiply("123", "456"));
    }
}
