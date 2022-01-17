package com.fakeEmp.fake.employee;

import com.fakeEmp.fake.employee.controllers.EmployeeController;
import com.fakeEmp.fake.employee.entities.Employee;
import com.fakeEmp.fake.employee.services.EmployeeService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//L#annotation ici presente permet d'executer les test par ordre...il suffit alors de
//  rajouter au dessus de chaque methode l'annotation suivante dans laquelle on precise l'ordre d'execution
//  @Order(1),@Order(2),@Order(3),..@Order(n),
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

//l'erreur pour laquelle le test ne donnait pas depuis est que j'avais
//ecrit @WebMvcTest(Employee.class) aulieu de @WebMvcTest(EmployeeController.class)
@WebMvcTest(EmployeeController.class)
//@RunWith(SpringRunner.class) a utiliser dans les classes de test pour services et non controllertest
//@ExtendWith(MockitoExtension.class)
//@SpringJUnitConfig
@AutoConfigureMockMvc
//@ComponentScan(basePackages = "com.restservices.demo")
//@SpringBootTest(classes = {EmployeeControllerTest.class})
//@ContextConfiguration
public class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@InjectMocks
	EmployeeController employeeController;
	
	@MockBean
	EmployeeService employeeService;

	Employee employee0 = new Employee(0L,"rosine", "enangue", "rosenang@mail.com", "244fr13rt46e", null);
	Employee employee1 = new Employee(1L,"bill", "gates", "bgates@microsoft.com", "hjngbjtr", null);
	Employee employee2 = new Employee(2L,"mark", "zuckerberg", "mzuker@facebook.com", "hhdligtlow", null);
	Employee employee3 = new Employee(3L,"larry", "pages", "lpages@google.com", "phrgtrfr", null);
	Employee employee4 = new Employee(4L,"larry", "ellison", "lell@oracle.com", "oifgkjre", null);
	Employee employee5 = new Employee(5L,"elon", "musk", "emusk@tesla.com", "qgjhtrgeh", null);
	Employee employee6 = new Employee(6L,"sergey", "brin", "sbrin@google.com", "hoerqghioght", null);
	Employee employee7 = new Employee(7L,"francoise", "bettencourt", "fbett@loreal.com", "püipogfjhzr", null);
	Employee employee8 = new Employee(8L,"bernard", "arnault", "barnault@lvmh.com", "ikojgtzruiofpo", null);


	@Test
	@Order(1)
	public void getAllEmployeeslistTest() throws Exception {
		
		List <Employee> listEmployee = List.of(employee1, employee2, employee3, employee4);
		when(employeeService.getAllEmployeesList()).thenReturn(listEmployee);

		mockMvc.perform(MockMvcRequestBuilders
		  .get("/api/controller/employee/employeeslist"))
		  .andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(4)))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[0].firstname", Matchers.is("bill") ))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[1].firstname", Matchers.is("mark") ))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[2].firstname", Matchers.is("larry") ))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[3].firstname", Matchers.is("larry") ))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[0].name", Matchers.is("gates") ))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[1].name", Matchers.is("zuckerberg") ))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[2].name", Matchers.is("pages") ))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[3].name", Matchers.is("ellison") ));
	}

	@Test
	@Order(2)
	public void getAllEmployeesTest() throws Exception {

		List <Employee> listEmployee = List.of(employee1, employee2, employee3, employee4);
		ResponseEntity <List<Employee>> responseEntity = new ResponseEntity<List<Employee>>(listEmployee, HttpStatus.OK);
		when(employeeService.getAllEmployees()).thenReturn(responseEntity);

		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/controller/employee/employees"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(4)))
  			.andExpect(MockMvcResultMatchers.jsonPath("$.[0].firstname", Matchers.is("bill") ))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[1].firstname", Matchers.is("mark") ))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[2].firstname", Matchers.is("larry") ))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].name", Matchers.is("gates") ))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[1].name", Matchers.is("zuckerberg") ))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[2].name", Matchers.is("pages") ));
	}

	@Test
	@Order(3)
	public void saveEmployeeTest() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders
				.post("/api/controller/employee/save")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\": \"10\",\"firstname\": \"rosine\",\"name\": \"enangue\",\"email\": \"rosenang@mail.com\", \"password\":\"244fr13rt46e@\"}"))
        .andExpect(MockMvcResultMatchers.status().isOk())
				//.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("enangue"))
		    .andReturn();
		//verify(employeeService).enregistre(employee0);
		verify(employeeService).enregistre(any(Employee.class));
	}
}
