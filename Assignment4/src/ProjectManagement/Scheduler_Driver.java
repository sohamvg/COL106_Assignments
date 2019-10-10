package ProjectManagement;

import PriorityQueue.MaxHeap;
import PriorityQueue.PriorityQueueDriverCode;
import RedBlack.RBTree;
import RedBlack.RedBlackNode;
import Trie.Trie;
import Trie.TrieNode;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class Scheduler_Driver extends Thread implements SchedulerInterface {

    private static int globalTime = 0;

    private static Trie<Project> projectTrie = new Trie<Project>();
    private static Trie<User> userTrie = new Trie<User>();
    private static MaxHeap<Job> jobMaxHeap = new MaxHeap<>();
    private static RBTree<String, Job> allJobs = new RBTree<>();
    private static ArrayList<Job> finishedJobs = new ArrayList<>();
    private static ArrayList<Job> unfinishedJobs = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Scheduler_Driver scheduler_driver = new Scheduler_Driver();


        File file;
        if (args.length == 0) {
            URL url = PriorityQueueDriverCode.class.getResource("INP");
            file = new File(url.getPath());
        } else {
            file = new File(args[0]);
        }

        scheduler_driver.execute(file);
    }

    public void execute(File file) throws IOException {

        URL url = Scheduler_Driver.class.getResource("INP");
        file = new File(url.getPath());

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.err.println("Input file Not found. "+file.getAbsolutePath());
        }
        String st;
        while ((st = br.readLine()) != null) {
            String[] cmd = st.split(" ");
            if (cmd.length == 0) {
                System.err.println("Error parsing: " + st);
                return;
            }

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
                case "":
                    handle_empty_line();
                    break;
                case "ADD":
                    handle_add(cmd);
                    break;
                default:
                    System.err.println("Unknown command: " + cmd[0]);
            }
        }


        run_to_completion();

        print_stats();

    }




    @Override
    public void run() {
        // till there are JOBS
        schedule();
    }


    @Override
    public void run_to_completion() {

//        Job job = jobMaxHeap.extractMax();
//
//        while (job != null) {
//
//            System.out.println("Running code");
//            System.out.println("Remaining jobs: " + jobMaxHeap.heapSize());
//
//            Object searchProject = projectTrie.search(job.getProject().getName());
//
//            if (searchProject != null) {
//                Project project = (Project) ((TrieNode) searchProject).getValue();
//                if (project.getBudget() >= job.getRuntime()) {
//
//                    job.setFinished(true);
//                    finishedJobs.add(job);
//                    globalTime+=job.getRuntime();
//                    job.setCompleteTime(globalTime);
//                    project.decreaseBudget(job.getRuntime());
//
//                    System.out.println("Executing: " + job.getName() + " from: " + project.getName());
//                    System.out.println("Project: " + project.getName() + " budget remaining: " + project.getBudget());
//                    job = jobMaxHeap.extractMax();
//
//                }
//                else {
//                    System.out.println("Un-sufficient budget.");
//                    unfinishedJobs.add(job);
//                    job = jobMaxHeap.extractMax();
//                }
//            }
//            else return;
//
//            System.out.println("System execution completed");
//        }

        while (jobMaxHeap.heapSize() > 0) {
            schedule();
            System.out.println("System execution completed");
        }

    }

    @Override
    public void handle_project(String[] cmd) {
        System.out.println("Creating project");
        String name = cmd[1];
        int priority = Integer.parseInt(cmd[2]);
        int budget = Integer.parseInt(cmd[3]);
        Project project = new Project(name, priority, budget);
        projectTrie.insert(name, project);

    }

    @Override
    public void handle_job(String[] cmd) {
        System.out.println("Creating job");
        String name = cmd[1];
        int runtime = Integer.parseInt(cmd[4]);
        Object searchProject = projectTrie.search(cmd[2]);
        if (searchProject != null) {
            Project project = (Project) ((TrieNode) searchProject).getValue();

            Object searchUser = userTrie.search(cmd[3]);
            if (searchUser != null) {

                User user = (User) ((TrieNode) searchUser).getValue();
                Job job = new Job(name,project,user,runtime,globalTime);

                jobMaxHeap.insert(job);
                allJobs.insert(name, job);
                // unfinishedJobs.add(job);

            }
            else {System.out.println("No such user exists: " + cmd[3]);}
        }
        else {System.out.println("No such project exists. " + cmd[2]);}
    }

    @Override
    public void handle_user(String name) {
        System.out.println("Creating user");
        User user = new User(name);
        userTrie.insert(user.getName(), user);
    }

    @Override
    public void handle_query(String key) {
        System.out.println("Querying");
        RedBlackNode searchJob = allJobs.search(key);
        if (searchJob.getValues() != null) {
            Job job = (Job) searchJob.getValue();

            if (job.isFinished()) {
                System.out.println(job.getName() + ": COMPLETED");
            }
            else System.out.println(job.getName() + ": NOT FINISHED");
        }
        else System.out.println(key + ": NO SUCH JOB");
    }

    @Override
    public void handle_empty_line() {

        schedule();
        System.out.println("Execution cycle completed");
    }

    @Override
    public void handle_add(String[] cmd) {
        System.out.println("ADDING Budget");
        Object searchProject = projectTrie.search(cmd[1]);
        if (searchProject != null) {
            Project project = (Project) ((TrieNode) searchProject).getValue();
            project.addBudget(Integer.parseInt(cmd[2]));

            Iterator itr = unfinishedJobs.iterator();
            while (itr.hasNext())
            {
                Job job = (Job) itr.next();
                if (job.getProject().getName().equals(project.getName())) {
                    jobMaxHeap.insert(job);
                    itr.remove();
                }
            }

        }
        else {System.out.println("No such project exists." + cmd[2]);}
    }

    @Override
    public void print_stats() {
        System.out.println("--------------STATS---------------");
        System.out.println("Total jobs done: " + finishedJobs.size());
        for (Job finishedJob : finishedJobs) {
            System.out.println(finishedJob);
        }
        System.out.println("------------------------");
        System.out.println("Unfinished jobs: ");
        for (Job unfinishedJob : unfinishedJobs) {
            System.out.println(unfinishedJob);
        }
        System.out.println("Total unfinished jobs: " + unfinishedJobs.size());
        System.out.println("--------------STATS DONE---------------");
    }

    @Override
    public void schedule() {
        System.out.println("Running code");
        System.out.println("Remaining jobs: " + jobMaxHeap.heapSize());

        Job job = jobMaxHeap.extractMax();

        while (job != null) {

            Object searchProject = projectTrie.search(job.getProject().getName());

            if (searchProject != null) {
                Project project = (Project) ((TrieNode) searchProject).getValue();

                System.out.println("Executing: " + job.getName() + " from: " + project.getName());
                if (project.getBudget() >= job.getRuntime()) {

                    job.setFinished(true);
                    finishedJobs.add(job);
                    globalTime+=job.getRuntime();
                    job.setCompleteTime(globalTime);
                    project.decreaseBudget(job.getRuntime());
                    System.out.println("Project: " + project.getName() + " budget remaining: " + project.getBudget());

                    break;
                }
                else {
                    System.out.println("Un-sufficient budget.");

                    unfinishedJobs.add(job);
                    job = jobMaxHeap.extractMax();
                }
            }
            else return;
        }

    }
}
