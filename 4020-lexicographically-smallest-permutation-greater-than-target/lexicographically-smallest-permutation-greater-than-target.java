class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int n = s.length();

        // Try the first differing position from right to left.
        for (int i = n - 1; i >= 0; i--) {

            int[] freq = new int[26];

            // Count all characters of s.
            for (char c : s.toCharArray()) {
                freq[c - 'a']++;
            }

            // Match target[0 ... i-1].
            boolean possible = true;

            for (int j = 0; j < i; j++) {
                int c = target.charAt(j) - 'a';

                if (freq[c] == 0) {
                    possible = false;
                    break;
                }

                freq[c]--;
            }

            if (!possible) {
                continue;
            }

            // Find the smallest character greater than target[i].
            int current = target.charAt(i) - 'a';
            int greater = -1;

            for (int c = current + 1; c < 26; c++) {
                if (freq[c] > 0) {
                    greater = c;
                    break;
                }
            }

            if (greater == -1) {
                continue;
            }

            // Build answer.
            StringBuilder ans = new StringBuilder();

            // Same prefix as target.
            ans.append(target, 0, i);

            // First character that makes answer greater.
            ans.append((char) ('a' + greater));
            freq[greater]--;

            // Put remaining characters in sorted order.
            for (int c = 0; c < 26; c++) {
                while (freq[c] > 0) {
                    ans.append((char) ('a' + c));
                    freq[c]--;
                }
            }

            return ans.toString();
        }

        return "";
    }
}