<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<title>근무관리</title>
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<script src="./js/main.js" defer></script>
<link rel="stylesheet" type="text/css" href="./css/style.css">
<style>
    table {
        clear: both;
        margin-top: 50px;
        left: 100px;
        width:90%;
        top: 15%;
        min-width:1300px;
        transform:none;
        margin-bottom: 100px;
        position:relative;
    }
    th {
        height: 30px;
        width: 70px;
        background-color:  #287bff;
    }
    td {
        text-align: center;
        height: 50px;
        width: 70px;
        padding: 40px 55px;
    
    }
     input {
        height: 50px;
        width: 133px;
        border: none;
        /* background-color:  #287bff; */
        font-size: 18px;
        color:black;
        cursor:pointer;
    }  
    .year_mon{
        font-size: 25px;
    }
    .colToday{
    
    }
    button{
    	all: unset;
    	text-decoration: none;
    	cursor:pointer;
    }
</style>
</head>
<body>
<div class="container">
    <jsp:include page="navigation.jsp"/>
    <!-- main -->
    <div class="main">
        <div class="topbar">
            <div class="toggle">
                <ion-icon name="menu-outline"></ion-icon>
            </div>
        </div>

    <!--table-->
    <div class="maintitle">
      <h2 style="margin-bottom:25px;" id="calendarManage"></h2>
        <div style="text-align-last: left;padding-right: 90px;margin-bottom: -35px;"">
	        <input type="month" id="dmonth" name="dnameFile" value="<%= dateForm %>">
			<!-- <button id="download_file_btn">양식 다운로드</button></div><br> -->
			<button id="download_file_btn" style="border-radius: 10px;width: 50px;vertical-align: middle;height: 50px;padding: 0;background: #00ff0000;color: var(--blue);">
				<ion-icon style="width: 40px;height: 40px;" name="arrow-down-circle-outline"></ion-icon>
			</button>
			<small style="font-size:initial; color:gray">*날짜를 선택하고 양식(엑셀파일)을 받아주세요.</small>
		</div>
		<br>
		<div style="text-align-last: left;padding-right: 90px;">
		<input type="month" id="month" name="nameFile" value="<%= dateForm %>">
		<form id="upload_file_frm" style="display: inline-block;">
			<input type="file" style="width:232px;" id="upload_file" name="uploadFile" accept="application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" />
			<button type="button" id="upload_file_btn" style="border-radius: 10px;width: 50px;vertical-align: middle;height: 50px;padding: 0;background: #00ff0000;color: var(--blue);">
				<ion-icon style="width: 40px;height: 40px;" name="arrow-up-circle-outline"></ion-icon>
			</button>
		</form>
		<small style="font-size:initial; color:gray">*날짜를 선택하고 양식을 업로드 해주세요.</small>
		</div>
        </div>
      <div>
	  	<table id="calendar">
	           <thead>
	               <tr>
	                   <th><input name="preMon" style="height: 50px; width: 70px; border: none; background-color:  #287bff; font-size: 30px; color:white;" id="preMon" type="button" value="<"></th>
	                 <th colspan="5" class="year_mon"></th>
	                 <th><input name="nextMon" style="height: 50px; width: 70px; border: none; background-color:  #287bff; font-size: 30px; color:white;"  id="nextMon" type="button" value=">"></th>
	             </tr>
	             <tr>
	                 <th>일</th>
	                 <th>월</th>
	                 <th>화</th>
	                 <th>수</th>
	                 <th>목</th>
	                 <th>금</th>
	                 <th>토</th>
	             </tr>
	         </thead>
	         <tbody>
	         </tbody>
	     </table>
	 </div> 
		 <!-- <div style="height:300px; background:black; margin-top:1000px;"></div> -->
	</div>
</div>
<script>
$(function(){
	let login = JSON.parse(sessionStorage.getItem("login"));
	document.getElementById("calendarManage").append(login.code+" 근무관리");
	
    var today = new Date();
    var date = new Date();           

    $("input[name=preMon]").click(function() { // 이전달
        $("#calendar > tbody > td").remove();
        $("#calendar > tbody > tr").remove();
        today = new Date ( today.getFullYear(), today.getMonth()-1, today.getDate());
        buildCalendar();
    })
    
    $("input[name=nextMon]").click(function(){ //다음달
        $("#calendar > tbody > td").remove();
        $("#calendar > tbody > tr").remove();
        today = new Date ( today.getFullYear(), today.getMonth()+1, today.getDate());
        buildCalendar();
    })
    
    $("#upload_file_btn").click(function(){
    	let code = JSON.parse(sessionStorage.getItem("login")).code;
		if($("#month").val() != null){
			if($("#upload_file").val() == ""){
				alert("파일을 등록해 주세요.");
			}else{
				$.ajax({
 					url:"http://localhost:3000/fileupload?date="+$("#month").val()+"&code="+code,
					type: "POST",
					data: new FormData($("#upload_file_frm")[0]),	
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
		let code = JSON.parse(sessionStorage.getItem("login")).code;
		
		// 클라이언트가 서버에서 기존 파일을 요청하면 AJAX success () 메소드가 실행되지만 파일이 다운로드되지도 않습니다. 
		/* 해결법
		1.ajax를 사용하지 말고 window.location.href를 파일의 URL로 설정하고 서버 스크립트에서 http 컨텐츠 처리 헤더를 설정하여 브라우저가 파일을 저장하도록하십시오.
		*/
		window.location.href = "http://localhost:3000/download?date="+$("#dmonth").val()+"&code="+code;
	});

    function buildCalendar() {
        
        nowYear = today.getFullYear();
        nowMonth = today.getMonth();
        firstDate = new Date(nowYear,nowMonth,1).getDate();
        firstDay = new Date(nowYear,nowMonth,1).getDay(); //1st의 요일
        lastDate = new Date(nowYear,nowMonth+1,0).getDate();

        if((nowYear%4===0 && nowYear % 100 !==0) || nowYear%400===0) { //윤년 적용
            lastDate[1]=29;
        }

        $(".year_mon").text(nowYear+"년 "+(nowMonth+1)+"월");
        let code = JSON.parse(sessionStorage.getItem("login")).code;
        
        $.ajax({
			url:"http://localhost:3000/getScheduleList",
			type:"post",
			data:{"code":code, "year":nowYear, "month":nowMonth+1},
			success:function(list){
				for (i=0; i<firstDay; i++) { //첫번째 줄 빈칸
		            $("#calendar tbody:last").append("<td></td>");
		        }
		        for (i=1; i <=lastDate; i++){ // 날짜 채우기
		            plusDate = new Date(nowYear,nowMonth,i).getDay();
		            if (plusDate==0) {
		                $("#calendar tbody:last").append("<tr></tr>");
		            }
		            let str = "";
		            str += "<td class='date'>"+ i;
		            $.each(list, function(idx, item){
		            	let d = parseInt(item.wdate.substring(8, 10));
		            	if(d == i){
		            		str += "<br>";
		            		str += item.time.toUpperCase() + " "+ item.name;
		            	}
					});
		            str += "</td>";
		            $("#calendar tbody:last").append(str);
		        }
		        if($("#calendar > tbody > td").length%7!=0) { //마지막 줄 빈칸
		            for(i=1; i<= $("#calendar > tbody > td").length%7; i++) {
		                $("#calendar tbody:last").append("<td></td>");
		            }
		        }
			},
			error:function(){
				alert("error");
			}
		});

        $(".date").each(function(index){ // 오늘 날짜 표시
            if(nowYear==date.getFullYear() && nowMonth==date.getMonth() && $(".date").eq(index).text()==date.getDate()) {
                $(".date").eq(index).addClass('colToday');
            }
        }) 
    }
    buildCalendar();
})
</script>
<script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
<script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>

</body>
</html>