package com.fakeEmp.fake.employee.repositories;

import com.fakeEmp.fake.employee.entities.CorpsWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Component
public interface CorpsWorkRepository extends JpaRepository<CorpsWork, Long> {
  @Query(value="SELECT * FROM CorpsWork where name=:specific_name ", nativeQuery = true)
  @Transactional
  public List<CorpsWork> findByName(String specific_name);

  @Modifying
  @Query(value="DELETE FROM CorpsWork where name=:specific_name ", nativeQuery = true)
  @Transactional
  public void deleteByName(String specific_name);
}
