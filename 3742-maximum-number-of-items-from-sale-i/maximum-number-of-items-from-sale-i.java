class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int minPrice = Integer.MAX_VALUE;

        for (int[] item : items) {
            minPrice = Math.min(minPrice, item[1]);
        }

        int[] gain = new int[n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j && items[j][0] % items[i][0] == 0) {
                    gain[i]++;
                }
            }
        }

        int INF = -1000000000;
        int[][] dp = new int[2][budget + 1];

        java.util.Arrays.fill(dp[0], INF);
        java.util.Arrays.fill(dp[1], INF);

        dp[0][0] = 0;

        for (int i = 0; i < n; i++) {
            int price = items[i][1];
            int value = gain[i] + 1;
            int flag = (price == minPrice) ? 1 : 0;

            for (int b = budget - price; b >= 0; b--) {
                for (int s = 0; s < 2; s++) {
                    if (dp[s][b] == INF) {
                        continue;
                    }

                    int ns = s | flag;

                    dp[ns][b + price] = Math.max(
                            dp[ns][b + price],
                            dp[s][b] + value);
                }
            }
        }

        int ans = 0;

        for (int b = 0; b <= budget; b++) {
            if (dp[1][b] != INF) {
                ans = Math.max(
                        ans,
                        dp[1][b] + (budget - b) / minPrice);
            }

            if (budget - b < minPrice && dp[0][b] != INF) {
                ans = Math.max(ans, dp[0][b]);
            }
        }

        return ans;
    }
}