package com.dabing.oa.biz.impl;

import com.dabing.oa.biz.DepartmentBiz;
import com.dabing.oa.dao.DepartmentDao;
import com.dabing.oa.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("departmentBiz")
public class DepartmentBizImpl implements DepartmentBiz {
    @Autowired
    @Qualifier("departmentDao")/*或者用@Resource(name="departmentDao")*/
    private DepartmentDao departmentDao;

    @Override
    public void add(Department department) {
        departmentDao.insert(department);
    }

    @Override
    public void remove(String sn) {
        departmentDao.delete(sn);
    }

    @Override
    public void edit(Department department) {
        departmentDao.update(department);
    }

    @Override
    public Department get(String sn) {
        return departmentDao.select(sn);
    }

    @Override
    public List<Department> getAll() {
        return departmentDao.selectAll();
    }
}
