gickage scplanner.manager;

import scplanner.model.Location;
import scplanner.graph.Graph;
import scplanner.tree.AVLTree;
import java.util.List;

public class LocationManager {
    private final AVLTree tree = new AVLTree();
    private final Graph graph = new Graph();

    
    public boolean addLocation(String name, String desc) {
        Location loc = new Location(name, desc);
        boolean ok = tree.insert(loc);
        if (ok) graph.addVertex(name);
        return ok;
    }

    public boolean removeLocation(String name) {
        boolean ok = tree.delete(name);
        if (ok) graph.removeVertex(name);
        return ok;
    }

    public boolean addRoad(String a, String b, int weight) {
        if (!tree.contains(a) || !tree.contains(b)) return false;
        graph.addEdge(a, b, weight);
        return true;
    }

    public boolean removeRoad(String a, String b) {
        graph.removeEdge(a, b);
        return true;
    }

    public void displayConnections() { graph.displayConnections(); }
    public void displayLocationsInOrder() {
        List<Location> list = tree.inorder();
        if (list.isEmpty()) System.out.println("No locations.");
        else list.forEach(l -> System.out.println("- " + l));
    }

    public List<String> bfs(String start) { return graph.bfs(start); }
    public List<String> dfs(String start) { return graph.dfs(start); }
    public boolean hasLocation(String name) { return tree.contains(name); }
    public boolean hasVertex(String name) { return graph.hasVertex(name); }
}