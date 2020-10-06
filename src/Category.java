public class Category {

    private String name;
    private Boolean exclude;
    private float default_goal;

    public Category(String name, float goal) {
        this.name = name;
        this.default_goal = goal;
    }

    public String getName() {
        return name;
    }

    public float getDefaultGoal() {
        return default_goal;
    }
}
