package com.fakeEmp.fake.employee;

import com.fakeEmp.fake.employee.entities.CorpsWork;
import com.fakeEmp.fake.employee.entities.Employee;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.net.URI;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {CorpsWorkControllerIntegrationTest.class})
public class CorpsWorkControllerIntegrationTest {

  @Test
  @Order(1)
  public void test_integration_getAllCorpsCorpsByController() throws JSONException {

    String expected = "[\n"
        + "\t{\n"
        + "\t\t\"id\": 1,\n"
        + "\t\t\"name\": \"corps de police\"\n"
        + "\t},\n"
        + "\t{\n"
        + "\t\t\"id\": 2,\n"
        + "\t\t\"name\": \"corps medical\"\n"
        + "\t},\n"
        + "\t{\n"
        + "\t\t\"id\": 3,\n"
        + "\t\t\"name\": \"corps de l armee\"\n"
        + "\t},\n"
        + "\t{\n"
        + "\t\t\"id\": 4,\n"
        + "\t\t\"name\": \"corps de taxi-moto\"\n"
        + "\t},\n"
        + "\t{\n"
        + "\t\t\"id\": 5,\n"
        + "\t\t\"name\": \"corps de taxi\"\n"
        + "\t},\n"
        + "\t{\n"
        + "\t\t\"id\": 6,\n"
        + "\t\t\"name\": \"corps enseignant\"\n"
        + "\t},\n"
        + "\t{\n"
        + "\t\t\"id\": 7,\n"
        + "\t\t\"name\": \"corps des avocats\"\n"
        + "\t},\n"
        + "\t{\n"
        + "\t\t\"id\": 8,\n"
        + "\t\t\"name\": \"corps des magistrat\"\n"
        + "\t},\n"
        + "\t{\n"
        + "\t\t\"id\": 9,\n"
        + "\t\t\"name\": \"corps informaticien\"\n"
        + "\t},\n"
        + "\t{\n"
        + "\t\t\"id\": 10,\n"
        + "\t\t\"name\": \"corps mathematicien\"\n"
        + "\t},\n"
        + "\t{\n"
        + "\t\t\"id\": 11,\n"
        + "\t\t\"name\": \"corps de physicien\"\n"
        + "\t},\n"
        + "\t{\n"
        + "\t\t\"id\": 12,\n"
        + "\t\t\"name\": \"corps de camionneur\"\n"
        + "\t}\n"
        + "]";
    TestRestTemplate restTemplate = new TestRestTemplate();
    ResponseEntity<String> responseEntity = restTemplate.getForEntity(URI.create("http://localhost:8080/api/corps/all"),  String.class);
    JSONAssert.assertEquals(expected, responseEntity.getBody(), true);
    Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

  }
  @Test
  @Order(2)
  public void test_integration_saveCorpsByController() throws JSONException {
    CorpsWork corpsWork = new CorpsWork(19L, "corps de sport de combat");
    Employee employee1 = new Employee(1L, "francis", "ngannou", "fngannou@email.com", "hhefb446464", corpsWork);
    Employee employee2 = new Employee(2L, "ciryl", "gane", "cgane@email.com", "pwjfor46464", corpsWork);
    Employee employee3 = new Employee(3L, "israel", "adesanya", "iadesan@email.com", "hiowöo64346ed", corpsWork);
    List<Employee>combattants = List.of(employee1,employee2,employee3);
    corpsWork.getEmployees().addAll(combattants);

    String expected = "{\n"
        + "\t\"id\": 13,\n"
        + "\t\"name\": \"corps de sport de combat\"\n"
        + "}";
    HttpHeaders headers= new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<CorpsWork> corpsWorkHttpEntity = new HttpEntity<CorpsWork>(corpsWork, headers);
    TestRestTemplate restTemplate = new TestRestTemplate();
    ResponseEntity<String> responseEntity = restTemplate.postForEntity(URI.create("http://localhost:8080/api/corps/save"), corpsWorkHttpEntity, String.class);
    JSONAssert.assertEquals(expected, responseEntity.getBody(), true);
    Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

  }

  @Test
  @Order(3)
  public void test_integration_DeleteCorpsWork() throws JSONException {
    CorpsWork corpsWork = new CorpsWork(13L, "corps de sport de combat");
    Employee employee1 = new Employee(1L, "francis", "ngannou", "fngannou@email.com", "hhefb446464", corpsWork);
    Employee employee2 = new Employee(2L, "ciryl", "gane", "cgane@email.com", "pwjfor46464", corpsWork);
    Employee employee3 = new Employee(3L, "israel", "adesanya", "iadesan@email.com", "hiowöo64346ed", corpsWork);
    List<Employee> combattants = List.of(employee1, employee2, employee3);
    corpsWork.getEmployees().addAll(combattants);

    String expected = "[\n"
        + "\t{\n"
        + "\t\t\"id\": 13,\n"
        + "\t\t\"name\": \"corps de sport de combat\"\n"
        + "\t}\n"
        + "]";
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<CorpsWork> corpsWorkHttpEntity = new HttpEntity<CorpsWork>(corpsWork, headers);

    TestRestTemplate restTemplate = new TestRestTemplate();
    ResponseEntity<String> responseEntity = restTemplate
        .exchange(URI.create("http://localhost:8080/api/corps/delete?name=corps%20de%20sport%20de%20combat"), HttpMethod.DELETE, corpsWorkHttpEntity,
            String.class);
    JSONAssert.assertEquals(expected, responseEntity.getBody(), false);
    Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }


}