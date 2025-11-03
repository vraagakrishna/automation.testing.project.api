package requestbuilder.reqres;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static common.BasePaths.ReqResBaseUrl;

public class BaseReqResApiRequestBuilder {

    protected static RequestSpecification baseSpec = new RequestSpecBuilder()
            .setBaseUri(ReqResBaseUrl)
            .setContentType("application/json")
            .build();

}
