package util;

import entities.Customer;
import entities.Inventory;
import entities.Product;

import java.io.*;
import java.util.ArrayList;

/**
 * Class which performs file input and output
 *
 * @author Abbishek Kamak, Bao Hoang, Muskaan Sheik, Tom
 * @version 5/16/2024
 */

public class FileIO {
    private final String INVENTORY_FILE = "inventory.txt";
    private final String USERS_FILE = "user.txt";

    /**
     * Method to perform input from a file.
     *
     * @param inputFileName A String value to provide the name of the file.
     * @return A String value with the contents of the file.
     */
    public ArrayList<String> readFile(String inputFileName) {
        ArrayList<String> products = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(inputFileName))) {
            String product;
            while ((product = br.readLine()) != null) {
                products.add(product);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    /**
     * Writes the inventory data to the inventory file.
     *
     * @param inventory the inventory object containing the products
     */
    public void writeInventoryToFile(Inventory inventory) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(INVENTORY_FILE))) {
            for (Product product : inventory.getProducts()) {
                String line = product.toStringForFileIO();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes the customer data to a file.
     *
     * @param filePath The path of the file to write the customer data to.
     * @param customers An ArrayList containing Customer objects to be written to the file.
     */
    public void writeCustomerFile(String filePath, ArrayList<Customer> customers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Customer customer: customers) {
                String line = customer.toStringForUpdate();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the inventory data from the inventory file and returns an Inventory object.
     *
     * @return the Inventory object containing the products read from the file
     */
    public Inventory readInventoryFile() {
        Inventory inventory = new Inventory();
        File file = new File(INVENTORY_FILE);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return inventory;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(INVENTORY_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 8) {
                    String productName = data[0].trim();
                    String brand = data[1].trim();
                    String category = data[2].trim();
                    String subCategory = data[3].trim();
                    double price = Double.parseDouble(data[4].trim());
                    double memberPrice = Double.parseDouble(data[5].trim());
                    int quantity = Integer.parseInt(data[6].trim());
                    String description = data[7].trim();
                    Product product = new Product(productName, brand, category, subCategory, price, memberPrice, quantity, description);
                    inventory.addProduct(product);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inventory;
    }

    /**
     * Method to perform output to a file in append mode.
     *
     * @param outputFileName A String value to provide the name of the file.
     * @param contents       A String value containing the contents to be written to the file.
     */
    public void writeFile(String outputFileName, String contents) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName, true))) {
            writer.write(contents);
            writer.newLine();
            System.out.println("Saved.");
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Method to overwrite a file.
     *
     * @param outputFileName A String value to provide the name of the file.
     * @param contents       An ArrayList of products to write to the file.
     */
    public void writeOver(String outputFileName, ArrayList<String> contents) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFileName))) {
            for (String product : contents) {
                bw.write(product);
                bw.newLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + outputFileName);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error writing to the file.");
            e.printStackTrace();
        }
    }
}