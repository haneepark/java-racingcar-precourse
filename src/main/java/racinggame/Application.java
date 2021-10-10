package racinggame;

public class Application {
    public static void main(String[] args) {
        View view = new View();
        Controller controller = new Controller(view);

        controller.play();
    }
}
