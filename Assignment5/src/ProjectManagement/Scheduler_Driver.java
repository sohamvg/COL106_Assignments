package ProjectManagement;

import PriorityQueue.MaxHeap;
import PriorityQueue.MaxHeapNoNode;
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
    private static int projectArrivalTime = 0;
    private static int userArrivalTime = 0;

    private static Trie<Project> projectTrie = new Trie<Project>();
    private static Trie<User> userTrie = new Trie<User>();
    private static Trie<Job> jobTrie = new Trie<Job>();

    private static MaxHeapNoNode<Job> jobMaxHeap = new MaxHeapNoNode<>(); // stores all untried jobs = total - ( completed + notReadyJobs )
    private static MaxHeap<Project> notReadyProjects = new MaxHeap<>();

    private static ArrayList<User> userArrayList = new ArrayList<>();
    private static ArrayList<Job> finishedJobs = new ArrayList<>();
    private static ArrayList<Job> notReadyJobs = new ArrayList<>(); // jobs which were tried but have insufficient budget
    private static ArrayList<Project> allProjects = new ArrayList<>();

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
//                        qstart_time = System.nanoTime();
                        ArrayList<UserReport_> res = timed_top_consumer(Integer.parseInt(cmd[1]));
//                        qend_time = System.nanoTime();

                        System.out.println("Top query");
//                        System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                        Print(res);

                        break;
                    case "NEW_FLUSH":
//                        qstart_time = System.nanoTime();
                        timed_flush( Integer.parseInt(cmd[1]));
//                        qend_time = System.nanoTime();
//                        System.out.println("Flush query");
//                        System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));


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
//                qstart_time = System.nanoTime();
                res = handle_new_project(cmd);
//                qend_time = System.nanoTime();
                System.out.println("Project query");
//                System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));

                print(res);

                break;
            case "NEW_USER":
//                qstart_time = System.nanoTime();
                res = handle_new_user(cmd);
//                qend_time = System.nanoTime();

                System.out.println("User query");
//                System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));

                print(res);

                break;
            case "NEW_PROJECTUSER":
//                qstart_time = System.nanoTime();
                res = handle_new_projectuser(cmd);
//                qend_time = System.nanoTime();

                System.out.println("Project User query");
//                System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));

                print(res);

                break;
            case "NEW_PRIORITY":
//                qstart_time = System.nanoTime();
                res = handle_new_priority(cmd[1]);
//                qend_time = System.nanoTime();

                System.out.println("Priority query");
                res.sort(null);
                print(res);

                break;
        }

        return res;
    }


    @Override
    public ArrayList<UserReport_> timed_top_consumer(int top) {
        ArrayList<UserReport_> topUsers = new ArrayList<>();
//        ArrayList<MaxHeap.Node<User>> t = new ArrayList<>();
//
//        int tu = 0;
//        for (User user : userArrayList) {
//            MaxHeap.Node<User> u = new MaxHeap.Node<>(user,tu);
//            t.add(u);
//            tu++;
//        }
//
//        MaxHeap<User> temp = new MaxHeap<>();
//        temp.buildHeap(t,t.size());
//        temp.setMaxHeap(t);
//
//        int s = temp.heapSize();
//        while (top != 0) {
//
//            User user = temp.extractMax();
//
//            if (user == null) {
//                break;
//            }
//
//            else {
//                topUsers.add(user);
//            }
//            top = top - 1;
//        }

        userArrayList.sort(null); // O((# user) * log(# user)) //

        int size = userArrayList.size();
        for (int i = size-1; i > size-1-top; i--) {
            if (i < 0) {
                break;
            }
            topUsers.add(userArrayList.get(i));
        }


        return topUsers;
    }


    @Override
    public void timed_flush(int waittime) {
        System.out.println("Flush query");
        // flush...
        int timeAtFlush = globalTime;
        ArrayList<Job> temp = new ArrayList<>();
        Job job = jobMaxHeap.extractMax();
        while (job != null) {
            int jobWaitTime = timeAtFlush - job.arrival_time();
            if (jobWaitTime >= waittime && job.getRuntime() <= job.getProject().getBudget()) {
                execute_a_job(job);

                System.out.println("Flushed: " + job.toString());
            }
            else {
                temp.add(job);
            }

            job = jobMaxHeap.extractMax();
        }

        assert jobMaxHeap.heapSize() == 0;

        jobMaxHeap.buildHeap(temp,temp.size());
        jobMaxHeap.setMaxHeap(temp);

    }


    private ArrayList<JobReport_> handle_new_priority(String s) {
        int priority = Integer.parseInt(s);
        ArrayList<JobReport_> res = new ArrayList<>();

        for (Job notReadyJob : notReadyJobs) {
            if (notReadyJob.getPriority() >= priority) {
                res.add(notReadyJob);
            }
        }

        for (Job notTriedJob : jobMaxHeap.getMaxHeap()) {
            if (notTriedJob.getPriority() >= priority) {
                res.add(notTriedJob);
            }
        }
        return res;
    }


    private ArrayList<JobReport_> handle_new_projectuser(String[] cmd) { // TODO check time

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
                res.sort(new JobCompletionComparator());
            }
//            else {System.out.println("No such user exists." + cmd[2]);}

        return res;
    }


    private ArrayList<JobReport_> handle_new_user(String[] cmd) {
        int t1 = Integer.parseInt(cmd[2]);
        int t2 = Integer.parseInt(cmd[3]);
        ArrayList<JobReport_> res = new ArrayList<>();

        TrieNode searchUser = userTrie.search(cmd[1]); // O(user name length)
        if (searchUser != null) {
            ArrayList<Job> userJobs = ((User) searchUser.getValue()).getJobs();

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

//        for (Project project1 : allProjects) {
//            notReadyProjects.insert(project1);
//        }
//
//        Project project = notReadyProjects.extractMax();
//        while (project != null) {
//            project.getJobs().sort(null);
//
//            for (Job job : project.getJobs()) {
//                if (!job.isFinished()) {
//                    System.out.println(job);
//                }
//            }
//            project = notReadyProjects.extractMax();
//        }

        allProjects.sort(null);
        for (Project project : allProjects) {
            //project.getJobs().sort(null);

            for (Job job : project.getJobs()) {
                if (!job.isFinished()) {
                    System.out.println(job);
                }
            }

//            for (int i = project.getJobs().size()-1; i >= 0; i--) {
//                Job job = project.getJobs().get(i);
//                if (!job.isFinished()) {
//                    System.out.println(job);
//                }
//            }
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
        User user = new User(name,0,0,userArrivalTime);
        userArrivalTime+=1;
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
        Project project = new Project(name, priority, budget, projectArrivalTime);
        projectArrivalTime+=1;
        projectTrie.insert(name, project);
        allProjects.add(project);
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
        User user = new User(name,0,0,userArrivalTime);
        userArrivalTime+=1;
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
        Project project = new Project(name, priority, budget, projectArrivalTime);
        projectArrivalTime+=1;
        projectTrie.insert(name, project);
        allProjects.add(project);
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

    private void print(ArrayList<JobReport_> x){
        System.out.println("----Output of the query. This is just for reference-----");
        for(JobReport_ j:x){
            System.out.println(j);
        }
        System.out.println("----------END--------------");
    }

    private void Print(ArrayList<UserReport_> x){
        System.out.println("----Output of the query. This is just for reference-----");
        for(UserReport_ j:x){
            System.out.println(j);
        }
        System.out.println("----------END--------------");

    }
}
