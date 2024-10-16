package Home;

public class Book {

	private String name;
	private String author;
	private String description;
	private String product_id;
	private String price;
	private String image;

	public Book(String name, String author, String description, String price, String image) {
		super();
		this.name = name;
		this.author = author;
		this.description = description;
		this.price = price;
		this.image = image;
	}

	// Constructor
	public Book(String name, String author, String description, String product_id) {
		this.name = name;
		this.author = author;
		this.description = description;
		this.product_id = product_id;
	}

	// Getters and Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
