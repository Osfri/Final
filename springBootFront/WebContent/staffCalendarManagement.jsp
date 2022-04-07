<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
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
                 <td><input type="time" value="07:00"/>~<input type="time" value="15:00"/></td>
             </tr> 
             <tr>
                 <td>E</td>
                 <td class="icons">
                     <i style="background-color:rgb(112,87,250);">E</i>
                 </td>
                 <td>EVE</td>
                 <td><input type="time" value="14:00"/>~<input type="time" value="22:00"/></td>
             </tr> 
             <tr>
                 <td>N</td>
                 <td class="icons">
                     <i style="background-color:rgb(113,113,113);">N</i>
                 </td>
                 <td>NIGHT</td>
                 <td><input type="time" value="21:00"/>~<input type="time" value="08:00"/></td>
             </tr> 
             <tr>
                 <td>M</td>
                 <td class="icons">
                     <i style="background-color:rgb(255,120,142);">M</i>
                 </td>
                 <td>M</td>
                 <td><input type="time" value="09:00"/>~<input type="time" value="17:00"/></td>
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


<script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
<script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
    
</body>
</html>