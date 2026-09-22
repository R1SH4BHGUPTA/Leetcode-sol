class Solution {
    int n, k;
    int[][] cnt;
    int[] prod;

    void build(int node, int l, int r, int[] nums) {
        if (l == r) {
            int rem = nums[l] % k;
            prod[node] = rem;
            cnt[node][rem] = 1;
            return;
        }

        int mid = (l + r) / 2;
        build(node * 2, l, mid, nums);
        build(node * 2 + 1, mid + 1, r, nums);
        merge(node);
    }

    void merge(int node) {
        int left = node * 2;
        int right = node * 2 + 1;

        prod[node] = (prod[left] * prod[right]) % k;

        for (int i = 0; i < k; i++) {
            cnt[node][i] = cnt[left][i];
        }

        for (int i = 0; i < k; i++) {
            if (cnt[right][i] == 0) continue;

            int rem = (prod[left] * i) % k;
            cnt[node][rem] += cnt[right][i];
        }
    }

    void update(int node, int l, int r, int idx, int value) {
        if (l == r) {
            int rem = value % k;

            for (int i = 0; i < k; i++) {
                cnt[node][i] = 0;
            }

            cnt[node][rem] = 1;
            prod[node] = rem;
            return;
        }

        int mid = (l + r) / 2;

        if (idx <= mid) {
            update(node * 2, l, mid, idx, value);
        } else {
            update(node * 2 + 1, mid + 1, r, idx, value);
        }

        merge(node);
    }

    Node query(int node, int l, int r, int ql, int qr) {
        if (ql <= l && r <= qr) {
            Node res = new Node(k);

            res.prod = prod[node];

            for (int i = 0; i < k; i++) {
                res.cnt[i] = cnt[node][i];
            }

            return res;
        }

        int mid = (l + r) / 2;

        if (qr <= mid) {
            return query(node * 2, l, mid, ql, qr);
        }

        if (ql > mid) {
            return query(node * 2 + 1, mid + 1, r, ql, qr);
        }

        Node left = query(node * 2, l, mid, ql, qr);
        Node right = query(node * 2 + 1, mid + 1, r, ql, qr);

        Node res = new Node(k);

        res.prod = (left.prod * right.prod) % k;

        for (int i = 0; i < k; i++) {
            res.cnt[i] = left.cnt[i];
        }

        for (int i = 0; i < k; i++) {
            int rem = (left.prod * i) % k;
            res.cnt[rem] += right.cnt[i];
        }

        return res;
    }

    static class Node {
        int prod;
        int[] cnt;

        Node(int k) {
            cnt = new int[k];
        }
    }

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        this.n = nums.length;
        this.k = k;

        cnt = new int[4 * n][k];
        prod = new int[4 * n];

        build(1, 0, n - 1, nums);

        int[] ans = new int[queries.length];

        for (int q = 0; q < queries.length; q++) {
            int index = queries[q][0];
            int value = queries[q][1];
            int start = queries[q][2];
            int x = queries[q][3];

            update(1, 0, n - 1, index, value);

            Node res = query(1, 0, n - 1, start, n - 1);

            ans[q] = res.cnt[x];
        }

        return ans;
    }
}