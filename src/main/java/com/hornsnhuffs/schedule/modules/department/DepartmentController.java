package com.hornsnhuffs.schedule.modules.department;

import com.hornsnhuffs.schedule.core.BaseCrudController;
import com.hornsnhuffs.schedule.modules.order.Order;
import com.hornsnhuffs.schedule.modules.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController extends BaseCrudController<Department> {

    private final DepartmentRepository departmentRepository;

    private final OrderService orderService;

    @Autowired
    public DepartmentController(DepartmentRepository departmentRepository, OrderService orderService) {
        this.departmentRepository = departmentRepository;
        this.orderService = orderService;
    }

    @Override
    protected CrudRepository<Department, Integer> getRepository() {
        return departmentRepository;
    }

    @RequestMapping(value = "/{departmentId}/orders")
    public List<Order> listOrders(@PathVariable Integer departmentId) {
        return orderService.findByDepartmentId(departmentId);
    }

}
