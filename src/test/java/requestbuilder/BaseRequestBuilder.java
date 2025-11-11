package requestbuilder;

import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.AllureUtils;

public class BaseRequestBuilder {

    protected static void addDataToAllureReport(RequestSpecification req) {
        req.filter((reqSpec, respSpec, ctx) -> {
            AllureUtils.attachRequestHeaders(reqSpec.getHeaders().toString());
            return ctx.next(reqSpec, respSpec);
        });
    }

    protected static void addDataToAllureReport(RequestSpecification req, Object payload, Response response) {
        AllureUtils.attachUri(
                ((RequestSpecificationImpl) req).getMethod() + " " + ((RequestSpecificationImpl) req).getURI()
        );

        if (payload != null)
            AllureUtils.attachRequest(payload.toString());

        AllureUtils.attachResponseHeaders(response.getHeaders().toString());
        AllureUtils.attachResponse(response.getBody().asString());
        AllureUtils.attachStatusCode(response.statusLine());
    }

}
