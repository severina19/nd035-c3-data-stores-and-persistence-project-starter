package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetNotFoundException;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PetService petService;

    @Autowired
    PetRepository petRepository;

    public Customer saveCustomer(Customer customer, List<Long> petIds){
        List<Pet> pets = new ArrayList<>();
        if (petIds != null && !petIds.isEmpty()) {
            pets = petIds.stream().map((petId) -> petRepository.getOne(petId)).collect(Collectors.toList());
        }
        customer.setPets(pets);
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomer(){
        return customerRepository.findAll();
    }

    public Customer getOwnerByPet(Long petId){
        Pet pet = petService.getPetById(petId);

        return pet.getCustomer();
    }

    public Customer getCustomerById(Long customerId) {
        Customer customer = new Customer();
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);

        if (optionalCustomer.isPresent()) {
            customer = optionalCustomer.get();
        } else {
            String errorString = String.format("Customer with id %o Not Found", customerId);
            throw new UserNotFoundException(errorString);
        }
        return customer;

    }

}
