<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<style type="text/css">
#app{
	margin: auto;
	margin-top: 60px;
	width: 60%;
	border: 3px solid #8ac007;
	padding: 10px;
}
</style>
</head>
<body>

<h1>Login</h1>
<div id="app">
	<table border="1">
		<tr>
			<td>아이디</td>
			<td>
				<input type="text" id="id" placeholder="아이디" size="20"> 
			</td>
		</tr>
		<tr>
			<td>비밀번호</td>
			<td>
				<input type="password" id="pwd" placeholder="비밀번호" size="20"> 
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<button type="button" id="login">log-in</button>
				<a href="account.html">회원가입</a>			
			</td>
		</tr>
	</table>
</div>
<script type="text/javascript">
$(document).ready(function(){
	$("#login").click(function(){
		$.ajax({
			url:"http://localhost:3000/webLogin",
			type:"post",
			data:{ id:$("#id").val(), pw:$("#pwd").val() },
			success:function(json){
			//	alert('success');
			//	alert(JSON.stringify(json));
				if(json == ""){
					alert("id나 비밀번호를 확인하세요");
					$("#id").val("");
					$("#pwd").val("");
				}else{
					sessionStorage.setItem("login", JSON.stringify(json));
					alert(json.name + "님 환영합니다");	
					location.href = "upload.jsp";
				}			
			},
			error:function(){
				alert('error');
			}
		});
	});
});
</script>
</body>
</html>