<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>修改页面</title>
<link rel="stylesheet" type="text/css"
	href="/resource/bootstrap.min.css">
<script type="text/javascript" src="/resource/jquery-1.8.3.min.js"></script>
</head>

<script type="text/javascript">
	
	$.ajax({
		url:'/getDept',
		type:'get',
		success:function(dept){
			for ( var i in dept) {
				$("#select1").append("<option value = '"+dept[i].id+"'>"+dept[i].name+"</option>");
			}
			$("#select1").val('${plan.dept_id}');
		}
	})
	
	
	function doupdate(){
		$.ajax({
			url:'/doupdate?'+$("#form1").serialize(),
			type:'post',
			success:function(flag){
				if (flag) {
					alert("修改成功");
					location.href = "/";
				}else{
					alert("修改失败");
					location.href = "/toupdate";
				}
			}
		})
	}

</script>

<body>
	<h2>项目发布</h2>
	
	<form id="form1">
			<input type="hidden" name="id" value="${plan.id }"> <br>
			项目名称:
			<input type="text" name="name" value="${plan.name }"> <br>
			所属部门:
				<select id="select1" name="dept_id">
					<option>请选择部门</option>
				</select>
			<br>投资金额:
			<input type="text" name="amount" value="${plan.amount }"> 元<br>
			分管领导:
			<input type="text" name="manager" value="${plan.manager }"> 
			<br>项目介绍:
			<textarea rows="4" cols="30"  name="content" >${plan.content }</textarea><br>
		
		<button   class="btn-primary btn my-1" onclick="doupdate()">修改</button>
	</form>
	
</body>
</html>