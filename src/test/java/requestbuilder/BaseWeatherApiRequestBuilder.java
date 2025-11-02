package requestbuilder;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static common.BasePaths.WeatherBaseUrl;

public class BaseWeatherApiRequestBuilder {

    protected static RequestSpecification baseSpec = new RequestSpecBuilder()
            .setBaseUri(WeatherBaseUrl)
            .setContentType("application/json")
            .build();

}
