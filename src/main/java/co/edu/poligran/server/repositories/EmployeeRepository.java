package co.edu.poligran.server.repositories;

import co.edu.poligran.domain.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class EmployeeRepository {
    private final Connection connection;

    public EmployeeRepository(Connection connection) {
        this.connection = connection;
    }

    public void save(Employee employee) {
        String query = """
            INSERT INTO employees (name, surname, email, date_of_birth, job_title, salary, created_at, updated_at)
            VALUES (?, ?, ?, ?, ?, ?, now(), now())
        """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getSurname());
            statement.setString(3, employee.getEmail());
            statement.setString(4, employee.getDateOfBirth());
            statement.setString(5, employee.getJobTitle());
            statement.setInt(6, employee.getSalary());
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

    public Employee findById(int id) {
        String query = """
            SELECT * FROM employees
            WHERE id = ?
        """;

        Employee employee = null;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                employee = Employee.builder()
                        .id(result.getInt("id"))
                        .name(result.getString("name"))
                        .surname(result.getString("surname"))
                        .email(result.getString("email"))
                        .dateOfBirth(result.getString("date_of_birth"))
                        .salary(result.getInt("salary"))
                        .jobTitle(result.getString("job_title"))
                        .createdAt(result.getString("created_at"))
                        .updatedAt(result.getString("created_at"))
                        .build();
            }
        } catch (Exception e) {
            System.out.println("Error getting employee: " + e.getMessage());
        }

        return employee;
    }

    public void update(Employee employee) {
        String query = """
            UPDATE employees
            SET job_title = ?, salary = ?, updated_at = now()
            WHERE id = ?
        """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, employee.getJobTitle());
            statement.setInt(2, employee.getSalary());
            statement.setInt(3, employee.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error updating employee: " + e.getMessage());
        }
    }

    public void delete(int id) {
        String query = """
            DELETE FROM employees
            WHERE id = ?
        """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error deleting employee: " + e.getMessage());
        }
    }
}
