import org.junit.internal.AssumptionViolatedException;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.log4j.BasicConfigurator;

import java.util.List;

public class DaSpecTestRunner extends Runner {
    private Class aClass;
    private List<Test> tests;
    private Description description;

    Logger logger = LoggerFactory.getLogger(DaSpecTestRunner.class);

    public DaSpecTestRunner(java.lang.Class aClass) {
        this.aClass = aClass;
        stepFinder = new StepFinder(aClass);
        specFinder = new SpecFinder(aClass);

        BasicConfigurator.configure();
    }

    private SpecFinder specFinder;
    private StepFinder stepFinder;
    private TestFinder testFinder = new TestFinder();
    private TestDescriber testDescriber = new TestDescriber();

    @Override
    public Description getDescription() {
        List<Spec> specs = specFinder.getSpecs();
        List<StepMethod> stepMethods = stepFinder.find();
        tests = testFinder.findFrom(specs, stepMethods);

        description = testDescriber.createDescription(tests, aClass);

        logger.info("Found : " + description.toString() + " Specs: " + specs.size() + " Steps: " + stepMethods.size());

        return description;
    }


    @Override
    public void run(RunNotifier runNotifier) {
        Result result = new Result();
        RunListener listener = result.createListener();
        runNotifier.addFirstListener(listener);
        try {
            runNotifier.fireTestRunStarted(Description.createSuiteDescription("DaSpecRunner"));
            tests.forEach(test -> run(test, runNotifier));
            runNotifier.fireTestRunFinished(result);
        } finally {
            runNotifier.removeListener(listener);;
        }

   /*     Result result = new Result();
        runNotifier.addListener(result.createListener());
        runNotifier.fireTestRunStarted(description);
        tests.forEach(test -> run(test, runNotifier));
        runNotifier.fireTestRunFinished(result);
    */ }

    private void run(Test test, RunNotifier runNotifier) {
        runNotifier.fireTestStarted(test.description);
        try {
            test.run();
        } catch (AssumptionViolatedException ex) {
            runNotifier.fireTestAssumptionFailed(new Failure(test.description, ex));
        } catch (Throwable ex) {
            runNotifier.fireTestFailure(new Failure(test.description, ex));
        }
        finally {
            runNotifier.fireTestFinished(test.description);
        }
    }
}
