package hcl.practice.inventory.model;

public class ProductDTO {

	private String productName;
	
	private Integer price;
	
	private Integer availableUnits;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getAvailableUnits() {
		return availableUnits;
	}

	public void setAvailableUnits(Integer availableUnits) {
		this.availableUnits = availableUnits;
	}

	@Override
	public String toString() {
		return "ProductDTO [productName=" + productName + ", price=" + price + ", availableUnits=" + availableUnits
				+ "]";
	}
	
	
	
}
