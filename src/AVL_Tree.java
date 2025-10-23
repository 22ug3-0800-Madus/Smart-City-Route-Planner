import java.util.*;

public class Graph {
    private final Map<String, List<Edge>> adj = new HashMap<>();

    private static class Edge {
        final String to;
        final int weight;
        Edge(String to, int weight) { this.to = to; this.weight = weight; }
        @Override public String toString() { return to + "(" + weight + ")"; }
    }

    public void addVertex(String name) {
        adj.putIfAbsent(name, new ArrayList<>());
    }

    public boolean hasVertex(String name) {
        return adj.containsKey(name);
    }

    public void removeVertex(String name) {
        if (!adj.containsKey(name)) return;
        adj.remove(name);
        for (List<Edge> edges : adj.values()) {
            edges.removeIf(e -> e.to.equals(name));
        }
    }

    public void addEdge(String a, String b, int weight) {
        addVertex(a); addVertex(b);
        if (adj.get(a).stream().noneMatch(e -> e.to.equals(b)))
            adj.get(a).add(new Edge(b, weight));
        if (adj.get(b).stream().noneMatch(e -> e.to.equals(a)))
            adj.get(b).add(new Edge(a, weight));
    }

    public void removeEdge(String a, String b) {
        if (adj.containsKey(a)) adj.get(a).removeIf(e -> e.to.equals(b));
        if (adj.containsKey(b)) adj.get(b).removeIf(e -> e.to.equals(a));
    }

    public void displayConnections() {
        if (adj.isEmpty()) {
            System.out.println("No connections.");
            return;
        }
        for (String v : adj.keySet()) {
            System.out.print(v + " -> ");
            List<Edge> list = adj.get(v);
            if (list.isEmpty()) System.out.println("none");
            else {
                System.out.println(String.join(", ",
                    list.stream().map(Edge::toString).toArray(String[]::new)));
            }
        }
    }

    // BFS returns traversal order using a queue
    public List<String> bfs(String start) {
        List<String> order = new ArrayList<>();
        if (!adj.containsKey(start)) return order;
        Queue<String> q = new LinkedList<>();
        Set<String> seen = new HashSet<>();
        q.add(start); seen.add(start);
        while (!q.isEmpty()) {
            String cur = q.poll();
            order.add(cur);
            for (Edge e : adj.get(cur)) {
                if (!seen.contains(e.to)) { seen.add(e.to); q.add(e.to); }
            }
        }
        return order;
    }

    // DFS using explicit stack
    public List<String> dfs(String start) {
        List<String> order = new ArrayList<>();
        if (!adj.containsKey(start)) return order;
        Stack<String> st = new Stack<>();
        Set<String> seen = new HashSet<>();
        st.push(start);
        while (!st.isEmpty()) {
            String cur = st.pop();
            if (seen.contains(cur)) continue;
            seen.add(cur); order.add(cur);
            for (Edge e : adj.get(cur)) {
                if (!seen.contains(e.to)) st.push(e.to);
            }
        }
        return order;
    }

    public Set<String> vertices() { return adj.keySet(); }
}
