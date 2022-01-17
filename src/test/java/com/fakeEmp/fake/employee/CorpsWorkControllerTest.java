package com.fakeEmp.fake.employee;

import com.fakeEmp.fake.employee.controllers.CorpsWorkController;
import com.fakeEmp.fake.employee.entities.CorpsWork;
import com.fakeEmp.fake.employee.entities.Employee;
import com.fakeEmp.fake.employee.services.CorpsWorkService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WebMvcTest(CorpsWorkController.class)
@AutoConfigureMockMvc
public class CorpsWorkControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @InjectMocks
  CorpsWorkController corpsWorkController;
  @MockBean
  CorpsWorkService service;

  CorpsWork corpsWork1 = new CorpsWork(1L, "corpswork1");
  CorpsWork corpsWork2 = new CorpsWork(2L, "corpswork2");
  CorpsWork corpsWork3 = new CorpsWork(3L, "corpswork3");
  CorpsWork corpsWork4 = new CorpsWork(3L, "corpswork1");

  Employee employee1 = new Employee(1L, "patrick", "nkollo", "nkollopatrick@yahoo.fr", "pw237", corpsWork1);
  Employee employee2 = new Employee(2L, "alexandre", "djangue", "adjangue@yahoo.fr", "sl237", corpsWork1);
  Employee employee3 = new Employee(3L, "samuel", "lembe", "slembe@yahoo.fr", "jk237", corpsWork1);

  @Test
  @Order(1)
  public void test_saveCorpsByController () throws Exception {
    ResponseEntity<CorpsWork> responseEntityExpected = new ResponseEntity<>(corpsWork1, HttpStatus.OK);
    Mockito.when(service.saveCorps(corpsWork1)).thenReturn(responseEntityExpected);
    this.mockMvc.perform(MockMvcRequestBuilders.post("/api/corps/save")
        .content("{\n"
            + "\t\t\"id\": 1,\n"
            + "\t\t\"name\": \"corps de police\"\n"
            + "\t}")
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();
    Mockito.verify(service).saveCorps(Mockito.any(CorpsWork.class));
  }
  @Test
  @Order(2)
  public void test_getAllCorpsWorkByController () throws Exception {
    List<CorpsWork> corpsWorks = List.of(corpsWork1,corpsWork2,corpsWork3);
    ResponseEntity<List<CorpsWork>> responseEntityExpected = new ResponseEntity<>(corpsWorks, HttpStatus.OK);
    Mockito.when(service.getAllCorpsWork()).thenReturn(responseEntityExpected);

    mockMvc.perform(MockMvcRequestBuilders
        .get("/api/corps/all"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(3)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name", Matchers.is("corpswork1") ))
    ;

  }
  @Test
  @Order(3)
  public void test_getCorpsWorkWithIdByController () throws Exception{
    Long id = 2L;
    List<CorpsWork> corpsWorks = List.of(corpsWork1,corpsWork4);
    ResponseEntity<Optional<CorpsWork>> responseEntityExpected = new ResponseEntity<>(Optional.of(corpsWork3), HttpStatus.OK);
    Mockito.when(service.getCorpsWorkWithId(id)).thenReturn(responseEntityExpected);

    mockMvc.perform(MockMvcRequestBuilders
        .get("/api/corps/id/{thisid}", id))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("corpswork3") ))
    ;
  }
  @Test
  @Order(4)
  public void test_getCorpsWorkWithNameByController()throws Exception{
    String name = "corpswork1";
    List<CorpsWork> corpsWorks = List.of(corpsWork1,corpsWork4);
    ResponseEntity<List<CorpsWork>> responseEntityExpected = new ResponseEntity<>(corpsWorks, HttpStatus.OK);
    Mockito.when(service.getCorpsWorkWithName(name)).thenReturn(responseEntityExpected);

    mockMvc.perform(MockMvcRequestBuilders
        .get("/api/corps/name/{thisname}", name))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(2)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name", Matchers.is("corpswork1") ))
    ;
  }
  @Test
  @Order(1)
  public void test_deleteCorpsWithNameByController () throws Exception{
    String name = "corpswork1";
    List<CorpsWork> corpsWorks = List.of(corpsWork1,corpsWork2,corpsWork3,corpsWork4);
    ResponseEntity<List<CorpsWork>> responseEntityExpected = new ResponseEntity<>(corpsWorks, HttpStatus.OK);
    Mockito.when(service.deleteCorpsWithName(name)).thenReturn(responseEntityExpected);

    mockMvc.perform(MockMvcRequestBuilders
        .delete("/api/corps/delete?name={name}", name))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();
    Mockito.verify(service).deleteCorpsWithName(name)
    ;
  }
  @Test
  @Order(1)
  public void test_UpdateCorpsWithNameByController () throws  Exception{

    ObjectMapper mapper = new ObjectMapper();
    String jsonBody = mapper.writeValueAsString(corpsWork1);

    Long corpsId = 1L;
    ResponseEntity<CorpsWork> responseEntityExpected = new ResponseEntity<>(corpsWork1, HttpStatus.OK);
    Mockito.when(service.UpdateCorpsWithName(corpsWork1, corpsId)).thenReturn(responseEntityExpected);

    mockMvc.perform(MockMvcRequestBuilders.put("/api/corps/update?id={corpsId}", corpsId)
        .content(jsonBody)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andReturn();
  }
}
