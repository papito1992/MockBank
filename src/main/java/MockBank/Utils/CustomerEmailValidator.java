package MockBank.Utils;

import org.apache.commons.validator.routines.EmailValidator;

public class CustomerEmailValidator {
    public static boolean isValidEmail(String email) {
        // valid email addresses
        //emails.add("alice@example.com");
        //emails.add("alice.bob@example.com");
        //emails.add("alice@example.me.org");

        //invalid email addresses
        //emails.add("alice.example.com");
        //emails.add("alice..bob@example.com");
        //emails.add("alice@.example.com");
        EmailValidator validator = EmailValidator.getInstance();

        return validator.isValid(email);
    }
}

