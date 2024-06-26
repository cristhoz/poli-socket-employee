package co.edu.poligran.server;

import co.edu.poligran.server.clients.DatabaseClient;
import co.edu.poligran.server.repositories.EmployeeRepository;
import co.edu.poligran.server.repositories.FormerEmployeeRepository;
import co.edu.poligran.server.services.*;

import java.io.IOException;

public class EmployeeServer {
    public static void main(String[] args) throws IOException {
        DatabaseClient databaseClient = new DatabaseClient();
        EmployeeRepository employeeRepository = new EmployeeRepository(databaseClient.getConnection());
        FormerEmployeeRepository formerEmployeeRepository = new FormerEmployeeRepository(databaseClient.getConnection());
        CreateEmployeeService createEmployeeService = new CreateEmployeeService(employeeRepository);
        SearchEmployeeService searchEmployeeService = new SearchEmployeeService(employeeRepository);
        UpdateEmployeeService updateEmployeeService = new UpdateEmployeeService(employeeRepository);
        DeleteEmployeeService deleteEmployeeService = new DeleteEmployeeService(employeeRepository, formerEmployeeRepository);
        ProcessMessageService processMessageService = new ProcessMessageService(
                createEmployeeService,
                searchEmployeeService,
                updateEmployeeService,
                deleteEmployeeService
        );

        SocketServer server = new SocketServer();
        server.start(8080, processMessageService::process);
    }
}
