import java.math.BigInteger;

class Solution {
    private static final BigInteger LIMIT = BigInteger.valueOf(1_000_000);

    public String smallestPalindrome(String s, int k) {
        int[] freq = new int[26];
        for (char c : s.toCharArray()) freq[c - 'a']++;

        int[] half = new int[26];
        int halfLen = 0;
        char mid = 0;

        for (int i = 0; i < 26; i++) {
            half[i] = freq[i] / 2;
            halfLen += half[i];
            if ((freq[i] & 1) == 1) mid = (char) ('a' + i);
        }

        BigInteger total = multinomial(half);

        if (total.compareTo(BigInteger.valueOf(k)) < 0) return "";

        StringBuilder left = new StringBuilder();

        while (halfLen > 0) {
            for (int c = 0; c < 26; c++) {
                if (half[c] == 0) continue;

                BigInteger ways = total.multiply(BigInteger.valueOf(half[c]))
                                       .divide(BigInteger.valueOf(halfLen));

                if (ways.compareTo(BigInteger.valueOf(k)) < 0) {
                    k -= ways.intValue();
                } else {
                    left.append((char) ('a' + c));
                    half[c]--;
                    halfLen--;
                    total = ways;
                    break;
                }
            }
        }

        StringBuilder ans = new StringBuilder(left);
        if (mid != 0) ans.append(mid);
        ans.append(left.reverse());

        return ans.toString();
    }

    private BigInteger multinomial(int[] cnt) {
        BigInteger res = BigInteger.ONE;
        int used = 0;

        for (int x : cnt) {
            if (x == 0) continue;
            res = res.multiply(comb(used + x, x));
            used += x;
        }
        return res;
    }

    private BigInteger comb(int n, int r) {
        r = Math.min(r, n - r);
        BigInteger res = BigInteger.ONE;

        for (int i = 1; i <= r; i++) {
            res = res.multiply(BigInteger.valueOf(n - r + i));
            res = res.divide(BigInteger.valueOf(i));
        }
        return res;
    }
}