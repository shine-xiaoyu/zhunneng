package com.xqy.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.xqy.entity.Dept;
import com.xqy.entity.Plan;

public interface PlanService {
	
	PageInfo<Plan> findAll(Plan plan,Integer pageNum,Integer pageSize);

	Boolean insert(Plan plan);

	Plan selectOne(Integer id);

	Boolean deleteAll(String ids);
	
	List<Dept> findAllDept();
	
	Boolean update(Plan plan);
	
}
