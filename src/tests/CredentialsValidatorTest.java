package tests;

import main.CredentialsValidator;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests if {@link CredentialsValidator} works as expected.
 */
public class CredentialsValidatorTest {

    @Test
    public void TestInvalidUsername() {
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

    @Test
    public void TestUsernameAndPassword() {
        assertTrue(CredentialsValidator.checkPasswordRequirements("admin", "admin"));
        assertTrue(CredentialsValidator.checkPasswordRequirements("000000", "000000"));
        assertFalse(CredentialsValidator.checkMatchingPasswords("admin", "ad"));
        assertFalse(CredentialsValidator.checkMatchingPasswords("ad", ""));
    }
}