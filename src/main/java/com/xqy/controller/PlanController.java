package com.xqy.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.xqy.entity.Dept;
import com.xqy.entity.Plan;
import com.xqy.service.PlanService;
import com.xqy.util.HLUtils;

@Controller
public class PlanController {
	
	@Autowired
	private PlanService planService;
	
	@Autowired
	ElasticsearchTemplate elasticsearchTemplate;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	RedisTemplate redisTemplate;
	
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	
	
	
	//列表查询
	@SuppressWarnings("unchecked")
	@RequestMapping("/")
	public String getlist(Plan plan,Model model,@RequestParam(defaultValue = "1")Integer pageNum,
			@RequestParam(defaultValue = "5")Integer pageSize) {
		//使用es查询
//		String value = plan.getContent()+plan.getName()+plan.getManager();
//		PageInfo<Plan> info = (PageInfo<Plan>) HLUtils.findByHighLight(elasticsearchTemplate, Plan.class, pageNum, pageSize, new String[] {"name","manager","content"}, "id", value);
//		model.addAttribute("plan", plan);
//		model.addAttribute("info", info);
		
		model.addAttribute("plan", plan);
		List<Plan> redisPlan = redisTemplate.opsForList().range("zhunneng_plan", 0, -1);
		if (pageNum==1) {
			if (redisPlan==null || redisPlan.size()==0) {
				System.out.println("从mysql中查询了数据");
				PageInfo<Plan> info = planService.findAll(plan, pageNum, pageSize);
				redisTemplate.opsForList().rightPushAll("zhunneng_plan", info.getList().toArray());
				model.addAttribute("info", info);
				
			}else {
				System.out.println("从redis中查询了数据");
				model.addAttribute("info", new PageInfo<>(redisPlan));
			}
		}
		if (pageNum>1) {
			PageInfo<Plan> info = planService.findAll(plan, pageNum, pageSize);
			model.addAttribute("info", info);
		}
		
		
		//一般使用条件查询
		return "list";
	}
	
	@RequestMapping("/search")
	public String ESSearch(String keyname,Model model,@RequestParam(defaultValue = "1")Integer pageNum,
			@RequestParam(defaultValue = "5")Integer pageSize) {
		PageInfo<Plan> info = (PageInfo<Plan>) HLUtils.findByHighLight(elasticsearchTemplate, Plan.class, pageNum, pageSize, new String[] {"manager","content","name"}, "id", keyname);
		model.addAttribute("info", info);
		model.addAttribute("key", keyname);
		return "list";
	}
	
	
	//根据ID查找   返回一个plan对象，用于项目详情和修改回显
	@RequestMapping("/selectOne")
	public String selectOne(Integer id,Model model) {
		
		Plan plan = planService.selectOne(id);
		model.addAttribute("plan", plan);
		return "details";
	}
	
	//批量删除
	@RequestMapping("deleteAll")
	public @ResponseBody Boolean deleteAll(String ids) {
		Boolean b = planService.deleteAll(ids);
		kafkaTemplate.send("zhunneng", "del="+ids);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}
	
	//去添加页面
	@RequestMapping("/toadd")
	public String toadd() {
		return "add";
	}
	
	//查询所有的部门，用于添加和修改的部门下拉框回显
	@RequestMapping("/getDept")
	public @ResponseBody List<Dept> getDept() {
		return planService.findAllDept();
	}
	
	//执行添加项目操作
	@RequestMapping("/doAdd")
	public String doAdd(Plan plan,MultipartFile file) {
		if (!file.isEmpty()) {
			String path  = "D:/pic/";
			String filename = file.getOriginalFilename();
			System.out.println(filename);
			File f = new File(path,filename);
			try {
				file.transferTo(f);
				plan.setPic(filename);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Boolean insert = planService.insert(plan);
		//添加的时候往ES索引库中添加一份，便于ES搜索，但是为了后期方便维护代码以及解耦合，
		//发送到kafka，在消费者监听器中操作ES索引库的添加功能
		System.out.println(plan.getId());
		String jsonString = JSON.toJSONString(plan);
		kafkaTemplate.send("zhunneng", "save="+jsonString);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  "redirect:/";
	}
	
	//去修改页面，并根据ID查找plan对象并回显
	@RequestMapping("/toupdate")
	public String toupdate(Integer id,Model model){
		Plan plan = planService.selectOne(id);
		model.addAttribute("plan", plan);
		return "update";
	}
	
	//执行修改操作
	@RequestMapping("/doupdate")
	public @ResponseBody Boolean doupdate(Plan plan) {
		
		System.out.println("controller:"+plan);
		String jsonString = JSON.toJSONString(plan);
		kafkaTemplate.send("zhunneng", "update="+jsonString);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return planService.update(plan);
	}
	
	
	
}
