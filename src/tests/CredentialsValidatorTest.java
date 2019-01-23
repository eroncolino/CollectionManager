package tests;

import main.CredentialsValidator;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests if {@link CredentialsValidator} works as expected.
 *
 * @author Elena Roncolino
 */
public class CredentialsValidatorTest {

    /**
     * Tests whether incorrect usernames generate the right error message.
     */
    @Test
    public void testInvalidUsername() {
        String[] invalidUsername = new String[]{
                "..",
                "__",
                "a",
                "..fdfwf",
                "dsnfk.knaa.",
                "fsòdf_fdfw_efwe",
                "fdfenfelndlkdnflknflckndlfnelnclfnkfnknfklnflwen"
        };

        for (String username : invalidUsername) {
            boolean invalid = CredentialsValidator.checkDataRequirements(username, "a", "a");
            assertFalse(invalid);
        }
    }

    /**
     * Tests if usernames and passwords are correctly checked according to the requirements.
     */
    @Test
    public void testUsernameAndPassword() {
        assertTrue(CredentialsValidator.checkPasswordRequirements("admin", "admin"));
        assertTrue(CredentialsValidator.checkPasswordRequirements("000000", "000000"));
        assertFalse(CredentialsValidator.checkMatchingPasswords("admin", "ad"));
        assertFalse(CredentialsValidator.checkMatchingPasswords("ad", ""));
    }
}