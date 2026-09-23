class Solution {
    public int minOperations(int[] nums, int x) {
        int sum = 0;
        for (int n : nums) sum += n;

        int target = sum - x;
        if (target < 0) return -1;
        if (target == 0) return nums.length;

        int l = 0, cur = 0, max = -1;

        for (int r = 0; r < nums.length; r++) {
            cur += nums[r];

            while (l <= r && cur > target)
                cur -= nums[l++];

            if (cur == target)
                max = Math.max(max, r - l + 1);
        }

        return max == -1 ? -1 : nums.length - max;
    }
}