public class Location {
    private final String name;
    private final String description;

    public Location(String name, String description) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Location name is required");
        }
        this.name = name.trim();
        this.description = description == null ? "" : description.trim();
    }

    public String getName() { return name; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return description.isEmpty() ? name : (name + " - " + description);
    }
}
