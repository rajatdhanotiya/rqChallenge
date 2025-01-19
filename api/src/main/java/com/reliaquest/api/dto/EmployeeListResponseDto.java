package com.reliaquest.api.dto;

import com.reliaquest.api.entities.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeListResponseDto {
    private String status;
    private List<Employee> data;
}
