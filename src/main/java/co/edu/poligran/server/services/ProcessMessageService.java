package co.edu.poligran.server.services;

import co.edu.poligran.domain.Employee;
import co.edu.poligran.server.dtos.GenericRequest;
import co.edu.poligran.server.dtos.SearchResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProcessMessageService {
    private final ObjectMapper objectMapper;

    private final CreateEmployeeService createEmployeeService;

    private final SearchEmployeeService searchEmployeeService;

    private final UpdateEmployeeService updateEmployeeService;

    private final DeleteEmployeeService deleteEmployeeService;

    public ProcessMessageService(
            CreateEmployeeService createEmployeeService,
            SearchEmployeeService searchEmployeeService,
            UpdateEmployeeService updateEmployeeService,
            DeleteEmployeeService deleteEmployeeService
    ) {
        this.objectMapper = new ObjectMapper();
        this.createEmployeeService = createEmployeeService;
        this.searchEmployeeService = searchEmployeeService;
        this.updateEmployeeService = updateEmployeeService;
        this.deleteEmployeeService = deleteEmployeeService;
    }

    public String process(String message) throws JsonProcessingException {
        String response = "Error: Non request processed";
        GenericRequest genericRequest = objectMapper.readValue(message, GenericRequest.class);

        switch (genericRequest.getRequestType()) {
            case "CreateEmployee" -> {
                Employee employee = objectMapper.treeToValue(genericRequest.getData(), Employee.class);

                response = this.createEmployeeService.create(employee);
            }
            case "UpdateEmployee" -> {
                Employee employee = objectMapper.treeToValue(genericRequest.getData(), Employee.class);

                response = this.updateEmployeeService.update(employee);
            }
            case "DeleteEmployee" -> {
                Employee employee = objectMapper.treeToValue(genericRequest.getData(), Employee.class);

                response = this.deleteEmployeeService.delete(employee.getId());
            }
            case "SearchEmployee" -> {
                SearchResponse<Employee> result = this.searchEmployeeService.search();

                response = objectMapper.writeValueAsString(result);
            }
            default -> response = "Error: Unknown request";
        }

        return response;
    }
}
