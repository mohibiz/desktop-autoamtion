package HIPPUtils;
/**
 * @author Mohit Gupta
 */

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.awaitility.Awaitility.with;

public class HIPPHelper {

    private static void waitUntil(boolean expression, int timeOutInSeconds) {
        try {
            with().pollInterval(
                    2,
                    TimeUnit.SECONDS
            ).await().atMost(
                    timeOutInSeconds,
                    TimeUnit.SECONDS).untilTrue(
                    new AtomicBoolean(expression)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
