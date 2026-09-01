import java.util.*;

class Solution {

    static class State {
        int r, c, mask, energy, moves;

        State(int r, int c, int mask, int energy, int moves) {
            this.r = r;
            this.c = c;
            this.mask = mask;
            this.energy = energy;
            this.moves = moves;
        }
    }

    public int minMoves(String[] classroom, int energy) {

        int m = classroom.length;
        int n = classroom[0].length();

        int startR = 0, startC = 0;

        int[][] litterId = new int[m][n];

        for (int[] row : litterId) {
            Arrays.fill(row, -1);
        }

        int litterCount = 0;

        // Find starting position and assign IDs to litter
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                char ch = classroom[i].charAt(j);

                if (ch == 'S') {
                    startR = i;
                    startC = j;
                }

                if (ch == 'L') {
                    litterId[i][j] = litterCount;
                    litterCount++;
                }
            }
        }

        int allCollected = (1 << litterCount) - 1;

        boolean[][][][] visited =
            new boolean[m][n][1 << litterCount][energy + 1];

        Queue<State> q = new ArrayDeque<>();

        q.offer(new State(startR, startC, 0, energy, 0));
        visited[startR][startC][0][energy] = true;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (!q.isEmpty()) {

            State cur = q.poll();

            // All litter collected
            if (cur.mask == allCollected) {
                return cur.moves;
            }

            // No energy -> cannot move
            if (cur.energy == 0) {
                continue;
            }

            for (int d = 0; d < 4; d++) {

                int nr = cur.r + dr[d];
                int nc = cur.c + dc[d];

                // Outside grid
                if (nr < 0 || nr >= m || nc < 0 || nc >= n) {
                    continue;
                }

                // Obstacle
                if (classroom[nr].charAt(nc) == 'X') {
                    continue;
                }

                int newEnergy = cur.energy - 1;
                int newMask = cur.mask;

                char cell = classroom[nr].charAt(nc);

                // Collect litter
                if (cell == 'L') {
                    int id = litterId[nr][nc];
                    newMask |= (1 << id);
                }

                // Reset energy
                if (cell == 'R') {
                    newEnergy = energy;
                }

                // Visit this state only once
                if (!visited[nr][nc][newMask][newEnergy]) {

                    visited[nr][nc][newMask][newEnergy] = true;

                    q.offer(
                        new State(
                            nr,
                            nc,
                            newMask,
                            newEnergy,
                            cur.moves + 1
                        )
                    );
                }
            }
        }

        return -1;
    }
}