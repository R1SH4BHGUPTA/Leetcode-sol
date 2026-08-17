class Solution {
    public int stoneGameV(int[] stoneValue) {
        int n = stoneValue.length;

        // Prefix sum for O(1) range sum
        int[] prefix = new int[n + 1];

        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + stoneValue[i];
        }

        // dp[l][r] = maximum score for subarray l...r
        int[][] dp = new int[n][n];

        // Length of current interval
        for (int len = 2; len <= n; len++) {

            for (int l = 0; l + len <= n; l++) {
                int r = l + len - 1;

                int total = prefix[r + 1] - prefix[l];

                // Try every split
                for (int k = l; k < r; k++) {

                    int left = prefix[k + 1] - prefix[l];
                    int right = total - left;

                    if (left < right) {
                        // Right is discarded
                        dp[l][r] = Math.max(
                            dp[l][r],
                            left + dp[l][k]
                        );

                    } else if (left > right) {
                        // Left is discarded
                        dp[l][r] = Math.max(
                            dp[l][r],
                            right + dp[k + 1][r]
                        );

                    } else {
                        // Alice can choose either side
                        dp[l][r] = Math.max(
                            dp[l][r],
                            Math.max(
                                left + dp[l][k],
                                right + dp[k + 1][r]
                            )
                        );
                    }
                }
            }
        }

        return dp[0][n - 1];
    }
}