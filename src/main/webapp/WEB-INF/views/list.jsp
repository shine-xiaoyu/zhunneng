<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>准能计划列表</title>
</head>
<link rel="stylesheet" type="text/css"
	href="/resource/bootstrap.min.css">
<script type="text/javascript" src="/resource/jquery-1.8.3.min.js"></script>

<script type="text/javascript">
	function goPage(page){
		
		var data = $("#inlineFormInput").val();
		if (data!="") {
			location.href = "/search?pageNum="+page+"&keyname="+data;
		}else{
			location.href = "/?pageNum="+page;
		}
		
	}
	
	function checkAll(){
		$("[name='id']").each(function(){
			this.checked=!this.checked;
		})
	}
	
	
	function deleteAll(ids){
		var ids = $("[name='id']:checked").map(function(){
			return this.value;
		}).get().join();
		alert(ids);
		if (ids==null||ids=="") {
			alert("为选中任何数据！请先选择");
			return;
		}
		
		$.ajax({
			url:"/deleteAll",
			type:"post",
			data:{ids:ids},
			success:function(flag){
				if(flag){
					alert("删除很成功");					
					location.href = "/";
				}else{
					alert("删除失败！");
				}
			}
		})
	}
	
	
	function add(){
		location.href = "/toadd";
	}
	
	
</script>
<body>
	
	
	<form id="form1" class="form-inline" action="search" method="get">
		<input type="text" name="keyname" id="inlineFormInput" class="my-1 form-control col-sm-3" value="${key }" placeholder="按项目名称查找">
		<input type="submit" value="查询" class="btn-primary btn my-1">
	</form>

	<table class="table" style="text-align: center">
		<input type="button" onclick="add()" value="添加"  class="btn-primary btn my-1"> 
		<input type="button" onclick="deleteAll()" value="批量删除"  class="btn btn-danger"> 
		<tr>
			<td><input type="checkbox" name="ids" onclick="checkAll()"> </td>
			<td>编号</td>
			<td>项目名称</td>
			<td>投资金额</td>
			<td>分管领导</td>
			<td>项目内容</td>
			<td>項目圖片</td>
			<td>部门</td>
			<td>操作</td>
		</tr>
		<c:forEach items="${info.list }" var="p">
			<tr>
				<td><input type="checkbox" name="id" value="${p.id }"> </td>
				<td>${p.id }</td>
				<td>${p.name }</td>
				<td>${p.amount }</td>
				<td>${p.manager }</td>
				<td>${p.content }</td>
				<td>
					<img alt=".." src="/pic/${p.pic}" width="50px" height="50px">
				 </td>
				<td>${p.dname }</td>
				<td>
					<a href="/selectOne?id=${p.id }">查看详情 |</a>
					<a href="/toupdate?id=${p.id }">修改</a>
				</td>
			</tr>
		</c:forEach>
	</table>
	<jsp:include page="/WEB-INF/common/pages.jsp"></jsp:include>
</body>
</html>