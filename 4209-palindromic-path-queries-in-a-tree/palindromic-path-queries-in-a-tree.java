
import java.util.*;

class Solution {
    int n, LOG, timer;
    int[] tin, tout, depth, parent, bit;
    int[][] up;
    char[] chars;
    List<Integer>[] adj;

    public List<Boolean> palindromePath(int n, int[][] edges, String s, String[] queries) {
        this.n = n;
        chars = s.toCharArray();
        adj = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            adj[e[0]].add(e[1]);
            adj[e[1]].add(e[0]);
        }

        LOG = 1;
        while ((1 << LOG) <= n) {
            LOG++;
        }

        tin = new int[n];
        tout = new int[n];
        depth = new int[n];
        parent = new int[n];
        up = new int[LOG][n];
        bit = new int[n + 2];

        buildEulerTour();

        for (int u = 0; u < n; u++) {
            rangeXor(tin[u], tout[u], 1 << (chars[u] - 'a'));
        }

        List<Boolean> answer = new ArrayList<>();

        for (String query : queries) {
            String[] parts = query.split(" ");
            int u = Integer.parseInt(parts[1]);

            if (parts[0].equals("update")) {
                char c = parts[2].charAt(0);

                if (chars[u] != c) {
                    int delta = (1 << (chars[u] - 'a'))
                            ^ (1 << (c - 'a'));

                    rangeXor(tin[u], tout[u], delta);
                    chars[u] = c;
                }
            } else {
                int v = Integer.parseInt(parts[2]);
                int lca = getLCA(u, v);

                int mask = prefixXor(tin[u])
                        ^ prefixXor(tin[v])
                        ^ (1 << (chars[lca] - 'a'));

                answer.add((mask & (mask - 1)) == 0);
            }
        }

        return answer;
    }

    void buildEulerTour() {
        timer = 0;

        int[] stackNode = new int[n];
        int[] stackParent = new int[n];
        int[] stackNext = new int[n];

        int top = 0;
        stackNode[0] = 0;
        stackParent[0] = -1;
        stackNext[0] = 0;

        parent[0] = 0;
        setAncestors(0, 0);
        tin[0] = ++timer;

        while (top >= 0) {
            int u = stackNode[top];

            if (stackNext[top] < adj[u].size()) {
                int v = adj[u].get(stackNext[top]++);

                if (v == stackParent[top]) {
                    continue;
                }

                depth[v] = depth[u] + 1;
                parent[v] = u;
                setAncestors(v, u);
                tin[v] = ++timer;

                top++;
                stackNode[top] = v;
                stackParent[top] = u;
                stackNext[top] = 0;
            } else {
                tout[u] = timer;
                top--;
            }
        }
    }

    void setAncestors(int u, int p) {
        up[0][u] = p;

        for (int j = 1; j < LOG; j++) {
            up[j][u] = up[j - 1][up[j - 1][u]];
        }
    }

    int getLCA(int u, int v) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int diff = depth[u] - depth[v];

        for (int j = 0; j < LOG; j++) {
            if ((diff & (1 << j)) != 0) {
                u = up[j][u];
            }
        }

        if (u == v) {
            return u;
        }

        for (int j = LOG - 1; j >= 0; j--) {
            if (up[j][u] != up[j][v]) {
                u = up[j][u];
                v = up[j][v];
            }
        }

        return parent[u];
    }

    void fenwickUpdate(int i, int mask) {
        while (i < bit.length) {
            bit[i] ^= mask;
            i += i & -i;
        }
    }

    void rangeXor(int l, int r, int mask) {
        fenwickUpdate(l, mask);

        if (r + 1 <= n) {
            fenwickUpdate(r + 1, mask);
        }
    }

    int prefixXor(int i) {
        int mask = 0;

        while (i > 0) {
            mask ^= bit[i];
            i -= i & -i;
        }

        return mask;
    }
}
