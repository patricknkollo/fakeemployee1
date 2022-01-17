package com.fakeEmp.fake.employee.services;

import com.fakeEmp.fake.employee.entities.CorpsWork;
import com.fakeEmp.fake.employee.entities.Employee;
import com.fakeEmp.fake.employee.repositories.CorpsWorkRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Component
public class CorpsWorkService {

  @Autowired
  CorpsWorkRepository repository;

  public ResponseEntity<CorpsWork> saveCorps(CorpsWork corpsWork) {
    CorpsWork result = repository.save(corpsWork);
    return result != null
        ? new ResponseEntity<CorpsWork>(result, HttpStatus.OK)
        : new ResponseEntity<CorpsWork>(result, HttpStatus.NO_CONTENT);
  }

  public ResponseEntity<List<CorpsWork>> getAllCorpsWork() {
    List<CorpsWork> corpsWorks = repository.findAll();
    return corpsWorks != null
        ? new ResponseEntity<List<CorpsWork>>(corpsWorks, HttpStatus.OK)
        : new ResponseEntity<List<CorpsWork>>(corpsWorks, HttpStatus.NO_CONTENT);
  }

  public ResponseEntity<Optional<CorpsWork>> getCorpsWorkWithId(Long id) {
    Optional<CorpsWork> corpsWorks = repository.findById(id);
    return corpsWorks != null
        ? new ResponseEntity<Optional<CorpsWork>>(corpsWorks, HttpStatus.OK)
        : new ResponseEntity<Optional<CorpsWork>>(corpsWorks, HttpStatus.NO_CONTENT);
  }

  public ResponseEntity<List<CorpsWork>> getCorpsWorkWithName(String name) {
    List<CorpsWork> corpsWorks = repository.findByName(name);
    return corpsWorks != null
        ? new ResponseEntity<List<CorpsWork>>(corpsWorks, HttpStatus.OK)
        : new ResponseEntity<List<CorpsWork>>(corpsWorks, HttpStatus.NO_CONTENT);
  }

  public ResponseEntity<List<CorpsWork>> deleteCorpsWithName(String name) {
    List<CorpsWork> listOfCorpsWithName = repository.findByName(name);
    repository.deleteByName(name);
    return listOfCorpsWithName != null
        ? new ResponseEntity<List<CorpsWork>>(listOfCorpsWithName, HttpStatus.OK)
        : new ResponseEntity<List<CorpsWork>>(listOfCorpsWithName, HttpStatus.NOT_FOUND);
  }

  public ResponseEntity<CorpsWork> UpdateCorpsWithName(CorpsWork corpsWork, Long id) {
    Optional<CorpsWork> corpsWorkTemp = repository.findById(id);
    if (corpsWorkTemp.isPresent()) {
      //corpsWorkTemp.get().setEmployees(corpsWork.getEmployees());
      corpsWorkTemp.get().getEmployees().addAll(corpsWork.getEmployees());
      CorpsWork result = repository.save(corpsWorkTemp.get());
      return new ResponseEntity<>(result, HttpStatus.OK);
    }
    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
  }
  //donne moi les travailleurs nommé nom/prenom appartenants au corps de metier police, armée, et coprs enseignant
  @NotNull
  public ResponseEntity<List<Employee>> getEmployeeOfParticularCorpsAndName(String name) {
    List<Employee> employees = repository.findAll()
        .stream()
        .filter(corpsWork -> corpsWork.getName().equals("corps de police")
            || corpsWork.getName().equals("corps enseignant")
            || corpsWork.getName().equals("corps medical"))
        .map(CorpsWork::getEmployees)
        .flatMap(List<Employee>::stream)
        .filter(employee -> employee.getName().equals(name))
        .collect(Collectors.toList());
    ResponseEntity<List<Employee>> responseEntity = new ResponseEntity<>(employees, HttpStatus.OK);
    return  responseEntity;
  }
  @NotNull
  public ResponseEntity<List<Employee>> getEmployeeOfParticularCorpsAndFirstname(String firstname) {
    List<Employee> employees = repository.findAll()
        .stream()
        .filter(corpsWork -> corpsWork.getName().equals("corps de police")
            || corpsWork.getName().equals("corps enseignant")
            || corpsWork.getName().equals("corps medical"))
        .map(CorpsWork::getEmployees)
        .flatMap(List<Employee>::stream)
        .filter(employee -> employee.getFirstname().equals(firstname))
        .collect(Collectors.toList());
    ResponseEntity<List<Employee>> responseEntity = new ResponseEntity<>(employees, HttpStatus.OK);
    return  responseEntity;
  }
}
