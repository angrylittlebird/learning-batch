package com.batch.learningbatch.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: ZHANG
 * @Date: 2019/12/15
 * @Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Department {
    private int id;
    private String departmentName;
}
