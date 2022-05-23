package com.dabing.oa.dao;

import com.dabing.oa.entity.Employee;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("employeeDao")
public interface EmployeeDao {
    //增
    void insert(Employee employee);
    //删
    void delete(String sn);
    //改
    void update(Employee employee);
    //查
    Employee select(String sn);
    List<Employee> selectAll();
    List<Employee> selectByDepartmentAndPost(@Param("dsn") String dsn,@Param("post") String post);
    //根据部门编号和职务查询,因为有两个参数，不明确所以需要@Param("dsn")来进行相应的调用
}

