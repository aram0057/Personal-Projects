package entities;

/**
 * Class which stores information of Admin for the Monash Merchant Application
 *
 * @author Abbishek Kamak, Bao Hoang, Muskaan Sheik, Tom
 * @version 5/16/2023
 */
public class Admin extends User {

    /**
     * Parametrized constructor which creates the object of the class Admin.
     */
    public Admin(String email, String password) {
        super(email, password);
    }

    /**
     * Method to display the state of the Admin object.
     */
    public void display() {
        System.out.println(toString());
    }

    /**
     * Method to return the state of Admin object as a string.
     *
     * @return The state of the object as a string.
     */
    public String toString() {
        String str = "";
        str += "Email: " + getEmail() + ", ";
        str += "Password: " + getPassword() + "\n";
        return (str);
    }

}
