package com.fakeEmp.fake.employee.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

@Entity
@Table(name = "EMPLOYEE")
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String firstname;
	
	private String name;
	
	private String email;
	
	private String password;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private CorpsWork corps;
	
	public Employee(Long id, String firstname, String name, String email, String password,
			CorpsWork corps) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.name = name;
		this.email = email;
		this.password = password;
		this.corps = corps;
	}

	public Employee(String firstname, String name, String email, String password,
			CorpsWork corps) {
		super();
		this.firstname = firstname;
		this.name = name;
		this.email = email;
		this.password = password;
		this.corps = corps;
	}

	
	public Employee() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public CorpsWork getCorps() {
		return corps;
	}

	public void setCorps(CorpsWork corps) {
		this.corps = corps;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstname=" + firstname + ", name=" + name + ", email=" + email + ", password="
				+ password + "]";
	}
}
