package Comments;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;

@WebServlet("/insertcomment")
public class insertComment extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userId = (String) req.getSession().getAttribute("userId");
		
		/*
	    if (userId != null) {
	        // user_id가 있는 경우, 필요한 로직 수행
	        System.out.println("User ID: " + userId);
	    } else {
	        // user_id가 없는 경우 (로그인하지 않은 경우)
	        //resp.sendRedirect("로그인"); // 로그인 페이지로 리다이렉트
	    }
	    */
		String productId = req.getParameter("product_id");
        String commentText = req.getParameter("comment_text");
        int rating = Integer.parseInt(req.getParameter("rating"));
        
        if (commentText == null || commentText.trim().isEmpty()) {
            // JSON 응답 생성: 댓글 입력 필요
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("{\"status\": \"error\", \"message\": \"내용을 입력해주세요.\"}");
            return; // 더 이상 진행하지 않고 메서드 종료
        }
        
        comment newComment = new comment(productId, userId, commentText, rating);
        // 댓글 서비스 클래스 인스턴스 생성
        commentService service = new commentService();
        // 데이터베이스에 댓글 저장
        service.insertComment(newComment);
        
        // JSON 응답 생성
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write("{\"status\": \"success\"}");
		
		
	}
}
