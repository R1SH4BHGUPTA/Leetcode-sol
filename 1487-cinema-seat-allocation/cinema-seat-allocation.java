import java.util.*;

class Solution {
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {

        // row -> bitmask of reserved seats
        Map<Integer, Integer> map = new HashMap<>();

        for (int[] seat : reservedSeats) {
            int row = seat[0];
            int s = seat[1];

            // Only seats 2 through 9 matter
            if (s >= 2 && s <= 9) {
                int bit = 1 << (s - 2);
                map.put(row, map.getOrDefault(row, 0) | bit);
            }
        }

        // Masks for:
        // A = 2,3,4,5
        // B = 4,5,6,7
        // C = 6,7,8,9
        int A = 0b00001111;
        int B = 0b00111100;
        int C = 0b11110000;

        long ans = 2L * (n - map.size());

        for (int reserved : map.values()) {

            boolean leftFree = (reserved & A) == 0;
            boolean middleFree = (reserved & B) == 0;
            boolean rightFree = (reserved & C) == 0;

            if (leftFree && rightFree) {
                // Can place 2 groups: A and C
                ans += 2;
            } else if (leftFree || middleFree || rightFree) {
                // Can place 1 group
                ans += 1;
            }
        }

        return (int) ans;
    }
}