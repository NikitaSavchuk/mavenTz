import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import ru.yandex.qatools.allure.annotations.Step;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

@RunWith(value = Parameterized.class)
public class AllureTest {

    private static final String TEST_DATA_FILE = "src/main/resources/data-file";

    @Parameter(value = 0)
    public String operand1;
    @Parameter(value = 1)
    public String operand2;
    @Parameter(value = 2)
    public String operation;
    @Parameter(value = 3)
    public String result;

    @Parameterized.Parameters
    public static List<String[]> readData() {

        final List<String[]> testsData = new ArrayList<String[]>();
        try {
            final BufferedReader bufferedReader = new BufferedReader(new java.io.FileReader(TEST_DATA_FILE));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                testsData.add(line.split(";"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return testsData;
    }

    @Test
    public void test() {

        executeTest(operand1, operation, operand2, result);
    }

    @Step
    public static void executeTest(
            final String operand1,
            final String operation,
            final String operand2,
            final String result) {

        final int expectedResult = Integer.parseInt(result);
        int actualResult;

        if (operation.equals("+")) {
            actualResult = Integer.parseInt(operand1) + Integer.parseInt(operand2);

        } else if (operation.equals("-")) {
            actualResult = Integer.parseInt(operand1) - Integer.parseInt(operand2);

        } else if (operation.equals("*")) {
            actualResult = Integer.parseInt(operand1) * Integer.parseInt(operand2);

        } else if (operation.equals("/")) {
            try {
                actualResult = Integer.parseInt(operand1) / Integer.parseInt(operand2);
            } catch (ArithmeticException e) {
                Assert.fail("ArithmeticException: " + e.getMessage());
                return;
            }
        } else {
            Assert.fail("Operation '" + operation + "' is not support.");
            return;
        }

        Assert.assertEquals(expectedResult, actualResult);
    }
}
