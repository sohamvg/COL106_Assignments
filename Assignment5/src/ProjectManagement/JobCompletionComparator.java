package ProjectManagement;

import java.util.Comparator;

public class JobCompletionComparator implements Comparator<JobReport_> {

    @Override
    public int compare(JobReport_ jobReport_, JobReport_ t1) {
        return Integer.compare(t1.completion_time(), jobReport_.completion_time()); // TODO check order
    }
}
