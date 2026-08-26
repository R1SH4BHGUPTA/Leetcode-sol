class Solution {
    public String shortestBeautifulSubstring(String s, int k) {
        int n = s.length();
        String ans = "";

        for (int i = 0; i < n; i++) {
            int ones = 0;

            for (int j = i; j < n; j++) {
                if (s.charAt(j) == '1') {
                    ones++;
                }

                // We have exactly k ones
                if (ones == k) {
                    String curr = s.substring(i, j + 1);

                    // First valid answer, or shorter answer
                    if (ans.isEmpty()
                            || curr.length() < ans.length()
                            || (curr.length() == ans.length()
                                && curr.compareTo(ans) < 0)) {
                        ans = curr;
                    }

                    // Extending further only makes it longer
                    break;
                }
            }
        }

        return ans;
    }
}