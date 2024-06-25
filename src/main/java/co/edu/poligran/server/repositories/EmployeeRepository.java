package co.edu.poligran.server.repositories;

import co.edu.poligran.domain.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EmployeeRepository {
    private final Connection connection;

    private final DateTimeFormatter dateTimeFormatter;

    public EmployeeRepository(Connection connection) {
        this.connection = connection;
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
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

    public ArrayList<Employee> findAll() {
        String query = """
            SELECT * FROM employees
        """;

        ArrayList<Employee> employees = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                employees.add(
                        Employee.builder()
                                .id(result.getInt("id"))
                                .name(result.getString("name"))
                                .surname(result.getString("surname"))
                                .email(result.getString("email"))
                                .dateOfBirth(result.getString("date_of_birth"))
                                .salary(result.getInt("salary"))
                                .jobTitle(result.getString("job_title"))
                                .createdAt(result.getString("created_at"))
                                .updatedAt(result.getString("created_at"))
                                .build()
                );
            }
        } catch (Exception e) {
            System.out.println("Error getting employees: " + e.getMessage());
        }

        return employees;
    }
}
