import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StepMethod {
    public Method method;
    public Pattern pattern;
    public List<Test> tests = new ArrayList<>();
    public Class<?> aClass;


    public static StepMethod from(Class aClass, Method method, String value) {
        StepMethod stepMethod = new StepMethod();
        stepMethod.aClass = aClass;
        stepMethod.method = method;
        stepMethod.pattern = Pattern.compile(value);
        return stepMethod;
    }

    public boolean matches(Spec candidateSpec) {
        boolean  matched = false;

        Matcher matcher = pattern.matcher(candidateSpec.markDown);
        while(matcher.find()) {
            matched = true;
            tests.add(new Test(this, candidateSpec, matcher));
        }

        return  matched;
    }
}
