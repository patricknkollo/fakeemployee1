package com.fakeEmp.fake.employee.controllers;

import com.fakeEmp.fake.employee.entities.CorpsWork;
import com.fakeEmp.fake.employee.entities.Employee;
import com.fakeEmp.fake.employee.services.CorpsWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping(path = "/api/corps")
@CrossOrigin(origins = "http://localhost:3000")
@Controller
@Component
public class CorpsWorkController {

  @Autowired
  CorpsWorkService service;

  @RequestMapping(path = "/save", method = RequestMethod.POST)
  public @ResponseBody ResponseEntity<CorpsWork> saveCorpsByController(@RequestBody CorpsWork corpsWork) {
    return service.saveCorps(corpsWork);
  }
  @RequestMapping(path = "/all", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<List<CorpsWork>> getAllCorpsWorkByController() {
     return service.getAllCorpsWork();
  }
  @RequestMapping(path = "/id/{thisid}", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<Optional<CorpsWork>> getCorpsWorkWithIdByController(@PathVariable("thisid") Long id) {
    return service.getCorpsWorkWithId(id);
  }
  @RequestMapping(path = "/name/{thisname}", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<List<CorpsWork>> getCorpsWorkWithNameByController(@PathVariable("thisname") String name) {
    return service.getCorpsWorkWithName(name);
  }
  @RequestMapping(path = "/delete", method = RequestMethod.DELETE)
  public @ResponseBody ResponseEntity<List<CorpsWork>> deleteCorpsWithNameByController(@RequestParam String name) {
    return service.deleteCorpsWithName(name);
  }
  @RequestMapping(path = "/update", method = RequestMethod.PUT)
  public @ResponseBody ResponseEntity<CorpsWork> UpdateCorpsWithNameByController(@RequestBody CorpsWork corpsWork, @RequestParam Long id) {
    return service.UpdateCorpsWithName(corpsWork, id);
  }

  @RequestMapping(path = "/getemployee/firstname", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<List<Employee>> getEmployeeOfParticularCorpsAndFirstnameController(@RequestParam String firstname) {
    return service.getEmployeeOfParticularCorpsAndFirstname(firstname);
  }
  @RequestMapping(path = "/getemployee/lastname/{thisname}", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<List<Employee>> getEmployeeOfParticularCorpsAndName(@PathVariable("thisname") String name) {
    return service.getEmployeeOfParticularCorpsAndName(name);
  }

}
