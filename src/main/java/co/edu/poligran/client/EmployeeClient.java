package co.edu.poligran.client;

import co.edu.poligran.client.controllers.InteractiveController;

import java.io.IOException;

public class EmployeeClient {
    public static void main(String[] args) throws IOException {
        SocketClient client = new SocketClient();
        client.startConnection("127.0.0.1", 8080);

        InteractiveController interactiveController = new InteractiveController(client);
        interactiveController.run();
    }
}
