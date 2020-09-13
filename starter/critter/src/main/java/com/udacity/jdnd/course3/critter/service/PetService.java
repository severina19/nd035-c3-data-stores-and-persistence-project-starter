package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.pet.PetNotFoundException;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;

import com.udacity.jdnd.course3.critter.user.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerService customerService;

    public List<Pet> getAllPets(){
        return petRepository.findAll();
    };

    public Pet getPetById(long petId) {
        Pet pet = new Pet();
        Optional<Pet> optionalPet = petRepository.findById(petId);

        if (optionalPet.isPresent()) {
            pet = optionalPet.get();
        } else {
            String errorString = String.format("Pet with id %o Not Found", petId);
            throw new PetNotFoundException(errorString);
        }
        return pet;

    }
    public Pet savePet(Pet pet, Long customerId){
        Customer customer = customerService.getCustomerById(customerId);
        pet.setCustomer(customer);
        pet = petRepository.save(pet);
        customer.addPet(pet);
        customerRepository.save(customer);
        return pet;
    }

    public List<Pet> getPetsByCustomerId(Long customerId){
        return petRepository.getPetsByCustomerId(customerId);
    }


}
