package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static requestbuilder.WeatherApiRequestBuilder.testRequest;

@Test
@Feature("Open WeatherMap")
@Story("Weather Station")
public class WeatherApiTests {

    @Description("As a automation tester, I want to test my framework")
    public void test() {
        testRequest()
                .then()
                .log().all()
                .assertThat().statusCode(HttpStatus.SC_OK);
    }

}
