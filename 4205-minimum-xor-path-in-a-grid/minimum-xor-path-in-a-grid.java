
class Solution {
    boolean[][][] visited;
    int ans = 1024;

    public int minCost(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        visited = new boolean[m][n][1024];
        ans = 1024;

        dfs(grid, 0, 0, 0);

        return ans;
    }

    void dfs(int[][] grid, int i, int j, int xor) {
        if (ans == 0) {
            return;
        }

        xor ^= grid[i][j];

        if (visited[i][j][xor]) {
            return;
        }

        visited[i][j][xor] = true;

        int m = grid.length;
        int n = grid[0].length;

        if (i == m - 1 && j == n - 1) {
            ans = Math.min(ans, xor);
            return;
        }

        if (i + 1 < m) {
            dfs(grid, i + 1, j, xor);
        }

        if (j + 1 < n) {
            dfs(grid, i, j + 1, xor);
        }
    }
}
