package test;


import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Unit test for simple Crypto.
 */
public class AppTest {

    /**
     * Rigourous Test :-)
     */
    @Test
    public void testApp() {
        convertToUTC("2018-02-05T07:26:51Z");
    }


    public static long convertToUTC(final String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'");
        long timeReported = 0;
        try {
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            timeReported = sdf.parse(dateString).getTime();

        } catch (ParseException e) {
            e.printStackTrace();
            // If date is not pass correctly time will be taken from the server.
            timeReported = new Date().getTime();
            Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis();

        }

        System.out.println(timeReported);
        System.out.println(new Date(timeReported).getSeconds());
        System.out.println(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis());
        return timeReported;
    }
}
