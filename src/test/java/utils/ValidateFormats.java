package utils;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidateFormats {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static boolean isValidEmail(String email) {
        return email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");
    }

    public static boolean isValidUrl(String url) {
        try {
            new URI(url);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidAnyDateTime(String dateTime) {
        if (isValidIso8601(dateTime))
            return true;
        else return isValidDateTime(dateTime);
    }

    public static boolean isValidDateTime(String dateTime) {
        if (dateTime == null) return false;

        try {
            LocalDateTime.parse(dateTime, FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isValidIso8601(String dateTime) {
        if (dateTime == null || dateTime.isEmpty()) return false;

        try {
            // Parse with milliseconds and 'Z' for UTC
            OffsetDateTime.parse(dateTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean isValidHexColor(String color) {
        // Matches #RGB, #RRGGBB, #RRGGBBAA (optional alpha)
        return color != null && color.matches("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$");
    }

}
