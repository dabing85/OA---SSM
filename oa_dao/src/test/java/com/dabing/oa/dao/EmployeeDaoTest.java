package com.dabing.oa.dao;

import com.dabing.oa.BaseTest;
import com.dabing.oa.entity.Employee;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class EmployeeDaoTest extends BaseTest {
    @Resource(name = "employeeDao")
    private EmployeeDao employeeDao;

    @Test
    public void test(){
        List<Employee> employeeList=employeeDao.selectAll();
        for(Employee employee:employeeList){
            System.out.println(employee);
        }

    }
}
