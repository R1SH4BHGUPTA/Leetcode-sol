import java.math.BigInteger;

class Solution {
    public int maxLength(int[] nums) {
        int n = nums.length;
        int ans = 1;

        for (int i = 0; i < n; i++) {
            BigInteger product = BigInteger.ONE;
            int gcd = 0;
            long lcm = 1;

            for (int j = i; j < n; j++) {
                int num = nums[j];

                product = product.multiply(BigInteger.valueOf(num));
                gcd = gcd(gcd, num);
                lcm = lcm(lcm, num);

                BigInteger right = BigInteger.valueOf(gcd)
                        .multiply(BigInteger.valueOf(lcm));

                if (product.equals(right)) {
                    ans = Math.max(ans, j - i + 1);
                }
            }
        }

        return ans;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private long lcm(long a, long b) {
        return (a / gcd((int) a, (int) b)) * b;
    }
}