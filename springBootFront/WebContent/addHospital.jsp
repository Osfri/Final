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
	<div class="main">
        <div class="topbar">
            <div class="toggle">
                <ion-icon name="menu-outline"></ion-icon>
            </div>
        </div>
	
	    <!--table-->
	    <div>
	        <div class="maintitle">
	            <h2>병동관리</h2>
	        </div>
			<input type="text" id="addText" placeholder="병동 이름 입력"/>
			<input type="button" id="addBtn" value="병동 추가하기"/>
	        <table>
		        <thead>
		            <tr>
		                <!-- <th><input type="checkbox" id="allcheck"></th> -->
		                <th></th>
		                <th>병동이름</th>
		                <th>병동코드</th>
		                <th>관리자 아이디</th>
		                <!-- 사람이 없을 경우에만 병동 삭제 -->
		                <th>삭제</th>
		            </tr>
	            </thead>
	            <!--임시데이터-->
	            <tbody id="tbody">
	            
	            </tbody>
	            </table>
            <!-- <input type="submit" value="탈퇴" id="out" /> -->
	     </div>
     </div>
<script type="text/javascript">
	$(document).ready(function(){
		let curCode = JSON.parse(sessionStorage.getItem("login")).code;
		getHospitalList(curCode);
		
		$("#addBtn").click(function(){
			if($("#addText").val().trim() == ""){
				alert("병동 이름을 적어주세요.");
				$("#addText").val("");
			}else{
				if(confirm($("#addText").val()+" 병동을 추가하시겠습니까?")){
					addHospital($("#addText").val(), curCode);
				}
			}
		});
	})
	function addHospital(name, curCode){
			$.ajax({
				url:"http://localhost:3000/addHospital",
				type:"post",
				data:{"name":name, "curCode":curCode},
				success:function(result){
					if(result=="already"){
						alert("동일한 이름이 존재합니다. 다른 이름으로 변경해주세요.");
						$("#addText").val("");
					}
					else if(result=="success"){
						alert("추가되었습니다.");
						$("#addText").val("");
						getHospitalList(curCode);
					}else{
						alert("추가되지 않았습니다.");
					}
				},
				error:function(){
					alert("병동 추가 에러");
				}
			})
		}
		function delHospital(code){
			if(confirm("삭제하시겠습니까?")){
				$.ajax({
					url:"http://localhost:3000/delHospital",
					type:"post",
					data:{"code":code},
					success:function(result){
						if(result=="success"){
							alert("삭제 되었습니다.");
							location.reload();
						}else{
							alert("삭제되지 않았습니다.");
						}
					},
					error:function(){
						alert("삭제되지 않았습니다.");
					}
				});
			}
		}
		function getHospitalList(code){
			$.ajax({
				url:"http://localhost:3000/getHospitalList",
				type:"post",
				data:{"code":code},
				success:function(list){
					//alert("success");
					$("#tbody").text("");
					if(list.length == 0){
						let str="";
						str="<tr><td colspan='5'>병동이 없습니다.</td></tr>"
		                $("#tbody").append(str);
					}else{
						$.each(list, function(idx, item){
							let str = "<tr>"
							+"<td>" + (idx+1) + "</td>"
							+"<td>" + item.name + "</td>"
							+"<td>" + item.code + "</td>"
							if(item.manager != null){
								str += "<td>" + item.manager + "</td>";
							}else{
								str += "<td>관리자가 없습니다.</td>";
							}
							if(item.cnt == 0){
								str += "<td>" + "<button type='button' onclick='delHospital(\""+item.code+"\")'>삭제</button></td>";
							}else{
								str+="<td>해당 병동에 소속된 사람이 없어야 삭제 가능합니다.</td>";
							}
			                $("#tbody").append(str);
						});
					}
				},
				error:function(){
					alert("getHospitalList error");
				}
			});
		}	
</script>
<script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
<script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
</div>
</body>
</html>