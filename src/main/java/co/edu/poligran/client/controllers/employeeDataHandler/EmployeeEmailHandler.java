package co.edu.poligran.client.controllers.employeeDataHandler;

import co.edu.poligran.domain.Employee;

public class EmployeeEmailHandler extends EmployeeDataHandler {
    private boolean isPrintedMessage = false;

    @Override
    public void printQuestion() {
        System.out.println("> Ingrese el correo electrónico:");
        isPrintedMessage = true;
    }

    @Override
    public boolean handle(Employee employee, String message) {
        if(!isPrintedMessage) {
            this.printQuestion();
            return false;
        }

        if (employee.getEmail() == null) {
            employee.setEmail(message);

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
