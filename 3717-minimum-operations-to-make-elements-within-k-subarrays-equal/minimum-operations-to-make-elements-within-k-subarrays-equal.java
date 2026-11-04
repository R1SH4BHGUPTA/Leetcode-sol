import java.util.*;

class Solution {

    class Fenwick {
        long[] tree;
        int n;

        Fenwick(int n) {
            this.n = n;
            tree = new long[n + 1];
        }

        void update(int i, long val) {
            while (i <= n) {
                tree[i] += val;
                i += i & -i;
            }
        }

        long query(int i) {
            long sum = 0;

            while (i > 0) {
                sum += tree[i];
                i -= i & -i;
            }

            return sum;
        }

        int kth(int k) {
            int idx = 0;

            for (int bit = Integer.highestOneBit(n); bit > 0; bit >>= 1) {

                int next = idx + bit;

                if (next <= n && tree[next] < k) {
                    idx = next;
                    k -= (int) tree[next];
                }
            }

            return idx + 1;
        }
    }

    public long minOperations(int[] nums, int x, int k) {

        int n = nums.length;

        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        int m = 0;

        for (int v : sorted) {
            if (m == 0 || sorted[m - 1] != v) {
                sorted[m++] = v;
            }
        }

        int[] ids = new int[n];

        for (int i = 0; i < n; i++) {
            ids[i] = Arrays.binarySearch(sorted, 0, m, nums[i]) + 1;
        }

        Fenwick count = new Fenwick(m);
        Fenwick sum = new Fenwick(m);

        long[] cost = new long[n - x + 1];

        long total = 0;

        for (int i = 0; i < x; i++) {
            count.update(ids[i], 1);
            sum.update(ids[i], nums[i]);
            total += nums[i];
        }

        for (int i = 0; i <= n - x; i++) {

            int medianIdx = count.kth((x + 1) / 2);
            long median = sorted[medianIdx - 1];

            long leftCount = count.query(medianIdx);
            long leftSum = sum.query(medianIdx);

            long rightCount = x - leftCount;
            long rightSum = total - leftSum;

            cost[i] = median * leftCount - leftSum
                    + rightSum - median * rightCount;

            if (i < n - x) {

                count.update(ids[i], -1);
                sum.update(ids[i], -nums[i]);

                count.update(ids[i + x], 1);
                sum.update(ids[i + x], nums[i + x]);

                total += (long) nums[i + x] - nums[i];
            }
        }

        long INF = Long.MAX_VALUE / 4;

        long[] prev = new long[n + 1];
        long[] curr = new long[n + 1];

        for (int j = 1; j <= k; j++) {

            Arrays.fill(curr, INF);

            for (int i = 1; i <= n; i++) {

                curr[i] = curr[i - 1];

                if (i >= x && prev[i - x] != INF) {

                    long take = prev[i - x] + cost[i - x];

                    curr[i] = Math.min(curr[i], take);
                }
            }

            long[] temp = prev;
            prev = curr;
            curr = temp;
        }

        return prev[n];
    }
}