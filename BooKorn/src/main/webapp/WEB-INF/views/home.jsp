<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %> <!-- ArrayList도 필요할 경우 추가 -->
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    response.setContentType("text/html; charset=UTF-8");
    request.setCharacterEncoding("UTF-8");

    // 로그인 여부 확인
    Boolean isLoggedIn = (Boolean) request.getAttribute("isLoggedIn");
    String myPageInfo = (String) request.getAttribute("myPageInfo");
    List<String> cartItems = (List<String>) request.getAttribute("cartItems");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BooKorn</title>
    <link rel="stylesheet" href="/BooKorn/css/styles.css">
</head>
<body>
    <header>
        <nav>
            <a href="#">국내도서</a>
            <a href="#">외국도서</a>
            <a href="#">중고도서</a>
            <a href="#">eBook</a>
            <a href="#">크레마클럽</a>
            <a href="#">CD/LP</a>
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
        <!-- 카테고리 섹션 -->
        <aside class="category-section">
            <ul>
                <li><a href="?category=YES NOW">YES NOW</a></li>
                <li><a href="?category=국내도서">국내도서</a></li>
                <li><a href="?category=외국도서">외국도서</a></li>
                <li><a href="?category=중고샵">중고샵</a></li>
                <li><a href="?category=eBook">eBook</a></li>
                <li><a href="?category=크레마클럽">크레마클럽</a></li>
                <li><a href="?category=CD/LP">CD/LP</a></li>
                <li><a href="?category=DVD/BD">DVD/BD</a></li>
                <li><a href="?category=문구/GIFT">문구/GIFT</a></li>
                <li><a href="?category=티켓">티켓</a></li>
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
                    <p>경제의 중심에는 금리가 있다. 국제금융<br>최전선에서 활약한 조원경 저자의 신간.</p>
                    <p><strong>저자: </strong>조원경</p>
                    <p><strong>가격: </strong>19,800원 (10% 할인)</p>
                </div>
            </div>
        </div>

        <!-- 베스트셀러 섹션 -->
        <div class="right-section">
            <h3>베스트셀러</h3>
            <ul>
                <% 
                    List<String> bestSellers = (List<String>) request.getAttribute("bestSellers");
                    for (String book : bestSellers) { 
                %>
                    <li><%= book %></li>
                <% } %>
            </ul>
        </div>

    </section>

    <footer>
        <p>저작권 정보 등...</p>
    </footer>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const categories = document.querySelectorAll('.category-section li a');
        const mainImage = document.getElementById('main-image');
        
        categories.forEach((category) => {
            const categoryText = category.textContent.trim();

            if (categoryText === '국내도서') {
                category.addEventListener('mouseover', () => {
                    mainImage.src = '/BooKorn/imgs/홈화면/삶에시가없다면 배경.png';
                });
            } else if (categoryText === 'YES NOW') {
                category.addEventListener('mouseover', () => {
                    mainImage.src = '/BooKorn/imgs/홈화면/yes.jpg';
                });
            } else if (categoryText === '외국도서') {
                category.addEventListener('mouseover', () => {
                    mainImage.src = '/BooKorn/imgs/홈화면/외국도서.jpg';
                });
            } else {
                category.addEventListener('mouseover', () => {
                    mainImage.src = `/BooKorn/imgs/홈화면/삶에시가없다면_배경_${Array.from(categories).indexOf(category) + 1}.png`;
                });
            }
            
            // 마우스를 떼면 기본 이미지로 돌아갑니다.
            category.addEventListener('mouseout', () => {
                mainImage.src = '/BooKorn/imgs/홈화면/삶에시가없다면 배경.png';
            });
        });
    });
</script>
</body>
</html>