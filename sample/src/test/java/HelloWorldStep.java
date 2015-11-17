import org.junit.Test;
import org.junit.runner.RunWith;

import static com.google.common.truth.Truth.assertThat;

@RunWith(DaSpecTestRunner.class)
public class HelloWorldStep {

    @Test
    @StepDefinition("right way to greet the (.*) is \"(.*)\"")
    public void greeting(String subject, String expectedGreeting) {

        Greeter sut = new Greeter();

        String actualGreeting = sut.greetingFor(subject);

        assertThat(actualGreeting).isEqualTo(expectedGreeting);
    }

}
