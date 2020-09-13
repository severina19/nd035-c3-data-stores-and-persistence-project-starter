package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetNotFoundException;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    PetRepository petRepository;

    @Autowired
    PetService petService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    CustomerService customerService;

    public List<Schedule> getAllSchedules(){
        return scheduleRepository.findAll();
    };

    public Schedule createSchedule(Schedule schedule, List<Long> employeeIds, List<Long> petIds){
        List<Employee> employees = employeeRepository.findAllById(employeeIds);
        if (employees.isEmpty()){
            String ids = employeeIds.stream().map(String::valueOf)
                    .collect(Collectors.joining(","));
            String errorString = String.format("Customer with id %s Not Found", ids);
            throw new UserNotFoundException(errorString);

        }
        List<Pet> pets = petRepository.findAllById(petIds);

        if (pets.isEmpty()){
            String ids = pets.stream().map(String::valueOf)
                    .collect(Collectors.joining(","));
            String errorString = String.format("Pet with id %s Not Found", ids);
            throw new PetNotFoundException(errorString);

        }
        schedule.setEmployee(employees);
        schedule.setPet(pets);
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getScheduleForPet(Long petId){
        Pet pet = petService.getPetById(petId);
        return scheduleRepository.getAllByPetContains(pet);
    }

    public List<Schedule> getScheduleForCustomer(Long customerId){
        Customer customer = customerService.getCustomerById(customerId);
        return scheduleRepository.getAllByPetIn(customer.getPets());
    }

    public List<Schedule> getScheduleForEmployee(Long employeeId){
        Employee employee = employeeService.getEmployeeById(employeeId);
        return scheduleRepository.getAllByEmployeeContains(employee);
    }

}
