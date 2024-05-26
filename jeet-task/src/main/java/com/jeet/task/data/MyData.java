package com.jeet.task.data;

import com.jeet.task.job.MyJob;
import org.quartz.*;

public class MyData implements JobDetail {

    private JobDataMap jobDataMap;

    public MyData(JobDataMap jobDataMap) {
        this.jobDataMap = jobDataMap;
    }

    @Override
    public JobKey getKey() {
        return new JobKey("myJobData");
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public Class<? extends Job> getJobClass() {
        return MyJob.class;
    }

    @Override
    public JobDataMap getJobDataMap() {
        return jobDataMap;
    }

    @Override
    public boolean isDurable() {
        return true;
    }

    @Override
    public boolean isPersistJobDataAfterExecution() {
        return true;
    }

    @Override
    public boolean isConcurrentExectionDisallowed() {
        return true;
    }

    @Override
    public boolean requestsRecovery() {
        return true;
    }

    @Override
    public Object clone() {
        return null;
    }

    @Override
    public JobBuilder getJobBuilder() {
        return null;
    }
}
