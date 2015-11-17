import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class StepFinder {
    private final Class aClass;

    public StepFinder(Class aClass) {

        this.aClass = aClass;
    }

    public List<StepMethod> find() {

        List<StepMethod> stepMethods = new ArrayList<>();

        for (Method method : aClass.getDeclaredMethods()) {
            StepDefinition annotation = method.getDeclaredAnnotation(StepDefinition.class);

            if (annotation != null) {
                StepMethod stepMethod = StepMethod.from(aClass, method, annotation.value());
                stepMethods.add(stepMethod);
            }
        }
        return stepMethods;
    }
}
