import org.junit.runner.Description;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class Test {
    public Class<?> aClass;
    public StepMethod method;
    public Spec spec;
    public List<String> paramterValues = new ArrayList<>();
    public Description description;

    public Test(StepMethod method, Spec spec, Matcher matcher) {

        this.method = method;
        this.spec = spec;
        for (int i=1; i <= matcher.groupCount(); i++ ) {
            paramterValues.add(matcher.group(i));
        }
    }

    public Description describe(Class<?> aClass) {
        description = Description.createTestDescription(aClass, spec.source + " " + method.method.getName() + " " + paramterValues.toString());
        return description;
    }

    public void run() {
        executeStepMethod();
    }

    private void executeStepMethod() {
        try {
            method.method.invoke(method.aClass.newInstance(), paramterValues.toArray(new String[paramterValues.size()]));
        } catch (IllegalAccessException |InvocationTargetException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}
