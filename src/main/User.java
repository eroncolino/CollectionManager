package main;

import javafx.scene.image.Image;

/**
 * Class that represents a user.
 *
 * @author Elena Roncolino
 */
public class User {
    private static String username, password;
    private static int id;
    private static Image image;

    /**
     * User constructor.
     *
     * @param id       The user id.
     * @param username The username chosen.
     * @param password The password chosen.
     * @param image    The chosen profile image.
     */
    public User(int id, String username, String password, Image image) {
        User.id = id;
        User.username = username;
        User.password = password;
        User.image = image;
    }

    /**
     * User id getter.
     *
     * @return int The user id.
     */
    public static int getUserId() {
        return id;
    }

    /**
     * Username getter.
     *
     * @return String The username.
     */
    public static String getUsername() {
        return username;
    }

    /**
     * User image getter.
     *
     * @return Image The chosen image.
     */
    public static Image getImage() {
        return image;
    }


}

