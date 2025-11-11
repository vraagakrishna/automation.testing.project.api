package listeners;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.TestNG;

import java.util.Collections;

public class SuiteRepeaterListener implements ISuiteListener {

    private static final String REPEAT_FLAG = "suiteRepeatInProgress";

    private int runCount = 1; // default if not set

    private long delay = 0;

    @Override
    public void onStart(ISuite suite) {
        // only set runCount if not already in a repeated run
        if (System.getProperty(REPEAT_FLAG) == null) {
            System.out.println("=== Running suite: " + suite.getName() + " | Run #1 ===");

            // get the parameters form testng.xml file
            String repeatCountParam = suite.getXmlSuite().getParameter("suiteRepeatCount");
            String delayParam = suite.getXmlSuite().getParameter("suiteRepeatDelayMs");

            if (repeatCountParam != null) {
                try {
                    runCount = Integer.parseInt(repeatCountParam);
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid suiteRepeatCount parameter: " + repeatCountParam);
                }
            }

            if (delayParam != null) {
                try {
                    delay = Long.parseLong(delayParam);
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid suiteRepeatDelayMs parameter: " + delayParam);
                }
            }
        }
    }

    @Override
    public void onFinish(ISuite suite) {
        // check if already running repeated runs
        if (System.getProperty(REPEAT_FLAG) != null) {
            return; // don't rerun again
        }

        // start repeated runs
        if (runCount > 1) {
            System.setProperty(REPEAT_FLAG, "true");

            // start at 1 because the first run happened already
            for (int i = 2; i <= runCount; i++) {
                try {
                    System.out.println("Waiting " + delay / 1000 + "s before re-running suite...");
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                System.out.println("=== Re-running suite: " + suite.getName() + " | Run #" + i + " ===");

                TestNG testng = new TestNG();
                testng.setDefaultSuiteName(suite.getName() + "Run #" + i);

                // point TestNG to the same testng.xml file
                testng.setTestSuites(Collections.singletonList("testng.xml"));

                // run the suite
                testng.run();
            }

            // clear flag after all runs
            System.clearProperty(REPEAT_FLAG);
        }
    }

}
