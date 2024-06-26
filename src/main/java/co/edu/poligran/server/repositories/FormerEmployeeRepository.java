package co.edu.poligran.server.repositories;

import co.edu.poligran.domain.FormerEmployee;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class FormerEmployeeRepository {
    private final Connection connection;

    public FormerEmployeeRepository(Connection connection) {
        this.connection = connection;
    }

    public void save(FormerEmployee former) {
        String query = """
            INSERT INTO former_employees (employee_code, name, surname, email, date_of_birth, job_title, salary, date_retired, created_at, updated_at)
            VALUES (?, ?, ?, ?, ?, ?, ?, now(), now(), now())
        """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, former.getEmployeeCode());
            statement.setString(2, former.getName());
            statement.setString(3, former.getSurname());
            statement.setString(4, former.getEmail());
            statement.setString(5, former.getDateOfBirth());
            statement.setString(6, former.getJobTitle());
            statement.setInt(7, former.getSalary());
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error saving employee: " + e.getMessage());
        }
    }
}
