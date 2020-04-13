<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>添加页面</title>
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
				$("#select1").append("<option value = '"+dept[i].id+"'>"+dept[i].name+"</option>")
			}
		}
	})
	
	
	/* function doAdd(){
		$.ajax({
			url:"/doAdd?"+$("#form1").serialize(),
			type:"post",
			success:function(flag){
				if (flag) {
					alert("添加成功！");
					location.href = "/";
				}else{
					location.href = "/doAdd";
				}
			}
			
		})
	} */
	
	
	
</script>

<body>
	<h2>项目发布</h2>
	
	<form id="form1" enctype="multipart/form-data" action="doAdd" method="post">
			项目名称:
			<input type="text" name="name"> <br>
			所属部门:
				<select id="select1" name="dept_id">
					<option>请选择部门</option>
				</select>
			<br>投资金额:
			<input type="text" name="amount"> 元<br>
			项目图片:
			<input type="file" name="file"><br>
			分管领导:
			<input type="text" name="manager"> 
			<br>项目介绍:
			<textarea rows="4" cols="20"  name="content"></textarea><br>
		
		<button   class="btn-primary btn my-1">添加</button>
	</form>
</body>
</html>