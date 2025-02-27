package dev.nodesify.springquartzdemo.scheduler;

import dev.nodesify.springquartzdemo.job.SampleQuartzJob;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(0)
@RequiredArgsConstructor
public class SampleScheduler implements CommandLineRunner {

    private final Scheduler scheduler;

    @Override
    public void run(String... args) throws Exception {
        JobDetail jobDetail = JobBuilder.newJob(SampleQuartzJob.class)
                .withIdentity("myJob4", "group1") // name "myJob", group "group1"
                .usingJobData("jobSays", "Hello World!")
                .usingJobData("myFloatValue", 3.141f)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity("myTrigge4", "group1")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(3) // Runs every 10 seconds
                        .repeatForever())
                .build();

        // Define a durable job instance (durable jobs can exist without triggers)
        JobDetail job1 = JobBuilder.newJob(SampleQuartzJob.class)
                .withIdentity("job13", "group1")
                .storeDurably()
                .build();

        // Add the job to the scheduler's store
        scheduler.addJob(job1, true);


//        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // and start it off
//        scheduler.start();

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (ObjectAlreadyExistsException e) {
            // Handle the exception, e.g., log a message or update the existing job
            System.out.println("Job already exists: " + e.getMessage());
            // Optionally, reschedule or update the existing job here
        }


//        scheduler.shutdown(true);

        System.out.println("Quartz job scheduled successfully.");
    }
}
