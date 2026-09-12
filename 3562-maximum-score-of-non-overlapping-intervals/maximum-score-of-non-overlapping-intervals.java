import java.util.*;

class Solution {

    public int[] maximumWeight(List<List<Integer>> intervals) {

        int n = intervals.size();

        // [left, right, weight, originalIndex]
        int[][] a = new int[n][4];

        for (int i = 0; i < n; i++) {
            a[i][0] = intervals.get(i).get(0);
            a[i][1] = intervals.get(i).get(1);
            a[i][2] = intervals.get(i).get(2);
            a[i][3] = i;
        }

        // Sort by starting position
        Arrays.sort(a, (x, y) -> {
            if (x[0] != y[0]) {
                return Integer.compare(x[0], y[0]);
            }
            return Integer.compare(x[1], y[1]);
        });

        // Find next non-overlapping interval
        int[] next = new int[n];

        for (int i = 0; i < n; i++) {
            next[i] = lowerBound(a, (long) a[i][1] + 1);
        }

        /*
         * dp[i][k] =
         * maximum score from intervals i...n-1
         * when we can still choose at most k intervals.
         */
        long[][] dp = new long[n + 1][5];

        /*
         * ans[i][k] =
         * lexicographically smallest list achieving dp[i][k]
         */
        @SuppressWarnings("unchecked")
        ArrayList<Integer>[][] ans = new ArrayList[n + 1][5];

        // IMPORTANT:
        // Initialize every state so addAll() never receives null.
        for (int i = 0; i <= n; i++) {
            for (int k = 0; k <= 4; k++) {
                ans[i][k] = new ArrayList<>();
            }
        }

        // Build DP from right to left
        for (int i = n - 1; i >= 0; i--) {

            for (int k = 1; k <= 4; k++) {

                // -------------------------
                // Option 1: Skip interval i
                // -------------------------
                long skipScore = dp[i + 1][k];

                ArrayList<Integer> skipList =
                        new ArrayList<>(ans[i + 1][k]);

                // -------------------------
                // Option 2: Take interval i
                // -------------------------
                long takeScore =
                        a[i][2] + dp[next[i]][k - 1];

                ArrayList<Integer> takeList =
                        new ArrayList<>();

                takeList.add(a[i][3]);

                takeList.addAll(ans[next[i]][k - 1]);

                // Sort because the final answer is compared
                // using original interval indices.
                Collections.sort(takeList);

                // -------------------------
                // Choose better option
                // -------------------------
                if (takeScore > skipScore) {

                    dp[i][k] = takeScore;
                    ans[i][k] = takeList;

                } else if (takeScore < skipScore) {

                    dp[i][k] = skipScore;
                    ans[i][k] = skipList;

                } else {

                    // Same score.
                    // Choose lexicographically smaller indices.

                    dp[i][k] = takeScore;

                    if (compareLexicographically(takeList, skipList) < 0) {
                        ans[i][k] = takeList;
                    } else {
                        ans[i][k] = skipList;
                    }
                }
            }
        }

        ArrayList<Integer> result = ans[0][4];

        int[] res = new int[result.size()];

        for (int i = 0; i < result.size(); i++) {
            res[i] = result.get(i);
        }

        return res;
    }

    /*
     * Find first interval whose left > right of current interval.
     *
     * We need:
     *
     *      left > right
     *
     * NOT:
     *
     *      left >= right
     *
     * because touching intervals are considered overlapping.
     */
    private int lowerBound(int[][] a, long target) {

        int low = 0;
        int high = a.length;

        while (low < high) {

            int mid = low + (high - low) / 2;

            if (a[mid][0] >= target) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        return low;
    }

    /*
     * Lexicographical comparison of two sorted lists.
     */
    private int compareLexicographically(
            ArrayList<Integer> a,
            ArrayList<Integer> b) {

        int size = Math.min(a.size(), b.size());

        for (int i = 0; i < size; i++) {

            if (!a.get(i).equals(b.get(i))) {
                return Integer.compare(a.get(i), b.get(i));
            }
        }

        // If one is a prefix of the other,
        // shorter one is lexicographically smaller.
        return Integer.compare(a.size(), b.size());
    }
}