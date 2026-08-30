class Solution {
    public int minimumDeletions(int[] nums) {
        int n = nums.length;

        int minIdx = 0;
        int maxIdx = 0;

        // Find indices of minimum and maximum
        for (int i = 1; i < n; i++) {
            if (nums[i] < nums[minIdx]) {
                minIdx = i;
            }

            if (nums[i] > nums[maxIdx]) {
                maxIdx = i;
            }
        }

        int left = Math.min(minIdx, maxIdx);
        int right = Math.max(minIdx, maxIdx);

        // Strategy 1: both from front
        int front = right + 1;

        // Strategy 2: both from back
        int back = n - left;

        // Strategy 3: left from front, right from back
        int both = (left + 1) + (n - right);

        return Math.min(front, Math.min(back, both));
    }
}