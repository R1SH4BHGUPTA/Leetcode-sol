class Solution {

    public String lexPalindromicPermutation(String s, String target) {
        int n = s.length();

        // Count characters in s
        int[] freq = new int[26];

        for (char ch : s.toCharArray()) {
            freq[ch - 'a']++;
        }

        // Check if a palindrome is possible
        int odd = 0;
        int middle = -1;

        for (int i = 0; i < 26; i++) {
            if (freq[i] % 2 == 1) {
                odd++;
                middle = i;
            }
        }

        if (odd > 1) {
            return "";
        }

        /*
         * A palindrome is completely determined by its left half.
         *
         * Example:
         *     left = "ab"
         *     mid  = "c"
         *
         * palindrome = "ab" + "c" + "ba"
         */

        int halfLen = n / 2;

        int[] halfFreq = new int[26];

        for (int i = 0; i < 26; i++) {
            halfFreq[i] = freq[i] / 2;
        }

        /*
         * We need the smallest possible left half such that
         *
         * palindrome > target
         *
         * For the first half, lexicographical comparison works
         * exactly the same as comparing the left halves.
         */

        char[] targetHalf = target.substring(0, halfLen).toCharArray();

        // Try to construct the smallest half >= targetHalf
        String half = buildHalf(halfFreq, targetHalf);

        if (half == null) {
            return "";
        }

        String answer = makePalindrome(half, middle);

        // If it is already strictly greater, we're done.
        if (answer.compareTo(target) > 0) {
            return answer;
        }

        /*
         * Otherwise answer == target.
         *
         * Find the next larger possible half.
         */
        String next = nextHalf(half);

        if (next == null) {
            return "";
        }

        return makePalindrome(next, middle);
    }


    // ---------------------------------------------------------
    // Build the smallest half >= targetHalf
    // ---------------------------------------------------------

    private String buildHalf(int[] originalFreq, char[] targetHalf) {

        int m = targetHalf.length;

        int[] freq = originalFreq.clone();

        char[] result = new char[m];

        for (int i = 0; i < m; i++) {

            int wanted = targetHalf[i] - 'a';

            /*
             * Can we keep the current character equal to target?
             */
            if (freq[wanted] > 0) {

                result[i] = targetHalf[i];
                freq[wanted]--;

            } else {

                /*
                 * We cannot use target[i].
                 *
                 * Try to use the smallest character greater than it.
                 */
                int bigger = -1;

                for (int c = wanted + 1; c < 26; c++) {
                    if (freq[c] > 0) {
                        bigger = c;
                        break;
                    }
                }

                /*
                 * No larger character exists here.
                 *
                 * We must go backwards and increase an
                 * earlier position.
                 */
                if (bigger == -1) {
                    return increaseEarlier(originalFreq, targetHalf, i);
                }

                result[i] = (char) ('a' + bigger);
                freq[bigger]--;

                // Once we are greater, fill the rest minimally.
                int pos = i + 1;

                for (int c = 0; c < 26; c++) {
                    while (freq[c] > 0) {
                        result[pos++] = (char) ('a' + c);
                        freq[c]--;
                    }
                }

                return new String(result);
            }
        }

        /*
         * We matched targetHalf exactly.
         *
         * This gives a palindrome equal to target,
         * so the caller will find the next half.
         */
        return new String(result);
    }


    // ---------------------------------------------------------
    // Increase an earlier position
    // ---------------------------------------------------------

    private String increaseEarlier(
            int[] originalFreq,
            char[] targetHalf,
            int failedAt) {

        int m = targetHalf.length;

        /*
         * remaining[i] = frequencies left after using
         * targetHalf[0 ... i-1].
         */
        int[][] remaining = new int[m + 1][26];

        remaining[0] = originalFreq.clone();

        for (int i = 0; i < m; i++) {

            remaining[i + 1] = remaining[i].clone();

            int c = targetHalf[i] - 'a';

            if (remaining[i + 1][c] == 0) {
                break;
            }

            remaining[i + 1][c]--;
        }

        /*
         * Try increasing the RIGHTMOST possible position.
         *
         * This gives the smallest lexicographical result.
         */
        for (int i = failedAt - 1; i >= 0; i--) {

            int[] freq = remaining[i].clone();

            int current = targetHalf[i] - 'a';

            /*
             * Try the smallest character > current.
             */
            for (int c = current + 1; c < 26; c++) {

                if (freq[c] == 0) {
                    continue;
                }

                freq[c]--;

                char[] result = new char[m];

                // Keep prefix equal to target
                for (int j = 0; j < i; j++) {
                    result[j] = targetHalf[j];
                }

                // Make this position larger
                result[i] = (char) ('a' + c);

                // Fill suffix with smallest characters
                int pos = i + 1;

                for (int x = 0; x < 26; x++) {
                    while (freq[x] > 0) {
                        result[pos++] = (char) ('a' + x);
                        freq[x]--;
                    }
                }

                return new String(result);
            }
        }

        return null;
    }


    // ---------------------------------------------------------
    // Find the next lexicographically larger valid half
    // ---------------------------------------------------------

    private String nextHalf(String half) {

        int n = half.length();

        /*
         * Characters from the suffix become available
         * as we move from right to left.
         */
        int[] freq = new int[26];

        for (int i = n - 1; i >= 0; i--) {

            int current = half.charAt(i) - 'a';

            // Current character can now be used in suffix.
            freq[current]++;

            /*
             * Try to replace half[i] with the smallest
             * available character greater than it.
             */
            for (int c = current + 1; c < 26; c++) {

                if (freq[c] == 0) {
                    continue;
                }

                freq[c]--;

                char[] result = half.toCharArray();

                // Increase position i
                result[i] = (char) ('a' + c);

                // Fill suffix minimally
                int pos = i + 1;

                for (int x = 0; x < 26; x++) {
                    while (freq[x] > 0) {
                        result[pos++] = (char) ('a' + x);
                        freq[x]--;
                    }
                }

                return new String(result);
            }
        }

        return null;
    }


    // ---------------------------------------------------------
    // Construct the full palindrome
    // ---------------------------------------------------------

    private String makePalindrome(String half, int middle) {

        StringBuilder sb = new StringBuilder();

        // Left half
        sb.append(half);

        // Middle character (only for odd length)
        if (middle != -1) {
            sb.append((char) ('a' + middle));
        }

        // Right half
        sb.append(new StringBuilder(half).reverse());

        return sb.toString();
    }
}