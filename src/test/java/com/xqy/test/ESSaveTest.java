package com.xqy.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xqy.dao.PlanDao;
import com.xqy.dao.PlanRepository;
import com.xqy.entity.Plan;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-beans.xml")
public class ESSaveTest {
	
	@Autowired
	PlanRepository planRepository;
	
	@Autowired
	PlanDao planDao;
	
	@Test
	public void saveData() {
		
		List<Plan> list = planDao.findAll(null);
		planRepository.saveAll(list);
		
	}
	
}
