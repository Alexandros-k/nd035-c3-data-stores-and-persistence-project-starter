package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.entity.Pet;
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

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO,pet);
       pet = petService.save(petDTO);
        BeanUtils.copyProperties(pet,petDTO);
        return petDTO;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
       Optional<Pet> pet = petService.getPet(petId);
       PetDTO petDTO = new PetDTO();
        if (pet.get() != null) {
            BeanUtils.copyProperties(pet.get(),petDTO);
            return petDTO;
        }
       return null;
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
        List<Pet> pets = petService.getPetByOwner(ownerId);
        List<PetDTO> petsDTO = new ArrayList<PetDTO>();
        pets.stream().forEach(pet -> {
            PetDTO petDTO = new PetDTO();
            BeanUtils.copyProperties(pet, petDTO);
            petsDTO.add(petDTO);
        });
         return petsDTO;
    }
}
