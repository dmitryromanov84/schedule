package com.hornsnhuffs.schedule.modules.employee;

import com.hornsnhuffs.schedule.modules.order.Order;
import com.hornsnhuffs.schedule.modules.order.OrderRepository;
import com.hornsnhuffs.schedule.web.UnableToProcessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class EmployeeService {

    @PersistenceContext
    private EntityManager entityManager;

    private final EmployeeRepository employeeRepository;

    private final OrderRepository orderRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, OrderRepository orderRepository) {
        this.employeeRepository = employeeRepository;
        this.orderRepository = orderRepository;
    }

    public Employee updateEmployee(Employee employee) throws UnableToProcessException {
        Employee savedEmployee = employeeRepository.findOne(employee.getId());
        if (!savedEmployee.getDepartmentId().equals(employee.getDepartmentId())) {
            releaseEmployee(savedEmployee);
        }
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Integer employeeId) throws UnableToProcessException {
        Employee employee = employeeRepository.findOne(employeeId);
        releaseEmployee(employee);
        employeeRepository.delete(employee);
    }

    private void releaseEmployee(Employee employee) throws UnableToProcessException {
        List<Employee> assigneeCandidates = entityManager.createNamedQuery(Employee.QUERY_OTHERS_BY_DEPARTMENT, Employee.class)
                .setParameter("departmentId", employee.getDepartmentId())
                .setParameter("employeeId", employee.getId())
                .getResultList();
        if (assigneeCandidates.isEmpty()) {
            throw new UnableToProcessException(String.format("Cannot update: %s is the last employee in department %s", employee, employee.getDepartment()));
        }

        List<Order> incompleteOrders = entityManager.createNamedQuery(Order.QUERY_INCOMPLETE_BY_EMPLOYEE, Order.class)
                .setParameter("employeeId", employee.getId())
                .getResultList();

        int i = 0;
        for (Order order : incompleteOrders) {
            order.assign(assigneeCandidates.get(i));
            i = i < assigneeCandidates.size()-1 ? i+1 : 0;
            orderRepository.save(order);
        }
    }

}
