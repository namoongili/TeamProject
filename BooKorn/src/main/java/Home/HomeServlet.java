package Home;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    private HomeService homeService;
    public String userId;

    @Override
    public void init() throws ServletException {
        this.homeService = new HomeService(); 
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
 
        // 세션에서 사용자 정보 가져오기
        HttpSession session = req.getSession();
        
        String userId = (String) session.getAttribute("userId");
        System.out.println(userId);

        // 오늘의 책, 베스트셀러 정보
        Book
        todayBook = homeService.getTodayBook();
        req.setAttribute("todayBook", todayBook);
        
        List<String> bestSellers = homeService.getBestSellers();

        // 카테고리 가져오기
        String selectedCategory = req.getParameter("category");
        if (selectedCategory == null) {
            selectedCategory = "DOMESTIC"; // '국내도서'의 카테고리 ID
        }
        // 선택된 카테고리에 해당하는 책 목록 가져오기
        List<Book> categoryBooks = homeService.getBooksByCategory(selectedCategory);

        // 로그인 처리
        boolean isLoggedIn = userId != null;
        String myPageInfo = null;
        List<String> cartItems = null;
        if (isLoggedIn) {
            myPageInfo = homeService.getMyPageInfo(userId);
            cartItems = homeService.getCartItems(userId);
        }
        // JSP로 전달할 값 설정
        req.setAttribute("todayBook", todayBook);
        req.setAttribute("bestSellers", bestSellers);
        req.setAttribute("categoryBooks", categoryBooks);  // 여기서 categoryBooks 전달
        req.setAttribute("selectedCategory", selectedCategory);  // selectedCategory 전달
        req.setAttribute("isLoggedIn", isLoggedIn);
        req.setAttribute("myPageInfo", myPageInfo);
        req.setAttribute("cartItems", cartItems);
 
        

 

        // JSP로 이동
 
        req.getRequestDispatcher("WEB-INF/views/home.jsp").forward(req, resp);
    }
}
