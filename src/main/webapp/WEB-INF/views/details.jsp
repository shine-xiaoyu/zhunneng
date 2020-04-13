<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>计划详情页</title>
<style type="text/css">
	table{margin :0,auto;}
</style>
</head>
<body>
	<table class="table col-sm-10  col-sm-offset-4">
		<h2>投资项目详情</h2>
		<tr>
			<td>项目名称：</td>
			<td>${plan.name }</td>
		</tr>
		<tr>
			<td>投资金额</td>
			<td>${plan.amount }元</td>
		</tr>
		<tr>
			<td>分管领导</td>
			<td>${plan.manager }</td>
		</tr>
		<tr>
			<td>所属部门</td>
			<td>${plan.dname }</td>
		</tr>
		<tr>
			<td>项目说明</td>
			<td>${plan.content }</td>
		</tr>
	</table>
	
</body>
</html>