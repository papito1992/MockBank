package MockBank.Utils;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
@Slf4j
public class DateFormater {
    public static Date convertStringToDate(String date) {

        Date stringAsDate = null;
        try {
            stringAsDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(date);
        } catch (ParseException e) {
            log.warn("");
            e.printStackTrace();
        }
        return stringAsDate;
    }
}
