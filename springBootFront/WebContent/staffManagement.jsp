<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<title>Insert title here</title>
<script src="./js/main.js" defer></script>
<link rel="stylesheet" type="text/css" href="./css/style.css">
</head>
<body>
<div class="container">
    <jsp:include page="navigation.jsp"></jsp:include>
    <!-- main -->
    <div class="main">
        <div class="topbar">
            <div class="toggle">
                <ion-icon name="menu-outline"></ion-icon>
            </div>
        </div>

	    <!--table-->
	    <div>
	        <div class="maintitle">
	            <h2>직원관리</h2>
	        </div>
	        <table>
		        <thead>
		            <tr>
		                <!-- <th><input type="checkbox" id="allcheck"></th> -->
		                <th></th>
		                <th>이름</th>
		                <th>아이디</th>
		                <th>병동</th>
		                <th>잔여포인트</th>
		                <th>관리</th>
		                <th>탈퇴</th>
		            </tr>
	            </thead>
	            <!--임시데이터-->
	            <tbody id="tbody">
	            
	            </tbody>
	            <!-- <tr>
	                <td><input type="checkbox"></td>
	                <td>aa</td>
	                <td>aa</td>
	                <td>aa</td>
	                <td>관리자</td>
	                <td>3800</td>
	                <td>
	                    <input type="button" value="승인">
	                    <input type="button" value="거절">
	                </td>
	            </tr> -->
	         </table>
            <!-- <input type="submit" value="탈퇴" id="out" /> -->
	     </div>
     </div>
</div>

<script type="text/javascript">
$(document).ready(function(){
	let login = JSON.parse(sessionStorage.getItem("login"));
	getMemberList(login);
})

function getMemberList(login){
	$.ajax({
		url:"http://localhost:3000/getMemberList",
		type:"post",
		data:login,
		success:function(list){
			$("#tbody").text("");
			$.each(list, function(idx, item){
				let str = "<tr>"
				+"<td>" + (idx+1) + "</td>"
				+"<td>" + item.name + "</td>"
				+"<td>" + item.id + "</td>"
				+"<td>" + item.code + "</td>"
				+"<td>" + item.point + "</td>"
				if(item.auth == 2){
					str += "<td>" + "<input type='button' value='승인'>"+
					"<input type='button' value='거절'>" + "</td>";
				}else{
					str+="<td></td>";
				}
                str += "<td>"+"<input type='submit' value='탈퇴' />"+"</td></tr>"
                $("#tbody").append(str);
			});
		},
		error:function(){
			alert("getMemberList error");
		}
	});
} 
</script>
<script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
<script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>

</body>
</html>