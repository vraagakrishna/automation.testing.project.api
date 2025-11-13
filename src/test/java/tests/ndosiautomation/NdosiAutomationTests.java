package tests.ndosiautomation;

import io.qameta.allure.Epic;
import org.testng.asserts.SoftAssert;
import utils.NdosiAutomationTestData;

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

}
