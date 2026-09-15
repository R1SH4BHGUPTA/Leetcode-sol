class Solution {
    public int maxPalindromes(String s, int k) {
        int n = s.length();

        // pal[i][j] = true if s[i...j] is a palindrome
        boolean[][] pal = new boolean[n][n];

        // Every single character is a palindrome
        for (int i = 0; i < n; i++) {
            pal[i][i] = true;
        }

        // Check substrings of length 2 or more
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1;

                if (s.charAt(i) == s.charAt(j)) {
                    if (len == 2) {
                        pal[i][j] = true;
                    } else {
                        pal[i][j] = pal[i + 1][j - 1];
                    }
                }
            }
        }

        // dp[i] = maximum number of non-overlapping palindromes
        // in s[0...i-1]
        int[] dp = new int[n + 1];

        for (int i = 1; i <= n; i++) {

            // Don't take a palindrome ending at i-1
            dp[i] = dp[i - 1];

            // Try every palindrome ending at i-1
            for (int j = 0; j < i; j++) {

                int length = i - j;

                if (length >= k && pal[j][i - 1]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

        return dp[n];
    }
}