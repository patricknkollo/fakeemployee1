package com.fakeEmp.fake.employee.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
//AUTO_INCREMENT et @JsonIgnore ne doivent pas être absent sinon l'application aura
// des erreurs de serialization dans le cas de l'absence de @JsonIgnore et des erreurs du
// type insert into table (id, att1, att2)values(null, ?,?) dans le cas d'omission de AUTO_INCREMENT dans la declaration du tableau
@Entity
@Table(name = "corpswork")
public class CorpsWork {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;

  @JsonIgnore
  @OneToMany(
      mappedBy = "corps",
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  private List<Employee> employees = new ArrayList<>();

  public CorpsWork() {
  }
  public CorpsWork(Long id, String name, List<Employee> employees) {
    this.id = id;
    this.name = name;
    this.employees = employees;
  }
  public CorpsWork(Long id, String name) {
    this.id = id;
    this.name = name;
  }
  public CorpsWork(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Employee> getEmployees() {
    return employees;
  }

  public void setEmployees(List<Employee> employees) {
    this.employees = employees;
  }

  @Override
  public String toString() {
    return "CorpsWork{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", employees=" + employees +
        '}';
  }
}
