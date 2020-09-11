package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    public List<Schedule> getAllSchedules(){
        return scheduleRepository.findAll();
    };

    public Schedule createSchedule(Schedule schedule, List<Long> employeeIds, List<Long> petIds){
        List<Employee> employees = employeeRepository.findAllById(employeeIds);
        List<Pet> pets = petRepository.findAllById(petIds);
        schedule.setEmployee(employees);
        schedule.setPet(pets);
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getScheduleForPet(Long petId){
        Pet pet = petRepository.getOne(petId);
        return scheduleRepository.getAllByPetContains(pet);
    }

    public List<Schedule> getScheduleForCustomer(Long customerId){
        Customer customer = customerRepository.getOne(customerId);
        return scheduleRepository.getAllByPetIn(customer.getPets());

    }

    public List<Schedule> getScheduleForEmployee(Long employeeId){
        Employee employee = employeeRepository.getOne(employeeId);
        return scheduleRepository.getAllByEmployeeContains(employee);
    }

}
