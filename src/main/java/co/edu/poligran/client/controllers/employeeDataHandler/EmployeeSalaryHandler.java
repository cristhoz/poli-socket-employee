package co.edu.poligran.client.controllers.employeeDataHandler;

import co.edu.poligran.domain.Employee;

public class EmployeeSalaryHandler extends EmployeeDataHandler {
    @Override
    public void printQuestion() {
        System.out.println("> Ingrese el salario:");
    }

    @Override
    public boolean handle(Employee employee, String message) {
        if (employee.getSalary() == null) {
            employee.setSalary(Integer.valueOf(message));

            if (next != null) {
                next.printQuestion();
                return false;
            }

            return true;
        }

        if (next == null) {
            return true;
        }

        return next.handle(employee, message);
    }
}
