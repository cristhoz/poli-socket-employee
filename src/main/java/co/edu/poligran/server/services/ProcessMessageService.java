package co.edu.poligran.server.services;

import co.edu.poligran.domain.Employee;
import co.edu.poligran.server.dtos.GenericRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProcessMessageService {
    ObjectMapper objectMapper;
    CreateEmployeeService createEmployeeService;

    public ProcessMessageService(
            ObjectMapper objectMapper,
            CreateEmployeeService createEmployeeService
    ) {
        this.objectMapper = objectMapper;
        this.createEmployeeService = createEmployeeService;
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
            case "GetEmployee" -> System.out.println("Get employee request");
            default -> response = "Error: Unknown request";
        }

        return response;
    }
}
