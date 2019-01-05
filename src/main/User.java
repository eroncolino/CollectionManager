package main;

import javafx.scene.image.Image;

/**
 * Class that represents a user.
 * @author Elena Roncolino
 */
public class User {
    String username, password;
    Image image;

    /**
     * main.User constructor.
     * @param username The username chosen.
     * @param password The password chosen.
     * @param image The chosen profile image.
     */
    public User(String username, String password, Image image) {
        this.username = username;
        this.password = password;
        this.image = image;
    }

    /**
     * Username getter.
     * @return String The username.
     */
    public static String getUsername(){
        return getUsername();
    }

}
