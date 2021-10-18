package greeter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GreeterTest {

    public GreeterTest() {
    }

    @Test
    void GreetTest() {
        Greeter greet = new Greeter();
        String name = "Pawel";
        String tempText = "Hello Pawel";
        String greetText = greet.greet(name);
        assertEquals(tempText, greetText);
    }
}