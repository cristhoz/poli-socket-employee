package co.edu.poligran.server.services;

import co.edu.poligran.domain.Employee;
import co.edu.poligran.server.dtos.GenericRequest;
import co.edu.poligran.server.dtos.SearchResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ProcessMessageService {
    private final ObjectMapper objectMapper;

    private final CreateEmployeeService createEmployeeService;

    private final SearchEmployeeService searchEmployeeService;

    public ProcessMessageService(
            CreateEmployeeService createEmployeeService,
            SearchEmployeeService searchEmployeeService
    ) {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.createEmployeeService = createEmployeeService;
        this.searchEmployeeService = searchEmployeeService;
    }

    public String process(String message) throws JsonProcessingException {
        String response = "Error: Non request processed";
        GenericRequest genericRequest = objectMapper.readValue(message, GenericRequest.class);

        switch (genericRequest.getRequestType()) {
            case "CreateEmployee" -> {
                Employee employee = objectMapper.treeToValue(genericRequest.getData(), Employee.class);

                response = this.createEmployeeService.create(employee);
            }
            case "UpdateEmployee" -> System.out.println("Update employee request");
            case "DeleteEmployee" -> System.out.println("Delete employee request");
            case "SearchEmployee" -> {
                SearchResponse<Employee> result = this.searchEmployeeService.search();

                response = objectMapper.writeValueAsString(result);
            }
            default -> response = "Error: Unknown request";
        }

        return response;
    }
}
