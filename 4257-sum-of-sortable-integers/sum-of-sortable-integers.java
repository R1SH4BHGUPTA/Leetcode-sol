class Solution {
    public int sortableIntegers(int[] nums) {
        int n = nums.length;
        int ans = 0;

        for (int k = 1; k <= n; k++) {
            if (n % k != 0) {
                continue;
            }

            boolean valid = true;
            int prev = Integer.MIN_VALUE;

            for (int start = 0; start < n; start += k) {
                int descents = 0;
                int rotationStart = 0;

                for (int i = 0; i < k; i++) {
                    int current = start + i;
                    int next = start + (i + 1) % k;

                    if (nums[current] > nums[next]) {
                        descents++;
                        rotationStart = (i + 1) % k;
                    }
                }

                if (descents > 1) {
                    valid = false;
                    break;
                }

                for (int i = 0; i < k; i++) {
                    int index = start + (rotationStart + i) % k;
                    int value = nums[index];

                    if (value < prev) {
                        valid = false;
                        break;
                    }

                    prev = value;
                }

                if (!valid) {
                    break;
                }
            }

            if (valid) {
                ans += k;
            }
        }

        return ans;
    }
}