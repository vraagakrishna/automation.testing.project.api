package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Allure;
import io.restassured.http.ContentType;

public class AllureUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    // <editor-fold desc="Public Methods">
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

    public static void attachResponseTime(String responseTime, String units) {
        Allure.addAttachment("Response Time", responseTime + " " + units);
    }

    public static void attachStatusCode(String statusCode) {
        Allure.addAttachment("Status Code", String.valueOf(statusCode));
    }

    public static void attachRequestHeaders(String requestHeaders) {
        Allure.addAttachment("Request Headers", requestHeaders);
    }

    public static void attachResponseHeaders(String responseHeaders) {
        Allure.addAttachment("Response Headers", responseHeaders);
    }

    public static void attachJwtToken(String payloadJson) {
        Allure.addAttachment("Decoded JWT Payload", prettyPrintJson(payloadJson));
    }

    public static void attachmentTime(String name, String time) {
        Allure.addAttachment(name, time);
    }
    // </editor-fold>

    // <editor-fold desc="Private Methods">
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
    // </editor-fold>

}
