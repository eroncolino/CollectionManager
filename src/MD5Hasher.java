import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class is used to encrypte the user's password
 * using MD5 algorithm.
 *
 * @author  Elena Roncolino
 */

public class MD5Hasher implements Hasher{

    /**
     * Method that generates and returns a password hash.
     * This method has been taken from the website suggested in
     * the requirements of this project.
     *
     * @author Lokesh Gupta
     * @see <a href="http://howtodoinjava.com/security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/">
     *     Generate Secure Password MD5Hasher : MD5, SHA, PBKDF2, BCrypt Examples</a>
     * @param passwordToHash The password to be ashed.
     * @return String The hashed password.
     */

    public String getSecurePassword(String passwordToHash)
    {
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            //md.update(salt);
            //Get the hash's bytes
            byte[] bytes = md.digest(passwordToHash.getBytes());
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}
