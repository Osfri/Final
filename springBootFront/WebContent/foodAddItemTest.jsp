<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<!-- 제이쿼리 사용 -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	
	<!-- 파이어베이스 스토리지 사용 -->
    <script src="https://www.gstatic.com/firebasejs/8.6.5/firebase-app.js"></script>
    <script src="https://www.gstatic.com/firebasejs/8.6.5/firebase-auth.js"></script>
    <script src="https://www.gstatic.com/firebasejs/8.6.5/firebase-firestore.js"></script>
    <script src="https://www.gstatic.com/firebasejs/8.6.5/firebase-storage.js"></script>
	<title>Insert title here</title>
	<link rel="stylesheet" type="text/css" href="./css/style.css">
</head>
<body>

	<div class="custom_container">
	    <jsp:include page="navigation.jsp"></jsp:include>
	    <!-- main -->
	    <div class="main" >
	        <div class="topbar">
	            <div class="toggle">
	                <ion-icon name="menu-outline"></ion-icon>
	            </div>
	        </div>
	        
	       	<!-- 식단추가 -->
		    <div>
			    <div class="maintitle">
			        <h2>식단추가</h2>
			    </div>
			</div>
			
			<!-- 식단 추가내용 -->

			<table>
				<tr>
					<th>요일</th>
					<td style="text-align: left;">
						<input type="date" id="fdate" size ="50">
					</td>	
				</tr>
				<tr>
					<th>메뉴</th>
					<td style="text-align: left;">
						<input type="text" id="menu" size ="50">
					</td>
				</tr>
				<tr>
					<th>사진</th>			
					<td style="text-align: left;">
						<input type="file" id="photo" size ="20" onchange="previewFile()">
						<br>
						<img src="" height="200" id="imgPreView" alt=""> 
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<button type="button" id="foodItemUpload">업로드</button>
					</td>
				</tr>
		
		</table>
			</div>
			
	    </div>

	<script>
		/* 사진미리보기 */
		function previewFile() { 
            var preview = document.querySelector('img'); 
            var file = document.querySelector('input[type=file]').files[0]; 
            var reader  = new FileReader(); 
             
            reader.onloadend = function () { 
                  preview.src = reader.result; 
           	} 
           	if (file) { 
                 reader.readAsDataURL(file); 
            } else { 
                 preview.src = ""; 
          	} 
      	}
	
	
		/* 파이어베이스 스토리지 설정 */
		const firebaseConfig = {
		    apiKey: "AIzaSyC047yE_SqX60Tg-bpMA-ZszC4alRRQItg",
		    authDomain: "finalprojectchat-7cc05.firebaseapp.com",
		    databaseURL: "https://finalprojectchat-7cc05-default-rtdb.asia-southeast1.firebasedatabase.app",
		    projectId: "finalprojectchat-7cc05",
		    storageBucket: "finalprojectchat-7cc05.appspot.com",
		    messagingSenderId: "699634507401",
		    appId: "1:699634507401:web:6e49377a51ad417621b058",
		    measurementId: "G-KQ1X2SJDF5"
	  	};
		
		/* 파이어베이스 초기화 */
		firebase.initializeApp(firebaseConfig);	// Initialize Firebase 
		const db = firebase.firestore();
		const storage = firebase.storage();
		
		$(document).ready(function() {
			// 업로드 버튼 클릭시
			$("#foodItemUpload").click(function(){
				let fdateVal = $("#fdate").val();
				let menuVal = $("#menu").val();
				let photoVal = $("#photo").val();
				if(fdateVal.trim() == "" || fdateVal == null){
					alert("요일을 입력하세요!");
				}else if(menuVal.trim() == "" || menuVal == null){
					alert("메뉴를 입력하세요!");
				}else if(photoVal.trim() == "" || photoVal == null){
					alert("사진을 등록하세요!");
				}else{
					let login = JSON.parse(sessionStorage.getItem("login"));
					let today = new Date();
					let year = today.getFullYear();
					let month = ('0' + (today.getMonth() + 1)).slice(-2);
					let day = ('0' + today.getDate()).slice(-2);
					let hours = ('0' + today.getHours()).slice(-2); 
					let minutes = ('0' + today.getMinutes()).slice(-2);
					let seconds = ('0' + today.getSeconds()).slice(-2);
					
					let dateString = year + month + day + hours + minutes + seconds;
					
					/* 파이어베이스 스토리지에 저장 */
			        const imgFile = document.querySelector('#photo').files[0];
			        const storageRef = storage.ref();
			        const imgLocation = storageRef.child('foods/' + (login.code + "_" + $("#fdate").val() + "_" + dateString + "_" + imgFile.name));
			        const uploadStart = imgLocation.put(imgFile);
			        uploadStart.on(
			                'state_changed',
			                null,
			                (error) => {	// 스토리지에 저장 실패시
			                	console.error('실패 :', error);
			                },
			                function () {	// 스토리지에 저장 성공시
			                	uploadStart.snapshot.ref.getDownloadURL().then((url) => {
			                        const toSave = {
			                        				code: login.code,
			                        				fdate: $("#fdate").val(),
			                        				menu: $("#menu").val(),
			                                		photo: url,  //업로드에 성공한 이미지 url
		                                			};
			                        
			                     // 서버 전달
		                        	$.ajax({
		    							url:"http://localhost:3000/foodItemAdd",
		    							type: "POST",
		    							data: toSave,
		    							success:function(result){
		    								if(result){
		    									alert("저장 완료!");
		    									// 초기화
		    									$("#fdate").val("");
		    									$("#menu").val("");
		    									$("#photo").val("");
		    									$("#imgPreView").attr("src", "");
		    									// 페이지 이동
		    									location.href = "foodTest.jsp";
		    								
		    								}else{
		    									alert("저장 실패!");
		    							}
		    							},
		    							error:function(request,status,error){ alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); }
				    				});
			                    
			                	})
			                			}
	                ); // uploadStart.on
				}
			}) // $("#shopItemUpload").click(function()
		}) // $(document).ready(function()
			
		
	</script>


<!-- 사이드 바 관련 -->
<script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
<script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>


</body>
</html>