package com.xqy.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.xqy.entity.Plan;

public interface PlanRepository extends ElasticsearchRepository<Plan, Integer>{
	
}
