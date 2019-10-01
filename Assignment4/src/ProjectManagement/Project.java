package ProjectManagement;


public class Project {

    private String name;
    private int priority, budget;

    public Project(String name, int priority, int budget) {
        this.name = name;
        this.priority = priority;
        this.budget = budget;
    }

    void addBudget(int amount) {
        this.budget = this.budget + amount;
    }

    void decreaseBudget(int amount) {
        this.budget = this.budget - amount;
    }

    public int getPriority() {
        return priority;
    }

    public String getName() {
        return name;
    }

    public int getBudget() {
        return budget;
    }
}
