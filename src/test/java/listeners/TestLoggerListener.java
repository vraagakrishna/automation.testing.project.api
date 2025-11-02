package listeners;

import io.qameta.allure.Description;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.logging.Logger;

public class TestLoggerListener implements ITestListener {

    private static final Logger log = Logger.getLogger(TestLoggerListener.class.getName());

    // <editor-fold desc="Overrides">
    @Override
    public void onTestStart(ITestResult result) {
        // Get the Description annotation
        Description description = result
                .getMethod().getConstructorOrMethod()
                .getMethod().getAnnotation(Description.class);

        if (description != null) {
            log.info("TEST START: " + description.value());
        } else {
            log.info("TEST START: " + result.getMethod().getMethodName());
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("TEST PASSED: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.info("TEST FAILED: " + result.getMethod().getMethodName());
        log.info("Failure message: " + result.getThrowable().getMessage());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.info("TEST SKIPPED: " + result.getMethod().getMethodName());
    }
    // </editor-fold>

}
