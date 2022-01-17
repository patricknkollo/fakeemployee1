package com.fakeEmp.fake.employee;

import com.fakeEmp.fake.employee.entities.Employee;
import com.fakeEmp.fake.employee.repositories.EmployeeRepository;
import com.fakeEmp.fake.employee.services.EmployeeService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@SpringBootTest(classes={EmployeeServiceTest.class}) //pour preciser à spring que cette classe est une classe de test
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RunWith(MockitoJUnitRunner.class) // sans cette annotation  les differents Mocks que je crée reste null. Spring ne sait pas comment
//les instancier implicitement de maniere à ce que lors du lancement
// du test en mode debugging les differents objets annotés @Mock, @MockBean, @injectMocks soient non null.
public class EmployeeServiceTest {

  @Mock
  EmployeeRepository employeeRepository;

  @InjectMocks
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

//GET    R
  @Test
  public void getAllEmployeesListTest(){
    List<Employee> employees = new ArrayList<>();
    employees.add(employee0);
    employees.add(employee1);
    employees.add(employee2);
    employees.add(employee3);

    Mockito.when(employeeRepository.findAll()).thenReturn(employees);
    Assert.assertEquals(4,employeeService.getAllEmployeesList().size());
    Assert.assertEquals(employees, employeeService.getAllEmployeesList());
  }
//GET    R
  @Test
  public void getAllEmployeesTest(){
    List<Employee> employees = new ArrayList<>();
    employees.add(employee0);
    employees.add(employee1);
    employees.add(employee2);
    employees.add(employee3);
    ResponseEntity<List<Employee>> responseEntity = new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);

    Mockito.when(employeeRepository.findAll()).thenReturn(employees);
    Assert.assertEquals(HttpStatus.OK,employeeService.getAllEmployees().getStatusCode());
    Assert.assertEquals(responseEntity, employeeService.getAllEmployees());
  }
//GET   R
  @Test
  public void getAllEmployeesWithIdTest(){
    Optional<Employee> employee = Optional.of(employee1);
    Long employeeID = 1L;
    ResponseEntity<Optional<Employee>> responseEntity = new ResponseEntity<Optional<Employee>>(employee, HttpStatus.OK);

    Mockito.when(employeeRepository.findById(employeeID)).thenReturn(employee);
    Assert.assertEquals(HttpStatus.OK,employeeService.getAllEmployeesWithId(employeeID).getStatusCode());
    Assert.assertEquals(responseEntity, employeeService.getAllEmployeesWithId(employeeID));
  }

  //POST Create
  @Test
  public void enregistreTest(){
    ResponseEntity<Employee> responseEntity = new ResponseEntity<Employee>(employee1, HttpStatus.OK);

    Mockito.when(employeeRepository.save(employee0)).thenReturn(employee1);
    Assert.assertEquals(responseEntity,employeeService.enregistre(employee0));
  }
//DELETE D
  @Test
  public void deleteEmployeeTest(){
    employeeService.deleteEmployee(employee1);
    Mockito.verify(employeeRepository).delete(employee1);
  }
//UPDATE U
  @Test
  public void updateEmployeeTest(){
    Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee1));
    Mockito.when(employeeRepository.save(employee1)).thenReturn(employee1);

    Assert.assertEquals(Optional.of(employee1), employeeService.updateEmployee(employee0, 1L));
    Assert.assertEquals(Optional.of(1L), Optional.ofNullable((employeeService.updateEmployee(employee0, 1L)).get().getId()));
    Assert.assertEquals(employee0.getName(), employeeService.updateEmployee(employee0, 1L).get().getName());
    Assert.assertEquals(employee0.getFirstname(), employeeService.updateEmployee(employee0, 1L).get().getFirstname());
    Assert.assertEquals(employee0.getEmail(), employeeService.updateEmployee(employee0, 1L).get().getEmail());
    Assert.assertEquals(employee0.getPassword(), employeeService.updateEmployee(employee0, 1L).get().getPassword());

//    repository.findById(id);                      @Optional.of(employee1)
//        repository.save(employee);                    @employee1
//        updateEmployee( employee,  id);   @Optional.of(empty())/employee1
//
//    assertEquals(employee.getName(), updateEmployeeemployee, id).getName())
  }
}
