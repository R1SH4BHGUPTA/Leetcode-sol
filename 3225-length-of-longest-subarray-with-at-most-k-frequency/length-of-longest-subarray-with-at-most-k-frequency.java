import java.util.*;

class Solution {
    public int maxSubarrayLength(int[] nums, int k) {
        HashMap<Integer, Integer> freq = new HashMap<>();

        int left = 0;
        int ans = 0;

        for (int right = 0; right < nums.length; right++) {
            int x = nums[right];

            freq.put(x, freq.getOrDefault(x, 0) + 1);

            // If x occurs more than k times,
            // shrink the window from the left.
            while (freq.get(x) > k) {
                int leftValue = nums[left];

                freq.put(leftValue, freq.get(leftValue) - 1);
                left++;

            }

            ans = Math.max(ans, right - left + 1);
        }

        return ans;
    }
}