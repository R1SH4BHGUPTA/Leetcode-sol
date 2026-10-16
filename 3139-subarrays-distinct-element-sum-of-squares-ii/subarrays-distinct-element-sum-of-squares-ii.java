
class Solution {
    static final int MOD = 1_000_000_007;

    public int sumCounts(int[] nums) {
        int n = nums.length;
        long[] sum = new long[4 * n];
        long[] sq = new long[4 * n];
        long[] lazy = new long[4 * n];

        java.util.HashMap<Integer, Integer> last = new java.util.HashMap<>();
        long ans = 0;

        for (int i = 0; i < n; i++) {
            int prev = last.getOrDefault(nums[i], -1);
            update(1, 0, n - 1, prev + 1, i, 1, sum, sq, lazy);

            ans = (ans + sq[1]) % MOD;
            last.put(nums[i], i);
        }

        return (int) ans;
    }

    void update(int node, int start, int end, int l, int r,
                long val, long[] sum, long[] sq, long[] lazy) {
        if (l > end || r < start) return;

        if (l <= start && end <= r) {
            long len = end - start + 1;
            sq[node] = (sq[node] + 2 * val % MOD * sum[node] % MOD
                        + len * val % MOD * val % MOD) % MOD;
            sum[node] = (sum[node] + len * val) % MOD;
            lazy[node] = (lazy[node] + val) % MOD;
            return;
        }

        push(node, start, end, sum, sq, lazy);

        int mid = start + (end - start) / 2;
        update(node * 2, start, mid, l, r, val, sum, sq, lazy);
        update(node * 2 + 1, mid + 1, end, l, r, val, sum, sq, lazy);

        sum[node] = (sum[node * 2] + sum[node * 2 + 1]) % MOD;
        sq[node] = (sq[node * 2] + sq[node * 2 + 1]) % MOD;
    }

    void push(int node, int start, int end,
              long[] sum, long[] sq, long[] lazy) {
        if (lazy[node] == 0 || start == end) return;

        int mid = start + (end - start) / 2;
        apply(node * 2, start, mid, lazy[node], sum, sq, lazy);
        apply(node * 2 + 1, mid + 1, end, lazy[node], sum, sq, lazy);
        lazy[node] = 0;
    }

    void apply(int node, int start, int end, long val,
               long[] sum, long[] sq, long[] lazy) {
        long len = end - start + 1;

        sq[node] = (sq[node] + 2 * val % MOD * sum[node] % MOD
                    + len * val % MOD * val % MOD) % MOD;
        sum[node] = (sum[node] + len * val) % MOD;
        lazy[node] = (lazy[node] + val) % MOD;
    }
}
