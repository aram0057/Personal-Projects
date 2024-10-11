import entities.Inventory;
import entities.Product;
import entities.User;
import util.StringValidation;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * The UserInterface class provides a command-line interface for users to interact with the Monash Merchant System.
 * It handles user authentication, product management, and customer operations.
 */
public class UserInterface {
    public static String loggedInUser = "";
    private final String LINE_BREAK = "-------------------------------------";
    private HashMap<String, Runnable> mainMenu;
    private HashMap<String, Runnable> AdminHomeMenu;
    private HashMap<String, Runnable> CustomerMenu;
    private Scanner scanner;
    private MonashMerchantSystem monashMerchantSystem;

    /**
     * Default constructor. Initialize attributes & HashMaps for menus.
     *
     */
    public UserInterface() throws FileNotFoundException, UnsupportedEncodingException {
        scanner = new Scanner(System.in);
        mainMenu = new HashMap<>();
        AdminHomeMenu = new HashMap<>();
        CustomerMenu = new HashMap<>();
        monashMerchantSystem = new MonashMerchantSystem();
        setupHashMaps();
    }

    /**
     * Sets up menu mappings for UserInterface.
     */
    private void setupHashMaps() {

        mainMenu.put("1", this::login);
        mainMenu.put("q", () -> System.exit(0));
        AdminHomeMenu.put("1", () -> monashMerchantSystem.addProduct());
        AdminHomeMenu.put("2", () -> monashMerchantSystem.deleteProduct());
        AdminHomeMenu.put("3", this::startEditProductMenu);
        AdminHomeMenu.put("4", () -> {
            monashMerchantSystem.initializeInventory();
            monashMerchantSystem.browseProducts();});
        AdminHomeMenu.put("b", () -> {
        });
        CustomerMenu.put("1", () -> {
            monashMerchantSystem.initializeInventory();
            monashMerchantSystem.browseProducts();});
        CustomerMenu.put("2", () -> monashMerchantSystem.shop());
        CustomerMenu.put("3", () -> monashMerchantSystem.checkout());
        CustomerMenu.put("b", () -> {
        });

    }

    /**
     * Display the main menu and handle the user choice.
     */
    public void startMainMenu() {
        while (true) {
            displayLogo();
            System.out.println(LINE_BREAK);
            System.out.println("Main Menu");
            System.out.println("Please login to continue");
            System.out.println(LINE_BREAK);
            System.out.println("1. Login");
            System.out.println("q. Quit");

            String choice = scanner.nextLine().trim(); // TODO: replace with I/O class method

            if (mainMenu.containsKey(choice)) {
                mainMenu.get(choice).run();
            } else {
                System.out.println("Invalid choice. Please try again."); // TODO: replace with i/o validation
            }
        }
    }


    /**
     * Authenticates the user and starts the appropriate menu based on their role.
     */
    private void login() {
        User user = monashMerchantSystem.authenticateUser();
        if (user != null) {
            if (user.getEmail().endsWith("@student.monash.edu")) {
                loggedInUser = user.getEmail();
                System.out.println("Logged in successfully!");
                startCustomerMenu();
            } else if (user.getEmail().endsWith("@merchant.monash.edu")) {
                loggedInUser = user.getEmail();
                System.out.println("Logged in successfully!");
                startAdminHomeMenu();
            }
        }
    }

    /**
     * Display the Admin Home menu and handle the user choice.
     */
    private void startAdminHomeMenu() {
        while (true) {
            displayLogo();
            System.out.println("Logged in as: " + loggedInUser);
            System.out.println(LINE_BREAK);
            System.out.println("What would you like to do?");
            System.out.println(LINE_BREAK);
            System.out.println("1. Add a product");
            System.out.println("2. Delete a product");
            System.out.println("3. Edit a product");
            System.out.println("4. View products");
            System.out.println("b. Log out");

            String choice = scanner.nextLine().trim(); // TODO: replace with I/O class method

            if (AdminHomeMenu.containsKey(choice)) {
                AdminHomeMenu.get(choice).run();
                if (choice.equals("b")) {
                    break;
                }
            } else {
                System.out.println("Invalid choice. Please try again."); // TODO: replace with i/o validation
            }
        }
    }

    /**
     * Display the Edit Product menu and handle the user choice.
     */
    private void startEditProductMenu() {
        Inventory inventory = monashMerchantSystem.getInventory();
        ArrayList<Product> products = inventory.getProducts();

        while (true) {
            displayLogo();
            displayInventory();
            System.out.println(LINE_BREAK);
            System.out.println("Enter the ID of the product to be edited?");
            System.out.println(LINE_BREAK);
            System.out.println("ID: ");

            String input = scanner.nextLine().trim();
            if (StringValidation.isInteger(input)) {
                int productID = Integer.parseInt(input);
                if (productID >= 0 && productID <= products.size() - 1) {
                    Product product = products.get(productID);
                    startEditProductMenuTwo(product);

                    //Update product in MonashMerchantSystem's inventory
                    products.set(productID, product);
                    monashMerchantSystem.updateInventoryFile();
                }
            }
            break;
        }

    }

    /**
     * Displays the Edit Product Menu 2 and allows the user to edit different fields of the product.
     *
     * @param product the product to be edited
     */
    private void startEditProductMenuTwo(Product product) {
        while (true) {
            displayLogo();
            System.out.println(LINE_BREAK);
            System.out.println("Which field do you want to edit?");
            System.out.println(LINE_BREAK);
            System.out.println("1. Name");
            System.out.println("2. Brand");
            System.out.println("3. Description");
            System.out.println("4. Price");
            System.out.println("5. Member Price");
            System.out.println("6. Quantity");
            System.out.println("7. Category");
            System.out.println("8. Subcategory");
            System.out.println("b. Cancel");

            String choice = scanner.nextLine().trim(); // TODO: replace with I/O class method
            if (choice.equals("b")) break;

            if (StringValidation.isInteger(choice) && Integer.parseInt(choice) >= 0 && Integer.parseInt(choice) <= 8) {
                String input = "";
                switch (choice) {
                    case "1":
                        System.out.print("Enter new name, comma is not accepted: ");
                        input = scanner.nextLine().trim();
                        if (input.contains(",")) {
                            System.out.println("Name can't contain comma symbol. Please try again.");
                            break;
                        } else {
                            product.setProductName(input);
                            System.out.println("Name updated.");
                        }
                        break;
                    case "2":
                        System.out.print("Enter new brand: ");
                        input = scanner.nextLine().trim();
                        if (input.contains(",")) {
                            System.out.println("Brand can't contain comma symbol. Please try again.");
                            break;
                        } else {
                            product.setBrand(input);
                            System.out.println("Brand updated.");
                        }
                        break;
                    case "3":
                        System.out.print("Enter new description: ");
                        input = scanner.nextLine().trim();
                        if (input.contains(",")) {
                            System.out.println("Description can't contain comma symbol. Please try again.");
                            break;
                        } else {
                            product.setDescription(input);
                            System.out.println("Description updated.");
                        }
                        break;
                    case "4":
                        System.out.print("Enter new price: ");
                        input = scanner.nextLine().trim();
                        if (!StringValidation.isDouble(input)) {
                            System.out.println("Not a valid price. Please try again.");
                            break;
                        } else {
                            product.setPrice(Double.parseDouble(input));
                            System.out.println("Price updated.");
                        }
                        break;
                    case "5":
                        System.out.print("Enter new member price: ");
                        input = scanner.nextLine().trim();
                        if (!StringValidation.isDouble(input)) {
                            System.out.println("Not a valid price. Please try again.");
                            break;
                        } else {
                            product.setMemberPrice(Double.parseDouble(input));
                            System.out.println("Member price updated.");
                        }
                        break;
                    case "6":
                        System.out.print("Enter new quantity: ");
                        input = scanner.nextLine().trim();
                        if (!StringValidation.isInteger(input)) {
                            System.out.println("Not a valid number. Please try again.");
                            break;
                        } else {
                            product.setQuantity(Integer.parseInt(input));
                            System.out.println("Quantity updated.");
                        }
                        break;
                    case "7":
                        System.out.print("Enter new category: ");
                        input = scanner.nextLine().trim();
                        if (input.contains(",")) {
                            System.out.println("Category name can't contain comma symbol. Please try again.");
                            break;
                        } else {
                            product.setDescription(input);
                            System.out.println("Category updated.");
                        }
                        break;
                    case "8":
                        System.out.print("Enter new subcategory: ");
                        input = scanner.nextLine().trim();
                        if (input.contains(",")) {
                            System.out.println("Subcategory name can't contain comma symbol. Please try again.");
                            break;
                        } else {
                            product.setDescription(input);
                            System.out.println("Subcategory updated.");
                        }
                        break;
                }
                break;

            } else {
                System.out.println("Please enter a number 1-8, or enter b to cancel.");
            }

        }
    }

    /**
     * Displays the Customer menu and allows handle the user choice.
     */
    private void startCustomerMenu() {
        while (true) {
            displayLogo();
            System.out.println("Logged in as: " + loggedInUser);
            System.out.println(LINE_BREAK);
            System.out.println("What would you like to do?");
            System.out.println(LINE_BREAK);
            System.out.println("1. Browse Products");
            System.out.println("2. Shop");
            System.out.println("3. View Shopping Cart/Checkout");
            System.out.println("b. Log out");

            String choice = scanner.nextLine().trim(); // TODO: replace with I/O class method

            if (CustomerMenu.containsKey(choice)) {
                CustomerMenu.get(choice).run();
                if (choice.equals("b")) {
                    break;
                }
            } else {
                System.out.println("Invalid choice. Please try again."); // TODO: replace with i/o validation
            }
        }
    }

    /**
     * Displays inventory in a pretty format.
     */
    public void displayInventory() {
        Inventory inventory = monashMerchantSystem.getInventory();
        System.out.printf("%-4s\t%-20s\t%-15s\t%-15s\t%-15s\t%-5s\t%-5s\t%s\t%s%n", "No.", "Name", "Brand", "Category"
                , "SubCategory", "Price", "Member Price", "Quantity", "Description");
        int index = 0;

        for (Product product : inventory.getProducts()) {
            System.out.printf("%-4d\t%-20s\t%-15s\t%-15s\t%-15s\t%.2f\t%-15.2f\t%-15d\t%s%n", index,
                    product.getProductName(), product.getBrand(), product.getCategory(), product.getSubCategory(),
                    product.getPrice(), product.getMemberPrice(), product.getQuantity(), product.getDescription());
            index++;
        }
    }

    /**
     * Displays Monash Merchant System logo.
     */
    public void displayLogo() {
        System.out.println();
        System.out.println("*************************************");
        System.out.println("\t\t\tMONASH MERCHANT");
        System.out.println("*************************************");
        System.out.println();
        System.out.println();
    }
}
