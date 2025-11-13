package utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class JwtUtils {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm:ss z");

    public static JSONObject decodeJwt(String jwtToken) {
        String[] parts = jwtToken.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid JWT token format");
        }

        String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));

        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(payloadJson);
            System.out.println("jwtToken: " + payloadJson);

            AllureUtils.attachJwtToken(payloadJson);

            convertTimeToReadableFormat((Long) jsonObject.get("iat"), "Issued At");
            convertTimeToReadableFormat((Long) jsonObject.get("exp"), "Expiration");

            return jsonObject;
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse JWT payload: " + e.getMessage());
        }
    }

    public static String expireJwtToken(String validToken) {
        String[] parts = validToken.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid JWT token format");
        }

        String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));
        JSONObject jsonObject;

        try {
            JSONParser parser = new JSONParser();
            jsonObject = (JSONObject) parser.parse(payloadJson);
            System.out.println("jwtToken: " + payloadJson);
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse JWT payload: " + e.getMessage());
        }

        // set exp to 1 hour ago
        long expiredTime = (System.currentTimeMillis() / 1000) - 3600;
        jsonObject.put("exp", expiredTime);

        // re-encode modified payload
        String modifiedPayload = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(jsonObject.toString().getBytes());

        // keep the same header but break the signature intentionally
        String fakeToken = parts[0] + "." + modifiedPayload + "." + parts[2];

        return fakeToken;
    }

    private static void convertTimeToReadableFormat(long time, String name) {
        // convert seconds → milliseconds
        Instant instant = Instant.ofEpochSecond(time);

        // format into readable UTC or local date
        ZonedDateTime utcTime = instant.atZone(ZoneOffset.UTC);
        ZonedDateTime localTime = instant.atZone(ZoneId.systemDefault());

        String readableUtcDate = utcTime.format(formatter);
        String readableLocalDate = localTime.format(formatter);

        System.out.println(name + " UTC Time:: " + readableUtcDate);
        System.out.println(name + " Local Time:: " + readableLocalDate);

        AllureUtils.attachmentTime(name + " UTC Time: ", readableUtcDate);
        AllureUtils.attachmentTime(name + " Local Time: ", readableLocalDate);
    }

}
