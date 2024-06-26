package co.edu.poligran.server.services;

import co.edu.poligran.domain.Employee;
import co.edu.poligran.server.repositories.EmployeeRepository;

public class UpdateEmployeeService {
    private final EmployeeRepository employeeRepository;

    public UpdateEmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public String update(Employee employee) {
        employeeRepository.update(employee);

        // TODO: Implement a better response
        return "employee_updated";
    }
}
