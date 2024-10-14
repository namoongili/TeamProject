package Home;

import java.util.List;

public class Home {

    private String todayBook;
    private List<String> bestSellers;
    private List<String> categoryBooks;
    private String selectedCategory;
    private boolean isLoggedIn;
    private String myPageInfo;
    private List<String> cartItems;

    // Constructor
    public Home(String todayBook, List<String> bestSellers, List<String> categoryBooks, 
                String selectedCategory, boolean isLoggedIn, String myPageInfo, List<String> cartItems) {
        this.todayBook = todayBook;
        this.bestSellers = bestSellers;
        this.categoryBooks = categoryBooks;
        this.selectedCategory = selectedCategory;
        this.isLoggedIn = isLoggedIn;
        this.myPageInfo = myPageInfo;
        this.cartItems = cartItems;
    }

    // Getters and Setters
    public String getTodayBook() {
        return todayBook;
    }

    public void setTodayBook(String todayBook) {
        this.todayBook = todayBook;
    }

    public List<String> getBestSellers() {
        return bestSellers;
    }

    public void setBestSellers(List<String> bestSellers) {
        this.bestSellers = bestSellers;
    }

    public List<String> getCategoryBooks() {
        return categoryBooks;
    }

    public void setCategoryBooks(List<String> categoryBooks) {
        this.categoryBooks = categoryBooks;
    }

    public String getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(String selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public String getMyPageInfo() {
        return myPageInfo;
    }

    public void setMyPageInfo(String myPageInfo) {
        this.myPageInfo = myPageInfo;
    }

    public List<String> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<String> cartItems) {
        this.cartItems = cartItems;
    }
}