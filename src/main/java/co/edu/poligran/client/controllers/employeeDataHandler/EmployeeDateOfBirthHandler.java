package co.edu.poligran.client.controllers.employeeDataHandler;

import co.edu.poligran.domain.Employee;

public class EmployeeDateOfBirthHandler extends EmployeeDataHandler {
    private boolean isPrintedMessage = false;

    @Override
    public void printQuestion() {
        System.out.println("> Ingrese la fecha de nacimiento (aaaa-mm-dd):");
        isPrintedMessage = true;
    }

    @Override
    public boolean handle(Employee employee, String message) {
        if(!isPrintedMessage) {
            this.printQuestion();
            return false;
        }

        if (employee.getDateOfBirth() == null) {
            employee.setDateOfBirth(message);

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
