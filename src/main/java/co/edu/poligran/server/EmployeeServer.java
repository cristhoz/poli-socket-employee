package co.edu.poligran.server;

import co.edu.poligran.server.clients.DatabaseClient;
import co.edu.poligran.server.repositories.EmployeeRepository;
import co.edu.poligran.server.services.CreateEmployeeService;
import co.edu.poligran.server.services.ProcessMessageService;
import co.edu.poligran.server.services.SearchEmployeeService;
import co.edu.poligran.server.services.UpdateEmployeeService;

import java.io.IOException;

public class EmployeeServer {
    public static void main(String[] args) throws IOException {
        DatabaseClient databaseClient = new DatabaseClient();
        EmployeeRepository employeeRepository = new EmployeeRepository(databaseClient.getConnection());
        CreateEmployeeService createEmployeeService = new CreateEmployeeService(employeeRepository);
        SearchEmployeeService searchEmployeeService = new SearchEmployeeService(employeeRepository);
        UpdateEmployeeService updateEmployeeService = new UpdateEmployeeService(employeeRepository);
        ProcessMessageService processMessageService = new ProcessMessageService(
                createEmployeeService,
                searchEmployeeService,
                updateEmployeeService
        );

        SocketServer server = new SocketServer();
        server.start(8080, processMessageService::process);
    }
}
