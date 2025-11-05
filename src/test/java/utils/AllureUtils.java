package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Allure;
import io.restassured.http.ContentType;

public class AllureUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static void attachUri(String uri) {
        Allure.addAttachment("Uri", uri);
    }

    public static void attachRequest(String request) {
        attachToReport("Request", request);
    }

    public static void attachResponse(String response) {
        attachToReport("Response", prettyPrintJson(response));
    }

    public static void attachNote(String note) {
        Allure.addAttachment("Note", note);
    }

    private static String prettyPrintJson(String json) {
        try {
            Object jsonObj = mapper.readValue(json, Object.class);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObj);
        } catch (Exception e) {
            // if it's not valid JSON (e.g., HTML or plain text), just return it as-is
            return json;
        }
    }

    private static void attachToReport(String name, String value) {
        Allure.addAttachment(name, ContentType.JSON.toString(), value, ".json");
    }

}
