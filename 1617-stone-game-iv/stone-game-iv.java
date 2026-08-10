class Solution {
    public boolean winnerSquareGame(int n) {
        boolean[] dp = new boolean[n + 1];

        // dp[0] = false
        // No stones -> player cannot move -> loses

        for (int i = 1; i <= n; i++) {

            for (int j = 1; j * j <= i; j++) {

                // If removing j*j stones makes opponent lose
                if (!dp[i - j * j]) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[n];
    }
}