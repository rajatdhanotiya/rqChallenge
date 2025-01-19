package com.reliaquest.api.service;

import com.reliaquest.api.dto.CreateEmployeeDto;
import com.reliaquest.api.entities.Employee;

import java.io.IOException;
import java.util.List;

public interface EmployeeService {

    List<Employee> getAllEmployeeList() throws IOException;

    Employee getEmployeeById(String id) throws IOException;

    List<Employee> getEmployeesByNameSearch(String searchString) throws IOException;

    Integer getHighestSalaryOfEmployee() throws IOException;

    List<Employee> getTopTenHighestEarningEmployeeNames() throws IOException;

    Employee createEmployee(CreateEmployeeDto employeeInput) throws IOException;

    String deleteEmployeeById(String id) throws IOException;
}
