import java.util.*;

class Solution {
    public int solution(int n, int[] weak, int[] dist) {
        Arrays.sort(dist);

        for (int i = 0; i < dist.length / 2; i++) {
            int tmp = dist[i];
            dist[i] = dist[dist.length - 1 - i];
            dist[dist.length - 1 - i] = tmp;
        }

        List<Long> visit = new ArrayList<>();
        visit.add(0L);

        Queue<Long> q = new LinkedList<>();
        q.add(0L);

        for (int i = 0; i < dist.length; i++) {
            List<Long> possible = new ArrayList<>();

            for (int startPoint : weak) {
                int endPoint = (startPoint + dist[i]) % n;
                long p = 0L;

                if (startPoint < endPoint) {
                    for (int j = 0; j < weak.length; j++) {
                        if (startPoint <= weak[j] && weak[j] <= endPoint) {
                            p |= (1 << j);
                        }
                    }
                } else {
                    for (int j = 0; j < weak.length; j++) {
                        if (startPoint <= weak[j] || weak[j] <= endPoint) {
                            p |= (1 << j);
                        }
                    }
                }

                possible.add(p);
            }

            int qSize = q.size();
            for (int j = 0; j < qSize; j++) {
                Long tmp = q.poll();

                for (Long p : possible) {
                    long newPossible = 0L;

                    newPossible |= tmp;
                    newPossible |= p;

                    if (newPossible == (1 << weak.length) - 1) {
                        return i + 1;
                    } else {
                        if (!visit.contains(newPossible)) {
                            q.add(newPossible);
                            visit.add(newPossible);
                        }
                    }
                }
            }
        }

        return -1;
    }
}