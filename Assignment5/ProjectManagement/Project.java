package ProjectManagement;

import Trie.Trie;

import java.util.ArrayList;

public class Project {

    private String name;
    private int priority, budget;
    private ArrayList<Job> jobs;
    private Trie<User> userTrie = new Trie<User>();

    public Project(String name, int priority, int budget) {
        this.name = name;
        this.priority = priority;
        this.budget = budget;
        this.jobs = new ArrayList<>();
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

    Trie<User> getUserTrie() {
        return userTrie;
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

}
