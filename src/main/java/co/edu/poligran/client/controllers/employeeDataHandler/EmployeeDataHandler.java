package co.edu.poligran.client.controllers.employeeDataHandler;

import co.edu.poligran.domain.Employee;

public abstract class EmployeeDataHandler {
    protected EmployeeDataHandler next;

    public static EmployeeDataHandler link(EmployeeDataHandler first, EmployeeDataHandler... handlers) {
        EmployeeDataHandler current = first;
        for (EmployeeDataHandler handler : handlers) {
            current.next = handler;
            current = handler;
        }

        return first;
    }

    public abstract void printQuestion();

    public abstract boolean handle(Employee employee, String message);
}
