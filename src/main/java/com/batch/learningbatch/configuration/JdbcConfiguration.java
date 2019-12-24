package com.batch.learningbatch.configuration;

import com.batch.learningbatch.bean.Department;
import com.batch.learningbatch.listener.MyChunkListener;
import com.batch.learningbatch.listener.MyJobExecutionListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * @Author: ZHANG
 * @Date: 2019/12/15
 * @Description:
 */
@Configuration
@EnableBatchProcessing
public class JdbcConfiguration {
    @Autowired
    @Qualifier("jdbcDemoWriter")
    private ItemWriter<? super Department> jdbcDemoWriter;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;


    @Bean
    public Job jdbcDemoJob() {
        return jobBuilderFactory.get("jdbcDemoJob")
                .incrementer(new RunIdIncrementer())
                .start(jobStep())
                .listener(new MyJobExecutionListener()) // job前后执行Listener,实现JobExecutionListener
                .build();
    }

    @Bean
    public Step jobStep() {
        return stepBuilderFactory.get("jobStep")
                .<Department, Department>chunk(2)
                .listener(new MyChunkListener()) // chunk前后执行Listener，这里每两次调用一次Listener，必须卸载chunk语句后面
                .reader(reader())
                .writer(jdbcDemoWriter)
                .build();
    }

    @Bean
    public ItemReader<? extends Department> reader() {
        MySqlPagingQueryProvider provider = new MySqlPagingQueryProvider();
        provider.setFromClause("from department");
        provider.setSelectClause("id,departmentName");
        HashMap<String, Order> sortKeys = new HashMap<>();
        sortKeys.put("id", Order.ASCENDING);
        provider.setSortKeys(sortKeys);

        JdbcPagingItemReader<Department> itemReader = new JdbcPagingItemReader<>();
        itemReader.setDataSource(dataSource);
        itemReader.setFetchSize(100);
        itemReader.setQueryProvider(provider);
        itemReader.setRowMapper((resultSet, i) -> Department.builder()
                .id(resultSet.getInt("id"))
                .departmentName(resultSet.getString("departmentName"))
                .build());

        return itemReader;
    }
}
