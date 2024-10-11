<%@ page contentType="text/html; charset=UTF-8" %>
<%
    response.setContentType("text/html; charset=UTF-8");
    request.setCharacterEncoding("UTF-8");
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
            <a href="#">로그인</a>
            <a href="#">마이페이지</a>
            <a href="#">장바구니</a>
        </div>
    </header>

    <section class="main-content">
     <!-- 카테고리 섹션 -->
    <aside class="category-section">
        <ul>
            <li>YES NOW</li>
            <li>국내도서</li>
            <li>외국도서</li>
            <li>중고샵</li>
            <li>eBook</li>
            <li>크레마클럽</li>
            <li>CD/LP</li>
            <li>DVD/BD</li>
            <li>문구/GIFT</li>
            <li>티켓</li>
        </ul>
    </aside>
        
        <!-- 중앙 이미지 섹션 -->
        <div class="center-section">
            <img id="main-image" src="/BooKorn/imgs/홈화면/삶에시가없다면 배경.png" alt="배경이미지">
        </div>

         <!-- 모두의 금리 이미지 및 설명 -->
        <section class="bottom-section">
            <div class="book-highlight">
                <div class="book-image">
                    <img src="/BooKorn/imgs/홈화면/모두의금리.jpg" alt="모두의 금리">
                </div>
                <div class="book-description">
                    <h3>오늘의 책</h3>
                    <p><strong>모두의 금리</strong></p>
                    <p>경제의 중심에는 금리가 있다. 국제금융 <br>최전선에서 활약한 조원경 저자의 신간.</p>
                    <p>금리가 경제와 금융 시장에 미치는 영향을 다각도로 분석하며, 자산 가치를 증가시키는 리스크 관리에 필수적인 금리 이해를 돕기 위해 작성된 책입니다.</p>
                    <p><strong>저자: </strong>조원경</p>
                    <p><strong>가격: </strong>19,800원 (10% 할인)</p>
                </div>
            </div>
        </section>

        <!-- 베스트셀러 섹션 -->
        <div class="right-section">
            <h3>베스트셀러</h3>
            <ul>
                <li>트렌드코리아 2025</li>
                <li>하루 한 장 나의 어휘력 노트</li>
                <li>생각의 연금술</li>
                <li>고전이 말하는 마땅히 해야 할 삶에 대하여</li>
                <li>언젠가 우리가 같은 별을 바라본다면</li>
            </ul>
        </div>
    </section>

    <footer>
        <p>저작권 정보 등...</p>
    </footer>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        const categories = document.querySelectorAll('.category-section li');
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