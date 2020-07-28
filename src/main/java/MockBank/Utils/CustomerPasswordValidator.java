package MockBank.Utils;

import org.passay.*;

import java.util.Arrays;

public class CustomerPasswordValidator {
    public static boolean isPasswordValid(String password) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(8, 20),
                new WhitespaceRule()
        ));
        RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        } else {
            return false;
        }
    }
}
