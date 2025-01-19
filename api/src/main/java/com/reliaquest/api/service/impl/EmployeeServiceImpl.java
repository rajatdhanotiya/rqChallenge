package com.reliaquest.api.service.impl;

import com.reliaquest.api.bean.ApiResponse;
import com.reliaquest.api.constants.Constants;
import com.reliaquest.api.dto.*;
import com.reliaquest.api.entities.Employee;
import com.reliaquest.api.enums.ErrorCode;
import com.reliaquest.api.service.EmployeeService;
import com.reliaquest.api.util.ApiConnector;
import com.reliaquest.api.util.ExceptionUtil;
import com.reliaquest.api.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Value("${mock.api.base.url}")
    private String mockApiBaseUrl;

    @Autowired
    private ApiConnector apiConnector;

    @Autowired
    private ExceptionUtil exceptionUtil;

    @Override
    public List<Employee> getAllEmployeeList() throws IOException{
        LOGGER.info("inside getAllEmployeeList method");

        try{
            ApiResponse apiResponse = apiConnector.doGet(mockApiBaseUrl + Constants.MOCK_API_CONTROLLER_PATH + Constants.MOCK_API_GET_EMPLOYEES, null);

            EmployeeListResponseDto employeeListResponseDto = JsonUtil.fromJson(apiResponse.getResponse(), EmployeeListResponseDto.class);

            List<Employee> employees = employeeListResponseDto.getData();

            if(CollectionUtils.isEmpty(employees)){
                LOGGER.error("EmployeeServiceImpl : getAllEmployees : No employees found");
                throw exceptionUtil.getAPIException(ErrorCode.NO_RECORDS_FOUND, new Object[]{"Employee"});
            }

            return employeeListResponseDto.getData();
        }catch (Exception e){
            LOGGER.error("Exception while calling server api to fetch all employees");
            throw exceptionUtil.getAPIException(ErrorCode.EXCEPTION_WHILE_CALLING_EXTERNAL_API, new Object[]{"Employee"});
        }

    }

    @Override
    public Employee getEmployeeById(String id) throws IOException{
        LOGGER.info("inside getAllEmployeeList method");

        try{
            ApiResponse apiResponse = apiConnector.doGet(mockApiBaseUrl + Constants.MOCK_API_CONTROLLER_PATH + Constants.MOCK_API_GET_EMPLOYEES + "/" +  id, null);

            EmployeeByIdResponseDto employeeByIdResponseDto = JsonUtil.fromJson(apiResponse.getResponse(), EmployeeByIdResponseDto.class);

            Employee employee = employeeByIdResponseDto.getData();
            if(Objects.isNull(employee)){
                LOGGER.error("EmployeeServiceImpl : getEmployeeById : No employee found for id : {}", id);
                throw exceptionUtil.getAPIException(ErrorCode.NO_RECORDS_FOUND, new Object[]{});
            }
            return employee;
        }catch(Exception e){
            LOGGER.error("Exception while calling server api to fetch employee by id");
            throw exceptionUtil.getAPIException(ErrorCode.EXCEPTION_WHILE_CALLING_EXTERNAL_API, new Object[]{"Employee"});
        }
    }

    @Override
    public List<Employee> getEmployeesByNameSearch(String searchString) throws IOException{
        LOGGER.info("inside getEmployeesByNameSearch method");
        try{
            List<Employee> employeeList = getAllEmployeeList();

            LOGGER.debug("exiting from getEmployeesByNameSearch");

            List<Employee> employees = employeeList.stream().filter(emp -> emp.getEmployeeName().toLowerCase().contains(searchString.toLowerCase()))
                    .collect(Collectors.toList());

            if(CollectionUtils.isEmpty(employees)){
                LOGGER.error("EmployeeServiceImpl : getEmployeesByNameSearch : No employees found for search string : {}", searchString);
                throw exceptionUtil.getAPIException(ErrorCode.NO_RECORDS_FOUND, new Object[]{});
            }

            return employees;

        }catch(Exception e){
            LOGGER.error("Exception while calling server api to fetch all employees");
            throw exceptionUtil.getAPIException(ErrorCode.EXCEPTION_WHILE_CALLING_EXTERNAL_API, new Object[]{"EmployeeName"});
        }

    }

    @Override
    public Integer getHighestSalaryOfEmployee() throws IOException{
        LOGGER.info("inside getEmployeesByNameSearch method");
        try{
            List<Employee> employeeList = getAllEmployeeList();
            LOGGER.debug("exsiting from getHighestSalaryOfEmployee");
            return employeeList.stream().mapToInt(emp -> emp.getEmployeeSalary()).max().getAsInt();
        } catch(Exception e){
            LOGGER.error("Exception while calling server api to fetch all employees");
            throw exceptionUtil.getAPIException(ErrorCode.EXCEPTION_WHILE_CALLING_EXTERNAL_API, new Object[]{"EmployeeName"});
        }

    }

    @Override
    public List<Employee> getTopTenHighestEarningEmployeeNames() throws IOException{
        LOGGER.info("inside getTopTenHighestEarningEmployeeNames");

        try{
            List<Employee> employeeList = getAllEmployeeList();

            LOGGER.debug("existing getTopTenHighestEarningEmployeeNames method");

            return employeeList.stream()
                    .sorted((e1, e2) -> e2.getEmployeeSalary() - e1.getEmployeeSalary())
                    .limit(10)
                    .collect(Collectors.toList());

        }catch(Exception e){
            LOGGER.error("Exception while calling server api to fetch all employees");
            throw exceptionUtil.getAPIException(ErrorCode.EXCEPTION_WHILE_CALLING_EXTERNAL_API, new Object[]{"EmployeeName"});
        }


    }

    @Override
    public Employee createEmployee(CreateEmployeeDto employeeInput) throws IOException {
        LOGGER.info("inside createEmployee method");
        try{
            ApiResponse apiResponse = apiConnector.doPost(mockApiBaseUrl + Constants.MOCK_API_CONTROLLER_PATH + Constants.MOCK_API_GET_EMPLOYEES, JsonUtil.toJson(employeeInput), null);

            CreateEmployeeResponseDto createEmployeeResponseDto = JsonUtil.fromJson(apiResponse.getResponse(), CreateEmployeeResponseDto.class);

            Employee employee = createEmployeeResponseDto.getData();

            return employee;
        }catch(Exception e){
            LOGGER.error("Exception while calling server api to create new employee");
            throw exceptionUtil.getAPIException(ErrorCode.EXCEPTION_WHILE_CALLING_EXTERNAL_API, new Object[]{"NewEmployee"});
        }


    }

    @Override
    public String deleteEmployeeById(String id) throws IOException{
        LOGGER.info("inside deleteEmployeeById method ");

        try{
            Employee employee = getEmployeeById(id);

            if(Objects.isNull(employee)){
                throw exceptionUtil.getAPIException(ErrorCode.NO_RECORDS_FOUND, new Object[]{"DeleteEmployee"});
            }

            DeleteEmployeeDto deleteEmployeeDto = new DeleteEmployeeDto();
            deleteEmployeeDto.setName(employee.getEmployeeName());

            ApiResponse apiResponse = apiConnector.
                    doDelete(mockApiBaseUrl + Constants.MOCK_API_CONTROLLER_PATH + Constants.MOCK_API_GET_EMPLOYEES , JsonUtil.toJson(deleteEmployeeDto) ,null);

            DeleteEmployeeResponseDto deleteEmployeeResponseDto = JsonUtil.fromJson(apiResponse.getResponse(), DeleteEmployeeResponseDto.class);
            if(deleteEmployeeResponseDto.isData()){
                LOGGER.info("Deleted Successfully");
            }
            return employee.getEmployeeName();
        }catch(Exception e){
            LOGGER.error("Exception while calling server api to delete an employee");
            throw exceptionUtil.getAPIException(ErrorCode.EXCEPTION_WHILE_CALLING_EXTERNAL_API, new Object[]{"DeleteEmployee"});
        }


    }
}
