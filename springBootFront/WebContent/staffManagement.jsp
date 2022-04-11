<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<!-- ss --> 
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<title>Insert title here</title>
<script src="./js/main.js" defer></script>
<link rel="stylesheet" type="text/css" href="./css/style.css">

<!-- ss -->
<script type="text/javascript" src="./jquery/jquery.twbsPagination.min.js"></script>

</head>
<body>
<div class="custom_container">
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
	        <select name="hospital" id="hospital">
			  <option value="all" selected>전체</option>
			</select>
			<button type="button" id="searchBtn">검색</button>
			<input type="number" id="point" placeholder="지급할 포인트를 입력하세요."/>
			<button type="button" id="pointBtn" onclick="point()">포인트 지급하기</button>
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
	            <tbody id="tbody">
	            
	            </tbody>
	         </table>
	           <div class="container" style="position: absolute; left: 50%; transform: translate(-50%, -50%); bottom: 50px;">
			    <nav aria-label="Page navigation">
			        <ul class="pagination" id="pagination" style="justify-content:center"></ul>
			    </nav>
			</div>
	     </div>
	</div>
</div>

<script type="text/javascript">
$(document).ready(function(){
	let login = JSON.parse(sessionStorage.getItem("login"));
	getHospitalList(login.code);
	getMemberList(0, "all");
	getStaffCount("all");
	$("#searchBtn").click(function(){
		getMemberList(0, $("#hospital option:selected").val());
		getStaffCount($("#hospital option:selected").val());
	});
})

function getMemberList(page, hospital){
	let code = JSON.parse(sessionStorage.getItem("login")).code;
	$.ajax({
		url:"http://localhost:3000/getMemberList",
		type:"post",
		data:{"page":page, "code":code, "hospital":hospital},
		success:function(list){
			$("#tbody").text("");
			let str="";
			if(list.length == 0){
				str="<tr><td colspan='7'>해당 병동의 직원이 없습니다.</td></tr>"
                $("#tbody").append(str);
			}else{
				$.each(list, function(idx, item){
					str = "<tr>"
					+"<td>" + (idx+1) + "</td>"
					+"<td>" + item.name + "</td>"
					+"<td>" + item.id + "</td>"
					//+"<td><div id='"+item.id+"'>" + item.code + "</div><button type='button' id='changeBtn_"+item.id+"' onclick='changeHospital(\""+item.id+"\",\""+code+"\")'>변경</button></td>"
					+"<td><div id='"+item.id+"'><a href='javascript:void(0);' onclick='changeHospital(\""+item.id+"\",\""+code+"\")'>" + item.code + "</a></div></td>"
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
			}
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
//병동 리스트 불러오기
function getHospitalList(code){
	$.ajax({
		url:"http://localhost:3000/getHospitalList",
		type:"post",
		data:{"code":code},
		success:function(list){
			$.each(list, function(idx, item){
				  var elOptNew = document.createElement('option');
				  elOptNew.text = item.code;
				  elOptNew.value = item.code;
				  var elSel = document.getElementById('hospital');
				  try {
				   elSel.add(elOptNew, null); // standards compliant; doesn't work in IE
				  }catch(ex) {
				   elSel.add(elOptNew); // IE only
				  }
			});
		},
		error:function(){
			alert("getHospitalList error");
		}
	});
}
function changeHospital(id, code){
	$.ajax({
		url:"http://localhost:3000/getHospitalList",
		type:"post",
		data:{"code":code},
		success:function(list){
			var curCode = $("#"+id).text(); 
			$("#"+id).empty();
			$("#"+id).append("<select name='s_"+id+"' id='s_"+id+"'></select><button type='button' id='changeAfBtn_"+id+"' onclick='changeAf(\""+id+"\")'>변경하기</button>");
			$.each(list, function(idx, item){
				  var elOptNew = document.createElement('option');
				  elOptNew.text = item.code;
				  elOptNew.value = "s_"+item.code;
				  if(curCode == item.code){
					  elOptNew.selected = "true";  
				  }
				  var elSel = document.getElementById("s_"+id);
				  try {
				   elSel.add(elOptNew, null); // standards compliant; doesn't work in IE
				  }catch(ex) {
				   elSel.add(elOptNew); // IE only
				  }
			});
		},
		error:function(){
			alert("changeHospital error");
		}
	});
}
function changeAf(id){
	let code = $("#s_"+id+" option:selected").val();
	if(confirm(id+"님의 병동을 변경하시겠습니까?")){
		$.ajax({
			url:"http://localhost:3000/changeHospital",
			type:"post",
			data:{"id":id, "code":code},
			success:function(result){
				if(result == "manager"){
					alert("관리자는 병동을 변경할 수 없습니다.");	
					location.reload();
				}else if(result=="success"){
					alert("변경 되었습니다.");
					location.reload();
				}else{
					alert("변경에 실패했습니다.");
				}
			},
			error:function(){
				alert("변경에 실패했습니다.");
			}
		});
	}
}
function point(){
	let point = $("#point").val();
	let login = JSON.parse(sessionStorage.getItem("login"));
	if(confirm(point+" 포인트를 모든 직원에게 지급하시겠습니까?")){
		$.ajax({
			url:"http://localhost:3000/point",
			type:"post",
			data:{"point":point, "code":login.code},
			success:function(result){
				if(result=="success"){
					alert("포인트가 지급되었습니다.");
					location.reload();
				}else{
					alert("포인트가 지급되지 않았습니다.");
				}
			},
			error:function(){
				alert("point error");
			}
		});
	}
}
function getStaffCount(hospital){
	let code = JSON.parse(sessionStorage.getItem("login")).code;
	$.ajax({
		url:"http://localhost:3000/getStaffCount",
		type:"post",
		data:{"hospital":hospital, "code":code},
		success:function(cnt){
			loadPage(cnt);
		},
		error:function(){
			alert("getStaffCount error");
		}
	});
}

function loadPage(totalCnt){
	let _totalPages = totalCnt / 10;
	if(totalCnt % 10 > 0){
		_totalPages += 1;
	}
	
	$('#pagination').twbsPagination('destroy');	

	$('#pagination').twbsPagination({
	    totalPages: _totalPages,
	    visiblePages: 10,
	    first:'<span sris-hidden="true">«</span>',
	    last:'<span sris-hidden="true">»</span>',
	    prev:"이전", 
	    next:"다음",
	    initiateStartPageClick:false,	//onPageClick
	    onPageClick: function (event, page) {
	        //alert(page);
	        getMemberList(page - 1, $("#hospital option:selected").val());
	    }
	 })
}
</script>
<script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
<script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>

</body>
</html>