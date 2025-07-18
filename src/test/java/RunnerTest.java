import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features",
        glue = {"StepDefinitions"},
        monochrome = true,
        tags = "@POST",
        plugin = { "json:target/cucumber.json" })

public class RunnerTest {
}
