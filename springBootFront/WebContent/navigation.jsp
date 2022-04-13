<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(document).ready(function(){
	let login = JSON.parse(sessionStorage.getItem("login"));
	document.getElementById("loginId").append(login.name+" 님");
	if(login.auth != 0){
		document.getElementById("staff").style.display ="none";
		document.getElementById("addHospital").style.display ="none";
	}
	if(login.auth == 0){
		document.getElementById("CalendarManagement").style.display ="none";
	}
})
function logout(){
	if(confirm("로그아웃 하시겠습니까?")){
		sessionStorage.setItem("login", "");
		location.href="login.jsp";
	}
}
</script>
<div class="navigation">
<<<<<<< HEAD
        <ul>
            <li>
                <a href="#">
                    <span class="icon"><ion-icon name="home-outline"></ion-icon></span>
                    <span class="title">Ho</span>
                </a>
            </li>
            <li>
                <a href="staffManagement.jsp">
                    <span class="icon"><ion-icon name="people-outline"></ion-icon></span>
                    <span class="title">직원관리</span>
                </a>
            </li>
            <li>
                <a href="#">
                    <span class="icon"><ion-icon name="desktop-outline"></ion-icon></span>
                    <span class="title">근무관리</span>
                </a>
            </li>
            <li>
                <a href="staffCalendarManagement.jsp">
                    <span class="icon"><ion-icon name="calendar-outline"></ion-icon></span>
                    <span class="title">스케줄관리</span>
                </a>
            </li>
            <li>
                <a href="foodTest.jsp"">
                    <span class="icon"><ion-icon name="desktop-outline"></ion-icon></span>
                    <span class="title">식단관리</span>
                </a>
            </li>
            <!-- <li>
                <a href="upload.jsp">
                    <span class="icon"><ion-icon name="calendar-outline"></ion-icon></span>
                    <span class="title">스케줄 등록</span>
                </a>
            </li> -->
            <li>
                <a href="addHospital.jsp">
                    <span class="icon"><ion-icon name="desktop-outline"></ion-icon></span>
                    <span class="title">병동 관리</span>
                </a>
            </li>
            <li>
                <a href="shopTest.jsp">
                    <span class="icon"><ion-icon name="desktop-outline"></ion-icon></span>
                    <span class="title">포인트몰관리</span>
                </a>
            </li>
        </ul>
    </div>
=======
    <ul>
        <li>
            <a href="#">
                <span class="icon"><ion-icon name="home-outline"></ion-icon></span>
                <span class="title" id="loginId"></span>
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
                <span class="icon"><ion-icon name="reader-outline"></ion-icon></span>
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
                <span class="icon"><ion-icon name="pizza-outline"></ion-icon></span>
                <span class="title">식단관리</span>
            </a>
        </li>
        <!-- <li>
            <a href="fileUpload.jsp">
                <span class="icon"><ion-icon name="calendar-outline"></ion-icon></span>
                <span class="title">스케줄 등록</span>
            </a>
        </li> -->
        <li id="addHospital">
            <a href="addHospital.jsp">
                <span class="icon"><ion-icon name="desktop-outline"></ion-icon></span>
                <span class="title">병동 관리</span>
            </a>
        </li>
        <li style="position:fixed; bottom:0; width:290px;">
            <a href="javascript:void(0);" onclick="logout();">
            	<span class="icon"><ion-icon name="exit-outline"></ion-icon></span>
                <span class="title">로그아웃</span>
            </a>
        </li>
    </ul>
</div>

>>>>>>> 7e947865ee4db6e8d521ab67b6822516679ccd8c
