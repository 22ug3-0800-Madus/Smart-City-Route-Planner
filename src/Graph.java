import java.util.*;


class Graph {
    private Map<String, List<String>> adjList;

    public Graph() {
        adjList = new HashMap<>();
    }

    public void addLocation(String location) {
        adjList.putIfAbsent(location, new ArrayList<>());
    }

    public void removeLocation(String location) {
        adjList.remove(location);
        for (List<String> connections : adjList.values()) {
            connections.remove(location);
        }
    }

    public void addRoad(String from, String to) {
        if (adjList.containsKey(from) && adjList.containsKey(to)) {
            adjList.get(from).add(to);
            adjList.get(to).add(from);
        } else {
            System.out.println("One or both locations do not exist!");
        }
    }

    public void removeRoad(String from, String to) {
        if (adjList.containsKey(from)) adjList.get(from).remove(to);
        if (adjList.containsKey(to)) adjList.get(to).remove(from);
    }

    public void displayConnections() {
        System.out.println("\n--- City Connections ---");
        for (String location : adjList.keySet()) {
            System.out.println(location + " -> " + adjList.get(location));
        }
    }


    public void bfsTraversal(String start) {
        if (!adjList.containsKey(start)) {
            System.out.println("Location not found!");
            return;
        }
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);
        System.out.println("\nBFS Traversal from " + start + ":");

        while (!queue.isEmpty()) {
            String current = queue.poll();
            System.out.print(current + " ");
            for (String neighbor : adjList.get(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        System.out.println();
    }
}