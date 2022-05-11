package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetService {

    @Autowired
    PetRepository petRepository;
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    UserService userService;

    public Pet save(Pet pet) {
      pet= petRepository.save(pet);
      userService.addPetToCustomer(pet,pet.getCustomer());
      return pet;
    }

    public Optional<Pet> getPet(long petId) {
       return petRepository.findById(petId);
    }

    public List<Pet> getPets() {
       return (List<Pet>) petRepository.findAll();
    }

    public List<Pet> getPetByOwner(long ownerId) {
         return petRepository.getPetsByCustomer_Id(ownerId);
    }
}
