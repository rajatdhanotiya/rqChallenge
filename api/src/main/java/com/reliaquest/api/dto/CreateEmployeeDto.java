package com.reliaquest.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmployeeDto {
    private String name;
    private Integer salary;
    private Integer age;
    private String title;

}
