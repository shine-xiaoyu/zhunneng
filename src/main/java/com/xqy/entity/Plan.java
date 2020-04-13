package com.xqy.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "zhunneng" ,type = "plan")
public class Plan implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private Integer id;
	@Field(index = true,store = true,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word",type = FieldType.text)
	private String name;
	private double amount;
	@Field(index = true,store = true,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word",type = FieldType.text)
	private String manager;
	@Field(index = true,store = true,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word",type = FieldType.text)
	private String content;
	private Integer dept_id;
	private String dname;
	private String pic;


	public String getPic() {
		return pic;
	}



	public void setPic(String pic) {
		this.pic = pic;
	}



	@Override
	public String toString() {
		return "Plan [id=" + id + ", name=" + name + ", amount=" + amount + ", manager=" + manager + ", content="
				+ content + ", dept_id=" + dept_id + ", dname=" + dname + "]";
	}



	public String getDname() {
		return dname;
	}



	public void setDname(String dname) {
		this.dname = dname;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getDept_id() {
		return dept_id;
	}

	public void setDept_id(Integer dept_id) {
		this.dept_id = dept_id;
	}

}
