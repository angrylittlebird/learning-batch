package com.batch.learningbatch.configuration;

import com.batch.learningbatch.bean.Department;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * @Author: ZHANG
 * @Date: 2019/12/15
 * @Description:
 */
//@Configuration
public class FlatFileConfiguration {
    @Autowired
    @Qualifier("jdbcDemoWriter")
    private ItemWriter<? super Department> jdbcDemoWriter;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job fileDemoJob() {
        return jobBuilderFactory.get("fileDemoJob")
                .start(fileStep())
                .build();
    }

    @Bean
    public Step fileStep() {
        return stepBuilderFactory.get("fileStep")
                .<Department, Department>chunk(100)
                .reader(fileReader())
                .writer(jdbcDemoWriter)
                .build();
    }


    @Bean
    public ItemReader<? extends Department> fileReader() {
        FlatFileItemReader flatFileItemReader = new FlatFileItemReader();
        flatFileItemReader.setResource(new ClassPathResource("department.csv"));
        flatFileItemReader.setLinesToSkip(1);

        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setNames(new String[]{"id","departmentName"});

        DefaultLineMapper<Department> lineMapper = new DefaultLineMapper<>();
        lineMapper.setFieldSetMapper(fieldSet -> Department.builder().id(fieldSet.readInt("id"))
                .departmentName(fieldSet.readString("departmentName"))
                .build());
        lineMapper.setLineTokenizer(delimitedLineTokenizer);
        lineMapper.afterPropertiesSet();

        flatFileItemReader.setLineMapper(lineMapper);

        return flatFileItemReader;
    }
}
