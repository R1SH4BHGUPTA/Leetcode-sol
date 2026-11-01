class Solution {
    public int minMoves(int[] nums) {

        int min = nums[0];
        long sum = 0;

        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];

            if (nums[i] < min) {
                min = nums[i];
            }
        }

        return (int)(sum - (long) min * nums.length);
    }
}