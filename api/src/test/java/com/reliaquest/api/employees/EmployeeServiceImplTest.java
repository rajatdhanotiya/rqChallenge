package com.reliaquest.api.employees;

import com.reliaquest.api.bean.ApiResponse;
import com.reliaquest.api.dto.*;
import com.reliaquest.api.entities.Employee;
import com.reliaquest.api.enums.ErrorCode;
import com.reliaquest.api.exception.APIException;
import com.reliaquest.api.service.impl.EmployeeServiceImpl;
import com.reliaquest.api.util.ApiConnector;
import com.reliaquest.api.util.ExceptionUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private ApiConnector apiConnector;

    @Mock
    private ExceptionUtil exceptionUtil;

    @Mock
    private Logger logger;

    @Mock
    private ErrorCode errorCode;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    void testGetAllEmployeeList() throws IOException {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResponse("{\"data\": [{\"id\": \"1\", \"employee_name\": \"John Doe\", \"employee_salary\": 50000}]}");
        when(apiConnector.doGet(anyString(), any())).thenReturn(apiResponse);

        List<Employee> employees = employeeService.getAllEmployeeList();

        assertNotNull(employees);
        assertEquals(1, employees.size());
        assertEquals("John Doe", employees.get(0).getEmployeeName());
    }

    @Test
    void testGetEmployeeById() throws IOException {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResponse("{\"data\": {\"id\": \"1\", \"employee_name\": \"John Doe\", \"employee_salary\": 50000}}");
        when(apiConnector.doGet(anyString(), any())).thenReturn(apiResponse);

        Employee employee = employeeService.getEmployeeById("1");

        assertNotNull(employee);
        assertEquals("John Doe", employee.getEmployeeName());
    }

    @Test
    void testGetEmployeesByNameSearch() throws IOException {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResponse("{\"data\": [{\"id\": \"1\", \"employee_name\": \"John Doe\", \"employee_salary\": 50000}]}");
        when(apiConnector.doGet(anyString(), any())).thenReturn(apiResponse);

        List<Employee> employees = employeeService.getEmployeesByNameSearch("John");

        assertNotNull(employees);
        assertEquals(1, employees.size());
        assertEquals("John Doe", employees.get(0).getEmployeeName());
    }

    @Test
    void testGetHighestSalaryOfEmployee() throws IOException {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResponse("{\"data\": [{\"id\": \"1\", \"employee_name\": \"John Doe\", \"employee_salary\": 50000}]}");
        when(apiConnector.doGet(anyString(), any())).thenReturn(apiResponse);

        Integer highestSalary = employeeService.getHighestSalaryOfEmployee();

        assertNotNull(highestSalary);
        assertEquals(50000, highestSalary);
    }

    @Test
    void testGetTopTenHighestEarningEmployeeNames() throws IOException {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResponse("{\"data\": [{\"id\": \"1\", \"employee_name\": \"John Doe\", \"employee_salary\": 50000}]}");
        when(apiConnector.doGet(anyString(), any())).thenReturn(apiResponse);

        List<Employee> employees = employeeService.getTopTenHighestEarningEmployeeNames();

        assertNotNull(employees);
        assertEquals(1, employees.size());
        assertEquals("John Doe", employees.get(0).getEmployeeName());
    }

    @Test
    void testCreateEmployee() throws IOException {
        CreateEmployeeDto createEmployeeDto = new CreateEmployeeDto();
        createEmployeeDto.setName("John Doe");
        createEmployeeDto.setSalary(50000);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResponse("{\"data\": {\"id\": \"1\", \"employee_name\": \"John Doe\", \"employee_salary\": 50000}}");
        when(apiConnector.doPost(anyString(), anyString(), any())).thenReturn(apiResponse);

        Employee employee = employeeService.createEmployee(createEmployeeDto);

        assertNotNull(employee);
        assertEquals("John Doe", employee.getEmployeeName());
    }

    @Test
    void testDeleteEmployeeById() throws IOException {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResponse("{\"data\": {\"id\": \"1\", \"employee_name\": \"John Doe\", \"employee_salary\": 50000}}");
        when(apiConnector.doGet(anyString(), any())).thenReturn(apiResponse);

        ApiResponse deleteResponse = new ApiResponse();
        deleteResponse.setResponse("{\"data\": true}");
        when(apiConnector.doDelete(anyString(), anyString(), any())).thenReturn(deleteResponse);

        String employeeName = employeeService.deleteEmployeeById("1");

        assertNotNull(employeeName);
        assertEquals("John Doe", employeeName);
    }

    // Negative Test Cases -

    @Test
    void testGetAllEmployeeList_NoEmployeesFound() throws IOException {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResponse("{\"data\": []}");
        when(apiConnector.doGet(anyString(), any())).thenReturn(apiResponse);
        when(exceptionUtil.getAPIException(any(),any())).thenThrow(new APIException(ErrorCode.NO_RECORDS_FOUND, "APIException"));

        APIException exception = assertThrows(APIException.class, () -> {
                    employeeService.getAllEmployeeList();
        });
        assertEquals(ErrorCode.NO_RECORDS_FOUND.getCode(), exception.getErrorCode().getCode());
    }

    @Test
    void testGetAllEmployeeList_ExceptionWhileCallingApi() throws IOException {
        when(apiConnector.doGet(anyString(), any())).thenThrow(new IOException("API call failed"));
        when(exceptionUtil.getAPIException(any(),any())).thenThrow(new APIException(ErrorCode.EXCEPTION_WHILE_CALLING_EXTERNAL_API, "APIException"));

        Exception exception = assertThrows(APIException.class, () -> {
            employeeService.getAllEmployeeList();
        });

        assertEquals(ErrorCode.EXCEPTION_WHILE_CALLING_EXTERNAL_API.getCode(), ((APIException) exception).getErrorCode().getCode());
    }

    @Test
    void testGetEmployeeById_NoEmployeeFound() throws IOException {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResponse("{\"data\": null}");
        when(apiConnector.doGet(anyString(), any())).thenReturn(apiResponse);
        when(exceptionUtil.getAPIException(any(),any())).thenThrow(new APIException(ErrorCode.NO_RECORDS_FOUND, "APIException"));

        Exception exception = assertThrows(APIException.class, () -> {
            employeeService.getEmployeeById("1");
        });

        assertEquals(ErrorCode.NO_RECORDS_FOUND.getCode(), ((APIException) exception).getErrorCode().getCode());
    }

    @Test
    void testGetEmployeeById_ExceptionWhileCallingApi() throws IOException {
        when(apiConnector.doGet(anyString(), any())).thenThrow(new IOException("API call failed"));
        when(exceptionUtil.getAPIException(any(),any())).thenThrow(new APIException(ErrorCode.EXCEPTION_WHILE_CALLING_EXTERNAL_API, "APIException"));

        Exception exception = assertThrows(APIException.class, () -> {
            employeeService.getEmployeeById("1");
        });

        assertEquals(ErrorCode.EXCEPTION_WHILE_CALLING_EXTERNAL_API.getCode(), ((APIException) exception).getErrorCode().getCode());
    }
}