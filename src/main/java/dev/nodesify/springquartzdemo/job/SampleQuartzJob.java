package dev.nodesify.springquartzdemo.job;

import lombok.Setter;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SampleQuartzJob implements Job {
    private static final Logger LOGGER = LoggerFactory.getLogger(SampleQuartzJob.class);

    String jobSays;

    float myFloatValue;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey key = context.getJobDetail().getKey();
        LOGGER.info("Executing SampleQuartzJob - {} with Instant {}, jobSay:{}, and myFloatvalue:{}", context.getFireTime(),
                key, jobSays, myFloatValue);
    }

    public void setJobSays(String jobSays) {
        this.jobSays = jobSays;
    }

    public void setMyFloatValue(float myFloatValue) {
        this.myFloatValue = myFloatValue;
    }
}
