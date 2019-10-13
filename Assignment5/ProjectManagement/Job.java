package ProjectManagement;


public class Job implements Comparable<Job>, JobReport_ {

    private String name;
    private Project project;
    private int priority;
    private User user;
    private int runtime, completeTime, assignTime;
    /**
     * independent arrival time : different for each job
     */
    private int preciseArrivalTime;
    private boolean isFinished;

    public Job(String name, Project project,int priority, User user, int runtime, int assignTime, int preciseArrivalTime) {
        this.name = name;
        this.project = project;
        this.priority = priority;
        this.user = user;
        this.runtime = runtime;
        this.assignTime = assignTime;
        this.preciseArrivalTime = preciseArrivalTime;
        this.isFinished = false;
    }

    boolean isFinished() {
        return isFinished;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Job job) {
        int projectPriorityCompare = Integer.compare(this.priority, job.priority);
        if (projectPriorityCompare == 0) {
            return Integer.compare(job.preciseArrivalTime, this.preciseArrivalTime);
        }
        return projectPriorityCompare;
    }

    Project getProject() {
        return project;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public User getUser() {
        return user;
    }
//    String getProjectName() {
//        return project.getName();
//    }

    int getRuntime() {
        return runtime;
    }

    void setFinished() {
        isFinished = true;
    }

    void setCompleteTime(int completeTime) {
        this.completeTime = completeTime;
    }

    @Override
    public String toString() {
        if (completeTime == 0) {

            return "Job{" +
                    "user='" + user.getName() + '\'' +
                    ", project='" + project.getName() + '\'' +
                    ", jobstatus=REQUESTED" +
                    ", execution_time=" + runtime +
                    ", end_time=null" +
                    ", priority=" + priority +
                    ", name='" + name + '\'' +
                    '}';
        }
        else {
            return "Job{" +
                    "user='" + user.getName() + '\'' +
                    ", project='" + project.getName() + '\'' +
                    ", jobstatus=COMPLETED" +
                    ", execution_time=" + runtime +
                    ", end_time=" + completeTime +
                    ", priority=" + priority +
                    ", name='" + name + '\'' +
                    '}';        }
    }


    @Override
    public String user() {
        return user.getName();
    }

    @Override
    public String project_name() {
        return project.getName();
    }

    @Override
    public int budget() {
        return runtime;
    }

    @Override
    public int arrival_time() {
        return assignTime;
    }

    public int getPreciseArrivalTime() {
        return preciseArrivalTime;
    }

    @Override
    public int completion_time() {
        return completeTime;
    }
}