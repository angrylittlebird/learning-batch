package com.batch.learningbatch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;


/**
 * @Author: ZHANG
 * @Date: 2019/12/24
 * @Description: 演示实现Listener接口，控制job执行的流程
 * (JobExecutionListener,StepExecutionListener,ChunkListener,ItemReaderListener,ItemProcessListener,ItemWriterListener)
 * 既可以通过实现接口的方式，也可以通过注解的方式来定义Listener
 */
public class MyJobExecutionListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("before job: " + jobExecution.getJobInstance().getJobName());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("after job: " + jobExecution.getJobInstance().getJobName());
    }
}
