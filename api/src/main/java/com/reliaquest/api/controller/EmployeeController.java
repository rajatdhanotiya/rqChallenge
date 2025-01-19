package com.reliaquest.api.controller;

import com.reliaquest.api.dto.CreateEmployeeDto;
import com.reliaquest.api.entities.Employee;
import com.reliaquest.api.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class EmployeeController implements IEmployeeController{

    @Autowired
    private EmployeeService employeeService;

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() throws IOException {
        return ResponseEntity.ok(employeeService.getAllEmployeeList());
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) throws IOException{
        return ResponseEntity.ok(employeeService.getEmployeesByNameSearch(searchString));
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) throws IOException{
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() throws IOException{
        return ResponseEntity.ok(employeeService.getHighestSalaryOfEmployee());
    }

    @Override
    public ResponseEntity<List<Employee>> getTopTenHighestEarningEmployeeNames() throws IOException{
        return ResponseEntity.ok(employeeService.getTopTenHighestEarningEmployeeNames());
    }

    @Override
    public ResponseEntity<Employee> createEmployee(CreateEmployeeDto employeeInput) throws IOException{
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.createEmployee(employeeInput));
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) throws IOException{
        return ResponseEntity.ok(employeeService.deleteEmployeeById(id));
    }
}
