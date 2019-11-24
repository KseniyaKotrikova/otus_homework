package ru.otus.annotations;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner  {

    static void runTests() {
        final JUnitCore runner = new JUnitCore();
        Result result = runner.runClasses(ru.otus.annotations.DrinkTest.class);
        if (result.wasSuccessful()) {
            System.out.println("All tests passed.");
        } else {
            System.out.println("======================================================\n");
            System.out.println(String.format("%d tests failed.", result.getFailureCount()));
            System.out.println(String.format("%d test passed.", (result.getRunCount()-result.getFailureCount())));
            System.out.println("======================================================\n");
            System.out.println("\nFailures:");
            for (final Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }

        System.out.println("======================================================\n");
        System.exit(result.wasSuccessful() ? 0 : -1);
    }

    public static void main(String[] args) {
        runTests();
    }
}

