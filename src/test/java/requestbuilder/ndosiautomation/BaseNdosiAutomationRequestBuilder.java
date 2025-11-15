package requestbuilder.ndosiautomation;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import requestbuilder.BaseRequestBuilder;

import static common.BasePaths.NdosiAutomationUrl;

public class BaseNdosiAutomationRequestBuilder extends BaseRequestBuilder {

    protected static RequestSpecification baseSpec = new RequestSpecBuilder()
            .setBaseUri(NdosiAutomationUrl)
            .setAccept(ContentType.JSON.toString())
            .setContentType(ContentType.JSON.toString())
            .build();


}
