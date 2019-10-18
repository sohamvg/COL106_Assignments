package ProjectManagement;

import java.util.ArrayList;

public class User implements Comparable<User>, UserReport_ {

    private String name;
    private int consumedBudget;
    private int latestJobTime, arrivalTime;
    private ArrayList<Job> jobs;

    User(String name, int consumedBudget, int latestJobTime, int arrivalTime) {
        this.name = name;
        this.consumedBudget = consumedBudget;
        this.latestJobTime = latestJobTime;
        this.jobs = new ArrayList<>();
        this.arrivalTime = arrivalTime;
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
//        int consumedCompare = Integer.compare(this.consumedBudget, user.consumedBudget); // TODO rev for arr
//        if (consumedCompare == 0) {
//            return Integer.compare(user.latestJobTime, this.latestJobTime); // checked
//        }
//        return consumedCompare;

        int consumedCompare = this.consumedBudget - user.consumedBudget; // use maxheap with node
        if (consumedCompare == 0) {
            int t = user.latestJobTime - this.latestJobTime;
            if (t==0) {
                return user.arrivalTime - this.arrivalTime; // TODO
            }
            return t;
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

    int getLatestJobTime() {
        return latestJobTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", usage=" + consumedBudget +
                '}';
    }
}
