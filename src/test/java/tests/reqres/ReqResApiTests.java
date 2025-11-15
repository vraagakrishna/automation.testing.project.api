package tests.reqres;

import io.qameta.allure.Epic;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;
import utils.RandomNumberGenerator;
import utils.ReqResTestData;

@Epic("ResReq API")
public class ReqResApiTests {

    ReqResTestData data = new ReqResTestData();

    RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();

    @BeforeMethod
    public void resetRestAssured() {
        RestAssured.reset();
    }

}
