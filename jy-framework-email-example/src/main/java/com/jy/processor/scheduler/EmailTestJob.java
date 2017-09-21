package com.jy.processor.scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class EmailTestJob extends QuartzJobBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailTestJob.class);
	private static int step = 0;
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		step += 1;
		LOGGER.info("定时任务执行中…执行第"+step+"次");
	}

}
