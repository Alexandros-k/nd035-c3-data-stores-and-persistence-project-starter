package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        schedule = scheduleService.createSchedule(schedule, scheduleDTO.getEmployeeIds(), scheduleDTO.getPetIds());
        BeanUtils.copyProperties(schedule, scheduleDTO);
        return scheduleDTO;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        List<ScheduleDTO> scheduleDTOS = convertScheduleToScheduleDTOS(schedules);
        return scheduleDTOS;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedules = scheduleService.getScheduleForPet(petId);
        return convertScheduleToScheduleDTOS(schedules);
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedules = scheduleService.getScheduleForEmployee(employeeId);
        return convertScheduleToScheduleDTOS(schedules);
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> schedules = scheduleService.getScheduleForCustomer(customerId);
        return convertScheduleToScheduleDTOS(schedules);
    }

    private List<ScheduleDTO> convertScheduleToScheduleDTOS(List<Schedule> schedules) {
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();

        for (Schedule schedule : schedules) {
            ScheduleDTO scheduleDTO = new ScheduleDTO();
            List<Long> employeeIds = new ArrayList<>();
            List<Long> petIds = new ArrayList<>();
            for (Employee employee : schedule.getEmployee()) {
                employeeIds.add(employee.getId());
            }
            for (Pet pet : schedule.getPet()) {
                petIds.add(pet.getId());
            }
            BeanUtils.copyProperties(schedule, scheduleDTO);
            scheduleDTO.setPetIds(petIds);
            scheduleDTO.setEmployeeIds(employeeIds);
            scheduleDTOS.add(scheduleDTO);
        }
        return scheduleDTOS;
    }
}
