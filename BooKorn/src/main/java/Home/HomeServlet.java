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

    @Override
    public void init() throws ServletException {
        this.homeService = new HomeService(); 
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 세션에서 사용자 정보 가져오기
        HttpSession session = req.getSession();
        String userId = (String) session.getAttribute("userId");

        // 오늘의 책, 베스트셀러 정보
        String todayBook = homeService.getTodayBook();
        List<String> bestSellers = homeService.getBestSellers();

        // 카테고리 가져오기
        String selectedCategory = req.getParameter("category");
        if (selectedCategory == null) {
            selectedCategory = "국내도서";
        }
        List<String> categoryBooks = homeService.getBooksByCategory(selectedCategory);

        // 로그인 처리
        boolean isLoggedIn = userId != null;
        String myPageInfo = null;
        List<String> cartItems = null;
        if (isLoggedIn) {
            myPageInfo = homeService.getMyPageInfo(userId);
            cartItems = homeService.getCartItems(userId);
        }


        req.setAttribute("todayBook", todayBook);
        req.setAttribute("bestSellers", bestSellers);
        req.setAttribute("categoryBooks", categoryBooks);
        req.setAttribute("selectedCategory", selectedCategory);
        req.setAttribute("isLoggedIn", isLoggedIn);
        req.setAttribute("myPageInfo", myPageInfo);
        req.setAttribute("cartItems", cartItems);


        req.getRequestDispatcher("WEB-INF/views/home.jsp").forward(req, resp);
    }
}