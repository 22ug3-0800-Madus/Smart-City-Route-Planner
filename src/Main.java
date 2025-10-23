import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final LocationManager manager = new LocationManager();

    public static void main(String[] args) {
        while (true) {
            printMenu();
            String choice = sc.nextLine().trim();
            switch(choice) {
                case "1": addLocation(); break;
                case "2": removeLocation(); break;
                case "3": addRoad(); break;
                case "4": removeRoad(); break;
                case "5": manager.displayConnections(); break;
                case "6": manager.displayLocationsInOrder(); break;
                case "7": traverseDemo(); break; // BFS/DFS demo
                case "0": System.out.println("Exiting..."); return;
                default: System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n--- Smart City Route Planner ---");
        System.out.println("1. Add a new location");
        System.out.println("2. Remove a location");
        System.out.println("3. Add a road between locations");
        System.out.println("4. Remove a road");
        System.out.println("5. Display all connections");
        System.out.println("6. Display all locations (AVL inorder)");
        System.out.println("7. Traverse (BFS/DFS)");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addLocation() {
        System.out.print("Location name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) { System.out.println("Name required."); return; }
        System.out.print("Short description (optional): ");
        String desc = sc.nextLine().trim();
        boolean ok = manager.addLocation(name, desc);
        System.out.println(ok ? "Location added." : "Location exists or failed.");
    }

    private static void removeLocation() {
        System.out.print("Location name to remove: ");
        String name = sc.nextLine().trim();
        boolean ok = manager.removeLocation(name);
        System.out.println(ok ? "Removed." : "Location not found.");
    }

    private static void addRoad() {
        System.out.print("From: "); String a = sc.nextLine().trim();
        System.out.print("To: "); String b = sc.nextLine().trim();
        System.out.print("Weight (integer, default 1): ");
        String w = sc.nextLine().trim();
        int weight = 1; try { if (!w.isEmpty()) weight = Integer.parseInt(w); } catch(NumberFormatException e){ System.out.println("Invalid weight. Using 1."); }
        if (!manager.hasLocation(a) || !manager.hasLocation(b)) {
            System.out.println("Both locations must exist. Add them first.");
            return;
        }
        boolean ok = manager.addRoad(a,b,weight);
        System.out.println(ok ? "Road added." : "Failed to add road.");
    }

    private static void removeRoad() {
        System.out.print("From: "); String a = sc.nextLine().trim();
        System.out.print("To: "); String b = sc.nextLine().trim();
        manager.removeRoad(a,b);
        System.out.println("Road removed (if existed).");
    }

    private static void traverseDemo() {
        System.out.print("Enter start location for BFS/DFS: ");
        String s = sc.nextLine().trim();
        List<String> bfs = manager.bfs(s);
        List<String> dfs = manager.dfs(s);
        System.out.println("BFS order: " + bfs);
        System.out.println("DFS order: " + dfs);
    }
}
