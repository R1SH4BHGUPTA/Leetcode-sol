class Solution {
    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;
        int[] sa = buildSuffixArray(nums);
        int[] rank = new int[n];

        for (int i = 0; i < n; i++) {
            rank[sa[i]] = i;
        }

        int[] lcp = new int[n - 1];
        int h = 0;

        for (int i = 0; i < n; i++) {
            int r = rank[i];

            if (r == n - 1) {
                h = 0;
                continue;
            }

            int j = sa[r + 1];

            while (i + h < n && j + h < n &&
                   nums[i + h] == nums[j + h]) {
                h++;
            }

            lcp[r] = h;

            if (h > 0) {
                h--;
            }
        }

        int ans = n;

        for (int r = 0; r < n; r++) {
            int left = (r > 0) ? lcp[r - 1] : 0;
            int right = (r < n - 1) ? lcp[r] : 0;

            int need = Math.max(left, right) + 1;

            if (need <= n - sa[r]) {
                ans = Math.min(ans, need);
            }
        }

        return ans;
    }

    private int[] buildSuffixArray(int[] nums) {
        int n = nums.length;
        int total = n + 1;

        int[] p = new int[total];
        int[] c = new int[total];
        int[] pn = new int[total];
        int[] cn = new int[total];

        int[] cnt = new int[Math.max(100002, total)];

        for (int i = 0; i < n; i++) {
            cnt[nums[i] + 1]++;
        }

        cnt[0]++;

        for (int i = 1; i < cnt.length; i++) {
            cnt[i] += cnt[i - 1];
        }

        for (int i = total - 1; i >= 0; i--) {
            int val = (i == n) ? 0 : nums[i] + 1;
            p[--cnt[val]] = i;
        }

        int classes = 1;
        c[p[0]] = 0;

        for (int i = 1; i < total; i++) {
            int cur = p[i];
            int prev = p[i - 1];

            int curVal = (cur == n) ? 0 : nums[cur] + 1;
            int prevVal = (prev == n) ? 0 : nums[prev] + 1;

            if (curVal != prevVal) {
                classes++;
            }

            c[cur] = classes - 1;
        }

        for (int len = 1; len < total; len <<= 1) {
            for (int i = 0; i < total; i++) {
                pn[i] = p[i] - len;

                if (pn[i] < 0) {
                    pn[i] += total;
                }
            }

            cnt = new int[classes];

            for (int i = 0; i < total; i++) {
                cnt[c[pn[i]]]++;
            }

            for (int i = 1; i < classes; i++) {
                cnt[i] += cnt[i - 1];
            }

            for (int i = total - 1; i >= 0; i--) {
                int x = pn[i];
                p[--cnt[c[x]]] = x;
            }

            cn[p[0]] = 0;
            int newClasses = 1;

            for (int i = 1; i < total; i++) {
                int cur = p[i];
                int prev = p[i - 1];

                int curMid = (cur + len) % total;
                int prevMid = (prev + len) % total;

                if (c[cur] != c[prev] ||
                    c[curMid] != c[prevMid]) {
                    newClasses++;
                }

                cn[cur] = newClasses - 1;
            }

            int[] temp = c;
            c = cn;
            cn = temp;

            classes = newClasses;
        }

        int[] sa = new int[n];

        for (int i = 1; i < total; i++) {
            sa[i - 1] = p[i];
        }

        return sa;
    }
}