<%@ page import="Home.Book" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    response.setContentType("text/html; charset=UTF-8");
    request.setCharacterEncoding("UTF-8");

    // 로그인 여부 확인
    Boolean isLoggedIn = (Boolean) request.getAttribute("isLoggedIn");
    String myPageInfo = (String) request.getAttribute("myPageInfo");
    List<String> cartItems = (List<String>) request.getAttribute("cartItems");
    List<Book> categoryBooks = (List<Book>) request.getAttribute("categoryBooks");  // Book 객체를 사용한다고 가정
    String selectedCategory = (String) request.getAttribute("selectedCategory");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BooKorn</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }

        /* 메인 컨텐츠 섹션 */
        .main-content {
            display: flex;
            flex-direction: column;
            align-items: center;
            margin-bottom: 40px;
        }

        /* 책 목록을 가로로 배열하는 스타일 */
        .book-container {
            display: flex;
            justify-content: space-between; /* 가로로 배열 */
            width: 100%;
            max-width: 1200px;
            gap: 20px;
            margin-top: 20px;
        }

        /* 각 책 박스 스타일 */
        .book-box {
            background-color: #f8f8f8;
            border-radius: 10px;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
            padding: 20px;
            width: 18%; /* 5개의 박스가 한 줄에 나타나도록 설정 */
            text-align: center;
        }

        .book-box h3 {
            font-size: 18px;
            margin-bottom: 10px;
        }

        .book-box p {
            font-size: 14px;
            color: #666;
            margin-bottom: 10px;
        }

        .book-box strong {
            font-size: 16px;
            color: #333;
        }

        /* 중앙 이미지와 책 설명 */
        .center-section {
            flex-grow: 2;
            text-align: center;
            margin-bottom: 20px;
        }
        .center-section img {
            width: 100%;
            max-width: 300px;
            height: auto;
        }

        /* 카테고리 섹션 */
        .category-section {
            width: 20%;
            padding: 20px;
            background-color: #f8f8f8;
            border-radius: 10px;
        }
        .category-section ul {
            list-style-type: none;
            padding: 0;
        }
        .category-section li {
            padding: 12px;
            cursor: pointer;
        }
        .category-section li:hover {
            background-color: #ddd;
        }
    </style>
</head>
<body>

    <header>
        <nav>
            <a href="?category=CAT001">국내도서</a>
            <a href="?category=CAT002">외국도서</a>
            <a href="?category=CAT003">중고도서</a>
            <a href="?category=CAT004">eBook</a>
            <a href="?category=CAT005">크레마클럽</a>
            <a href="?category=CAT006">CD/LP</a>
        </nav>
        <div class="search-bar">
            <input type="text" placeholder="예비중학 필수! 1등 초등 세계사">
            <button>검색</button>
        </div>
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

    <section class="main-content">
        <!-- 중앙 이미지 섹션 -->
        <div class="center-section">
            <h2>오늘의 책</h2>
            <div class="book-highlight">
                <div class="book-image">
                    <img src="/BooKorn/imgs/홈화면/모두의금리.jpg" alt="모두의 금리" style="width: 300px;">
                </div>
                <div class="book-description">
                    <h3><%= request.getAttribute("todayBook") %></h3>
                    <p>경제의 중심에는 금리가 있다. 국제금융 최전선에서 활약한 조원경 저자의 신간.</p>
                    <p><strong>저자: </strong>조원경</p>
                    <p><strong>가격: </strong>19,800원 (10% 할인)</p>
                </div>
            </div>
        </div>

        <!-- 카테고리별 책 목록 -->
        <div class="book-container">
            <% if (categoryBooks != null && !categoryBooks.isEmpty()) { %>
                <% for (Book book : categoryBooks) { %>
                    <div class="book-box">
                        <h3><%= book.getName() %></h3>
                        <p><%= book.getAuthor() %></p>
                        <strong><%= book.getDescription() %></strong>
                    </div>
                <% } %>
            <% } else { %>
                <p>해당 카테고리에 책이 없습니다.</p>
            <% } %>
        </div>
    </section>

    <footer>
        <p>저작권 정보 등...</p>
    </footer>

</body>
</html>
