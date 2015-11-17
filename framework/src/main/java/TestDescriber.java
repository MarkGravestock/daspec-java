import org.junit.runner.Description;

import java.util.List;

public class TestDescriber {
    Description createDescription(List<Test> tests, Class<?> aClass) {
        Description description = Description.createSuiteDescription(aClass);

        tests.forEach(test -> description.addChild(test.describe(aClass)));
        return description;
    }
}
