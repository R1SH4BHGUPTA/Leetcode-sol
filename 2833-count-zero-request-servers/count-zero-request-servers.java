import java.util.*;

class Solution {
    public int[] countServers(int n, int[][] logs, int x, int[] queries) {

        Arrays.sort(logs, (a, b) -> Integer.compare(a[1], b[1]));

        int[][] q = new int[queries.length][2];

        for (int i = 0; i < queries.length; i++) {
            q[i][0] = queries[i];
            q[i][1] = i;
        }

        Arrays.sort(q, (a, b) -> Integer.compare(a[0], b[0]));

        int[] ans = new int[queries.length];
        int[] freq = new int[n + 1];

        int left = 0;
        int right = 0;
        int active = 0;

        for (int i = 0; i < q.length; i++) {

            int start = q[i][0] - x;
            int end = q[i][0];

            while (right < logs.length && logs[right][1] <= end) {

                int server = logs[right][0];

                if (freq[server] == 0) {
                    active++;
                }

                freq[server]++;
                right++;
            }

            while (left < right && logs[left][1] < start) {

                int server = logs[left][0];

                freq[server]--;

                if (freq[server] == 0) {
                    active--;
                }

                left++;
            }

            ans[q[i][1]] = n - active;
        }

        return ans;
    }
}