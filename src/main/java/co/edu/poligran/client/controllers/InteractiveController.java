package co.edu.poligran.client.controllers;

import java.io.IOException;
import java.util.Scanner;

import co.edu.poligran.client.SocketClient;
import co.edu.poligran.client.controllers.employeeDataHandler.*;
import co.edu.poligran.client.dtos.Request;
import co.edu.poligran.domain.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;

public class InteractiveController {
    private final SocketClient socketClient;

    private String firstLevel;

    private Employee employee;

    private EmployeeDataHandler employeeDataHandler;

    public InteractiveController(SocketClient socketClient) {
        this.socketClient = socketClient;
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

            if (firstLevel == null) {
                firstLevel = message;
            }
            System.out.println("**** DEBUG **** Message received: " + message + " first level: " + firstLevel);
            switch (firstLevel) {
                case "1" -> insertEmployee(message);
                case "2" -> System.out.println("get employee");
            }

            // System.out.println(socketClient.sendMessage(message));
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
            ObjectMapper objectMapper = new ObjectMapper();
            System.out.println(socketClient.sendMessage(objectMapper.writeValueAsString(request)));
        }
    }
}
