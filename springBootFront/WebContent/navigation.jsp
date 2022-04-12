<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(document).ready(function(){
	let login = JSON.parse(sessionStorage.getItem("login"));
	if(login.auth != 0){
		document.getElementById("staff").style.display ="none";
		document.getElementById("addHospital").style.display ="none";
	}
	if(login.auth == 0){
		document.getElementById("CalendarManagement").style.display ="none";
	}
})
</script>
<div class="navigation">
    <ul>
        <li>
            <a href="#">
                <span class="icon"><ion-icon name="home-outline"></ion-icon></span>
                <span class="title">Ho</span>
            </a>
        </li>
        <li id="staff">
            <a href="staffManagement.jsp">
                <span class="icon"><ion-icon name="people-outline"></ion-icon></span>
                <span class="title">직원관리</span>
            </a>
        </li>
        <li id="CalendarManagement">
            <a href="staffCalendarManagement.jsp">
            <!-- <a href="test.jsp"> -->
                <span class="icon"><ion-icon name="desktop-outline"></ion-icon></span>
                <span class="title">근무관리</span>
            </a>
        </li>
        <li>
            <a href="staffScheduleManagement.jsp">
                <span class="icon"><ion-icon name="calendar-outline"></ion-icon></span>
                <span class="title">스케줄관리</span>
            </a>
        </li>
        <li>
            <a href="shopTest.html">
                <span class="icon"><ion-icon name="desktop-outline"></ion-icon></span>
                <span class="title">식단관리</span>
            </a>
        </li>
        <li>
            <a href="fileUpload.jsp">
                <span class="icon"><ion-icon name="calendar-outline"></ion-icon></span>
                <span class="title">스케줄 등록</span>
            </a>
        </li>
        <li id="addHospital">
            <a href="addHospital.jsp">
                <span class="icon"><ion-icon name="desktop-outline"></ion-icon></span>
                <span class="title">병동 관리</span>
            </a>
        </li>
    </ul>
</div>
    
    