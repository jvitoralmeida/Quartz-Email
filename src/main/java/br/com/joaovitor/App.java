package br.com.joaovitor;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import br.com.joaovitor.jobs.JobRollout;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		SchedulerFactory shedFact = new StdSchedulerFactory();

		try {
			Scheduler scheduler = shedFact.getScheduler();
			scheduler.start();
			JobDetail job = JobBuilder.newJob(JobRollout.class).withIdentity("JobRollout", "grupo01").build();
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity("RolloutTrigger", "groupo01")
					.withSchedule(CronScheduleBuilder.cronSchedule("0/1 * * * * ?")).build();

			scheduler.scheduleJob(job, trigger);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
