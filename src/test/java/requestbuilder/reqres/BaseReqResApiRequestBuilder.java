package requestbuilder.reqres;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static common.BasePaths.ReqResBaseUrl;

public class BaseReqResApiRequestBuilder {

    protected static RequestSpecification baseSpec = new RequestSpecBuilder()
            .setBaseUri(ReqResBaseUrl)
            .setAccept(ContentType.JSON.toString())
            .setContentType(ContentType.JSON.toString())
            .build();

}
