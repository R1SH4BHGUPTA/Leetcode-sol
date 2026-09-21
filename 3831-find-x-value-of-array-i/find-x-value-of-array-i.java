class Solution {
    public long[] resultArray(int[] nums, int k) {
        long ans[] = new long[k];
        long dp[][] = new long[nums.length][k];
        // apan log aisa dp soch rhe
        // dp[i][j] -> number of subarrays which end at i, and have prod%k == j
        dp[0][nums[0]%k]++; // base case? 
        ans[nums[0]%k] = 1L;
        int cur;
        for(int i = 1;i < nums.length;i++){
            cur = nums[i]%k;
            dp[i][cur] = 1L;
            ans[cur] += 1L;
            for(int j = 0;j < k;j++){
                dp[i][(j*cur)%k] += dp[i-1][j];                                
                ans[(j*cur)%k] += dp[i-1][j];
            }            
            // for(int j = 0;j < k;j++){
            //     ans[j] += dp[i][j];                
            // }            
            // System.out.printf("for idx: %d\n", i);
            // for(int j = 0;j < k;j++)
            //     System.out.printf("\t%d: %d\n", j, dp[i][j]);
        }        
        return ans;
    }
}