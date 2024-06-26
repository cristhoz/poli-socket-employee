package co.edu.poligran.server.services;

import co.edu.poligran.domain.Employee;
import co.edu.poligran.domain.FormerEmployee;
import co.edu.poligran.server.repositories.EmployeeRepository;
import co.edu.poligran.server.repositories.FormerEmployeeRepository;

public class DeleteEmployeeService {
    private final EmployeeRepository employeeRepository;

    private final FormerEmployeeRepository formerEmployeeRepository;

    public DeleteEmployeeService(
            EmployeeRepository employeeRepository,
            FormerEmployeeRepository formerEmployeeRepository
    ) {
        this.employeeRepository = employeeRepository;
        this.formerEmployeeRepository = formerEmployeeRepository;
    }

    public String delete(int idEmployee) {
        Employee employee = employeeRepository.findById(idEmployee);
        FormerEmployee formerEmployee = FormerEmployee.builder()
                .employeeCode(employee.getId())
                .name(employee.getName())
                .surname(employee.getSurname())
                .email(employee.getEmail())
                .dateOfBirth(employee.getDateOfBirth())
                .salary(employee.getSalary())
                .jobTitle(employee.getJobTitle())
                .createdAt(employee.getCreatedAt())
                .updatedAt(employee.getUpdatedAt())
                .build();

        formerEmployeeRepository.save(formerEmployee);
        employeeRepository.delete(idEmployee);

        // TODO: Implement a better response
        return "employee_deleted";
    }
}
