package entities;

/**
 * Customer entity class, extended from user class.
 * Represent a customer.
 *
 * @author Abbishek Kamak, Bao Hoang, Muskaan Sheik, Tom
 * @version 5/16/2024
 */
public class Customer extends User {
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String address;
    private String mobileNumber;
    private double funds;
    private Boolean membershipStatus;
    private ShoppingCart shoppingCart;

    /**
     * Default constructor.
     */
    public Customer() {
        super("", "");
        this.firstName = "";
        this.lastName = "";
        this.dateOfBirth = "";
        this.address = "";
        this.mobileNumber = "";
        this.funds = 0.0;
        this.membershipStatus = false;
        this.shoppingCart = new ShoppingCart();
    }

    /**
     * Parameterized constructor
     *
     * @param email            the email address of the customer
     * @param password         the password of the customer
     * @param firstName        the first name of the customer
     * @param lastName         the last name of the customer
     * @param dateOfBirth      the date of birth of the customer
     * @param address          the address of the customer
     * @param mobileNumber     the mobile number of the customer
     * @param funds            the available funds of the customer
     * @param membershipStatus the membership status of the customer
     * @param shoppingCart     the shopping cart of the customer
     */
    public Customer(String email, String password, String firstName, String lastName, String dateOfBirth,
                    String address, String mobileNumber, double funds, Boolean membershipStatus,
                    ShoppingCart shoppingCart) {
        super(email, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.mobileNumber = mobileNumber;
        this.funds = funds;
        this.membershipStatus = membershipStatus;
        this.shoppingCart = shoppingCart;
    }

    /**
     * Returns the first name of the customer.
     *
     * @return the first name of the customer
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the customer.
     *
     * @param firstName the new firstname cart to be set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the last name of the customer.
     *
     * @return the last name of the customer
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the first name of the customer.
     *
     * @param lastName the new firstname cart to be set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the date of birth of the customer.
     *
     * @return the date of birth of the customer
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the date of birth of the customer.
     *
     * @param dateOfBirth the new date of birth cart to be set
     */
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Returns the address of the customer.
     *
     * @return the address of the customer
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the customer.
     *
     * @param address the new address cart to be set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns the mobile number of the customer.
     *
     * @return the mobile number of the customer
     */
    public String getMobileNumber() {
        return mobileNumber;
    }

    /**
     * Sets the mobile number of the customer.
     *
     * @param mobileNumber the new mobile number cart to be set
     */
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    /**
     * Returns the funds of the customer.
     *
     * @return the funds of the customer
     */
    public double getFunds() {
        return funds;
    }

    /**
     * Sets the funds of the customer.
     *
     * @param funds the new funds to be set
     */
    public void setFunds(double funds) {
        this.funds = funds;
    }

    /**
     * Returns the membership status of the customer.
     *
     * @return the membership status of the customer
     */
    public boolean getMembershipStatus() {
        return membershipStatus;
    }

    /**
     * Sets the membership status of the customer.
     *
     * @param membershipStatus the new membership status to be set
     */
    public void setMembershipStatus(boolean membershipStatus) {
        this.membershipStatus = membershipStatus;
    }

    /**
     * Returns the shopping cart of the customer.
     *
     * @return the shopping cart of the customer
     */
    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    /**
     * Sets the shopping cart of the customer.
     *
     * @param shoppingCart the new shopping cart to be set
     */
    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    /**
     * Returns a string representation of the Customer object.
     *
     * @return a string containing the customer's information
     */
    public String toString() {
        String str = "";
        str += "Email: " + getEmail() + ",";
        str += "Password: " + getPassword() + ",";
        str += "First Name: " + firstName + ",";
        str += "Last Name: " + lastName + ",";
        str += "DOB: " + dateOfBirth + ",";
        str += "Address: " + address + ",";
        str += "Mobile Number: " + mobileNumber + ", ";
        str += "Funds: " + funds + ",";
        str += "Membership Status: " + membershipStatus;
        return (str);
    }
    public String toStringForUpdate() {
        String str = "";
        str += getEmail() + ",";
        str += getPassword() + ",";
        str += firstName + ",";
        str += lastName + ",";
        str += dateOfBirth + ",";
        str += address + ",";
        str += mobileNumber + ",";
        str += funds + ",";
        str += membershipStatus;
        return (str);
    }

    /**
     * Displays the customer's information by printing it to the console.
     */
    public void display() {
        System.out.println(toString());
    }

    /**
     * Returns a comma-separated string representation of the Customer object
     * for file I/O purposes.
     *
     * @return a comma-separated string containing the customer's information
     */
    public String toStringForFileIO() {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%.2f,%b",
                getEmail(), getPassword(), firstName, lastName, dateOfBirth,
                address, mobileNumber, funds, membershipStatus);
    }
}