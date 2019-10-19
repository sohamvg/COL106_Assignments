package ProjectManagement;

import java.util.ArrayList;

public class User implements Comparable<User>, UserReport_ {

    private String name;
    private int consumedBudget;
    private int latestJobTime;
    private ArrayList<Job> jobs;

    User(String name, int consumedBudget, int latestJobTime) {
        this.name = name;
        this.consumedBudget = consumedBudget;
        this.latestJobTime = latestJobTime;
        this.jobs = new ArrayList<>();
    }

    String getName() {
        return name;
    }

    public ArrayList<Job> getJobs() {
        return jobs;
    }

    void addJob(Job job) {
        jobs.add(job);
    }

    void addConsumedBudget(int amount) {
        this.consumedBudget = this.consumedBudget + amount;
    }

    void setLatestJobTime(int latestJobTime) {
        this.latestJobTime = latestJobTime;
    }

    @Override
    public int compareTo(User user) {

        int consumedCompare = user.consumedBudget - this.consumedBudget; // use maxheap with node
        if (consumedCompare == 0) {
            return this.latestJobTime - user.latestJobTime;
        }
        return consumedCompare;

    }

    @Override
    public String user() {
        return name;
    }

    @Override
    public int consumed() {
        return consumedBudget;
    }
//
//    int getLatestJobTime() {
//        return latestJobTime;
//    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", usage=" + consumedBudget +
                '}';
    }
}
