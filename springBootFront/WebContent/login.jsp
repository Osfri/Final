<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html style="margin-top: 8%;">
<head>
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<link href="css/loginStyle.css?version=7" rel="stylesheet" />

<meta charset="UTF-8">
<title>로그인</title>
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
<div id="sform">
	<article style="text-align: -webkit-center;">
	<img alt="" src="./img/logo.png" style="margin-bottom: 2%;">
		<div class="main" style="text-align: -webkit-center;margin-top: 35px;"">
			<div class="col-md-6 col-sm-12">
				<div class="login-form">
					<form id="frm" action="loginAf.do" method="post">
						<!-- ID입력 -->
						<div class="form-group">				
							<!-- <label style="float:left">ID</label> -->
							 <input type="text" class="form-control" style="width:65%;" placeholder="ID" name="id" id="id">						
						</div>
						<!-- PWD입력 -->
						<div class="form-group">				
							<!-- <label style="float:left">Password</label> --> 
							<input type="password" style="width:65%;" class="form-control" placeholder="Password" name="pwd" id="pwd" onkeypress="if(event.keyCode == 13) loginEnter();">						
							<p id="logincheck" style="font-size: 13px; margin-top: 5px;"></p>				
						</div>
						<!-- 로그인,회원가입 버튼 -->
						<button type="button" class="btn" style="background-color: #FF6417; margin-top: 2%; color: white;" id="login" onclick="loginEnter()">로그인</button>					
					</form>
				</div>
			</div>
		</div>
	</article>
</div>
<div style="margin-top: 130px; text-align: -webkit-center;">
<img alt="" src="./img/back.png" style="height: 330px; ">
</div>
<script type="text/javascript">
function loginEnter(){
	if($("#id").val().trim() == ""){
		alert("아이디를 입력하세요.");
		$("#id").val("");
	}else if($("#pwd").val().trim() == ""){
		alert("비밀번호를 입력하세요.");
		$("#pwd").val("");
	}else{
		$.ajax({
			url:"http://localhost:3000/webLogin",
			type:"post",
			data:{ id:$("#id").val(), pw:$("#pwd").val() },
			success:function(json){
				if(json == ""){
					alert("아이디나 비밀번호를 확인하세요");
					$("#id").val("");
					$("#pwd").val("");
				}else{
					sessionStorage.setItem("login", JSON.stringify(json));
					alert(json.name + "님 환영합니다");	
					location.href = "staffScheduleManagement.jsp";
				}			
			},
			error:function(){
				alert('login error');
			}
		});
	}
}
</script>
</body>
</html>