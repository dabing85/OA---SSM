package com.dabing.oa.biz.impl;

import com.dabing.oa.biz.EmployeeBiz;
import com.dabing.oa.dao.EmployeeDao;
import com.dabing.oa.entity.Employee;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("employeeBiz")
public class EmployeeBizImpl implements EmployeeBiz {
    @Resource(name = "employeeDao")
    private EmployeeDao employeeDao;

    @Override
    public void add(Employee employee) {
        employee.setPassword("000000");//默认密码
        employeeDao.insert(employee);
    }

    @Override
    public void remove(String sn) {
        employeeDao.delete(sn);
    }

    @Override
    public void edit(Employee employee) {
        employeeDao.update(employee);
    }

    @Override
    public Employee get(String sn) {
        return employeeDao.select(sn);
    }

    @Override
    public List<Employee> getAll() {
        return employeeDao.selectAll();
    }
}
