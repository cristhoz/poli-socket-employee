package co.edu.poligran.client.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import co.edu.poligran.client.SocketClient;
import co.edu.poligran.client.controllers.employeeDataHandler.*;
import co.edu.poligran.client.dtos.Request;
import co.edu.poligran.client.dtos.SearchResponse;
import co.edu.poligran.domain.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class InteractiveController {
    private final SocketClient socketClient;

    private final ObjectMapper objectMapper;

    private String firstLevel;

    private String secondLevel;

    private Employee employee;

    private EmployeeDataHandler employeeDataHandler;

    public InteractiveController(SocketClient socketClient) {
        this.socketClient = socketClient;
        this.objectMapper = new ObjectMapper();
    }

    public void run() throws IOException {
        initialMessage();
        listenInput();
    }

    private void listenInput() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String message = scanner.nextLine();

            if (message.equals("0")) {
                socketClient.stopConnection();
                break;
            }

            if (message.equals("9")) {
                firstLevel = null;
                secondLevel = null;
                initialMessage();
                continue;
            }

            if (firstLevel == null) {
                firstLevel = message;
            }

            switch (firstLevel) {
                case "1" -> insertEmployee(message);
                case "2" -> getEmployee(message);
            }
        }
    }

    private void initialMessage() {
        System.out.println("""
                > Ingresa el número de las siguientes opciones:
                    1. Insertar un nuevo empleado.
                    2. Consultar un empleado.

                    0. Salir.
                """);
    }

    private void insertEmployee(String message) throws IOException {
        if (employee == null) {
            employee = Employee.builder().build();
        }

        if (employeeDataHandler == null) {
            employeeDataHandler = EmployeeDataHandler.link(
                    new EmployeeNameHandler(),
                    new EmployeeSurnameHandler(),
                    new EmployeeEmailHandler(),
                    new EmployeeDateOfBirthHandler(),
                    new EmployeeSalaryHandler()
            );
        }

        boolean isFinished = employeeDataHandler.handle(employee, message);

        if (isFinished) {
            Request<Object> request = Request.builder().requestType("CreateEmployee").data(employee).build();
            String result = socketClient.sendMessage(objectMapper.writeValueAsString(request));

            if (!result.equals("employee_created")) {
                System.out.println("Error al crear el empleado");
                socketClient.stopConnection();
                return;
            }

            employee = null;
            employeeDataHandler = null;

            System.out.println("""
                > Empleado creado con éxito.
                > Ingresa el número de las siguientes opciones:
                    1. Crear otro empleado.

                    9. Volver al menú principal.
                    0. Salir.
                """);
        }
    }

    private void getEmployee(String message) throws IOException {
        if (secondLevel == null || secondLevel.equals("1")) {
            Request<Object> request = Request.builder().requestType("SearchEmployee").build();
            String result = socketClient.sendMessage(objectMapper.writeValueAsString(request));
            SearchResponse searchResponse = objectMapper.readValue(result, SearchResponse.class);
            List<Employee> employees = objectMapper.readValue(objectMapper.treeAsTokens(searchResponse.getResult()), new TypeReference<>() {
            });
            System.out.println("> La lista de empleados es el siguiente:");

            employees.forEach(employee -> {
                String data = String.format("""
                            - \033[1m %s %s \033[0m
                                Puesto: %s
                                Código de empleado: %s
                                Correo electrónico: %s
                                Fecha de nacimiento: %s
                                Salario: %s
                        """,
                        employee.getName(),
                        employee.getSurname(),
                        employee.getJobTitle(),
                        employee.getId(),
                        employee.getEmail(),
                        employee.getDateOfBirth(),
                        employee.getSalary()
                );
                System.out.println(data);
            });

            secondLevel = "1";

            System.out.println("""
                > Ingresa el número de las siguientes opciones:
                    1. Listar empleados.
                    2. Editar un empleado.
                    3. Eliminar un empleado.

                    9. Volver al menú principal.
                    0. Salir.
                """);
        }

        // TODO: print employees
        // TODO: print menu
    }

    private void listEmployees() {
        System.out.println("list employees");
    }
}
