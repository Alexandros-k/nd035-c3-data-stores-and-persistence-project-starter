package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    @Autowired
    PetRepository petRepository;
    @Autowired
    CustomerRepository customerRepository;

    public Pet save(Pet pet) {
       Optional<Customer> customer =customerRepository.findById(pet.getCustomer().getId());
      //TODO see if its needed
        if (customer.isPresent()) {
            pet.setCustomer(customer.get());
        }
       return petRepository.save(pet);
    }

    public Optional<Pet> getPet(long petId) {
       return petRepository.findById(petId);
    }

    public List<Pet> getPets() {
       return (List<Pet>) petRepository.findAll();
    }

    public List<Pet> getPetByOwner(long ownerId) {
         return petRepository.findPetByCustomerId(ownerId);
    }
}
