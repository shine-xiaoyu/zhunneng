package com.xqy.dao;

import java.util.List;

import com.xqy.entity.Dept;
import com.xqy.entity.Plan;

public interface PlanDao {
	
	List<Plan> findAll(Plan plan);

	int insert(Plan plan);

	Plan selectOne(Integer id);

	int deleteAll(String ids);
	
	List<Dept> findAllDept();
	
	int update(Plan plan);
	
}
