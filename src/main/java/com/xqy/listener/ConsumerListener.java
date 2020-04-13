package com.xqy.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.listener.MessageListener;

import com.alibaba.fastjson.JSON;
import com.xqy.dao.PlanRepository;
import com.xqy.entity.Plan;
import com.xqy.service.PlanService;
 
public class ConsumerListener implements MessageListener<String, String>{

	@Autowired
	private PlanService planService;
	
	@Autowired
	PlanRepository planRepository; 
	
	@SuppressWarnings("rawtypes")
	@Autowired
	RedisTemplate redisTemplate ;
	 
	@Override
	public void onMessage(ConsumerRecord<String, String> data) {
		System.out.println("接收到了消息");
		String value = data.value();
		
		if (value.startsWith("update")) {
			String[] split = value.split("=");
			Plan plan = JSON.parseObject(split[1], Plan.class);
			//修改es索引库的数据
			planRepository.save(plan);
			redisTemplate.delete("zhunneng_plan");
		}
		
		if (value.startsWith("del")) {
			String[] split = value.split("=");
			String ids = split[1];
			String[] split2 = ids.split(",");
			for (String s : split2) {
				planRepository.deleteById(Integer.parseInt(s));
				
				redisTemplate.delete("zhunneng_plan");
			}
			System.out.println("删除ES索引库成功！！！");
		}
		if (value.startsWith("save")) {
			String[] split = value.split("=");
			Plan plan = JSON.parseObject(split[1], Plan.class);
			planRepository.save(plan);
			System.out.println("同步ES索引库成功！！！");
		}
		
		
		if (value.startsWith("input")) {
			String[] split = value.split("=");
			Plan plan = JSON.parseObject(split[1], Plan.class);
			System.out.println(plan);
			planService.insert(plan);
			System.out.println("保存成功！");
		}
		
	}
	
	
}
