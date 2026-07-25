class Solution {
    public int maxProduct(int n) {
        int[] digits = new int[10];
        while (n > 0) {
            digits[n % 10]++;
            n /= 10;
        }
        
        int max1 = -1, max2 = -1;
        for (int d = 9; d >= 0; d--) {
            int count = digits[d];
            while (count > 0 && max2 == -1) {
                if (max1 == -1) {
                    max1 = d;
                } else {
                    max2 = d;
                }
                count--;
            }
        }
        
        return max1 * max2;
    }
}