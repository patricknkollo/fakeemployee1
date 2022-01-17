package com.fakeEmp.fake.employee.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fakeEmp.fake.employee.entities.Employee;
import com.fakeEmp.fake.employee.repositories.EmployeeRepository;

import projections.EmployeeNameAndVornameProjection;

@Service
@Component
public class EmployeeService {

	@Autowired
	EmployeeRepository repository;
	
	public ResponseEntity<List<Employee>> getAllEmployees(){
		List<Employee> allEmployees = repository.findAll();
		return allEmployees != null
				? new ResponseEntity<List<Employee>>(allEmployees, HttpStatus.OK)
						: new ResponseEntity<List<Employee>>(allEmployees, HttpStatus.NO_CONTENT);
	}
	
	public List<Employee> getAllEmployeesList(){
		List<Employee> allEmployees = repository.findAll();
		return allEmployees;
	}
	
	public ResponseEntity<Optional<Employee>> getAllEmployeesWithId(Long id){
		Optional<Employee> employee = repository.findById(id);
		return employee != null
				? new ResponseEntity<Optional<Employee>>(employee, HttpStatus.OK)
						: new ResponseEntity<Optional<Employee>>(employee, HttpStatus.NO_CONTENT);
	}
	
	public ResponseEntity<Employee> enregistre(Employee employee){
		Employee resEmployee = repository.save(employee);
		return resEmployee != null
				? new ResponseEntity<Employee>(resEmployee, HttpStatus.OK)
						: new ResponseEntity<Employee>(resEmployee, HttpStatus.NO_CONTENT);
		
	}
	
	public void deleteEmployee(Employee employee){
		 repository.delete(employee);
		
	}

	public Optional<Employee> updateEmployee(Employee employee, Long id){
		Optional<Employee> employeeTemp = repository.findById(id);
		if(employeeTemp.isPresent()){
			employeeTemp.get().setFirstname(employee.getFirstname());
			employeeTemp.get().setName(employee.getName());
			employeeTemp.get().setEmail(employee.getEmail());
			employeeTemp.get().setPassword(employee.getPassword());
			employeeTemp.get().setCorps(employee.getCorps());
			repository.save(employeeTemp.get());
			return  employeeTemp;
		}
		return Optional.empty();
	}
	
	public List<EmployeeNameAndVornameProjection> getAllEmployeesNameVorname(){
		List<EmployeeNameAndVornameProjection> allEmployees = repository.getEmployeeNameVorname();
		return allEmployees ;
	}
}
