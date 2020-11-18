package utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class TimeFunctions {

    /**
     * Placeholder function for converting to Timestamp. Might need later if conversion issues arise from the sql driver.
     *
     * @param time the time to convert to a Timestamp formatted for the DB
     * @return a Timestamp in the correct format for the DB
     */
    public static Timestamp toDBTimestamp(LocalDateTime time) {
        Timestamp DBTime;
        DBTime = Timestamp.valueOf(time);
        return DBTime;
    }

    /**
     * Placeholder function for converting from Timestamp. Might need later if conversion issues arise from the sql driver.
     *
     * @param time the Timestamp to convert to LocalDateTime
     * @return the time converted to LocalDateTime
     */
    public static LocalDateTime fromDBTimestamp(Timestamp time) {
        LocalDateTime localTime;
        localTime = time.toLocalDateTime();
        return localTime;
    }

    public static String formatDateTimeForDisplay(LocalDateTime localDateTime) {
        String formattedDateTime;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd hh:mm a");
        formattedDateTime = localDateTime.format(formatter);
        return formattedDateTime;
    }



}
