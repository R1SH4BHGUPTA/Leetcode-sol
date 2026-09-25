class Solution {
    int i = 0;

    public List<String> braceExpansionII(String expression) {
        Set<String> set = solve(expression);
        List<String> ans = new ArrayList<>(set);
        Collections.sort(ans);
        return ans;
    }

    private Set<String> solve(String s) {
        Set<String> res = new HashSet<>();
        Set<String> cur = new HashSet<>();
        cur.add("");

        while (i < s.length() && s.charAt(i) != '}') {
            char c = s.charAt(i);

            if (c == ',') {
                res.addAll(cur);
                cur = new HashSet<>();
                cur.add("");
                i++;
            } 
            else if (c == '{') {
                i++;
                Set<String> next = solve(s);
                i++;

                cur = multiply(cur, next);
            } 
            else {
                i++;
                Set<String> next = new HashSet<>();
                next.add(String.valueOf(c));

                cur = multiply(cur, next);
            }
        }

        res.addAll(cur);
        return res;
    }

    private Set<String> multiply(Set<String> a, Set<String> b) {
        Set<String> res = new HashSet<>();

        for (String x : a) {
            for (String y : b) {
                res.add(x + y);
            }
        }

        return res;
    }
}