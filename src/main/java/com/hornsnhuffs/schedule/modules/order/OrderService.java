package com.hornsnhuffs.schedule.modules.order;

import com.hornsnhuffs.schedule.modules.employee.Employee;
import com.hornsnhuffs.schedule.modules.furniture.FurnitureRepository;
import com.hornsnhuffs.schedule.web.UnableToProcessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class OrderService {

    @PersistenceContext
    private EntityManager entityManager;

    private final FurnitureRepository furnitureRepository;

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(FurnitureRepository furnitureRepository, OrderRepository orderRepository) {
        this.furnitureRepository = furnitureRepository;
        this.orderRepository = orderRepository;
    }

    public List<Order> findByDepartmentId(Integer departmentId) {
        return entityManager.createNamedQuery(Order.QUERY_BY_DEPARTMENT, Order.class)
                .setParameter("departmentId", departmentId)
                .getResultList();
    }

    public List<Order> findByEmployeeId(Integer employeeId) {
        return entityManager.createNamedQuery(Order.QUERY_BY_EMPLOYEE, Order.class)
                .setParameter("employeeId", employeeId)
                .getResultList();
    }

    public Order createAndAssign(Order order) throws UnableToProcessException {
        if (!order.isAssigned()) {
            order = assign(order);
        }
        return orderRepository.save(order);
    }

    private Order assign(Order order) throws UnableToProcessException {
        Integer departmentId = furnitureRepository.findOne(order.getFurnitureId()).getManufacturerId();
        try {
            Employee assignee = (Employee) ((Object[]) entityManager.createQuery(
                    "select e, (select count(o) from Order o where o.assignee.id=e.id and o.complete is false) as cnt " +
                            "from Employee e where e.department.id=:departmentId order by cnt"
            )
                    .setParameter("departmentId", departmentId)
                    .setMaxResults(1)
                    .getSingleResult())[0];
            order.assign(assignee);
        } catch (NoResultException e) {
            throw new UnableToProcessException(String.format("Cannot assign order for furniture piece %s", order.getFurnitureName()));
        }
        return order;
    }

}
