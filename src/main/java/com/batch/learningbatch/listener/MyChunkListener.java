package com.batch.learningbatch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.batch.core.scope.context.ChunkContext;


/**
 * @Author: ZHANG
 * @Date: 2019/12/24
 * @Description: 演示实现Listener接口，控制job执行的流程
 * (JobExecutionListener,StepExecutionListener,ChunkListener,ItemReaderListener,ItemProcessListener,ItemWriterListener)
 * 既可以通过实现接口的方式，也可以通过注解的方式来定义Listener
 */
public class MyChunkListener{

    @BeforeChunk
    public void beforeChunk(ChunkContext chunkContext) {
        System.out.println("before chunk: " + chunkContext.getStepContext().getStepName());
    }

    @AfterChunk
    public void afterChunk(ChunkContext chunkContext) {
        System.out.println("after chunk: " + chunkContext.getStepContext().getStepName());
    }
}
