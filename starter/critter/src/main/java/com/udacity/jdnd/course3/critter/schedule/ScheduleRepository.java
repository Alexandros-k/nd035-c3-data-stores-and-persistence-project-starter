package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entity.Schedule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends CrudRepository<Schedule,Long> {

    //@Query("select s from Schedule s join s.pet pet where pet.id =:petId")
    List<Schedule> findByPet_Id(long petId);

    //@Query("select s from Schedule s join s.employee employee where employee.id =:employeeId")
    List<Schedule> findByEmployee_Id(long employeeId);

    @Query("select s from Schedule s join s.pet pet join pet.customer customer where customer.id =:customerId")
    List<Schedule> findByCustomer_Id(long customerId);
}
