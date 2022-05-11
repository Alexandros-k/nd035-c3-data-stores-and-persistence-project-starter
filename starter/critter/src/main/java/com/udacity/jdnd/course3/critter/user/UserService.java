package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
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

    public Optional<Customer> getCustomerById(long customerId){
        return customerRepository.findById(customerId);
    }

    public Customer getOwnerByPet(long petId) {
     return customerRepository.getCustomerByPetId(petId);
    }

    public Employee saveEmployee(Employee employee) {
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

    public Set<Employee> findEmployeesForService(Employee employee,DayOfWeek localDate) {
        Set<Employee> employees = employeeRepository.findEmployeeByskills(employee.getSkills(),employee.getSkills().size());
        return employees.stream().filter(employee1 -> employee1.getDaysAvailable().contains(localDate)).collect(Collectors.toSet());
    }

    public void addPetToCustomer(Pet pet, Customer customer) {
        List<Pet> pets = customer.getPets();
        if (pets != null)
            pets.add(pet);
        else {
            pets = new ArrayList<Pet>();
            pets.add(pet);
        }
        customer.setPets(pets);
        customerRepository.save(customer);
    }
}
