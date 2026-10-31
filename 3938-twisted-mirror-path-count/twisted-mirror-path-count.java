import java.util.Arrays;

class Solution {
    public int uniquePaths(int[][] grid) {

        int m = grid.length;
        int n = grid[0].length;
        int MOD = 1000000007;

        int[][] fromLeft = new int[m][n];
        int[][] fromAbove = new int[m][n];

        for (int i = 0; i < m; i++) {
            Arrays.fill(fromLeft[i], -1);
            Arrays.fill(fromAbove[i], -1);
        }

        // Precompute mirror transitions
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {

                if (grid[i][j] == 0) {
                    fromLeft[i][j] = i * n + j;
                    fromAbove[i][j] = i * n + j;
                } else {

                    if (i + 1 < m) {
                        fromLeft[i][j] = fromAbove[i + 1][j];
                    }

                    if (j + 1 < n) {
                        fromAbove[i][j] = fromLeft[i][j + 1];
                    }
                }
            }
        }

        long[][] dp = new long[m][n];
        dp[0][0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (dp[i][j] == 0) {
                    continue;
                }

                // Attempt to move right
                if (j + 1 < n) {
                    int dest = fromLeft[i][j + 1];

                    if (dest != -1) {
                        int r = dest / n;
                        int c = dest % n;

                        dp[r][c] = (dp[r][c] + dp[i][j]) % MOD;
                    }
                }

                // Attempt to move down
                if (i + 1 < m) {
                    int dest = fromAbove[i + 1][j];

                    if (dest != -1) {
                        int r = dest / n;
                        int c = dest % n;

                        dp[r][c] = (dp[r][c] + dp[i][j]) % MOD;
                    }
                }
            }
        }

        return (int) dp[m - 1][n - 1];
    }
}