package com.dabing.oa.dao;

import com.dabing.oa.entity.Department;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("departmentDao")
public interface DepartmentDao {
    //增
    void insert(Department department);
    //删
    void delete(String sn);
    //改
    void update(Department department);
    //查
    Department select(String sn);
    List<Department> selectAll();
}
