<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
	Date nowTime = new Date();
	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
	String dateForm = sf.format(nowTime).toString();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>upload</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="./js/main.js" defer></script>
<link rel="stylesheet" type="text/css" href="./css/style.css">
</head>
<body>
<div class="container">
	<jsp:include page="navigation.jsp"></jsp:include>
	<div class="main">
        <div class="topbar">
            <div class="toggle">
                <ion-icon name="menu-outline"></ion-icon>
            </div>
        </div>
		<input type="month" id="dmonth" name="dnameFile" value="<%= dateForm %>">
		<button id="download_file_btn">근무표 양식 다운로드</button>
		<br><br><br>
			
		<input type="month" id="month" name="nameFile" value="<%= dateForm %>">
		<form id="upload_file_frm">
			<input type="file" id="upload_file" name="uploadFile" accept="application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" />
			<!-- <input type="file" id="upload_file" name="uploadFile" accept="*" /> -->
			<button type="button" id="upload_file_btn">근무표 업로드</button>
		</form>
		
		<script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
		<script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
	</div>
</div>
</body>
<script type="text/javascript">
$(document).ready(function(){
	let str = sessionStorage.getItem("login");	
	let json = JSON.parse(str);

	$("#upload_file_btn").click(function(){
		if($("#month").val() != null){
			if($("#upload_file").val() == ""){
				alert("파일을 등록해 주세요.");
			}else{
				$.ajax({
 					url:"http://localhost:3000/fileupload?date="+$("#month").val()+"&code="+json.code,
					//url:"http://localhost:3000/files?nameFile="+$("#month").val(),
					type: "POST",
					data: new FormData($("#upload_file_frm")[0]),	
					//data: {file: $("#upload_file").val()},
					enctype: 'multipart/form-data',
			        processData: false,
			        contentType: false,
			        cache: false,
			        success: function () {   
			        	alert("등록했습니다.");
			        	location.reload();
			        },
			        error: function (e) { 
			        	alert("error", e);
			        }
				}); 
			}
		}else{
			alert("날짜를 입력해주세요.");
		}
	});
	
	$("#download_file_btn").click(function(){
		
		// 클라이언트가 서버에서 기존 파일을 요청하면 AJAX success () 메소드가 실행되지만 파일이 다운로드되지도 않습니다. 내가 잘못하고 있습니까?
		/* 해결법
		1.ajax를 사용하지 말고 window.location.href를 파일의 URL로 설정하고 서버 스크립트에서 http 컨텐츠 처리 헤더를 설정하여 브라우저가 파일을 저장하도록하십시오.
		*/
		window.location.href = "http://localhost:3000/download?date="+$("#dmonth").val()+"&code="+json.code;
		
		/*
		 $.ajax({
			url:"http://localhost:3000/download",
			type: "GET",
			data: { fileName:'1.png' },			
	        success: function () {        	
	            alert('success');
	        },
	        error: function () {        	
	        	alert('error');
	        }
		});*/
	});
});


</script>
</html>