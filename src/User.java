import javafx.scene.image.Image;

import java.io.File;

/**
 * Class that represents a user.
 * @author Elena Roncolino
 */
public class User {
    String username, password;
    Image image;

    /**
     * User constructor.
     * @param username The username chosen.
     * @param password The password chosen.
     * @param image The chosen profile image.
     */
    public User(String username, String password, Image image) {
        this.username = username;
        this.password = password;
        this.image = image;
    }

}
