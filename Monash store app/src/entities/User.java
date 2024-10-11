package entities;

/**
 * User entity class. Represent a user.
 *
 * @author Abbishek Kamak, Bao Hoang, Muskaan Sheik, Tom
 * @version 5/16/2024
 */
public class User {
    private String email;
    private String password;

    /**
     * Default constructor.
     */
    public User() {
        this.email = "";
        this.password = "";
    }

    /**
     * Constructor that initializes a User object with the given email and password.
     *
     * @param email    the email address of the user
     * @param password the password of the user
     */
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Returns the email address of the user.
     *
     * @return the email address of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     *
     * @param email the new email address to be set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the password of the user.
     *
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the new password to be set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}