class Solution {
    public int uniqueXorTriplets(int[] nums) {
        final int MAX = 2048;

        boolean[][] dp = new boolean[4][MAX];
        dp[0][0] = true;

        for (int v : nums) {
            for (int cnt = 2; cnt >= 0; cnt--) {
                for (int x = 0; x < MAX; x++) {
                    if (dp[cnt][x]) {
                        dp[cnt + 1][x ^ v] = true;
                    }
                }
            }
        }

        boolean[] seen = new boolean[MAX];

        // XORs from one element (covers i=j=k and two-equal cases)
        for (int x = 0; x < MAX; x++) {
            if (dp[1][x]) seen[x] = true;
        }

        // XORs from three distinct elements
        for (int x = 0; x < MAX; x++) {
            if (dp[3][x]) seen[x] = true;
        }

        int ans = 0;
        for (boolean b : seen) {
            if (b) ans++;
        }
        return ans;
    }
}