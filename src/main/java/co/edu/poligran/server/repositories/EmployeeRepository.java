package co.edu.poligran.server.repositories;

import co.edu.poligran.domain.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class EmployeeRepository {
    private final Connection connection;

    public EmployeeRepository(Connection connection) {
        this.connection = connection;
    }

    public void save(Employee employee) {
        String query = """
            INSERT INTO employees (name, surname, email, date_of_birth, salary, created_at, updated_at)
            VALUES (?, ?, ?, ?, ?, now(), now())
        """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getSurname());
            statement.setString(3, employee.getEmail());
            statement.setString(4, employee.getDateOfBirth());
            statement.setInt(5, employee.getSalary());
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error saving employee: " + e.getMessage());
        }
    }
}
