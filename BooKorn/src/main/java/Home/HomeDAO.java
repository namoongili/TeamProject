package Home;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HomeDAO {

	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String user = "system";
	private String password = "pass";

	// 데이터베이스 연결
	private Connection dbCon() throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		return DriverManager.getConnection(url, user, password);
	}

	// 카테고리별 책 목록 가져오기 (Book 객체로 반환)
	public List<Book> getBooksByCategory(String categoryId) {
		List<Book> books = new ArrayList<>();
		String sql = "SELECT product_name, product_author, product_detail, product_id FROM products WHERE category_id = ?";

		try (Connection con = dbCon(); PreparedStatement pst = con.prepareStatement(sql)) {

			pst.setString(1, categoryId);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				String name = rs.getString("product_name");
				String author = rs.getString("product_author");
				String description = rs.getString("product_detail");
				String product_id = rs.getString("product_id");
				books.add(new Book(name, author, description, product_id));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return books;
	}

	// 오늘의 책 가져오기
	public Book getTodayBook() {
		Book todayBook = null;
		//String sql = "SELECT product_name FROM products WHERE product_id = 'P001'";  // 예시 쿼리

		// 랜덤으로 하나의 책을 선택
		String sql = "SELECT products.product_name, product_author, product_detail, product_price, product_image "
				+ "FROM products ORDER BY DBMS_RANDOM.VALUE FETCH FIRST 1 ROWS ONLY"; // Oracle
																												// DB 예시

		try (Connection con = dbCon();
				PreparedStatement pst = con.prepareStatement(sql);
				ResultSet rs = pst.executeQuery()) {

			if (rs.next()) {//String name, String author, String description, String price, String image
				todayBook = new Book(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return todayBook;
	}

	// 베스트셀러 목록 가져오기
	public List<String> getBestSellers() {
		List<String> bestSellers = new ArrayList<>();
		String sql = "SELECT product_name FROM products ORDER BY sales DESC FETCH FIRST 5 ROWS ONLY"; // 예시 쿼리

		try (Connection con = dbCon();
				PreparedStatement pst = con.prepareStatement(sql);
				ResultSet rs = pst.executeQuery()) {

			while (rs.next()) {
				bestSellers.add(rs.getString("product_name"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bestSellers;
	}

	// 사용자 로그인 처리
	public boolean login(String userId, String password) {
		boolean isValidUser = false;
		String sql = "SELECT * FROM users WHERE user_id = ? AND password = ?";

		try (Connection con = dbCon(); PreparedStatement pst = con.prepareStatement(sql)) {

			pst.setString(1, userId);
			pst.setString(2, password);
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				isValidUser = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return isValidUser;
	}

	// 장바구니 목록 가져오기
	public List<String> getCartItems(String userId) {
		List<String> cartItems = new ArrayList<>();
		String sql = "SELECT product_name FROM cart JOIN products ON cart.product_id = products.product_id WHERE user_id = ?";

		try (Connection con = dbCon(); PreparedStatement pst = con.prepareStatement(sql)) {

			pst.setString(1, userId);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				cartItems.add(rs.getString("product_name"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return cartItems;
	}

	// 마이페이지 정보 가져오기
	public String getMyPageInfo(String userId) {
		String myPageInfo = null;
		String sql = "SELECT * FROM users WHERE user_id = ?";

		try (Connection con = dbCon(); PreparedStatement pst = con.prepareStatement(sql)) {

			pst.setString(1, userId);
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				myPageInfo = "Username: " + rs.getString("username") + ", Email: " + rs.getString("email");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return myPageInfo;
	}
}
