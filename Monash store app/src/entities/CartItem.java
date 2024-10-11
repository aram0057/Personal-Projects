package entities;

/**
 * Class which stores information of Cart Item for the Monash Merchant Application
 *
 * @author Abbishek Kamak, Bao Hoang, Muskaan Sheik, Tom
 * @version 5/16/2024
 */
public class CartItem {

    private Product product;
    private int quantity;
    private double totalPrice;

    /**
     * Default constructor which creates the object of the class Admin.
     */
    public CartItem() {
    }

    /**
     * Parametrized constructor which creates the object of the class Admin.
     */
    public CartItem(Product product, int quantity) {

        this.product = product;
        this.quantity = quantity;

    }

    /**
     * Accessor method to get an object of Product.
     *
     * @return An object of Product that stores product details.
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Mutator method to set Product.
     *
     * @param product An object of Product that stores the product added to cart.
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Accessor method to get an integer value indicating the quantity of the product.
     *
     * @return An integer value that shows the quantity of product.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Mutator method to set quantity.
     *
     * @param quantity An integer that stores the quantity of the product.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Accessor method to get an integer value that stores the total price.
     *
     * @return An integer that indicates the total price.
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Mutator method to set total price.
     *
     * @param totalPrice A double value that stores the total price.
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Method to calculate the total price based on quantity and price.
     */
    public void calculateTotalPrice() {
        totalPrice = product.getPrice() * quantity;
    }

    /**
     * Method to return the state of CartItem object as a string.
     *
     * @return The state of the object as a string.
     */
    public String toString() {
        String str = "";
        str += "Product Name: " + product.getProductName() + "\n";
        str += "Quantity: " + quantity + ", ";
        str += "Price: " + totalPrice + "\n";
        return (str);
    }

    /**
     * Method to display the state of the CartItem object.
     */
    public void display() {
        System.out.println(toString());
    }

}
