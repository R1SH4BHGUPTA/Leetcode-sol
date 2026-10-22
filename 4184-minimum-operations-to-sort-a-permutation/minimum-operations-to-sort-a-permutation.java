
class Solution {
    public int minOperations(int[] nums) {
        int n = nums.length;
        int zero = 0;
        int last = 0;

        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) zero = i;
            if (nums[i] == n - 1) last = i;
        }

        boolean forward = true;
        boolean backward = true;

        for (int i = 0; i < n; i++) {
            int next = (i + 1) % n;

            if (nums[next] != (nums[i] + 1) % n) {
                forward = false;
            }

            if (nums[next] != (nums[i] - 1 + n) % n) {
                backward = false;
            }
        }

        if (forward) {
            int rotations = zero;
            return Math.min(rotations, n - rotations + 2);
        }

        if (backward) {
            int rotations = (n - last) % n;
            return 1 + Math.min(rotations, n - rotations);
        }

        return -1;
    }
}
