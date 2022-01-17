package com.fakeEmp.fake.employee;

import com.fakeEmp.fake.employee.entities.CorpsWork;
import com.fakeEmp.fake.employee.entities.Employee;
import com.fakeEmp.fake.employee.repositories.CorpsWorkRepository;
import com.fakeEmp.fake.employee.services.CorpsWorkService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = {CorpsWorkServiceTest.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CorpsWorkServiceTest {
  @InjectMocks
  CorpsWorkService service;
  @Mock
  CorpsWorkRepository repository;

  @Mock
  CorpsWork corpsWorkMock1;
  @Mock
  CorpsWork corpsWorkMock2;
  @Mock
  CorpsWork corpsWorkMock3;
  @Mock
  CorpsWork corpsWorkMock4;
  @Mock
  CorpsWork corpsWorkMock5;

  @Mock
  Employee employeeMock1;
  @Mock
  Employee employeeMock2;
  @Mock
  Employee employeeMock3;

  CorpsWork corpsWork1 = new CorpsWork(1L, "corpswork1");
  CorpsWork corpsWork2 = new CorpsWork(2L, "corpswork2");
  CorpsWork corpsWork3 = new CorpsWork(3L, "corpswork3");

  Employee employee1 = new Employee(1L, "patrick", "nkollo", "nkollopatrick@yahoo.fr", "pw237", corpsWork1);
  Employee employee2 = new Employee(2L, "alexandre", "djangue", "adjangue@yahoo.fr", "sl237", corpsWork1);
  Employee employee3 = new Employee(3L, "samuel", "lembe", "slembe@yahoo.fr", "jk237", corpsWork1);

  @Before
  public void init_all(){
    List<Employee> employees1 = List.of(employee1,employee2,employee3);
    corpsWork1.setEmployees(employees1);
    List<Employee> employees2 = List.of(employee2,employee1,employee3);
    corpsWork2.setEmployees(employees2);
    List<Employee> employees3 = List.of(employee3,employee2,employee1);
    corpsWork3.setEmployees(employees3);
  }
  @Test
  @Order(1)
  public void test_saveCorps(){
    List<Employee> employees = List.of(employee1,employee2,employee3);
    corpsWork2.getEmployees().addAll(employees);
    ResponseEntity<CorpsWork> responseEntityExpected = new ResponseEntity<>(corpsWork2, HttpStatus.OK);
    Mockito.when(repository.save(corpsWork1)).thenReturn(corpsWork2);
    Assert.assertEquals(responseEntityExpected,service.saveCorps(corpsWork1));
    Assert.assertEquals("corpswork2",service.saveCorps(corpsWork1).getBody().getName());
    Assert.assertEquals("lembe", service.saveCorps(corpsWork1).getBody().getEmployees().get(2).getName());
  }
  @Test
  @Order(2)
  public void test_getAllCorpsWork(){
    List<CorpsWork> corpsWorks = List.of(corpsWork1,corpsWork2,corpsWork3);
    ResponseEntity<List<CorpsWork>> responseEntityExpected = new ResponseEntity<>(corpsWorks, HttpStatus.OK);
    Mockito.when(repository.findAll()).thenReturn(corpsWorks);
    Assert.assertEquals("corpswork3",service.getAllCorpsWork().getBody().get(2).getName());
    Assert.assertEquals(Long.valueOf(2L),service.getAllCorpsWork().getBody().get(1).getId());
  }
  @Test
  @Order(3)
  public void test_getCorpsWorkWithId(){
    Long corpsId = 1L;
    ResponseEntity<Optional<CorpsWork>> responseEntityExpected = new ResponseEntity<>(Optional.of(corpsWork2), HttpStatus.OK);
    Mockito.when(repository.findById(corpsId)).thenReturn(Optional.of(corpsWork2));
    Assert.assertEquals(responseEntityExpected,service.getCorpsWorkWithId(corpsId));
  }
  @Test
  @Order(4)
  public void test_getCorpsWorkWithName(){
    String corpsName = "name";
    ResponseEntity<List<CorpsWork>> responseEntityExpected = new ResponseEntity<>(List.of(corpsWork2), HttpStatus.OK);
    Mockito.when(repository.findByName(corpsName)).thenReturn(List.of(corpsWork2));
    Assert.assertEquals(responseEntityExpected,service.getCorpsWorkWithName(corpsName));
  }
  @Test
  @Order(5)
  public void test_deleteCorpsWithName(){
    String corpsName = "name";
    service.deleteCorpsWithName(corpsName);
    Mockito.verify(repository).deleteByName(corpsName);

  }
  @Test
  @Order(6)
  public void test_UpdateCorpsWithName(){
    Long corpsId =1L;
    ResponseEntity<CorpsWork> responseEntityExpected = new ResponseEntity<>(corpsWork2, HttpStatus.OK);

    Mockito.when(repository.findById(corpsId)).thenReturn(Optional.of(corpsWork2));
    Mockito.when(repository.save(corpsWork2)).thenReturn(corpsWork2);
    Assert.assertEquals(responseEntityExpected, service.UpdateCorpsWithName(corpsWork1, corpsId));
    Assert.assertEquals(corpsWork1.getEmployees(), service.UpdateCorpsWithName(corpsWork1, corpsId).getBody().getEmployees());

  }
  @Test
  @Order(7)
  public void test_getEmployeeOfParticularCorpsAndFirstname(){
    List<CorpsWork>corpsWorks = List.of(corpsWorkMock1,corpsWorkMock2,corpsWorkMock3, corpsWorkMock4, corpsWorkMock5);
    Mockito.when(repository.findAll()).thenReturn(corpsWorks);
    Mockito.when(corpsWorkMock1.getName()).thenReturn("corps de police");
    Mockito.when(corpsWorkMock2.getName()).thenReturn("corps medical");
    Mockito.when(corpsWorkMock3.getName()).thenReturn("corps armee");
    Mockito.when(corpsWorkMock4.getName()).thenReturn("corps magistrat");
    Mockito.when(corpsWorkMock5.getName()).thenReturn("corps enseignant");
    Mockito.when(corpsWorkMock1.getEmployees()).thenReturn(List.of(employeeMock1,employeeMock2,employeeMock3));
    Mockito.when(corpsWorkMock2.getEmployees()).thenReturn(List.of(employeeMock2,employeeMock1,employeeMock3));
    Mockito.when(corpsWorkMock5.getEmployees()).thenReturn(List.of(employeeMock3,employeeMock2,employeeMock1));
    Mockito.when(employeeMock1.getFirstname()).thenReturn("patrick");
    Mockito.when(employeeMock2.getFirstname()).thenReturn("nadege");
    Mockito.when(employeeMock3.getFirstname()).thenReturn("patrick");
    Mockito.when(employeeMock2.getFirstname()).thenReturn("nadege");
    Mockito.when(employeeMock1.getFirstname()).thenReturn("patrick");
    Mockito.when(employeeMock3.getFirstname()).thenReturn("patrick");
    Mockito.when(employeeMock3.getFirstname()).thenReturn("patrick");
    Mockito.when(employeeMock2.getFirstname()).thenReturn("nadege");
    Mockito.when(employeeMock1.getFirstname()).thenReturn("patrick");

    List<Employee> result = service.getEmployeeOfParticularCorpsAndFirstname("patrick").getBody();
    List<Employee> expected = List.of(employeeMock1, employeeMock3,employeeMock1, employeeMock3,employeeMock3, employeeMock1);

    Assert.assertEquals(expected, result);

  }
  @Test
  @Order(8)
  public void test_getEmployeeOfParticularCorpsAndName(){
    List<CorpsWork>corpsWorks = List.of(corpsWorkMock1,corpsWorkMock2,corpsWorkMock3, corpsWorkMock4, corpsWorkMock5);
    Mockito.when(repository.findAll()).thenReturn(corpsWorks);
    Mockito.when(corpsWorkMock1.getName()).thenReturn("corps de police");
    Mockito.when(corpsWorkMock2.getName()).thenReturn("corps medical");
    Mockito.when(corpsWorkMock3.getName()).thenReturn("corps armee");
    Mockito.when(corpsWorkMock4.getName()).thenReturn("corps magistrat");
    Mockito.when(corpsWorkMock5.getName()).thenReturn("corps enseignant");
    Mockito.when(corpsWorkMock1.getEmployees()).thenReturn(List.of(employeeMock1,employeeMock2,employeeMock3));
    Mockito.when(corpsWorkMock2.getEmployees()).thenReturn(List.of(employeeMock2,employeeMock1,employeeMock3));
    Mockito.when(corpsWorkMock5.getEmployees()).thenReturn(List.of(employeeMock3,employeeMock2,employeeMock1));
    Mockito.when(employeeMock1.getName()).thenReturn("patrick");
    Mockito.when(employeeMock2.getName()).thenReturn("nadege");
    Mockito.when(employeeMock3.getName()).thenReturn("patrick");
    Mockito.when(employeeMock2.getName()).thenReturn("nadege");
    Mockito.when(employeeMock1.getName()).thenReturn("patrick");
    Mockito.when(employeeMock3.getName()).thenReturn("patrick");
    Mockito.when(employeeMock3.getName()).thenReturn("patrick");
    Mockito.when(employeeMock2.getName()).thenReturn("nadege");
    Mockito.when(employeeMock1.getName()).thenReturn("patrick");

    List<Employee> result = service.getEmployeeOfParticularCorpsAndName("patrick").getBody();
    List<Employee> expected = List.of(employeeMock1, employeeMock3,employeeMock1, employeeMock3,employeeMock3, employeeMock1);

    Assert.assertEquals(expected, result);

  }
}
