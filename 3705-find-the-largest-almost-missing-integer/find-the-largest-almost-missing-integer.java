class Solution {
    public int largestInteger(int[] nums, int k) {
        int n = nums.length;

        int[] count = new int[51];

        for (int i = 0; i <= n - k; i++) {
            boolean[] present = new boolean[51];

            // Mark distinct numbers in this window
            for (int j = i; j < i + k; j++) {
                present[nums[j]] = true;
            }

            // Count this window for each distinct number
            for (int x = 0; x <= 50; x++) {
                if (present[x]) {
                    count[x]++;
                }
            }
        }

        int ans = -1;

        // Since we go from small to large,
        // the last valid number will be the largest.
        for (int x = 0; x <= 50; x++) {
            if (count[x] == 1) {
                ans = x;
            }
        }

        return ans;
    }
}