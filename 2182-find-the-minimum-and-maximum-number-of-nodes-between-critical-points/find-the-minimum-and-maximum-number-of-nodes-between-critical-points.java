class Solution {
    public int[] nodesBetweenCriticalPoints(ListNode head) {

        // We need at least 3 nodes to have a critical point
        if (head == null || head.next == null || head.next.next == null) {
            return new int[]{-1, -1};
        }

        ListNode prev = head;
        ListNode curr = head.next;

        int position = 1;

        int first = -1;
        int prevCritical = -1;

        int minDistance = Integer.MAX_VALUE;
        int maxDistance = -1;

        while (curr.next != null) {

            int prevVal = prev.val;
            int currVal = curr.val;
            int nextVal = curr.next.val;

            boolean isCritical =
                    (currVal > prevVal && currVal > nextVal) ||
                    (currVal < prevVal && currVal < nextVal);

            if (isCritical) {

                // First critical point
                if (first == -1) {
                    first = position;
                }

                // If this is not the first critical point,
                // calculate distance from previous critical point
                if (prevCritical != -1) {
                    int distance = position - prevCritical;
                    minDistance = Math.min(minDistance, distance);
                }

                prevCritical = position;
            }

            prev = curr;
            curr = curr.next;
            position++;
        }

        // Fewer than two critical points
        if (prevCritical == first) {
            return new int[]{-1, -1};
        }

        // Distance between first and last critical point
        maxDistance = prevCritical - first;

        return new int[]{minDistance, maxDistance};
    }
}