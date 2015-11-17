import org.junit.Test;
import org.junit.runner.RunWith;

import static com.google.common.truth.Truth.assertThat;

@RunWith(DaSpecTestRunner.class)
public class GreetingStep {

    @Test
    @StepDefinition("wrong way to greet the (.*) is \"(.*)\"")
    public void wrongGreeting(String subject, String expectedGreeting) {

        Greeter sut = new Greeter();

        String actualGreeting = sut.greetingFor(subject);

        assertThat(actualGreeting).isEqualTo(expectedGreeting);
    }

}
