import java.util.ArrayList;
import java.util.List;

public class TestFinder {
    public List<Test> findFrom(List<Spec> specs, List<StepMethod> stepMethods) {
        List<Test> matchedTest = new ArrayList<>();

        for (StepMethod method : stepMethods) {
            specs.forEach(spec -> {
                if (method.matches(spec)) {
                    //Urghhh!!
                    matchedTest.addAll(method.tests);
                }
            });
        }

        return matchedTest;
    }
}
