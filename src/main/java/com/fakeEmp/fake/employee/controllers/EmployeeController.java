package com.fakeEmp.fake.employee.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.fakeEmp.fake.employee.entities.Employee;
import com.fakeEmp.fake.employee.services.EmployeeService;

import projections.EmployeeNameAndVornameProjection;

@CrossOrigin(origins =  "http://localhost:3000")
@Controller
@RequestMapping(path = "/api/controller/employee")
@Component
public class EmployeeController {
	
	@Autowired
	EmployeeService service;
	
	@RequestMapping(path = "/employees", method = RequestMethod.GET )
	public @ResponseBody ResponseEntity<List<Employee>> getAllEmployees() {
		return service.getAllEmployees();
	}
	
	@RequestMapping(path = "/employeeslist", method = RequestMethod.GET )
	public @ResponseBody List<Employee> getAllEmployeesList() {
		return service.getAllEmployeesList();
	}
	
	@RequestMapping(path = "/employee/{thisid}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Optional<Employee>> getEmployeeWithId(@PathVariable("thisid") Long id){
		return service.getAllEmployeesWithId(id);	
	}
	
	@RequestMapping(path = "/save", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Employee> saveEmployee(Employee employee){
	   return service.enregistre(employee);
	}

	@RequestMapping(path = "/delete", method = RequestMethod.DELETE)
	public void deleteEmployee (Employee employee) {
		service.deleteEmployee(employee);
	}
	
	@RequestMapping(path="/employee/nameAndVorname", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeNameAndVornameProjection> getAllEmployeeNameAndVorname(){
		return service.getAllEmployeesNameVorname();
	}
	@RequestMapping(path = "/updateemployee/{thisid}", method = RequestMethod.PUT)
	public  Optional<Employee> updateEmployee (@RequestBody Employee employee, @PathVariable("thisid") Long id){
		return service.updateEmployee(employee,id);
	}
}
