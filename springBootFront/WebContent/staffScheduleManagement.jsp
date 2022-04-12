<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<title>스케줄 관리</title>
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
             <h2>스케줄관리</h2>
         </div>
         <table>
             <tr>
                 <th>근무명</th>
                 <th>아이콘 색상</th>
                 <th>설명</th>
                 <th>근무시간</th>
             </tr>
             <!--임시데이터-->
             <tr>
                 <td>D</td>
                 <td class="icons">
                     <i style="background-color:rgb(101,176,254);">D</i>
                 </td>
                 <td>DAY</td>
                 <td><input type="time" id="s_d" disabled/>~<input type="time" id="e_d" disabled/>
                 <button type="button" onclick="changeTime('d')" id="c_d">변경하기</button>
                 <button type="button" onclick="saveTime('d')" id="sa_d" style="display:none">저장하기</button>
                 </td>
             </tr> 
             <tr>
                 <td>E</td>
                 <td class="icons">
                     <i style="background-color:rgb(112,87,250);">E</i>
                 </td>
                 <td>EVE</td>
                 <td><input type="time" id="s_e" disabled/>~<input type="time" id="e_e" disabled/>
                 <button type="button" onclick="changeTime('e')" id="c_e">변경하기</button>
                 <button type="button" onclick="saveTime('e')" id="sa_e" style="display:none">저장하기</button>
                 </td>
             </tr> 
             <tr>
                 <td>N</td>
                 <td class="icons">
                     <i style="background-color:rgb(113,113,113);">N</i>
                 </td>
                 <td>NIGHT</td>
                 <td><input type="time" id="s_n" disabled/>~<input type="time" id="e_n" disabled/>
                 <button type="button" onclick="changeTime('n')" id="c_n">변경하기</button>
                 <button type="button" onclick="saveTime('n')" id="sa_n" style="display:none">저장하기</button>
                 </td>
             </tr> 
             <tr>
                 <td>M</td>
                 <td class="icons">
                     <i style="background-color:rgb(255,120,142);">M</i>
                 </td>
                 <td>M</td>
                 <td><input type="time" id="s_m" disabled/>~<input type="time" id="e_m" disabled/>
                 <button type="button" onclick="changeTime('m')" id="c_m">변경하기</button>
                 <button type="button" onclick="saveTime('m')" id="sa_m" style="display:none">저장하기</button>
                 </td>
             </tr>
             <tr>
                 <td>OFF</td>
                 <td class="icons">
                     <i style="background-color:rgb(82,195,175);">F</i>
                 </td>
                 <td>OFF</td>
                 <td></td>
             </tr> 
         </table>
     </div>
    </div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	let login = JSON.parse(sessionStorage.getItem("login"));
	getParttime(login.code);
})
function getParttime(code){
	$.ajax({
		url:"http://localhost:3000/getParttime",
		type:"post",
		data:{"code":code},
		success:function(list){
			//alert("dddd");
			$.each(list, function(idx, item){
				let name = item.name.toLowerCase();
				$("#s_"+name).val(item.starttime);
				$("#e_"+name).val(item.endtime);
			});
		},
		error:function(){
			alert("error");
		}
	});
}
function changeTime(time){
	document.getElementById("s_"+time).disabled = false;
	document.getElementById("e_"+time).disabled = false;
	$("#c_"+time).hide();
	$("#sa_"+time).show();
}
function saveTime(time){
	let login = JSON.parse(sessionStorage.getItem("login"));
	if(confirm("저장하시겠습니까?")){
		$.ajax({
			url:"http://localhost:3000/saveTime",
			type:"post",
			data:{"name":time.toUpperCase(), "st":$("#s_"+time).val().toString(), "et":$("#e_"+time).val().toString(), "code":login.code},
			success:function(result){
				if(result=="success"){
					alert("성공적으로 변경되었습니다.");
					location.reload();
				}else{
					alert("변경에 실패했습니다.");
				}
			},
			error:function(){
				alert("error");
			}
		});
		document.getElementById("s_"+time).disabled = true;
		document.getElementById("e_"+time).disabled = true;
		$("#c_"+time).show();
		$("#sa_"+time).hide();
	}
}
</script>

<script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
<script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
    
</body>
</html>