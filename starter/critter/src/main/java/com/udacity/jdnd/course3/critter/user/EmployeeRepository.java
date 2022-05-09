package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.entity.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee,Long> {

    List<Employee> findEmployeeBySkillsInAndDaysAvailable(Set<EmployeeSkill> skills, LocalDate date);

    Set<Employee> findEmployeeBySkillsIn(Set<EmployeeSkill> skills);
}
