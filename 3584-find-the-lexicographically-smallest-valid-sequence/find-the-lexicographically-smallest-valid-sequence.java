public class Solution {
    public int[] validSequence(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();
        int[] ans = new int[m];
        
        // last[j] stores the maximum index in word1 that can match word2[j...m-1] greedily from the right
        int[] last = new int[m];
        java.util.Arrays.fill(last, -1);
        
        int i = n - 1;
        int j = m - 1;
        
        // Step 1: Precompute backward matching positions
        while (i >= 0 && j >= 0) {
            if (word1.charAt(i) == word2.charAt(j)) {
                last[j] = i;
                j--;
            }
            i--;
        }
        
        boolean canSkip = true; // Indicates if we still have our 1-character modification allowance
        j = 0;
        
        // Step 2: Greedily build the lexicographically smallest sequence forward
        for (i = 0; i < n; i++) {
            if (j == m) {
                break;
            }
            
            // Match without modification
            if (word1.charAt(i) == word2.charAt(j)) {
                ans[j] = i;
                j++;
            } 
            // Attempt to modify the current character to force a match
            else if (canSkip && (j == m - 1 || i < last[j + 1])) {
                canSkip = false; // Consume our single modification token
                ans[j] = i;
                j++;
            }
        }
        
        // If we matched the entire word2, return the sequence; otherwise return an empty array
        return (j == m) ? ans : new int[0];
    }
}
