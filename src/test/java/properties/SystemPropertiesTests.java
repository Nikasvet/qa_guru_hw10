package properties;


import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("properties")
public class SystemPropertiesTests {
    @Test
    void test1() {
        System.out.println("[test1] Browser: " + System.getProperty("browser"));

    }
}
