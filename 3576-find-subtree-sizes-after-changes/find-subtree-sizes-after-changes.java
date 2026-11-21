
class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;

        ArrayList<Integer>[] tree = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            tree[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            tree[parent[i]].add(i);
        }

        int[] newParent = new int[n];
        int[] last = new int[26];
        int[] oldLast = new int[n];

        Arrays.fill(last, -1);

        int[] stack = new int[2 * n + 5];
        int top = 0;

        stack[top++] = 0;

        while (top > 0) {
            int v = stack[--top];

            if (v >= 0) {
                int ch = s.charAt(v) - 'a';

                oldLast[v] = last[ch];

                if (last[ch] != -1) {
                    newParent[v] = last[ch];
                } else {
                    newParent[v] = parent[v];
                }

                last[ch] = v;

                stack[top++] = ~v;

                for (int child : tree[v]) {
                    stack[top++] = child;
                }

            } else {
                int node = ~v;
                int ch = s.charAt(node) - 'a';

                last[ch] = oldLast[node];
            }
        }

        ArrayList<Integer>[] newTree = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            newTree[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            newTree[newParent[i]].add(i);
        }

        int[] order = new int[n];
        top = 0;

        stack[top++] = 0;

        int idx = 0;

        while (top > 0) {
            int node = stack[--top];

            order[idx++] = node;

            for (int child : newTree[node]) {
                stack[top++] = child;
            }
        }

        int[] ans = new int[n];
        Arrays.fill(ans, 1);

        for (int i = n - 1; i > 0; i--) {
            int node = order[i];

            ans[newParent[node]] += ans[node];
        }

        return ans;
    }
}
