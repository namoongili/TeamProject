<%@ page import="java.util.List" %>
<%@ page import="Home.Book" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    response.setContentType("text/html; charset=UTF-8");
    request.setCharacterEncoding("UTF-8");

    // 로그인 여부 확인
    Boolean isLoggedIn = (Boolean) request.getAttribute("isLoggedIn");
    String myPageInfo = (String) request.getAttribute("myPageInfo");
    List<Book> categoryBooks = (List<Book>) request.getAttribute("categoryBooks");
    String selectedCategory = (String) request.getAttribute("selectedCategory");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BooKorn</title>
    <link rel="stylesheet" href="/BooKorn/css/styles.css">

    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0 auto;
            padding: 0;
            box-sizing: border-box;
        }

        /* 헤더 스타일 */
        header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 15px;
            background-color: #fff;
            border-bottom: 1px solid #e0e0e0;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05);
			width: 98.5%;
			overflow: hidden;
        }

        /* 내비게이션 메뉴 */
        .nav-bar {
            display: flex;
            gap: 10px;
        }

        .nav-bar a {
            text-decoration: none;
            color: #333;
            font-weight: bold;
            padding: 8px 12px;
            transition: background-color 0.3s, color 0.3s;
            border-radius: 5px;
        }

        .nav-bar a:hover {
            background-color: #f0f0f0;
            color: #007BFF;
        }

        /* 검색 바 */
		.search-bar {
 	    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%; /* 전체 너비를 차지하도록 설정 */
    max-width: 600px; /* 검색 바의 최대 너비 */
    margin: 0 auto; /* 중앙에 위치하도록 설정 */
}

         .search-bar input {
 		    flex-grow: 1;
		    max-width: 400px; /* 검색 바의 최대 너비 */
		    padding: 10px;
  	  	    border-radius: 20px;
}

        .search-bar button {
            background-color: #007BFF;
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 20px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .search-bar button:hover {
            background-color: #0056b3;
        }

        /* 유저 메뉴 */
        .user-menu {
            display: flex;
            align-items: center;
            gap: 10px;
            margin-left: auto; /* 유저 메뉴를 오른쪽 끝으로 보내기 위한 설정 */
        }

        .user-menu a {
            text-decoration: none;
            color: #333;
            font-weight: bold;
            transition: color 0.3s;
        }

        .user-menu a:hover {
            color: #007BFF;
        }

        /* 메인 컨텐츠 */
        .main-content {
            display: flex;
            justify-content: space-between;
            padding: 20px;
            width: 100%;
            max-width: 1600px;
            gap: 20px;
        }

        /* 카테고리 섹션 */
        .category-section {
            width: 15%;
            padding: 20px;
            background-color: #f8f8f8;
            border-radius: 10px;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
            max-width: 100px;
        }

        .category-section ul {
            list-style-type: none;
            padding: 0;
        }

        .category-section li {
            padding: 12px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .category-section li:hover {
            background-color: #ddd;
        }

        /* 중앙 이미지 및 책 설명 */
        .center-section {
            flex-grow: 2;
            text-align: center;
            margin-bottom: 20px;
            position: relative;
        }

        .center-section img {
            width: 100%;
            max-width: 600px;
            height: auto;
            transition: transform 0.3s ease-in-out;
        }

        .center-section img:hover {
            transform: scale(1.05);
        }

        /* 책 강조 섹션 */
        .book-highlight {
            display: flex;
            justify-content: center;
            align-items: flex-start;
            width: 100%;
            max-width: 1000px;
            gap: 20px;
            margin-top: 40px;
        }

        .book-image img {
            width: 100%;
            max-width: 250px;
            height: auto;
            transition: opacity 0.3s ease-in-out;
        }

        .book-image img:hover {
            opacity: 0.8;
        }

        .book-description {
            width: 100%;
            max-width: 350px;
            text-align: left;
        }

     
       .book-container {
    display: flex;
    justify-content: space-around;
    flex-wrap: wrap;
    margin-top: 30px;
    width: 100%;
    text-align: center;
    clear: both; /* 이전 섹션과의 간섭을 없앱니다 */
}

.book-box {
    background-color: #f8f8f8;
    border-radius: 10px;
    box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
    padding: 20px;
    width: 200px; /* 각 책 박스의 너비 */
    margin: 10px; /* 박스 간 간격 */
}
    
    .book-container:after {
        content: "";
        display: block;
        clear: both;
    }
    

        /* 푸터 스타일 */
        footer {
            background-color: #333;
            color: white;
            text-align: center;
            padding: 10px;
            position: fixed;
            width: 100%;
            bottom: 0;
        }
    </style>
</head>
<body>
    <header>
        <!-- 네비게이션 바 -->
        <div class="nav-bar">
            <a href="?category=CAT001">국내도서</a>
            <a href="?category=CAT002">외국도서</a>
            <a href="?category=CAT003">중고도서</a>
            <a href="?category=CAT004">eBook</a>
            <a href="?category=CAT005">크레마클럽</a>
            <a href="?category=CAT006">CD/LP</a>
        </div>

        <!-- 검색 바 -->
        <div class="search-bar">
            <input type="text" placeholder="예비중학 필수! 1등 초등 세계사">
            <button>검색</button>
        </div>

        <!-- 유저 메뉴 -->
        <div class="user-menu">
            <% if (isLoggedIn != null && isLoggedIn) { %>
                <a href="#">마이페이지</a>
                <a href="/BooKorn/cart">장바구니</a>
                <a href="/logout">로그아웃</a>
            <% } else { %>
                <a href="/login">로그인</a>
            <% } %>
        </div>
    </header>

    <!-- 메인 콘텐츠 -->
    <section class="main-content">
        <!-- 카테고리 섹션 -->
        <aside class="category-section">
            <ul>
                <li><a href="?category=CAT001">국내도서</a></li>
                <li><a href="?category=CAT002">외국도서</a></li>
                <li><a href="?category=CAT003">중고샵</a></li>
                <li><a href="?category=CAT004">eBook</a></li>
                <li><a href="?category=CAT005">크레마클럽</a></li>
                <li><a href="?category=CAT006">CD/LP</a></li>
                <li><a href="?category=CAT007">DVD/BD</a></li>
                <li><a href="?category=CAT008">문구/GIFT</a></li>
                <li><a href="?category=CAT009">티켓</a></li>
            </ul>
        </aside>

        <!-- 중앙 이미지 섹션 -->
        <div class="center-section">
    <h2>오늘의 책</h2>
    <div class="book-highlight">
        <div class="book-image">
            <img src="/BooKorn/imgs/홈화면/모두의금리.jpg" alt="모두의 금리">
        </div>
        <div class="book-description">
            <h3><%= request.getAttribute("todayBook") %></h3>
            <p>경제의 중심에는 금리가 있다. 국제금융 최전선에서 활약한 조원경 저자의 신간.</p>
            <p><strong>저자: </strong>조원경</p>
            <p><strong>가격: </strong>19,800원 (10% 할인)</p>
        </div>
    </div>


               <!-- 책 목록을 출력할 부분 -->
    <div class="book-container">
        <% if (categoryBooks != null && !categoryBooks.isEmpty()) { %>
            <% for (Book book : categoryBooks) { %>
                <div class="book-box" onclick="location.href='/BooKorn/product?id=<%= book.getProduct_id() %>'">
                    <h3><%= book.getName() %></h3>
                    <p><%= book.getAuthor() %></p>
                    <strong><%= book.getDescription() %></strong>
                </div>
            <% } %>
        <% } else { %>
            <p>해당 카테고리에 책이 없습니다.</p>
        <% } %>
    </div>
    </div>
    </section>

    <footer>

    </footer>
</body>
<script>
   let bookDiv = document.queryselectorAll(".book-box");
   
   bookDiv.forEach("book"=>{
      book.addtouchlistener("click",function(){
         
      })
   }){
      
   }

</script>
</html>
