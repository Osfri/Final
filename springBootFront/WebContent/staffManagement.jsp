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
					str += "<td>" + "<button type='button' onclick='toYes(\""+item.id+"\")'>승인</button>"+
					"<button type='button' onclick='toNo(\""+item.id+"\")'>거절</button>" + "</td>";
				}else if(item.auth == 3){
					str+="<td><button type='button' onclick='toStaff(\""+item.id+"\")'>직원으로 변경</button></td>";
				}else{
					str+="<td><button type='button' onclick='toManager(\""+item.id+"\",\""+item.code+"\")'>관리자로 변경</button></td>";
				}
                str += "<td>"+"<button type='button' onclick='delMember(\""+item.id+"\")'>탈퇴</button>"+"</td></tr>"
                $("#tbody").append(str);
			});
		},
		error:function(){
			alert("getMemberList error");
		}
	});
} 
function toStaff(id){
	if(confirm(id+"님을 관리자에서 직원으로 변경하시겠습니까?")){
		$.ajax({
			url:"http://localhost:3000/toStaff",
			type:"post",
			data:{"id":id},
			success:function(result){
				if(result=="success"){
					alert("변경 되었습니다.");
					location.reload();
				}else{
					alert("변경되지 않았습니다.");
				}
			},
			error:function(){
				alert("변경되지 않았습니다.");
			}
		});
	}
}
function toManager(id, code){
	if(confirm(id+"님을 직원에서 관리자로 변경하시겠습니까?")){
		$.ajax({
			url:"http://localhost:3000/toManager",
			type:"post",
			data:{"id":id, "code":code},
			success:function(result){
				if(result=="already"){
					alert("해당 병동에 이미 관리자가 존재합니다.");
				}
				else if(result=="success"){
					alert("변경 되었습니다.");
					location.reload();
				}else{
					alert("변경되지 않았습니다.");
				}
			},
			error:function(){
				alert("변경되지 않았습니다.");
			}
		});
	}
}
function toYes(id){
	if(confirm(id+"님을 병동에 추가하시겠습니까?")){
		$.ajax({
			url:"http://localhost:3000/toYes",
			type:"post",
			data:{"id":id},
			success:function(result){
				if(result=="success"){
					alert("추가되었습니다.");
					location.reload();
				}else{
					alert("추가되지 않았습니다.");
				}
			},
			error:function(){
				alert("추가되지 않았습니다.");
			}
		});
	}
}
function toNo(id){
	if(confirm(id+"님을 병동에서 내보내시겠습니까?")){
		$.ajax({
			url:"http://localhost:3000/toNo",
			type:"post",
			data:{"id":id},
			success:function(result){
				if(result=="success"){
					alert("승인을 거부했습니다.");
					location.reload();
				}else{
					alert("다시 시도해주세요.");
				}
			},
			error:function(){
				alert("다시 시도해주세요.");
			}
		});
	}
}
function delMember(id){
	if(confirm(id+"님을 탈퇴시키시겠습니까?")){
		$.ajax({
			url:"http://localhost:3000/toNo",
			type:"post",
			data:{"id":id},
			success:function(result){
				if(result=="success"){
					alert("탈퇴 되었습니다.");
					location.reload();
				}else{
					alert("탈퇴에 실패했습니다.");
				}
			},
			error:function(){
				alert("탈퇴에 실패했습니다.");
			}
		});
	}
}
</script>
<script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
<script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>

</body>
</html>