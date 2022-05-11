package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.user.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;

    @Autowired
    UserService userService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
       Optional<Customer> customer = userService.getCustomerById(petDTO.getOwnerId());
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO,pet);
        if (customer.isPresent()) {
            pet.setCustomer(customer.get());
        }
        pet =  petService.save(pet);
        PetDTO petDTO1 = new PetDTO();
        BeanUtils.copyProperties(pet,petDTO1);
        petDTO1.setOwnerId(pet.getCustomer().getId());
        return petDTO1;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
       Optional<Pet> pet = petService.getPet(petId);
       PetDTO petDTO = new PetDTO();
        if (pet.isPresent()) {
            BeanUtils.copyProperties(pet.get(),petDTO);
            petDTO.setOwnerId(pet.get().getCustomer().getId());
            return petDTO;
        }
       return null; //TODO custom error
    }

    @GetMapping
    public List<PetDTO> getPets() {
        List<Pet> pets = petService.getPets();
        List<PetDTO> petsDTO = new ArrayList<PetDTO>();
        pets.stream().forEach(pet -> {
            PetDTO petDTO = new PetDTO();
            BeanUtils.copyProperties(pet, petDTO);
            petsDTO.add(petDTO);
        });
        return petsDTO;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets1 = petService.getPets();
        List<Pet> pets = petService.getPetByOwner(ownerId);
        List<PetDTO> petsDTO = new ArrayList<PetDTO>();
        pets.stream().forEach(pet -> {
            PetDTO petDTO = new PetDTO();
            BeanUtils.copyProperties(pet, petDTO);
            petDTO.setOwnerId(pet.getCustomer().getId());
            petsDTO.add(petDTO);
        });
         return petsDTO;
    }
}
