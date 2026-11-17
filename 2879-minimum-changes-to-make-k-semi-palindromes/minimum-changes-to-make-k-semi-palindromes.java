
class Solution {
    public int minimumChanges(String s, int k) {
        int n = s.length();
        int[][] cost = new int[n][n];

        for (int len = 2; len <= n; len++) {
            for (int l = 0; l + len <= n; l++) {
                int r = l + len - 1;
                cost[l][r] = Integer.MAX_VALUE;

                for (int d = 1; d < len; d++) {
                    if (len % d != 0)
                        continue;

                    int changes = 0;
                    int groups = d;
                    int size = len / d;

                    for (int i = 0; i < groups; i++) {
                        for (int j = 0; j < size / 2; j++) {
                            int a = l + i + j * d;
                            int b = l + i + (size - 1 - j) * d;

                            if (s.charAt(a) != s.charAt(b)) {
                                changes++;
                            }
                        }
                    }

                    cost[l][r] = Math.min(cost[l][r], changes);
                }
            }
        }

        int[][] dp = new int[n + 1][k + 1];

        for (int[] row : dp) {
            java.util.Arrays.fill(row, 1000000);
        }

        dp[0][0] = 0;

        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= k; j++) {
                for (int p = 0; p <= i - 2; p++) {
                    if (dp[p][j - 1] == 1000000)
                        continue;

                    dp[i][j] = Math.min(
                            dp[i][j],
                            dp[p][j - 1] + cost[p][i - 1]);
                }
            }
        }

        return dp[n][k];
    }
}
