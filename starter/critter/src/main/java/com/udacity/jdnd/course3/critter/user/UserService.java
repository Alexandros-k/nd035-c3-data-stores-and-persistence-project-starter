package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    PetRepository petRepository;

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
         return (List<Customer>) customerRepository.findAll();
    }

    public CustomerDTO getOwnerByPet(long petId) {
     Customer customer = customerRepository.getCustomerByPetId(petId);
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer,customerDTO);
        List<Long> petIds = customer.getPets().stream().map(pet -> pet.getId()).collect(Collectors.toList());
        customerDTO.setPetIds(petIds);
        return customerDTO;
    }

    public Employee saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employeeRepository.save(employee);
    }

    public Employee getEmployee(long employeeId) {
    Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isPresent()) {
            return employee.get();
        }
       return null; //TODO add custom exception
    }

    public void setEmployeesAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
       Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isPresent()) {
            employee.get().setDaysAvailable(daysAvailable);
            employeeRepository.save(employee.get());
        }
    }

    public List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO employeeDTO) {
        DayOfWeek localDate = employeeDTO.getDate().getDayOfWeek();
        Set<Employee> employees = employeeRepository.findEmployeeByskills(employeeDTO.getSkills(),employeeDTO.getSkills().size());
        employees = employees.stream().filter(employee -> employee.getDaysAvailable().contains(localDate)).collect(Collectors.toSet());
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        employees.stream().forEach(employee -> {
            EmployeeDTO employeeDTO1 = new EmployeeDTO();
            BeanUtils.copyProperties(employee, employeeDTO1);
            employeeDTOS.add(employeeDTO1);
        });
        return employeeDTOS;
    }
}
