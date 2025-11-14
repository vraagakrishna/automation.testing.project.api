package tests.ndosiautomation;

import io.qameta.allure.Epic;
import model.ndosiautomation.LoginRequest;
import model.ndosiautomation.LoginResponse;
import org.apache.http.HttpStatus;
import org.testng.asserts.SoftAssert;
import utils.NdosiAutomationTestData;

import static requestbuilder.ndosiautomation.NdosiAutomationRequestBuilder.login;
import static utils.ValidateNdosiAutomationUtils.validateSuccessLoginResponse;

@Epic("Ndosi Automation API")
public class NdosiAutomationTests {

    protected static final NdosiAutomationTestData data = new NdosiAutomationTestData();

    SoftAssert softAssert;

    public NdosiAutomationTests() {
        this.softAssert = new SoftAssert();
    }

    protected void checkSoftAssertion() {
        try {
            softAssert.assertAll();
        } finally {
            softAssert = new SoftAssert();
        }
    }

    public void loginUserWithValidData() {
        LoginRequest<Object> loginRequest = new LoginRequest<>();
        loginRequest.setEmail(data.getEmail());
        loginRequest.setPassword(data.getPassword());

        LoginResponse loginResponse = login(loginRequest)
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(LoginResponse.class);

        validateSuccessLoginResponse(loginResponse, data, softAssert);

        checkSoftAssertion();
    }

}
