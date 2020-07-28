package MockBank.Utils;

import com.mifmif.common.regex.Generex;

public class IbanGenerator {
    public static String generateIbanByRegex() {
        Generex generex = new Generex("LT\\d{2}[ ]\\d{4}[ ]\\d{4}[ ]\\d{4}[ ]\\d{4}");
        return generex.random();
    }
}
