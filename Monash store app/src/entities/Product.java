package entities;

/**
 * Product entity class.
 * Represent a product in Monash Merchant System.
 *
 * @author Abbishek Kamak, Bao Hoang, Muskaan Sheik, Tom
 * @version 5/16/2024
 */
public class Product {
    private String productName;
    private String brand;
    private String category;
    private String subCategory;
    private double price;
    private double memberPrice;
    private int quantity;
    private String description;

    /**
     * Default constructor.
     */
    public Product() {
        this.productName = "";
        this.brand = "";
        this.category = "";
        this.subCategory = "";
        this.price = 0;
        this.memberPrice = 0;
        this.quantity = 0;
        this.description = "";
    }

    /**
     * Parameterized constructor.
     *
     * @param productName the name of the product
     * @param brand       the brand of the product
     * @param category    the category of the product
     * @param subCategory the sub-category of the product
     * @param price       the price of the product
     * @param memberPrice the member price of the product
     * @param quantity    the quantity of the product
     * @param description the description of the product
     */
    public Product(String productName, String brand, String category, String subCategory, double price, double memberPrice, int quantity, String description) {
        this.productName = productName;
        this.brand = brand;
        this.category = category;
        this.subCategory = subCategory;
        this.price = price;
        this.memberPrice = memberPrice;
        this.quantity = quantity;
        this.description = description;
    }

    /**
     * Return the name of the product.
     *
     * @return the product name
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Sets the name of the product.
     *
     * @param productName the product name to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Return the brand of the product.
     *
     * @return the brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Sets the brand of the product.
     *
     * @param brand the brand to set
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Return the category of the product.
     *
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the category of the product.
     *
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Return the sub-category of the product.
     *
     * @return the sub-category
     */
    public String getSubCategory() {
        return subCategory;
    }

    /**
     * Sets the sub-category of the product.
     *
     * @param subCategory the sub-category to set
     */
    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    /**
     * Return the member price of the product.
     *
     * @return the member price
     */
    public double getMemberPrice() {
        return memberPrice;
    }

    /**
     * Sets the member price of the product.
     *
     * @param memberPrice the member price to set
     */
    public void setMemberPrice(double memberPrice) {
        this.memberPrice = memberPrice;
    }

    /**
     * Return the price of the product.
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the product.
     *
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Return the quantity of the product.
     *
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the product.
     *
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Return the description of the product.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the product.
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns a string representation of the product object.
     *
     * @return a string containing all the product attributes
     */
    public String toString() {
        String str = "";
        str += "Product Name: " + productName + ", ";
        str += "Brand: " + brand + ", ";
        str += "Category: " + category + ", ";
        str += "Sub Category: " + subCategory + ", ";
        str += "Price: " + price + ", ";
        str += "Member Price: " + memberPrice + ", ";
        str += "Quantity: " + quantity + ", ";
        str += "Description: " + description + ", ";
        return (str);
    }

    /**
     * Displays the product details by printing the string representation of the object.
     */
    public void display() {
        System.out.println(toString());
    }

    /**
     * Returns a string representation of the product object suitable for file I/O operations.
     *
     * @return a comma-separated string containing all the product attributes
     */
    public String toStringForFileIO() {
        return String.format("%s,%s,%s,%s,%.2f,%.2f,%d,%s", getProductName(), getBrand(), getCategory(), getSubCategory(), getPrice(), getMemberPrice(), getQuantity(), getDescription());
    }
}