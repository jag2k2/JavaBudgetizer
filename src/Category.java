public class Category {

    private final String name;
    private final Boolean exclude;
    private final Float default_goal;

    public Category(String name, float goal, Boolean exclude) {
        this.name = name;
        this.default_goal = goal;
        this.exclude = exclude;
    }

    public String toString() {
        return (name + " " + default_goal + " " + exclude);
    }

    public String getName() {
        return name;
    }

    public float getDefaultGoal() {
        return default_goal;
    }

    public String getExcludeAsString() {
        return exclude.toString().toUpperCase();
    }

    public String getDefaultGoalAsString() {
        if (Float.isNaN(default_goal)) return "NULL";
        else return String.format("%.2f", default_goal);
    }

    @Override
    public boolean equals(Object toCompare) {
        if (this == toCompare) return true;
        if (this.getClass() != toCompare.getClass()) return false;
        Category categoryToCompare = (Category) toCompare;
        return this.name.equals(categoryToCompare.name) &&
                this.default_goal.equals(categoryToCompare.default_goal) &&
                this.exclude.equals(categoryToCompare.exclude);
    }
}
