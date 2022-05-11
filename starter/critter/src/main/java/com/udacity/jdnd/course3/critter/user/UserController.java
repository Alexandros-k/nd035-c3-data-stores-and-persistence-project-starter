package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.pet.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    PetService petService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        customer = userService.saveCustomer(customer);
        BeanUtils.copyProperties(customer,customerDTO);
        return customerDTO;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customers = userService.getAllCustomers();
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        customers.forEach(customer -> {
                    CustomerDTO customerDTO = new CustomerDTO();
                    BeanUtils.copyProperties(customer, customerDTO);
                    if (customer.getPets() != null) {
                        List<Long> petIds = customer.getPets().stream().map(pet -> pet.getId()).collect(Collectors.toList());
                        customerDTO.setPetIds(petIds);
                    }
                    customerDTOS.add(customerDTO);
                }
        );
        return customerDTOS;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Customer customer = userService.getOwnerByPet(petId);
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer,customerDTO);
        List<Long> petIds = customer.getPets().stream().map(pet -> pet.getId()).collect(Collectors.toList());
        customerDTO.setPetIds(petIds);
        return customerDTO;
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        employee = userService.saveEmployee(employee);
        BeanUtils.copyProperties(employee,employeeDTO);
        return employeeDTO;
    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        Employee employ = userService.getEmployee(employeeId);
        BeanUtils.copyProperties(employ,employeeDTO);
        return employeeDTO;
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        userService.setEmployeesAvailability(daysAvailable,employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        Employee employee = new Employee();
        DayOfWeek localDate = employeeDTO.getDate().getDayOfWeek();
        employee.setSkills(employeeDTO.getSkills());
        Set<Employee> employees = userService.findEmployeesForService(employee,localDate);
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        employees.stream().forEach(employee1 -> {
            EmployeeDTO employeeDTO1 = new EmployeeDTO();
            BeanUtils.copyProperties(employee1, employeeDTO1);
            employeeDTOS.add(employeeDTO1);
        });
         return employeeDTOS;
    }

}
