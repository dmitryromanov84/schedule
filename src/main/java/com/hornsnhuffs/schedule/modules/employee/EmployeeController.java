package com.hornsnhuffs.schedule.modules.employee;

import com.hornsnhuffs.schedule.core.BaseCrudController;
import com.hornsnhuffs.schedule.modules.order.Order;
import com.hornsnhuffs.schedule.modules.order.OrderService;
import com.hornsnhuffs.schedule.web.UnableToProcessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController extends BaseCrudController<Employee> {

    private final EmployeeRepository employeeRepository;

    private final EmployeeService employeeService;

    private final OrderService orderService;

    public EmployeeController(EmployeeRepository employeeRepository, EmployeeService employeeService, OrderService orderService) {
        this.employeeRepository = employeeRepository;
        this.employeeService = employeeService;
        this.orderService = orderService;
    }

    @Override
    protected CrudRepository<Employee, Integer> getRepository() {
        return employeeRepository;
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void remove(@PathVariable Integer id) throws UnableToProcessException {
        employeeService.deleteEmployee(id);
    }

    @RequestMapping("/{employeeId}/orders")
    public List<Order> listOrders(@PathVariable Integer employeeId) {
        return orderService.findByEmployeeId(employeeId);
    }
}
