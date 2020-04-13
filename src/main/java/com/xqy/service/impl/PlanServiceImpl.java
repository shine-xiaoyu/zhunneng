package com.xqy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xqy.dao.PlanDao;
import com.xqy.entity.Dept;
import com.xqy.entity.Plan;
import com.xqy.service.PlanService;

@Service
public class PlanServiceImpl implements PlanService {

	@Autowired
	private PlanDao planDao;
	
	//列表查询
	@Override
	public PageInfo<Plan> findAll(Plan plan,Integer pageNum,Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Plan> list = planDao.findAll(plan);
		return new PageInfo<Plan>(list);
	}

	//添加项目
	@Override
	public Boolean insert(Plan plan) {
		return planDao.insert(plan)>0;
	}

	//根据ID查找   返回一个plan对象，用于项目详情和修改回显
	@Override
	public Plan selectOne(Integer id) {
		Plan plan = planDao.selectOne(id);
		return plan;
	}
	
	//批量删除
	@Override
	public Boolean deleteAll(String ids) {
		return planDao.deleteAll(ids)>0;
	}

	//查找所有部门
	@Override
	public List<Dept> findAllDept() {
		
		return planDao.findAllDept();
	}

	//修改
	@Override
	public Boolean update(Plan plan) {
		System.out.println("service:"+plan);
		
		return planDao.update(plan)>0;
	}

}
