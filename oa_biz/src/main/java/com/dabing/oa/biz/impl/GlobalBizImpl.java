package com.dabing.oa.biz.impl;

import com.dabing.oa.biz.GlobalBiz;
import com.dabing.oa.dao.EmployeeDao;
import com.dabing.oa.entity.Employee;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("globalBiz")
public class GlobalBizImpl implements GlobalBiz {
    @Resource(name = "employeeDao")
    private EmployeeDao employeeDao;

    public Employee login(String sn, String password) {
        Employee employee = employeeDao.select(sn);
        if(employee!=null&&employee.getPassword().equals(password)){
            return  employee;
        }
        return null;
    }

    public void changePassword(Employee employee) {
        employeeDao.update(employee);
    }
}

