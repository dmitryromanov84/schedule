package com.hornsnhuffs.schedule.modules.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hornsnhuffs.schedule.core.BaseEntity;
import com.hornsnhuffs.schedule.modules.department.Department;
import com.hornsnhuffs.schedule.modules.employee.Employee;
import com.hornsnhuffs.schedule.modules.furniture.Furniture;

import javax.persistence.*;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

/**
 * Заказ
 */
@Entity
@Table(name = "customer_order")
@NamedQueries({
        @NamedQuery(name = Order.QUERY_BY_DEPARTMENT, query = "from Order o inner join fetch o.furniture where o.department.id = :departmentId"),
        @NamedQuery(name = Order.QUERY_BY_EMPLOYEE, query = "from Order o inner join fetch o.furniture where o.assignee.id = :employeeId"),
        @NamedQuery(name = Order.QUERY_INCOMPLETE_BY_EMPLOYEE, query = "from Order o inner join fetch o.furniture where o.assignee.id = :employeeId and o.complete is false")
})
public class Order extends BaseEntity {

    public static final String QUERY_BY_DEPARTMENT = "orders.byDepartment";
    public static final String QUERY_BY_EMPLOYEE = "orders.byEmployee";
    public static final String QUERY_INCOMPLETE_BY_EMPLOYEE = "orders.incompleteByEmployee";

    /**
     * Время регистрации заказа
     */
    private Calendar registerDate = Calendar.getInstance();

    /**
     * Заказ завершен
     */
    private boolean complete;

    /**
     * Срок исполнения заказа
     */
    private Calendar deadline;

    /**
     * Тип заказанной мебели, определяет отдел, в котором будет выполняться заказ
     */
    @ManyToOne(targetEntity = Furniture.class)
    private Furniture furniture;

    /**
     * Назначенный отдел
     */
    @ManyToOne(targetEntity = Department.class)
    private Department department;

    /**
     * Назначенный сотрудник
     */
    @ManyToOne(targetEntity = Employee.class)
    private Employee assignee;

    @Override
    public String toString() {
        return "Order{" +
                "registerDate=" + registerDate +
                ", complete=" + complete +
                ", deadline=" + deadline +
                ", furniture=" + furniture +
                ", department=" + department +
                ", assignee=" + assignee +
                '}';
    }

    public void assign(Employee employee) {
        assignee = employee;
        department = employee.getDepartment();
    }

    /**
     * @return количество календарных дней до выполнения заказа
     */
    @JsonProperty
    public Long daysToDeadline() {
        return ChronoUnit.DAYS.between(Calendar.getInstance().toInstant(), deadline.toInstant());
    }

    /**
     * @return количество часов до выполнения заказа
     */
    @JsonProperty
    public Long hoursToDeadline() {
        return ChronoUnit.HOURS.between(Calendar.getInstance().toInstant(), deadline.toInstant()) % 24;
    }

    @JsonIgnore
    public Integer getFurnitureId() {
        return furniture != null ? furniture.getId() : null;
    }

    @JsonIgnore
    public String getFurnitureName() {
        return furniture != null ? furniture.getName() : null;
    }

    @JsonIgnore
    public boolean isAssigned() {
        return assignee != null && department != null;
    }

    @JsonIgnore
    public boolean isIncomplete() {
        return !complete;
    }

    public Calendar getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Calendar registerDate) {
        this.registerDate = registerDate;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public Calendar getDeadline() {
        return deadline;
    }

    public void setDeadline(Calendar deadline) {
        this.deadline = deadline;
    }

    public Furniture getFurniture() {
        return furniture;
    }

    public void setFurniture(Furniture furniture) {
        this.furniture = furniture;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Employee getAssignee() {
        return assignee;
    }

    public void setAssignee(Employee assignee) {
        this.assignee = assignee;
    }
}
