<%@page import="Products.product"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>


<style type="text/css">
#pdwrap{
	max-width : 1000px;
	margin: 0 auto;	
}

</style>


<script>

$(document).ready(function() {
    loadComments(1); // 페이지 로드 시 첫 페이지 댓글 로드
});

function loadComments(page) {
    $.ajax({
        type: "GET", // 요청 방식 (GET 또는 POST)
        url: '/BooKorn/comments', // 서블릿 URL
        dataType: 'json', // 응답 데이터 형식
        data: { page: page, prod_id: "${prod.product_id}" }, // 전송할 데이터
        success: function(data) {
        	
        	//console.log( "success");
        	//console.log(  data);
            // 댓글 목록을 업데이트
           
            $('#commentList').empty(); // 기존 댓글 삭제
            
            

            let list = data.list; // 댓글 리스트
            
            //console.log( list.length);
            
            let str = ""; // 댓글 HTML 문자열 초기화

            // 댓글 리스트 생성
            for (let i = 0; i < list.length; i++) {
                let comment = list[i];
                str += `<div class="commentWrap">
                            <p> \${comment.rating *2} | \${comment.user_id} | \${comment.comment_date}</p>
                            <p> \${comment.comment_text}</p>
                        </div><hr>`;
            }
            
            // jsp에서 EL로 인식 , 실행된 결과가 반환되어 비어 있음   \앞에 붙여주면 EL로 해석되지 않음 

            
            //console.log( str);
            
            
            // 댓글 리스트 HTML 추가
            $('#commentList').html(str);
            // 페이지 버튼 업데이트
            //updatePagination(data.totalPages, data.page);
        },
        error: function(err) {
            // 오류 처리
            alert("요청에 실패했습니다");
            console.log(err);
        }
    });
}
/*
function updatePagination(totalPages, currentPage) {
    $('#pagination').empty(); 
    $('#pagination').append(`<a href="#" onclick="changePage(${currentPage - 1})"><</a>`);

    for (let i = 1; i < totalPages; i++) {
        const activeClass = (i === currentPage) ? 'active' : '';
        $('#pagination').append(`<a href="#" class="${activeClass}" onclick="changePage(${i})">${i}</a>`);
    }

    $('#pagination').append(`<a href="#" onclick="changePage(${currentPage + 1})">></a>`);
}

*/
function changePage(page) {
	event.preventDefault();
    loadComments(page);
}



	
</script>

</head>
<body>

<% product prod = (product)request.getAttribute("prod");
	if (prod == null) {
    out.println("상품 정보가 없습니다.");
    return;
}
%>

<div id="pdwrap">
	<div id="proddetailwrap">
		<div id="prodcollaft">
			<img src="${prod.product_image}" alt="상품 이미지" width= 300px>
		</div>
		<div id="prodcolright">
			<div id="prodinfo">
				<h2>${ prod.product_name} </h2>
				<p>${ prod.product_author} 저 | ${prod.product_company}</p>
				<h5>평균별점 : ${prod.product_grade *2 }</h5>
				<h5> 판매가 : ${prod.product_price }원</h5>
				
			</div>
		</div>
	</div>
	<div>
	<br>
	<hr>
	
	<pre>
		${prod.product_detail }
	</pre>
	</div>
	<br>
	<hr>
	
	<div id="reviewwrap">
		<div id="cmtwrite">
			<div id="cmtwrite">
				<form action="submitReview" method="post">
					<label for="rating">별점을 선택하세요:</label>
					<select id="rating" name="rating">
						<option value="5">★★★★★</option>
						<option value="4">★★★★☆</option>
						<option value="3">★★★☆☆</option>
						<option value="2">★★☆☆☆</option>
						<option value="1">★☆☆☆☆</option>
					</select>
					<br>
					<textarea name="review" rows="5" cols="30" placeholder="한줄평을 입력하세요"></textarea><br>
					<button>등록</button>
				</form>
			</div>
		<hr>
		<div id="commentList">
		</div>
		<div id="pagination">
		</div>
		
		</div>
	</div>

</div>


</body>
</html>