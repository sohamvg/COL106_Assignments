package ProjectManagement;


public class Job implements Comparable<Job> {

    private String name;
    private Project project;
    private User user;
    private int runtime, completeTime, assignTime;
    private boolean isFinished;

    public Job(String name, Project project, User user, int runtime, int assignTime) {
        this.name = name;
        this.project = project;
        this.user = user;
        this.runtime = runtime;
        this.assignTime = assignTime;
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
        int projectPriorityCompare = Integer.compare(this.project.getPriority(), job.project.getPriority());
        if (projectPriorityCompare == 0) {
            return Integer.compare(job.assignTime, this.assignTime);
        }
        return projectPriorityCompare;
    }

    Project getProject() {
        return project;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public void setCompleteTime(int completeTime) {
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
                    ", name='" + name + '\'' +
                    '}';        }
    }
}