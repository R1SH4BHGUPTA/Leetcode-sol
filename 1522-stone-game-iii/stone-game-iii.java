class Solution {
    public String stoneGameIII(int[] stoneValue) {
        int n = stoneValue.length;
        int[] dp = new int[4];

        for (int i = n - 1; i >= 0; i--) {
            dp[i % 4] = Integer.MIN_VALUE;
            int sum = 0;

            for (int j = i; j < Math.min(n, i + 3); j++) {
                sum += stoneValue[j];
                dp[i % 4] = Math.max(dp[i % 4], sum - dp[(j + 1) % 4]);
            }
        }

        if (dp[0] > 0) return "Alice";
        if (dp[0] < 0) return "Bob";
        return "Tie";
    }
}