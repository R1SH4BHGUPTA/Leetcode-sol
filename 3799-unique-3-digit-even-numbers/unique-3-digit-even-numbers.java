class Solution {
    public int totalNumbers(int[] digits) {
        int[] freq = new int[10];

        // Count how many times each digit appears
        for (int d : digits) {
            freq[d]++;
        }

        int count = 0;

        // Check every 3-digit even number
        for (int num = 100; num <= 998; num += 2) {
            int a = num / 100;          // hundreds digit
            int b = (num / 10) % 10;   // tens digit
            int c = num % 10;          // units digit

            // Check if we have enough copies of each digit
            int[] need = new int[10];
            need[a]++;
            need[b]++;
            need[c]++;

            boolean possible = true;

            for (int d = 0; d <= 9; d++) {
                if (need[d] > freq[d]) {
                    possible = false;
                    break;
                }
            }

            if (possible) {
                count++;
            }
        }

        return count;
    }
}