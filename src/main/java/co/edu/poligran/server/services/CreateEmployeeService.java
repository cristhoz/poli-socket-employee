package co.edu.poligran.server.services;

import co.edu.poligran.domain.Employee;
import co.edu.poligran.server.repositories.EmployeeRepository;

public class CreateEmployeeService {
    private final EmployeeRepository employeeRepository;

    public CreateEmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public String create(Employee employee) {
        employeeRepository.save(employee);

        // TODO: Implement a better response
        return "employee_created";
    }
}
