import java.util.*;

class Solution {
    private static final int STATES = 5;
    private static final int W = STATES * STATES;
    private static final int NEG = -1_000_000_000;

    public List<Integer> maxActiveSectionsAfterTrade(String s, int[][] queries) {
        int n = s.length();
        int totalOnes = 0;

        for (char c : s.toCharArray()) {
            if (c == '1') totalOnes++;
        }

        int size = 1;
        while (size < n) size <<= 1;

        int[][] tree = new int[size * 2][W];

        for (int i = 0; i < tree.length; i++) {
            setIdentity(tree[i]);
        }

        for (int i = 0; i < n; i++) {
            setLeaf(tree[size + i], s.charAt(i));
        }

        for (int i = size - 1; i > 0; i--) {
            compose(tree[i << 1], tree[i << 1 | 1], tree[i]);
        }

        List<Integer> ans = new ArrayList<>();

        for (int[] q : queries) {
            int l = q[0] + size;
            int r = q[1] + size;

            int[] left = new int[W];
            int[] right = new int[W];
            int[] tmp = new int[W];

            setIdentity(left);
            setIdentity(right);

            while (l <= r) {
                if ((l & 1) == 1) {
                    compose(left, tree[l++], tmp);
                    int[] swap = left;
                    left = tmp;
                    tmp = swap;
                }

                if ((r & 1) == 0) {
                    compose(tree[r--], right, tmp);
                    int[] swap = right;
                    right = tmp;
                    tmp = swap;
                }

                l >>= 1;
                r >>= 1;
            }

            compose(left, right, tmp);

            int gain = Math.max(tmp[0 * STATES + 3], tmp[0 * STATES + 4]);
            gain = Math.max(gain, 0);

            ans.add(totalOnes + gain);
        }

        return ans;
    }

    private void setIdentity(int[] m) {
        Arrays.fill(m, NEG);
        for (int i = 0; i < STATES; i++) {
            m[i * STATES + i] = 0;
        }
    }

    private void setLeaf(int[] m, char c) {
        Arrays.fill(m, NEG);

        if (c == '0') {
            m[0 * STATES + 0] = 0;
            m[0 * STATES + 1] = 1;
            m[1 * STATES + 1] = 1;
            m[2 * STATES + 3] = 1;
            m[3 * STATES + 3] = 1;
            m[4 * STATES + 4] = 0;
        } else {
            m[0 * STATES + 0] = 0;
            m[1 * STATES + 2] = 0;
            m[2 * STATES + 2] = 0;
            m[3 * STATES + 4] = 0;
            m[4 * STATES + 4] = 0;
        }
    }

    private void compose(int[] a, int[] b, int[] out) {
        Arrays.fill(out, NEG);

        for (int i = 0; i < STATES; i++) {
            for (int j = i; j < STATES; j++) {
                int best = NEG;

                for (int k = i; k <= j; k++) {
                    if (a[i * STATES + k] == NEG || b[k * STATES + j] == NEG) {
                        continue;
                    }

                    best = Math.max(best, a[i * STATES + k] + b[k * STATES + j]);
                }

                out[i * STATES + j] = best;
            }
        }
    }
}