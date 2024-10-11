package entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Class which stores information of shopping cart for the users in Monash Merchant Application
 *
 * @author Abbishek Kamak, Bao Hoang, Muskaan Sheik, Tom
 * @version 5/16/2024
 */
public class ShoppingCart {
    private List<CartItem> items;

    /**
     * Default constructor.
     */
    public ShoppingCart() {
        this.items = new ArrayList<>();
    }

    /**
     * Parameterized constructor.
     *
     * @param items the list of cart items
     */
    public ShoppingCart(List<CartItem> items) {
        this.items = items;
    }

    /**
     * Adds a new item to the shopping cart with the specified product and quantity.
     *
     * @param product  the product to be added
     * @param quantity the quantity of the product
     */
    public void addItem(Product product, int quantity) {
        items.add(new CartItem(product, quantity));
    }

    /**
     * Retrieves the list of cart items in the shopping cart.
     *
     * @return the list of cart items
     */
    public List<CartItem> getItems() {
        return items;
    }

    /**
     * Clears all items from the shopping cart.
     */
    public void clear() {
        items.clear();
    }
}