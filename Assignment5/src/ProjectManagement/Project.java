package ProjectManagement;

import java.util.ArrayList;

public class Project implements Comparable<Project> {

    private String name;
    private int priority, budget, arrivalTime;
    private ArrayList<Job> jobs;

    public Project(String name, int priority, int budget, int arrivalTime) {
        this.name = name;
        this.priority = priority;
        this.budget = budget;
        this.jobs = new ArrayList<>();
        this.arrivalTime = arrivalTime;
    }

    void addBudget(int amount) {
        this.budget = this.budget + amount;
    }

    void addJob(Job job) {
        jobs.add(job);
    }

    public ArrayList<Job> getJobs() {
        return jobs;
    }

    void decreaseBudget(int amount) {
        this.budget = this.budget - amount;
    }

    int getPriority() {
        return priority;
    }

    public String getName() {
        return name;
    }

    public int getBudget() {
        return budget;
    }

    @Override
    public int compareTo(Project project) {
        int t = project.priority - this.priority;
        if (t == 0) {
            return this.arrivalTime - project.arrivalTime;
        }
        return t;
    }
}
