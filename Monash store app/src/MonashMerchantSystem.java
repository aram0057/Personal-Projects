import entities.*;
import util.FileIO;

import java.io.*;
import java.util.*;

/**
 * Control class which handles the flow of the  Monash Merchant Application
 *
 * @author Abbishek Kamak, Bao Hoang, Muskaan Sheik, Tom
 * @version 5/16/2023
 */
public class MonashMerchantSystem {
    private final String USER_FILENAME = "users.txt";
    private final String CUSTOMER_FILENAME = "customers.txt";
    private final String INVENTORY_FILENAME = "inventory.txt";
    private Inventory inventory;
    private User currentUser;
    private Customer customer;
    private Inventory inventory_copy;
    private ShoppingCart shoppingCart;

    /**
     * Default constructor.
     */
    public MonashMerchantSystem() {
        FileIO fileIO = new FileIO();
        this.inventory = fileIO.readInventoryFile();
        shoppingCart = new ShoppingCart();
        //inventory = new Inventory();
        //use to checkout to update inventory file
        //inventory_copy = new Inventory();
        //customer = new Customer();
        initializeInventory();
        createMockUserDate();
        createMockCustomerData();
        createMockProductsData();
    }

    /**
     * Creates mock user data
     */
    public void createMockUserDate() {
        File file = new File(USER_FILENAME);
        if (!file.exists() || file.length() == 0) {
            FileIO fileIO = new FileIO();
            String customerDetails = "member@student.monash.edu,Monash1234";
            fileIO.writeFile(USER_FILENAME, customerDetails);
            String adminDetails = "admin@merchant.monash.edu,12345678";
            fileIO.writeFile(USER_FILENAME, adminDetails);
        }
    }

    /**
     * Creates mock customer data
     */
    public void createMockCustomerData() {
        File file = new File(CUSTOMER_FILENAME);
        if (!file.exists() || file.length() == 0) {
            FileIO fileIO = new FileIO();
            Customer customer = new Customer("member@student.monash.edu", "Monash1234", "Member", "Pearce", "02/01" +
                    "/1999", "Clayton", "0435123456", 1000.0, false, new ShoppingCart());
            fileIO.writeFile(CUSTOMER_FILENAME, customer.toStringForFileIO());
        }
    }

    /**
     * Creates mock product data
     */
    public void createMockProductsData() {
        File file = new File(INVENTORY_FILENAME);
        if (!file.exists() || file.length() == 0) {
            FileIO fileIO = new FileIO();
            Product product = new Product("bag", "nike", "accessories", "bags", 20, 18, 12, "bag");
            Product product1 = new Product("book", "cm", "stationary", "books", 5, 4, 12, "ruled book");
            Product product2 = new Product("nutbar", "coles", "food", "health", 3, 2.50, 50, "nut bar");
            fileIO.writeFile(INVENTORY_FILENAME, product.toStringForFileIO());
            fileIO.writeFile(INVENTORY_FILENAME, product1.toStringForFileIO());
            fileIO.writeFile(INVENTORY_FILENAME, product2.toStringForFileIO());
        }
    }

    /**
     * Reads users.txt and returns an ArrayList of User objects
     *
     * @return an ArrayList of user objects
     */
    public ArrayList<User> readUser(String userPath) throws FileNotFoundException {

        // set up userList, file var, and Scanner obj
        ArrayList<User> userList = new ArrayList<>();
        File file = new File(userPath);
        Scanner reader = new Scanner(file);

        // get individual credentials and store in hashmap
        while (reader.hasNextLine()) {
            String user = reader.nextLine();
            String[] credentials = user.split(",");

            // create new User obj and add it to userlist
            User newUser = new User(credentials[0], credentials[1]);
            userList.add(newUser);
        }

        // close Scanner obj and return hashmap
        reader.close();
        return userList;

    }

    /**
     * Reads customers.txt and returns an arraylist of Customer objects
     *
     * @return an arraylist of Customer objects
     */
    public ArrayList<Customer> readCustomer(String customerPath) throws FileNotFoundException {

        ArrayList<Customer> userList = new ArrayList<>();
        File file = new File(customerPath);
        Scanner reader = new Scanner(file);
        while (reader.hasNextLine()) {
            String customer = reader.nextLine();
            String[] details = customer.split(",");
            Customer customerObj = new Customer(details[0], details[1], details[2], details[3], details[4],
                    details[5], details[6], Double.parseDouble(details[7]), Boolean.parseBoolean(details[8]),
                    this.shoppingCart);
            userList.add(customerObj);
        }
        reader.close();
        return userList;

    }

    /**
     * Authenticates the user and returns a User object
     *
     * @return logged in User object
     */
    public User authenticateUser() {
        Scanner console = new Scanner(System.in);
        boolean authenticated = false;
        User validUser = null;
        try {
            ArrayList<Customer> customerList = readCustomer(CUSTOMER_FILENAME);
            ArrayList<User> userList = readUser(USER_FILENAME);
            boolean loginFlag = true;
            while (loginFlag) {
                System.out.print("Enter username: ");
                String username = console.nextLine();
                for (User user : userList) {
                    if (user.getEmail().equals(username)) {
                        validUser = user;
                        break;
                    }
                }
                if (validUser != null) {
                    System.out.print("Enter password: ");
                    String password = console.nextLine();
                    while (!password.equals(validUser.getPassword())) {
                        System.out.println("Invalid password, please try again");
                        System.out.print("Enter password: ");
                        password = console.nextLine();
                    }
                    if (password.equals(validUser.getPassword())) {
                        if (username.endsWith("@student.monash.edu")) {
                            System.out.println("Successfully logged in as a Customer");
                            for (Customer customer1 : customerList) {
                                if (customer1.getEmail().equals(username)) {
                                    this.customer = customer1;
                                    break;
                                }
                            }
                            authenticated = true;
                        } else if (username.endsWith("@merchant.monash.edu")) {
                            System.out.println("Successfully logged in as an Admin");
                            authenticated = true;
                        }
                    }
                    loginFlag = false;
                } else {
                    System.out.println("Invalid username, please try again!");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("users.txt file not found - " + e.getMessage());
        }
        this.currentUser = validUser;
        return validUser;

    }

    /**
     * Adds a product to the inventory
     */
    public void addProduct() {
        Scanner myObj = new Scanner(System.in);
        String productName;
        do {
            System.out.println("Enter product name:");
            productName = myObj.nextLine();
            if (!productName.matches("[a-zA-Z0-9\\s]+")) {
                System.out.println("Product name must contain only letters, numbers, and spaces.");
            }
        } while (!productName.matches("[a-zA-Z0-9\\s]+"));
        String brand;
        do {
            System.out.println("Enter brand name:");
            brand = myObj.nextLine();
            if (!brand.matches("[a-zA-Z0-9\\s]+")) {
                System.out.println("Brand name must contain only letters, numbers, and spaces.");
            }
        } while (!brand.matches("[a-zA-Z0-9\\s]+"));
        String category;
        do {
            System.out.println("Enter category:");
            category = myObj.nextLine();
            if (!category.matches("[a-zA-Z]+")) {
                System.out.println("Category must contain only letters.");
            }
        } while (!category.matches("[a-zA-Z]+"));
        String subCategory;
        do {
            System.out.println("Enter subcategory name:");
            subCategory = myObj.nextLine();
            if (!subCategory.matches("[a-zA-Z]+")) {
                System.out.println("Subcategory must contain only letters.");
            }
        } while (!subCategory.matches("[a-zA-Z]+"));
        double price;
        do {
            System.out.println("Enter price:");
            while (!myObj.hasNextDouble()) {
                System.out.println("Price must be a valid number.");
                myObj.next();
            }
            price = myObj.nextDouble();
        } while (price <= 0);
        double memberPrice;
        do {
            System.out.println("Enter member price:");
            while (!myObj.hasNextDouble()) {
                System.out.println("Member price must be a valid number.");
                myObj.next();
            }
            memberPrice = myObj.nextDouble();
        } while (memberPrice <= 0);
        int quantity;
        do {
            System.out.println("Enter quantity:");
            while (!myObj.hasNextInt()) {
                System.out.println("Quantity must be a positive integer.");
                myObj.next();
            }
            quantity = myObj.nextInt();
        } while (quantity <= 0);
        String description;
        do {
            System.out.println("Enter description:");
            description = myObj.nextLine();
            if (!description.matches("[a-zA-Z0-9\\s]+")) {
                System.out.println("Enter a valid description");
            }
        } while (!description.matches("[a-zA-Z0-9\\s]+"));
        Product product = new Product(productName, brand, category, subCategory, price, memberPrice, quantity,
                description);
        product.display();
        String productDetails = productName + ", " + brand + ", " + category + ", " + subCategory + ", " + price + "," +
                " " + memberPrice + ", " + quantity + ", " + description;
        System.out.println("Save Product to list: Y [or] N ");
        String save = myObj.nextLine();
        if (Objects.equals(save, "Y")) {
            System.out.println("Saving to System.....");
            FileIO fileIO = new FileIO();
            fileIO.writeFile(INVENTORY_FILENAME, productDetails);
        } else {
            System.out.println("Saving to system was cancelled");
        }

    }


    /**
     * Deletes a product from the inventory
     */
    public void deleteProduct() {
        Scanner scanner = new Scanner(System.in);
        FileIO fileIO = new FileIO();
        ArrayList<Product> products = inventory.getProducts();

        browseProducts();
        System.out.println("Enter the product number to be deleted:");
        while (!scanner.hasNextInt()) {
            System.out.println("Product number must be a positive integer between 1 and " + products.size() + ".");
            scanner.next();
        }
        int prodNumber = scanner.nextInt();

        scanner.nextLine();
        System.out.println(products.get(prodNumber - 1));
        System.out.print("Are you sure you want to delete this product? Y [or] N");
        String save = scanner.nextLine();
        if (save.equals("Y") || save.equals("y")) {
            System.out.println("Deleting product.....");
            products.remove(prodNumber - 1);
            inventory.setProducts(products);
            updateInventoryFile();
        } else {
            System.out.println("Product deletion cancelled");
        }

    }

    /**
     * Initializes inventory and inventory_copy from inventory.txt
     */
    public void initializeInventory() {

        this.inventory = new Inventory();
        this.inventory_copy = new Inventory();
        FileIO fileIO = new FileIO();
        ArrayList<String> products = fileIO.readFile(INVENTORY_FILENAME);
        Collections.sort(products, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                // Split each string by comma and extract the integer value
                int quantity1 = Integer.parseInt(s1.split(",")[6].trim());
                int quantity2 = Integer.parseInt(s2.split(",")[6].trim());
                // Compare the integer values
                return Integer.compare(quantity2, quantity1);
            }
        });
        for (String product : products) {
            String[] productValues = product.split(",");
            Product product1 = new Product(productValues[0].trim(), productValues[1].trim(), productValues[2].trim(),
                    productValues[3].trim(), Double.parseDouble(productValues[4].trim()),
                    Double.parseDouble(productValues[5].trim()), Integer.parseInt(productValues[6].trim()),
                    productValues[7].trim());
            inventory.addProduct(product1);
            inventory_copy.addProduct(product1);

        }

    }

    /**
     * Displays the available products
     */
    public void browseProducts() {

        System.out.printf("%-4s\t%-20s\t%-15s\t%-15s\t%-15s\t%-5s\t%-5s\t%s\t%s%n", "No.", "Name", "Brand", "Category"
                , "SubCategory", "Price", "Member Price", "Quantity", "Description");
        int prodNumber = 1;
        for (Product product : inventory.getProducts()) {
            System.out.printf("%-4d\t%-20s\t%-15s\t%-15s\t%-15s\t%.2f\t%-15.2f\t%-15d\t%s%n", prodNumber,
                    product.getProductName(), product.getBrand(), product.getCategory(), product.getSubCategory(),
                    product.getPrice(), product.getMemberPrice(), product.getQuantity(), product.getDescription());
            prodNumber++;
        }

    }

    /**
     * Allows the customer to add products to cart
     */
    public void shop() {

        Scanner scanner = new Scanner(System.in);
        int prodNumber;
        int quantity;
        initializeInventory();
        while (true) {
            browseProducts();
            System.out.println("0. Go to main menu");
            System.out.println((inventory.getProducts().size() + 1) + ". Proceed to checkout");
            do {
                System.out.println("Enter the product number to add to cart:");
                while (!scanner.hasNextInt()) {
                    System.out.println("Input must be a positive integer between 0 and " + inventory.getProducts().size() + ".");
                    scanner.next();
                }
                prodNumber = scanner.nextInt();
            } while (!(prodNumber >= 0 && prodNumber <= (inventory.getProducts().size() + 1)));
            scanner.nextLine();
            if (prodNumber == 0) {
                System.out.println("Redirecting to main menu...");   // needs to work properly
                break;
            } else if (prodNumber == (inventory.getProducts().size() + 1)) {
                System.out.println("Redirecting to checkout...");  //if same item is added twice it needs to add the qty
                checkout();
                break;

            } else if (shoppingCart.getItems().size() <= 20) {
                do {
                    System.out.println("Enter the quantity:(Cannot exceed 10)");
                    while (!scanner.hasNextInt()) {
                        System.out.println("Quantity must be a positive integer between 1 and " + inventory.getProducts().get(prodNumber - 1).getQuantity() + ".");
                        scanner.next();
                    }
                    quantity = scanner.nextInt();
                } while (!(quantity >= 1 && prodNumber <= inventory.getProducts().get(prodNumber - 1).getQuantity() && quantity <= 10));
                scanner.nextLine();
                int initialQuantity = inventory.getProducts().get(prodNumber - 1).getQuantity();
                //checking if there's enough stock
                if (initialQuantity >= quantity) {
                    shoppingCart.addItem(inventory.getProducts().get(prodNumber - 1), quantity);
                    //deducting the quantity from inventory copy
                    inventory_copy.getProducts().get(prodNumber - 1).setQuantity(initialQuantity - quantity);
                    System.out.println("Product added to cart!!");
                    //for test purposes need to be removed.
                    System.out.println(inventory_copy.getProducts().get(prodNumber - 1).getQuantity());
                } else {
                    System.out.println("Only " + initialQuantity + " left in stock");
                }
            } else {
                System.out.println("Only 20 items can be added to cart.");
            }
        }

    }

    /**
     * Updates the funds of a customer in the customer file.
     *
     * @param funds The new funds to be set for the customer.
     * @throws FileNotFoundException If the customer file is not found.
     */
    public void updateCustomerFunds(double funds) throws FileNotFoundException {
        ArrayList<Customer> userList = new ArrayList<>();
        File file = new File(CUSTOMER_FILENAME);
        Scanner reader = new Scanner(file);
        while (reader.hasNextLine()) {
            String cust = reader.nextLine();
            String[] details = cust.split(",");
            Customer customerObj = new Customer(details[0], details[1], details[2], details[3], details[4], details[5], details[6], Double.parseDouble(details[7]), Boolean.parseBoolean(details[8]), this.shoppingCart);
            if(customerObj.getEmail().equals(customer.getEmail())){
                customerObj.setFunds(funds);
            }
            userList.add(customerObj);
        }
        reader.close();
        FileIO fileIO = new FileIO();
        fileIO.writeCustomerFile(CUSTOMER_FILENAME, userList);

    }

    /**
     * Performs checkout operations
     */
    public void checkout() {

        double funds = customer.getFunds(); //assign based on customer logged in=================================================================
        String customerEmail = customer.getEmail();
        String customerName = customer.getFirstName(); //needs to be shown in summary
        System.out.println(UserInterface.loggedInUser);
        System.out.println("===================================  Check out=============================================");
        System.out.printf("%-20s %-10s %-15s %-10s%n", "Product", "Quantity", "Product Price", "Total Price");
        System.out.println("---------------------------------------------------------------------------------------------");
        double checkoutPrice = 0.0;
        for (CartItem item : shoppingCart.getItems()) {
            item.calculateTotalPrice();
            System.out.printf("%-20s %-10d %-15.2f %-10.2f%n", item.getProduct().getProductName(), item.getQuantity()
                    , item.getProduct().getPrice(), item.getTotalPrice());
            checkoutPrice += item.getTotalPrice();


        }
        System.out.println("The total checkout amount is: " + checkoutPrice);
        Scanner scanner = new Scanner(System.in);
        String option;

        do {
            System.out.println("Enter Y to proceed or N to cancel order and return to homescreen. (Items will not be on hold) ");
            option = scanner.nextLine();
            if (option.equals("Y") || option.equals("y")) {
                System.out.println("Proceeding to place order....");
                try {
                    Thread.sleep(1000);
                    System.out.println("Checking balance and verifying inventory.....");
                    if (funds > checkoutPrice) {
                        System.out.println("Funds verified..");
                        funds = funds - checkoutPrice;
                        System.out.println("================");
                        System.out.println("Order Summary");
                        System.out.println("================");
                        System.out.println("Name: " + this.customer.getFirstName() + " Email: " + this.customer.getEmail());
                        System.out.println("Order Items:");
                        System.out.println("===================================  Check out=============================================");
                        System.out.printf("%-20s %-10s %-15s %-10s%n", "Product", "Quantity", "Product Price", "Total Price");
                        System.out.println("---------------------------------------------------------------------------------------------");
                        for (CartItem item : shoppingCart.getItems()) {
                            item.calculateTotalPrice();
                            System.out.printf("%-20s %-10d %-15.2f %-10.2f%n", item.getProduct().getProductName(), item.getQuantity(), item.getProduct().getPrice(), item.getTotalPrice());
                        }
                        System.out.println("Total Price: " + checkoutPrice);
                        System.out.println("Remaining balance: " + funds);

                        FileIO fileIO = new FileIO();
                        fileIO.writeInventoryToFile(this.inventory_copy);
                        updateCustomerFunds(funds);
                    } else {
                        System.out.println("Insufficient funds to complete order.");
                        System.out.println("Current funds: " + funds);
                        System.out.println("Cart Value: " + checkoutPrice);
                        shoppingCart.clear();
                        System.out.println("Cart is being emptied.");
                        System.out.println("Directing to shop again....");
                        shop();
                    }

                    // need to verify funds with the user and if not display error message
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                try {
                    Thread.sleep(1000);
                    System.out.println("Payment processing.....");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(1000);
                    System.out.println("Confirming order. Almost there.....");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(1000);
                    System.out.println("Order confirmed !! Here is your order number: ");    // insert an order number here
                    System.out.println("Your remaining funds are: " + funds);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print("Please give your feed back for your shopping experience...");
                System.out.print("Enter X to exit");
                String option1 = scanner.nextLine();
                if (option1.equals("X")) {

                } else {
                    System.out.println("Thank you for your feedback! See you next time!");
                }
            } else if (option.equals("N") || option.equals("n")) {
                System.out.println("Directing to shopping menu....");
                shop();

            }
        } while (!"Y".equals(option) && !"N".equals(option));

    }

    /**
     * Returns the inventory copy.
     *
     * @return the inventory copy as an Inventory object
     */
    public Inventory getInventoryCopy() {
        return inventory_copy;
    }

    /**
     * Sets the inventory copy
     *
     * @param inventoryCopy the new value of inventory copy
     */
    public void setInventoryCopy(Inventory inventoryCopy) {
        this.inventory_copy = inventoryCopy;
    }

    /**
     * Returns the inventory.
     *
     * @return the Inventory object
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Sets the inventory of Monash Merchant System
     *
     * @param inventory the new inventory value
     */
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Returns the current logged-in user.
     *
     * @return the current logged-in User object
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the current logged-in user
     *
     * @param currentUser the new current user to be set
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Updates the inventory.txt file with latest values
     */
    public void updateInventoryFile() {
        FileIO fileIO = new FileIO();
        fileIO.writeInventoryToFile(this.inventory);
    }

    /**
     * The main entry point of the application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        MonashMerchantSystem monashMerchantSystem = new MonashMerchantSystem();
        UserInterface userInterface = new UserInterface();
        userInterface.startMainMenu();

    }
}
