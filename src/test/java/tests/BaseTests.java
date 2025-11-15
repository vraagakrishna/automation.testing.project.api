package tests;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import org.testng.annotations.BeforeTest;

import java.io.OutputStream;
import java.io.PrintStream;

public class BaseTests {

    @BeforeTest
    public void globalRestAssuredConfig() {
        // Timeout configuration (applies to all APIs)
        HttpClientConfig httpClientConfig = HttpClientConfig.httpClientConfig()
                .setParam("http.connection.timeout", 5000)
                .setParam("http.socket.timeout", 5000)
                .setParam("http.connection-manager.timeout", 5000);

        // Check environment variable
        String enableLogs = System.getProperty("ENABLE_LOGS", System.getenv("ENABLE_LOGS"));
        boolean logsEnabled = "true".equalsIgnoreCase(enableLogs);

        LogConfig logConfig;
        if (logsEnabled) {
            System.out.println("RestAssured logging ENABLED (logs will appear).");
            logConfig = LogConfig.logConfig().enablePrettyPrinting(true); // prints to System.out
        } else {
            System.out.println("RestAssured logging DISABLED (logs will NOT appear).");
            logConfig = LogConfig.logConfig().defaultStream(new PrintStream(OutputStream.nullOutputStream()));
        }

        // Apply both HttpClientConfig (timeouts) and LogConfig (logging)
        RestAssured.config = RestAssuredConfig.config()
                .httpClient(httpClientConfig)
                .logConfig(logConfig);

        RestAssured.replaceFiltersWith((req, res, ctx) -> ctx.next(req, res));
    }

}
