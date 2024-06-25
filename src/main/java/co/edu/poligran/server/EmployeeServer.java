package co.edu.poligran.server;

import co.edu.poligran.server.clients.DatabaseClient;
import co.edu.poligran.server.repositories.EmployeeRepository;
import co.edu.poligran.server.services.CreateEmployeeService;
import co.edu.poligran.server.services.ProcessMessageService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class EmployeeServer {
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        DatabaseClient databaseClient = new DatabaseClient();
        EmployeeRepository employeeRepository = new EmployeeRepository(databaseClient.getConnection());
        CreateEmployeeService createEmployeeService = new CreateEmployeeService(employeeRepository);
        ProcessMessageService processMessageService = new ProcessMessageService(objectMapper, createEmployeeService);

        SocketServer server = new SocketServer();
        server.start(8080, processMessageService::process);
    }
}
