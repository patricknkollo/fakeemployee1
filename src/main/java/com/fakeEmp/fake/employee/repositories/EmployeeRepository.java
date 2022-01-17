package com.fakeEmp.fake.employee.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.fakeEmp.fake.employee.entities.Employee;

import projections.EmployeeNameAndVornameProjection;

@Repository
@Component
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	
	@Query(value = "SELECT * FROM EMPLOYEE" ,nativeQuery = true)
	public List<EmployeeNameAndVornameProjection> getEmployeeNameVorname();

}
