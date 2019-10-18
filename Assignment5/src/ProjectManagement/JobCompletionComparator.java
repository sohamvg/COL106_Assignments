package ProjectManagement;

import java.util.Comparator;

public class JobCompletionComparator implements Comparator<JobReport_> {

    @Override
    public int compare(JobReport_ jobReport_, JobReport_ t1) {
        if (t1.completion_time() != 0 && jobReport_.completion_time() != 0) {
            return jobReport_.completion_time() - t1.completion_time();
        }
        else if (jobReport_.completion_time() == 0 && t1.completion_time() != 0) {
            return 1;
        }
        else if (jobReport_.completion_time() != 0 && t1.completion_time() == 0) {
            return -1;
        }
        else {
            return jobReport_.arrival_time() - t1.arrival_time();
        }
    }
}
