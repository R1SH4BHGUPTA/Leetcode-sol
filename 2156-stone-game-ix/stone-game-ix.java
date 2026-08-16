class Solution {
    public boolean stoneGameIX(int[] stones) {
        int[] cnt = new int[3];

        for (int x : stones) {
            cnt[x % 3]++;
        }

        int c0 = cnt[0];
        int c1 = cnt[1];
        int c2 = cnt[2];

        // Even number of remainder-0 stones
        if (c0 % 2 == 0) {
            return c1 > 0 && c2 > 0;
        }

        // Odd number of remainder-0 stones
        return Math.abs(c1 - c2) > 2;
    }
}