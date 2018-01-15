package com.hornsnhuffs.schedule.modules.order;

import com.hornsnhuffs.schedule.core.BaseCrudController;
import com.hornsnhuffs.schedule.web.UnableToProcessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController extends BaseCrudController<Order> {

    private final OrderRepository orderRepository;

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderRepository orderRepository, OrderService orderService) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    @Override
    protected CrudRepository<Order, Integer> getRepository() {
        return orderRepository;
    }

    @RequestMapping("/incomplete")
    public List<Order> listOrdersInProgress() {
        return orderRepository.findByCompleteIsFalse();
    }

    @Override
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Order create(@RequestBody Order order) throws UnableToProcessException {
        return orderService.createAndAssign(order);
    }

}
