package requestbuilder.weatherapi;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import requestbuilder.BaseRequestBuilder;

import static common.BasePaths.WeatherBaseUrl;

public class BaseWeatherApiRequestBuilder extends BaseRequestBuilder {

    protected static RequestSpecification baseSpec = new RequestSpecBuilder()
            .setBaseUri(WeatherBaseUrl)
            .setContentType(ContentType.JSON.toString())
            .build();

}
