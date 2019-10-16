package ProjectManagement;

import PriorityQueue.BuildHeap;
import PriorityQueue.MaxHeap;
import Trie.Trie;
import Trie.TrieNode;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

// TODO remove final print statements
public class Scheduler_Driver extends Thread implements SchedulerInterface {

    private static int globalTime = 0;
    private static int globalArrivalTime = 0;

    private static Trie<Project> projectTrie = new Trie<Project>();
    private static Trie<User> userTrie = new Trie<User>();
    private static Trie<Job> jobTrie = new Trie<Job>();

//    private static MaxHeap<User> userMaxHeap = new MaxHeap<>(); // increases flush complexity
    private static MaxHeap<Job> jobMaxHeap = new MaxHeap<>(); // stores all untried jobs = total - ( completed + notReadyJobs )
    private static MaxHeap<Job> unfinishedJobHeap = new MaxHeap<>();

    private static ArrayList<User> userArrayList = new ArrayList<>();
    private static ArrayList<Job> finishedJobs = new ArrayList<>();
    private static ArrayList<Job> notReadyJobs = new ArrayList<>(); // jobs which were tried but have insufficient budget
//    private static ArrayList<MaxHeap.Node<Job>> flushedJobs = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        Scheduler_Driver scheduler_driver = new Scheduler_Driver();
        File file;
        if (args.length == 0) {
            URL url = Scheduler_Driver.class.getResource("INP");
            file = new File(url.getPath());
        } else {
            file = new File(args[0]);
        }

        scheduler_driver.execute(file);
    }

    private void execute(File commandFile) throws IOException {


        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(commandFile));

            String st;
            while ((st = br.readLine()) != null) {
                String[] cmd = st.split(" ");
                if (cmd.length == 0) {
                    System.err.println("Error parsing: " + st);
                    return;
                }
//                String project_name, user_name;
//                Integer start_time, end_time;

                long qstart_time, qend_time;

                switch (cmd[0]) {
                    case "PROJECT":
                        handle_project(cmd);
                        break;
                    case "JOB":
                        handle_job(cmd);
                        break;
                    case "USER":
                        handle_user(cmd[1]);
                        break;
                    case "QUERY":
                        handle_query(cmd[1]);
                        break;
                    case "": // HANDLE EMPTY LINE
                        handle_empty_line();
                        break;
                    case "ADD":
                        handle_add(cmd);
                        break;
                    //--------- New Queries
                    case "NEW_PROJECT":
                    case "NEW_USER":
                    case "NEW_PROJECTUSER":
                    case "NEW_PRIORITY":
                        timed_report(cmd);
                        break;
                    case "NEW_TOP":
                        qstart_time = System.nanoTime();
                        ArrayList<UserReport_> res = timed_top_consumer(Integer.parseInt(cmd[1]));
                        qend_time = System.nanoTime();
                        System.out.println("Top query");
                        System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));

                        for (UserReport_ j : res) {
                            System.out.println(j.user() + " " + j.consumed() + " " + ((User) j).getLatestJobTime());
                        }

                        break;
                    case "NEW_FLUSH":
                        qstart_time = System.nanoTime();
                        timed_flush( Integer.parseInt(cmd[1]));
                        qend_time = System.nanoTime();
                        System.out.println("Flush query");
                        System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));

//                        for (MaxHeap.Node<Job> j : flushedJobs) {
//                            System.out.println(j.getElement());
//                        }

                        break;
                    default:
                        System.err.println("Unknown command: " + cmd[0]);
                }

            }


            run_to_completion();
            print_stats();

        } catch (FileNotFoundException e) {
            System.err.println("Input file Not found. " + commandFile.getAbsolutePath());
        } catch (NullPointerException ne) {
            ne.printStackTrace();

        }
    }


    @Override
    public ArrayList<JobReport_> timed_report(String[] cmd) {
        long qstart_time, qend_time;
        ArrayList<JobReport_> res = null;
        switch (cmd[0]) {
            case "NEW_PROJECT":
                qstart_time = System.nanoTime();
                res = handle_new_project(cmd);
                qend_time = System.nanoTime();
                System.out.println("Project query");
                System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));

                for (JobReport_ j : res) {
                    System.out.println(j);
                }

                break;
            case "NEW_USER":
                qstart_time = System.nanoTime();
                res = handle_new_user(cmd);
                qend_time = System.nanoTime();
                System.out.println("User query");
                System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));

                for (JobReport_ j : res) {
                    System.out.println(j);
                }

                break;
            case "NEW_PROJECTUSER":
                qstart_time = System.nanoTime();
                res = handle_new_projectuser(cmd);
                qend_time = System.nanoTime();
                System.out.println("Project User query");
                System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));

                for (JobReport_ j : res) {
                    System.out.println(j.toString() + " " + j.completion_time());
                }

                break;
            case "NEW_PRIORITY":
                qstart_time = System.nanoTime();
                res = handle_new_priority(cmd[1]);
                qend_time = System.nanoTime();
                System.out.println("Priority query");
                System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));

                for (JobReport_ j : res) {
                    System.out.println(j);
                }

                break;
        }

        return res;
    }


    @Override
    public ArrayList<UserReport_> timed_top_consumer(int top) {
        ArrayList<UserReport_> topUsers = new ArrayList<>();

//        userArrayList.sort(null); // O((# user) * log(# user)) // TODO check order
//
//        int size = userArrayList.size();
//
//        for (int i = size-1; i > size-1-top; i--) {
//            if (i == -1) {
//                break;
//            }
//            topUsers.add(userArrayList.get(i));
//        }

        BuildHeap<User> temp = new BuildHeap<>(); // TODO check
        temp.buildHeap(userArrayList,userArrayList.size());

        while (top != 0) {

            User user = temp.extractMax(userArrayList);

            if (user == null) {
                break;
            }

            else {
                topUsers.add(user);
            }
            top = top - 1;
        }

        return topUsers;
    }


    @Override
    public void timed_flush(int waittime) {
//        System.out.println("#########################");
        // flush...
        int timeAtFlush = globalTime;
        ArrayList<Job> temp = new ArrayList<>();
        Job job = jobMaxHeap.extractMax();
        while (job != null) {
            int jobWaitTime = timeAtFlush - job.arrival_time();
            if (jobWaitTime >= waittime && job.getRuntime() <= job.getProject().getBudget()) {
                System.out.println(job);
                execute_a_job(job);
                System.out.println(job.getProject().getBudget() + " " + job + " ddddddddd");
            }
            else {
                temp.add(job);
            }

            job = jobMaxHeap.extractMax();
        }

        // TODO: if jobMaxHeap is empty then buildHeap
        for (Job j : temp) {
            jobMaxHeap.insert(j);
        }

        // sort...
        /*
        Method 1
        O((# untried jobs) + (# untried jobs)*log(# untried jobs))
         */
//        flushedJobs = (ArrayList<MaxHeap.Node<Job>>)jobMaxHeap.getMaxHeap().clone(); // O(# untried jobs)
//        flushedJobs.sort(new JobArrivalComparator()); // O((# untried jobs) * log(# untried jobs))

        /*
        Method 2
        O(# total jobs)
         */

//        System.out.println("#########################");

    }


    private ArrayList<JobReport_> handle_new_priority(String s) {
        int priority = Integer.parseInt(s);
        ArrayList<JobReport_> res = new ArrayList<>();

        for (Job notReadyJob : notReadyJobs) {
            if (notReadyJob.getPriority() >= priority) {
                res.add(notReadyJob);
            }
        }

        for (MaxHeap.Node<Job> jobNode : jobMaxHeap.getMaxHeap()) {
            Job notTriedJob = jobNode.getElement();
            if (notTriedJob.getPriority() >= priority) {
                res.add(notTriedJob);
            }
        }
        // TODO sort
        return res;
    }


    private ArrayList<JobReport_> handle_new_projectuser(String[] cmd) {

        int t1 = Integer.parseInt(cmd[3]);
        int t2 = Integer.parseInt(cmd[4]);
        ArrayList<JobReport_> res = new ArrayList<>();

        TrieNode searchUser = userTrie.search(cmd[2]); // O(user name length)
            if (searchUser != null) {

                ArrayList<Job> userJobs = ((User) searchUser.getValue()).getJobs();

                int bs = binarySearch(userJobs, t1);
                if (bs != -1) {
                    for (int i = bs; i < userJobs.size(); i++) {
                        Job job = userJobs.get(i);
                        if (job.arrival_time() > t2) {
                            break;
                        }
                        if (job.getProject().getName().equals(cmd[1]) && job.arrival_time() >= t1 && job.arrival_time() <= t2) {
                            res.add(job);
                        }
                    }
                }
                res.sort(new JobCompletionComparator()); // TODO check
            }
            else {System.out.println("No such user exists." + cmd[2]);}

        return res;
    }


    private ArrayList<JobReport_> handle_new_user(String[] cmd) {
        int t1 = Integer.parseInt(cmd[2]);
        int t2 = Integer.parseInt(cmd[3]);
        ArrayList<JobReport_> res = new ArrayList<>();

        TrieNode searchUser = userTrie.search(cmd[1]); // O(user name length)
        if (searchUser != null) {
            ArrayList<Job> userJobs = ((User) searchUser.getValue()).getJobs();

            System.out.println("jjjjjjjjjjjjjjjjjjjjjjjjjj");
            for (Job j : userJobs) {
                System.out.println(j + " " + j.arrival_time());
            }

            int bs = binarySearch(userJobs, t1);
            if (bs != -1) {
                for (int i = bs; i < userJobs.size(); i++) {
                    Job job = userJobs.get(i);
                    if (job.arrival_time() > t2) {
                        break;
                    }
                    if (job.arrival_time() >= t1 && job.arrival_time() <= t2) {
                        res.add(job);
                    }
                }
            }
        }
//        else {System.out.println("No such user exists." + cmd[1]);}

        return res;
    }


    private ArrayList<JobReport_> handle_new_project(String[] cmd) {

        int t1 = Integer.parseInt(cmd[2]);
        int t2 = Integer.parseInt(cmd[3]);
        ArrayList<JobReport_> res = new ArrayList<>();

        TrieNode searchProject = projectTrie.search(cmd[1]); // O(project name length)
        if (searchProject != null) {
            ArrayList<Job> projectJobs = ((Project) searchProject.getValue()).getJobs();

            int bs = binarySearch(projectJobs, t1);
            if (bs != -1) {

                for (int i = bs; i < projectJobs.size(); i++) {

                    Job job = projectJobs.get(i);
                    if (job.arrival_time() > t2) {
                        break;
                    }
                    if (job.arrival_time() >= t1 && job.arrival_time() <= t2) {
                        res.add(job);
                    }
                }
            }
        }
//        else {System.out.println("No such project exists." + cmd[1]);}

        return res;
    }


    public void schedule() {

        System.out.println("Running code");
        System.out.println("Remaining jobs: " + jobMaxHeap.heapSize());

        Job job = jobMaxHeap.extractMax();

        while (job != null) {

            assert job.getProject() != null;
            Project project = job.getProject();


            System.out.println("Executing: " + job.getName() + " from: " + project.getName());
            if (project.getBudget() >= job.getRuntime()) {

                execute_a_job(job);
                System.out.println("Project: " + project.getName() + " budget remaining: " + project.getBudget());

                break;
            }
            else {
                System.out.println("Un-sufficient budget.");

                notReadyJobs.add(job); // O(1) -> O(log (no. of projects with unfinished jobs)) + O(1)
                job = jobMaxHeap.extractMax();
            }
        }
    }


    public void run_to_completion() {
        while (jobMaxHeap.heapSize() > 0) {
            schedule();
            System.out.println("System execution completed");
        }
    }


    public void print_stats() {
        System.out.println("--------------STATS---------------");
        System.out.println("Total jobs done: " + finishedJobs.size());
        for (Job finishedJob : finishedJobs) {
            System.out.println(finishedJob);
//            System.out.println(finishedJob + " " + finishedJob.getPreciseArrivalTime());
        }
        System.out.println("------------------------");
        System.out.println("Unfinished jobs: "); // print_stats is called after every job is tried, so unfinished jobs = not ready jobs
        for (Job unfinishedJob : notReadyJobs) { // TODO buildHeap
            unfinishedJobHeap.insert(unfinishedJob);
        }
        while (unfinishedJobHeap.heapSize() != 0) {
            System.out.println(unfinishedJobHeap.extractMax());
        }
        System.out.println("Total unfinished jobs: " + notReadyJobs.size());
        System.out.println("--------------STATS DONE---------------");
    }


    public void handle_add(String[] cmd) {
        System.out.println("ADDING Budget");
        TrieNode searchProject = projectTrie.search(cmd[1]);
        if (searchProject != null) {
            Project project = (Project) searchProject.getValue();
            project.addBudget(Integer.parseInt(cmd[2]));

            Iterator itr = notReadyJobs.iterator();
            while (itr.hasNext()) // O(no. of unfinished jobs) -> O(no. of projects with unfinished jobs) + O(no. of jobs in that project)
            {
                Job job = (Job) itr.next();
                if (job.getProject().getName().equals(project.getName()) && project.getBudget() >= job.getRuntime()) {
                    jobMaxHeap.insert(job);
                    itr.remove();
                }
            }
        }
        else {System.out.println("No such project exists." + cmd[2]);}

    }


    public void handle_empty_line() {
       schedule();
        System.out.println("Execution cycle completed");
    }


    public void handle_query(String key) {
        System.out.println("Querying");
        TrieNode searchJob = jobTrie.search(key);
        if (searchJob != null) {
            Job job = (Job) searchJob.getValue();

            if (job.isFinished()) {
                System.out.println(job.getName() + ": COMPLETED");
            }
            else System.out.println(job.getName() + ": NOT FINISHED");
        }
        else System.out.println(key + ": NO SUCH JOB");
    }

    public void handle_user(String name) {
        System.out.println("Creating user");
        User user = new User(name,0,0);
        userTrie.insert(user.getName(), user);
//        userMaxHeap.insert(user);
        userArrayList.add(user);
    }


    public void handle_job(String[] cmd) {
        System.out.println("Creating job");
        String name = cmd[1];
        int runtime = Integer.parseInt(cmd[4]);
        TrieNode searchProject = projectTrie.search(cmd[2]);
        if (searchProject != null) {
            Project project = (Project) searchProject.getValue();

            TrieNode searchUser = userTrie.search(cmd[3]);
            if (searchUser != null) {

                User user = (User) searchUser.getValue();

                Job job = new Job(name,project,project.getPriority(),user,runtime,globalTime,globalArrivalTime);
                globalArrivalTime+=1;
                jobMaxHeap.insert(job);
                jobTrie.insert(name,job);
                project.addJob(job);
                user.addJob(job);
            }
            else {System.out.println("No such user exists: " + cmd[3]);}
        }
        else {System.out.println("No such project exists. " + cmd[2]);}
    }


    public void handle_project(String[] cmd) {
        System.out.println("Creating project");
        String name = cmd[1];
        int priority = Integer.parseInt(cmd[2]);
        int budget = Integer.parseInt(cmd[3]);
        Project project = new Project(name, priority, budget);
        projectTrie.insert(name, project);

    }


    private void execute_a_job(Job job) {

        assert job.getProject() != null;
        Project project = job.getProject();
        int jobRunTime = job.getRuntime();
        globalTime+=jobRunTime;
        job.setFinished();
        job.getUser().addConsumedBudget(jobRunTime);
        job.getUser().setLatestJobTime(globalTime);
        finishedJobs.add(job);

        job.setCompleteTime(globalTime);
        project.decreaseBudget(jobRunTime);
    }

    @Override
    public void timed_handle_user(String name) {
        User user = new User(name,0,0);
        userTrie.insert(user.getName(), user);
//        userMaxHeap.insert(user);
        userArrayList.add(user);
    }

    @Override
    public void timed_handle_job(String[] cmd) {
        String name = cmd[1];
        int runtime = Integer.parseInt(cmd[4]);
        TrieNode searchProject = projectTrie.search(cmd[2]);
        if (searchProject != null) {
            Project project = (Project) searchProject.getValue();

            TrieNode searchUser = userTrie.search(cmd[3]);
            if (searchUser != null) {

                User user = (User) searchUser.getValue();

                Job job = new Job(name,project,project.getPriority(),user,runtime,globalTime,globalArrivalTime);
                globalArrivalTime+=1;
                jobMaxHeap.insert(job);
                jobTrie.insert(name,job);
                project.addJob(job);
                user.addJob(job);
            }
        }
    }

    @Override
    public void timed_handle_project(String[] cmd) {
        String name = cmd[1];
        int priority = Integer.parseInt(cmd[2]);
        int budget = Integer.parseInt(cmd[3]);
        Project project = new Project(name, priority, budget);
        projectTrie.insert(name, project);
    }

    @Override
    public void timed_run_to_completion() {
        while (jobMaxHeap.heapSize() > 0) {

            Job job = jobMaxHeap.extractMax();

            while (job != null) {

                assert job.getProject() != null;
                Project project = job.getProject();

                if (project.getBudget() >= job.getRuntime()) {

                    execute_a_job(job);

                    break;
                }
                else {

                    notReadyJobs.add(job); // O(1) -> O(log (no. of projects with unfinished jobs)) + O(1)
                    job = jobMaxHeap.extractMax();
                }
            }
        }
    }

    /**
     * @param jobs array
     * @param arrivalTime t1
     * @return smallest index with arrival time >= arrivalTime
     * O(log (# jobs))
     */
    private int binarySearch(ArrayList<Job> jobs, int arrivalTime) {

        int left = 0, right = jobs.size()-1;

        while (left <= right) {

            if (jobs.get(left).arrival_time() >= arrivalTime) {
                return left;
            }

            int mid = (left + right)/2;

            if (jobs.get(mid).arrival_time() == arrivalTime) {
                return mid;
            }

            else if (jobs.get(mid).arrival_time() > arrivalTime && jobs.get(mid-1).arrival_time() < arrivalTime) {
                return mid;
            }

            else if (jobs.get(mid).arrival_time() < arrivalTime) {
                left = mid + 1;
            }

            else right = mid - 1;
        }
        return -1; // when all jobs have arrival time < arrivalTime
    }
}
