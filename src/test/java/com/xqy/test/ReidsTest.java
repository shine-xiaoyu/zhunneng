package com.xqy.test;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.xqy.common.utils.StreamUtil;
import com.xqy.entity.Plan;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:producer.xml")
public class ReidsTest {
	
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	
	@Test
	public void testSend() throws Exception {
		
		//C:\\Users\\Administrator\\Desktop\\data.txt
		
		//第一个字符不显示  按每一条显示
//		String line = FileUtil.readTextFileByLine("C:\\Users\\Administrator\\Desktop\\data.txt");
//		System.out.println(line);
		
		//按行正常显示数据
//		List<String> line = FileUtil.readFile2List("C:\\\\Users\\\\Administrator\\\\Desktop\\\\data.txt", "utf-8");
//		for (String string : line) {
//			System.out.println(string);
//		}
//		List<String> list = FileUtil.readFile2List(new File("C:\\\\Users\\\\Administrator\\\\Desktop\\\\data.txt"));
//		for (String s : list) {
//			System.out.println(s);
//		}
		
		
		
//		InputStream in = this.getClass().getResourceAsStream("C:\\\\Users\\\\Administrator\\\\Desktop\\\\data.txt");
//		List<String> list = FileUtil.readFile2List("/data.txt", "utf-8");
//		for (String s : list) {
//			System.out.println(s);
//		}
		
		//读取内部文件
		InputStream rs = this.getClass().getResourceAsStream("/data.txt");
		List<String> list = StreamUtil.readFile2List(rs);
		for (String s : list) {
			String[] split = s.split("\\|\\|");
			String name = split[0];
			double amount = Double.parseDouble(split[1]);
			String manager = split[3];
			String content = split[2];
			Integer dept_id	=  0 ;
			if (split[4].contains("炸药厂")) {
				dept_id = 1;
			}else if (split[4].contains("准能选煤厂")) {
				dept_id = 2;
			}else if (split[4].contains("洗选车间")) {
				dept_id = 3;
			}else if (split[4].contains("生产服务中心")) {
				dept_id = 4;
			}
			else if (split[4].contains("矸电公司")) {
				dept_id = 5;
			}
			else if (split[4].contains("大准铁路公司")) {
				dept_id = 6;
			}
			
			Plan plan = new Plan();
			plan.setName(name);
			plan.setAmount(amount);
			plan.setContent(content);
			plan.setDept_id(dept_id);
			plan.setManager(manager);
			
			String jsonString = JSON.toJSONString(plan);
			kafkaTemplate.send("plan", "input="+jsonString);
			
		}
		
		
	}
	
}
