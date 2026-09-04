class Solution {
    public int firstStableIndex(int[] nums, int k) {
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            int maxLeft = nums[0];
            int minRight = nums[i];

            // Find maximum in nums[0..i]
            for (int j = 0; j <= i; j++) {
                maxLeft = Math.max(maxLeft, nums[j]);
            }

            // Find minimum in nums[i..n-1]
            for (int j = i; j < n; j++) {
                minRight = Math.min(minRight, nums[j]);
            }

            if (maxLeft - minRight <= k) {
                return i;
            }
        }

        return -1;
    }
}