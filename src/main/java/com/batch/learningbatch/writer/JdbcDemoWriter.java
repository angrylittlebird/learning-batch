package com.batch.learningbatch.writer;

import com.batch.learningbatch.bean.Department;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: ZHANG
 * @Date: 2019/12/15
 * @Description:
 */
@Component("jdbcDemoWriter")
public class JdbcDemoWriter implements ItemWriter<Department> {
    @Override
    public void write(List<? extends Department> list) throws Exception {
        for (Department department : list) {
            System.out.println(department);
        }
    }
}
