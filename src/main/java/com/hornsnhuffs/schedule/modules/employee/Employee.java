package com.hornsnhuffs.schedule.modules.employee;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hornsnhuffs.schedule.core.BaseEntity;
import com.hornsnhuffs.schedule.modules.department.Department;
import com.hornsnhuffs.schedule.modules.order.Order;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Сотрудник
 */
@NamedQueries({
        @NamedQuery(name = Employee.QUERY_BY_DEPARTMENT, query = "select e from Employee e where e.department.id=:departmentId"),
        @NamedQuery(name = Employee.QUERY_OTHERS_BY_DEPARTMENT, query = "select e from Employee e where e.department.id=:departmentId and e.id<>:employeeId")/*,
        @NamedQuery(name = Employee.QUERY_WITHOUT_ORDERS, query = "select e from Employee e where e.department.id=:departmentId " +
                "and e.id not in (select o.assignee.id from Order o where o.assignee.department.id=:departmentId and o.complete is false)"),
        @NamedQuery(name = Employee.QUERY_ORDER_BY_ORDERS_COUNT, query = "select o.assignee from Order o where o.assignee.department.id=:departmentId and o.complete is false " +
                "group by o.assignee.id order by count(o)")*/
})
@Entity
public class Employee extends BaseEntity {

    public static final String QUERY_BY_DEPARTMENT = "employee.byDepartment";
    public static final String QUERY_OTHERS_BY_DEPARTMENT = "employee.othersByDepartment";
/*
    public static final String QUERY_WITHOUT_ORDERS = "employee.withoutOrders";
    public static final String QUERY_ORDER_BY_ORDERS_COUNT = "employee.orderByOrdersCount";
*/

    private String name;

    @ManyToOne(targetEntity = Department.class)
    private Department department;

    @JsonIgnore
    @OneToMany(targetEntity = Order.class, mappedBy = "assignee", cascade = CascadeType.REMOVE)
    private Set<Order> orders = new HashSet<>();

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", department=" + department +
                '}';
    }

    @JsonIgnore
    public Integer getDepartmentId() {
        return department != null ? department.getId() : null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
