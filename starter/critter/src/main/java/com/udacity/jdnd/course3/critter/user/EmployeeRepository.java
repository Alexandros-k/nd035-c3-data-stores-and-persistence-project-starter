package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.entity.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee,Long> {
    @Query("SELECT e FROM Employee e LEFT JOIN e.skills sk WHERE sk IN :skillList"
            + " GROUP BY e HAVING COUNT( sk) = :skillListSize" )
    Set<Employee> findEmployeeByskills(@Param("skillList") Set<EmployeeSkill> skills, @Param("skillListSize") long skillListSize);
}
