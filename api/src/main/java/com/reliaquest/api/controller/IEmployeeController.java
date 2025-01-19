package com.reliaquest.api.controller;

import java.io.IOException;
import java.util.List;

import com.reliaquest.api.constants.Constants;
import com.reliaquest.api.dto.CreateEmployeeDto;
import com.reliaquest.api.entities.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Please <b>do not</b> modify this interface. If you believe there's a bug or the API contract does not align with our
 * mock web server... that is intentional. Good luck!
 *
 * @implNote It's uncommon to have a web controller implement an interface; We include such design pattern to
 * ensure users are following the desired input/output for our API contract, as outlined in the code assessment's README.
 *
 * @param <//Entity> object representation of an Employee
 * @param <//Input> object representation of a request body for creating Employee(s)
 */

@RestController
public interface IEmployeeController {

    @GetMapping(Constants.API_GET_EMPLOYEE)
    ResponseEntity<List<Employee>> getAllEmployees() throws IOException;

    @GetMapping(Constants.API_EMPLOYEE_SEARCH_BY_NAME)
    ResponseEntity<List<Employee>> getEmployeesByNameSearch(@PathVariable String searchString) throws IOException;

    @GetMapping(Constants.API_GET_EMPLOYEE_BY_ID)
    ResponseEntity<Employee> getEmployeeById(@PathVariable String id) throws IOException;

    @GetMapping(Constants.API_GET_HIGHEST_SALARY_EMPLOYEE)
    ResponseEntity<Integer> getHighestSalaryOfEmployees() throws IOException;

    @GetMapping(Constants.API_GET_TOP_TEN_HIGHEST_EARNING_EMPLOYEES)
    ResponseEntity<List<Employee>> getTopTenHighestEarningEmployeeNames() throws IOException;

    @PostMapping(Constants.API_CREATE_EMPLOYEE)
    ResponseEntity<Employee> createEmployee(@RequestBody CreateEmployeeDto employeeInput) throws IOException;

    @DeleteMapping(Constants.API_DELETE_EMPLOYEE)
    ResponseEntity<String> deleteEmployeeById(@PathVariable String id) throws IOException;
}
