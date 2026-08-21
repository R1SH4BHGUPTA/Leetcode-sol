import java.util.*;

class Solution {

    public long findKthSmallest(int[] coins, int k) {
        int n = coins.length;

        long low = 1;

        // The answer cannot be greater than min(coins) * k
        long minCoin = Integer.MAX_VALUE;
        for (int coin : coins) {
            minCoin = Math.min(minCoin, coin);
        }

        long high = minCoin * (long) k;

        while (low < high) {
            long mid = low + (high - low) / 2;

            if (count(mid, coins) >= k) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        return low;
    }

    private long count(long x, int[] coins) {
        int n = coins.length;
        long result = 0;

        // Inclusion-exclusion over all subsets
        for (int mask = 1; mask < (1 << n); mask++) {

            long lcm = 1;
            int bits = 0;
            boolean overflow = false;

            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    bits++;

                    long g = gcd(lcm, coins[i]);
                    long temp = lcm / g;

                    // Prevent overflow
                    if (temp > x / coins[i]) {
                        overflow = true;
                        break;
                    }

                    lcm = temp * coins[i];

                    // No multiple of this LCM can be <= x
                    if (lcm > x) {
                        overflow = true;
                        break;
                    }
                }
            }

            if (overflow) {
                continue;
            }

            long multiples = x / lcm;

            if ((bits & 1) == 1) {
                // Odd-sized subset: add
                result += multiples;
            } else {
                // Even-sized subset: subtract
                result -= multiples;
            }
        }

        return result;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }
}