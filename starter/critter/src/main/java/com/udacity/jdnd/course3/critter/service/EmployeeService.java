package com.udacity.jdnd.course3.critter.service;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public Employee getEmployeeById(Long employeeId){
        Employee employee = new Employee();
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);

        if (optionalEmployee.isPresent()) {
            employee = optionalEmployee.get();
        } else {
            String errorString = String.format("Employee with id %o Not Found", employeeId);
            throw new UserNotFoundException(errorString);
        }
        return employee;
    }
    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public List<Employee> findEmployeesForService(LocalDate date, Set<EmployeeSkill> skillSet){
        List<Employee> employeesAvailable = employeeRepository.getAllByDaysAvailableContains(date.getDayOfWeek());
        List<Employee> employees = employeesAvailable.stream()
                .filter(employee -> employee.getSkills().containsAll(skillSet))
                .collect(Collectors.toList());
        return employees;
    }
    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Employee employee = getEmployeeById(employeeId);
        employee.setDaysAvailable(daysAvailable);
        employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }
}
