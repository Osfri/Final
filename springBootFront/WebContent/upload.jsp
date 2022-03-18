<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>upload</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
<button id="download_file_btn">근무표 양식 다운로드</button>
<br><br><br>
<form id="upload_file_frm">
	<input type="month" id="month" name="month" value="2022-03">
	<input type="file" id="upload_file" name="uploadFile" accept="application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" />
	<button type="button" id="upload_file_btn">근무표 업로드</button>
</form>
</body>
<script type="text/javascript">
$(document).ready(function(){
	   
	$("#upload_file_btn").click(function(){
		if($("#month").val() != null){
			if($("#upload_file").val() == ""){
				alert("파일을 등록해 주세요.");
			}else{
				$.ajax({
					url:"http://localhost:3000/fileupload?date="+$("#month").val(),
					type: "POST",
					data: new FormData($("#upload_file_frm")[0]),			
					enctype: 'multipart/form-data',
			        processData: false,
			        contentType: false,
			        cache: false,
			        success: function () {   
			        	alert("success");
			        },
			        error: function () { 
			        	alert("error");
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
		window.location.href = "http://localhost:3000/download?fileName=" + "근무표작성용.xls";
		
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