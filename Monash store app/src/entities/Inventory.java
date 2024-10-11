package entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Inventory entity class.
 * Stores product information for Inventory for the Monash Merchant Application
 *
 * @author Abbishek Kamak, Bao Hoang, Muskaan Sheik, Tom
 * @version 5/16/2024
 */
public class Inventory {
    private ArrayList<Product> products;

    /**
     * Default constructor.
     */
    public Inventory() {
        products = new ArrayList<>();
    }

    /**
     * Accessor method to get an Arraylist of Products in the inventory.
     *
     * @return An arraylist of Product objects.
     */
    public ArrayList<Product> getProducts() {
        return products;
    }

    /**
     * Mutator method to set products.
     *
     * @param products An arraylist of products in the inventory.
     */
    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    /**
     * Method to add a new product to the inventory.
     *
     * @param product Product to be added to the inventory.
     */
    public void addProduct(Product product) {
        this.products.add(product);
    }


}
