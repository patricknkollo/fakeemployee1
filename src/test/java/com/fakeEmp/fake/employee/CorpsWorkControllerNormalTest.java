package com.fakeEmp.fake.employee;

import com.fakeEmp.fake.employee.controllers.CorpsWorkController;
import com.fakeEmp.fake.employee.entities.CorpsWork;
import com.fakeEmp.fake.employee.entities.Employee;
import com.fakeEmp.fake.employee.services.CorpsWorkService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = {CorpsWorkControllerNormalTest.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CorpsWorkControllerNormalTest {

  @InjectMocks
  CorpsWorkController corpsWorkController;
  @Mock
  CorpsWorkService service;

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
    corpsWork1.setEmployees(employees2);
    List<Employee> employees3 = List.of(employee3,employee2,employee1);
    corpsWork1.setEmployees(employees3);
  }
  @Test
  @Order(1)
  public void test_saveCorpsByController(){
    List<Employee> employees = List.of(employee1,employee2,employee3);
    corpsWork1.setEmployees(employees);
    ResponseEntity<CorpsWork> responseEntityExpected = new ResponseEntity<>(corpsWork1, HttpStatus.OK);
    Mockito.when(service.saveCorps(corpsWork1)).thenReturn(responseEntityExpected);
    Assert.assertEquals(responseEntityExpected,corpsWorkController.saveCorpsByController(corpsWork1));
    Assert.assertEquals("corpswork1",corpsWorkController.saveCorpsByController(corpsWork1).getBody().getName());
    Assert.assertEquals("lembe", corpsWorkController.saveCorpsByController(corpsWork1).getBody().getEmployees().get(2).getName());
  }
  @Test
  @Order(2)
  public void test_getAllCorpsWorkByController(){
    List<CorpsWork> corpsWorks = List.of(corpsWork1,corpsWork2,corpsWork3);
    ResponseEntity<List<CorpsWork>> responseEntityExpected = new ResponseEntity<>(corpsWorks, HttpStatus.OK);
    Mockito.when(service.getAllCorpsWork()).thenReturn(responseEntityExpected);
    Assert.assertEquals("corpswork3",corpsWorkController.getAllCorpsWorkByController().getBody().get(2).getName());
    Assert.assertEquals(Long.valueOf(2L),corpsWorkController.getAllCorpsWorkByController().getBody().get(1).getId());
  }
  @Test
  @Order(3)
  public void test_getCorpsWorkWithIdByController(){
    Long corpsId = 1L;
    ResponseEntity<Optional<CorpsWork>> responseEntityExpected = new ResponseEntity<>(Optional.of(corpsWork2), HttpStatus.OK);
    Mockito.when(service.getCorpsWorkWithId(corpsId)).thenReturn(responseEntityExpected);
    Assert.assertEquals(responseEntityExpected,corpsWorkController.getCorpsWorkWithIdByController(corpsId));
  }
  @Test
  @Order(4)
  public void test_getCorpsWorkWithNameByController(){
    String corpsName = "name";
    ResponseEntity<List<CorpsWork>> responseEntityExpected = new ResponseEntity<>(List.of(corpsWork2), HttpStatus.OK);
    Mockito.when(service.getCorpsWorkWithName(corpsName)).thenReturn(responseEntityExpected);
    Assert.assertEquals(responseEntityExpected,corpsWorkController.getCorpsWorkWithNameByController(corpsName));
  }
  @Test
  @Order(5)
  public void test_deleteCorpsWithNameByController(){
    String corpsName = "name";
    corpsWorkController.deleteCorpsWithNameByController(corpsName);
    Mockito.verify(service).deleteCorpsWithName(corpsName);

  }
  @Test
  @Order(6)
  public void test_UpdateCorpsWithNameByController(){
    Long corpsId =1L;
    ResponseEntity<CorpsWork> responseEntityExpected = new ResponseEntity<>(corpsWork1, HttpStatus.OK);
    Mockito.when(service.UpdateCorpsWithName(corpsWork1, corpsId)).thenReturn(responseEntityExpected);
    Assert.assertEquals(responseEntityExpected,corpsWorkController.UpdateCorpsWithNameByController(corpsWork1, corpsId));
  }

}

