package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    PetRepository petRepository;


    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO,schedule);
        schedule.setEmployee((List<Employee>) employeeRepository.findAllById(scheduleDTO.getEmployeeIds()));
        List<Pet> pets = (List<Pet>) petRepository.findAllById(scheduleDTO.getPetIds());
        schedule.setPet(pets);
        schedule = scheduleRepository.save(schedule);
        BeanUtils.copyProperties(schedule,scheduleDTO);
        return scheduleDTO;
    }

    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = (List<Schedule>) scheduleRepository.findAll();
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();

        List<Long> employeeIds = new ArrayList<>();
        List<Long> petIds = new ArrayList<>();
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        for (Schedule schedule : schedules) {
            for (Employee employee : schedule.getEmployee()) {
                employeeIds.add(employee.getId());
            }
            for (Pet pet : schedule.getPet()) {
                petIds.add(pet.getId());
            }
            BeanUtils.copyProperties(schedule, scheduleDTO);
        }
        scheduleDTO.setPetIds(petIds);
        scheduleDTO.setEmployeeIds(employeeIds);
        scheduleDTOS.add(scheduleDTO);
        return scheduleDTOS;
    }


    public List<ScheduleDTO> getScheduleForPet(long petId) {
       List<Schedule> schedules = scheduleRepository.findScheduleByPetId(petId);
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

    public List<ScheduleDTO> getScheduleForEmployee(long employeeId) {
        List<Schedule> schedules = scheduleRepository.findScheduleByEmployeeId(employeeId);
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

    public List<ScheduleDTO> getScheduleForCustomer(long customerId) {
        List<Schedule> schedules = scheduleRepository.findScheduleByCustomerId(customerId);
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
