package com.hornsnhuffs.schedule;

import com.hornsnhuffs.schedule.modules.department.Department;
import com.hornsnhuffs.schedule.modules.department.DepartmentController;
import com.hornsnhuffs.schedule.modules.employee.EmployeeController;
import com.hornsnhuffs.schedule.modules.furniture.FurnitureController;
import com.hornsnhuffs.schedule.modules.order.OrderController;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScheduleApplicationTests {

	@Autowired
	private DepartmentController departmentController;

	@Autowired
	private EmployeeController employeeController;

	@Autowired
	private FurnitureController furnitureController;

	@Autowired
	private OrderController orderController;

	private TestRestTemplate restTemplate;

	@Before
	public void before() {
		restTemplate = new TestRestTemplate();
	}

	@Test
	public void contextLoads() {
		assertThat(departmentController).isNotNull();
		assertThat(employeeController).isNotNull();
		assertThat(furnitureController).isNotNull();
		assertThat(orderController).isNotNull();
	}

	@Test
	public void shouldCreateDepartment() throws Exception {
		assertThat(restTemplate.getForObject("http://localhost:8080/department", Object[].class)).isEmpty();
		Department department = new Department();
		department.setName("Департамент");
		restTemplate.put("http://localhost:8080", department);

	}

}
