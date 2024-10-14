<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
<div id="prodwrap">
	<div id="prodinfowrap">
		<div id="collft">
			<img src="${prod.product_image }" alt="상세이미지" width="200px">
		</div>
		<div id="colrgt">
			<h2>${prod.product_name }</h2>
			<p>${prod.product_author } 저 | ${prod. product_company}</p>
			<h3>평균별점 : ${prod.product_grade *2} </h3>
			<hr>
			<h2>판매가 : ${prod.product_price } 원</h2>
		</div>
		<hr>
		<p>
			${prod.product_detail }
		</p>
		<hr>
		<div id="reviewwrap">
    <h2>한줄평</h2>

    <!-- 한 줄 평 입력 영역 -->
    <div id="insertreviewarea">
        <form id="reviewForm">
            <input type="hidden" id="product_id" value="${prod.product_id}" />
            <textarea id="comment_text" rows="4" cols="50" placeholder="한줄평을 작성해주세요" style="resize: none;"></textarea><br/>
            <label for="rating">평점:</label>
            <select id="rating">
                <option value="1">★☆☆☆☆</option>
                <option value="2">★★☆☆☆</option>
                <option value="3">★★★☆☆</option>
                <option value="4">★★★★☆</option>
                <option value="5">★★★★★</option>
            </select>
            <br/>
            <button type="button" onclick="submitReview()">한줄평 등록</button>
        </form>
    </div>
	<hr>
    <!-- 한 줄 평 리스트 -->
    <div id="reviewlist"></div>

    <!-- 페이징 처리 -->
    <div id="reivewpag"></div>
	</div>

		
	</div>
</div>

<script>
    // 현재 페이지 번호 저장
    var currentPage = 1;

    // 제품 ID 저장
    var product_id = $('#product_id').val();

    // 한 줄 평 리스트와 페이징을 불러오는 함수
    function loadReviews(page) {
        $.ajax({
            url: "comments",
            method: "POST",
            data: {
            	product_id: product_id,
                page: page
            },
            success: function(response) {
                // 리뷰 리스트 업데이트
                $('#reviewlist').empty();
                $.each(response.comments, function(index, comment) {
                    $('#reviewlist').append(
                        '<div class="comment">' +
                        '<p><strong>' + comment.filtered_userid + '</strong></p>' +
                        '<p>평점: ' + comment.rating*2 + '</p>' +
                        '<p>' + comment.comment_text + '</p>' +
                        '<p><small>' + comment.formatDate + '</small></p>' +
                        '</div><hr/>'
                    );
                });

                // 페이징 업데이트
                $('#reivewpag').empty();
                if (response.currentPage > 1) {
                    $('#reivewpag').append('<a href="#" onclick="loadReviews(' + (response.currentPage - 1) + ')">이전</a>');
                }

                for (var i = 1; i <= response.totalPages; i++) {
                    if (i == response.currentPage) {
                        $('#reivewpag').append('<span>' + i + '</span>');
                    } else {
                        $('#reivewpag').append('<a href="#" onclick="loadReviews(' + i + ')">' + i + '</a>');
                    }
                }

                if (response.currentPage < response.totalPages) {
                    $('#reivewpag').append('<a href="#" onclick="loadReviews(' + (response.currentPage + 1) + ')">다음</a>');
                }
            }
        });
    }

    // 첫 페이지 로드
    $(document).ready(function() {
        loadReviews(currentPage);
    });
	
    function submitReview() {
        var commentText = $('#comment_text').val();
        var rating = $('#rating').val();

        $.ajax({
            url: "insertcomment", // 리뷰 등록 서블릿으로 요청 보냄
            method: "POST",
            data: {
            	product_id: product_id,
                comment_text: commentText,
                rating: rating
            },
            success: function(response) {
                alert("리뷰가 등록되었습니다!");
                $('#comment_text').val(''); // 입력 필드 초기화
                loadReviews(currentPage);    // 현재 페이지 리뷰 다시 불러오기
            }
        });
    }
	
</script>

</body>
</html>