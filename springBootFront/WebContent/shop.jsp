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
	
	<!-- 부트스트랩 페이징 -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="./jquery/jquery.twbsPagination.min.js"></script>

	<title>포인트몰 관리</title>
	<link rel="stylesheet" type="text/css" href="./css/style.css">
</head>
<body>
	<div class="CustomContainer">
	    <jsp:include page="navigation.jsp"></jsp:include>
	    <!-- main -->
	    <div class="main" >
	        <div class="topbar">
	            <div class="toggle">
	                <ion-icon name="menu-outline"></ion-icon>
	            </div>
	        </div>
	        
	       	<!-- 포인트몰관리 -->
		    <div>
			    <div class="maintitle">
			        <h2>포인트몰관리</h2>
			    </div>
			</div>
			
			<!-- 포인트몰 상품추가 버튼 -->
			<button type="button" id="addShopItem" value="상품 추가" style="position: absolute; right: 5%;">상품 추가</button>
			
			<!-- 상품목록 -->
			<table>
				<thead>
					<tr>
						<th></th>
						<th>상품명</th>
						<th>상품설명</th>
						<th>가격</th>
						<th>사진</th>
						<th>재고</th>
						<th>삭제</th>
					</tr>
				</thead>
				
				<tbody id="tbody">
				</tbody>
			</table>
							<!-- 페이지 번호 기능 -->
				<div class="container">
			    	<nav aria-label="Page navigation">
			        	<ul class="pagination" id="pagination" style="position: absolute; left: 50%; transform: translate(-50%, -50%); bottom: 50px;"></ul>
			    	</nav>
				</div>			

	    </div>
	</div>
				
	
	<script type="text/javascript"> 		
		$(document).ready(function() {
			
			// 처음 화면 진입시 초기화
			getShopItemList(0);
			getShopListCount();
			
			/* 식단추가하기 버튼 클릭시 */
			$("#addShopItem").click( function(){
				location.href = "shopAddItem.jsp";
			});	
			
		}) // $(document).ready(function()
			
				
		// 페이지 번호 기능
		function loadPage(totalCount, pageSize){
			// totalCount: 글의 총 개수
			// pageSize: 화면에 보이는 한 페이지에 보이는 글 개수
			let pageNumber = 1;						// 현재 시작 페이지
			let totalPage = totalCount / pageSize;	// 페이지 개수
			if(totalCount % pageSize >0){
				totalPage++;
			}
			
			$('#pagination').twbsPagination('destroy');		// 페이지 갱신

			$('#pagination').twbsPagination({
				startPage:pageNumber,						// 1: 현재 시작 페이지
		        totalPages: totalPage,						// 페이지 개수
		        visiblePages: pageSize,						// 화면에 보이는 한 페이지에 보이는 글 개수
		        first:'<span sris-hidden="true">«</span>',	// first 이름 명칭
		        last:'<span sris-hidden="true">»</span>',	// last 이름 명칭
		        prev:"이전",	// prev 이름 명칭
		        next:"다음",	// next 이름 명칭
		        initiateStartPageClick:false,				// 자동호출 방지
		        onPageClick: function (event, page) {
		            //alert(page);
		        	getShopItemList( page - 1 );					//	page: 1,2,3..으로 들어가므로 0,1,2,3으로 맞춰줘야함
		        }
		    });
		}	
		
		// 상품의 총 개수 가져오기
		function getShopListCount(){
			let login = JSON.parse(sessionStorage.getItem("login"));
			$.ajax({
				url:"http://localhost:3000/getShopItemListCnt",
				type: "post",
				data:{"code": login.code},
				success:function(count){
					loadPage(count, 3);
				},
				error:function(){
					alert('getShopListCount error');
				}
			});
		}
		
		// 상품목록 얻어오기 (페이지 기능 o)
		function getShopItemList(page){
			let login = JSON.parse(sessionStorage.getItem("login"));
			$("#tbody").html("");
			$.ajax({
				url:"http://localhost:3000/getShopItemList",
				data:{"pageNumber": page, "code": login.code},
				type: "post",
				success:function(list){
					$.each(list, function(index, item){
						let str = "<tr>"
									+ "<th>" + (index + (3*page) + 1) + "</th>"
									+ "<td>" + item.title + "</td>"
									+ "<td width='500'>" + item.content + "</td>"
									+ "<td>" + item.price + "</td>"
									+ "<td> <img src=\"" + item.photo + "\" width='200' onerror=\"this.src='https://firebasestorage.googleapis.com/v0/b/finalprojectchat-7cc05.appspot.com/o/error%2Foutline_upload_file_black_48.png?alt=media&token=d2caeae4-ca3e-4eab-a5da-206f4529081b'\"> </td>"
									+ "<td>" + item.cnt + "</td>"
									+ "<td> <button type='submit' value=\"" + item.seq + "\" class='delShopItem'>" + "삭제" + "</button> </td>"
									+ "</tr>"
						$("#tbody").append(str);
					});
					
					/* 삭제버튼 클릭시 */
					$(".delShopItem").click( function(){
						if(confirm("삭제하시겠습니까?")){
							$.ajax({
								url:"http://localhost:3000/shopItemModify",
								data:{"seq":$(this).val()},
								type: "post",
								success:function(result){
									if(result>0){
										getShopItemList(0);
										getShopListCount();
										alert("삭제되었습니다.");
									}else{
										alert("삭제실패!");
									}
								},
								error:function(){
									alert('error');
								}
							});
						}
						
					});
				},
				error:function(){
					alert('error');
				}
			});
		}
	</script>

<!-- 사이드 바 관련 -->
<script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
<script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>

	
</body>
</html>