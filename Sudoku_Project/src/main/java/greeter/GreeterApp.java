package greeter;

public class GreeterApp {

    public static void main(String[] args) {
        Greeter greeter = new Greeter();
        System.out.println(greeter.greet(args[0]));
    }

}