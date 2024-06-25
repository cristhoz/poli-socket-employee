package co.edu.poligran.server.services;

import co.edu.poligran.domain.Employee;
import co.edu.poligran.server.dtos.SearchResponse;
import co.edu.poligran.server.repositories.EmployeeRepository;


public class SearchEmployeeService {
    private final EmployeeRepository employeeRepository;

    public SearchEmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public SearchResponse<Employee> search() {
        SearchResponse<Employee> result = new SearchResponse<>();
        result.setResult(employeeRepository.findAll());

        return result;
    }
}
