public class Category {

    private final String name;
    private final Boolean exclude;
    private final float default_goal;

    public Category(String name, float goal, Boolean exclude) {
        this.name = name;
        this.default_goal = goal;
        this.exclude = exclude;
    }

    public String toString() {
        return name + ": " + default_goal + ": " + exclude + "\n";
    }

    public String getName() {
        return name;
    }

    public float getDefaultGoal() {
        return default_goal;
    }
}
