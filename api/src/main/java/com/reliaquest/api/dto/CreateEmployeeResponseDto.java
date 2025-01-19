package com.reliaquest.api.dto;

import com.reliaquest.api.entities.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmployeeResponseDto {
    private String status;
    private Employee data;
}
