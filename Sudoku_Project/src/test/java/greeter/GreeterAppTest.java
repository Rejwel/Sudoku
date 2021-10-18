package greeter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GreeterAppTest {

    public GreeterAppTest() {
    }

    @Test
    void withoutParametersTest() {
        String [] appParameters = {};
        try {
            GreeterApp.main(appParameters);
        } catch (ArrayIndexOutOfBoundsException exception) {
            System.out.println("Exception " + exception);
        }

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> GreeterApp.main(appParameters));
    }

    @Test
    void withParametersTest() {
        String [] appParameters = {"Pawel"};
        GreeterApp.main(appParameters);
    }
}