package com.reliaquest.api.constants;

public interface Constants {
    String MOCK_API_CONTROLLER_PATH = "/api/v1";
    String MOCK_API_GET_EMPLOYEES = "/employee";
    String API_GET_EMPLOYEE = "/employees";
    String API_EMPLOYEE_SEARCH_BY_NAME = "/search/{searchString}";
    String API_GET_EMPLOYEE_BY_ID = "/{id}";
    String API_GET_HIGHEST_SALARY_EMPLOYEE = "/highestSalary";
    String API_GET_TOP_TEN_HIGHEST_EARNING_EMPLOYEES = "/topTenHighestEarningEmployees";
    String API_CREATE_EMPLOYEE = "/create/employee";
    String API_DELETE_EMPLOYEE = "/deleteEmployee/{id}";
}
