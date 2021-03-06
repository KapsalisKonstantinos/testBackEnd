package com.testTask.testTask.modelDTOs;

import com.testTask.testTask.models.Company;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String address;
    private Float salary;
    private Integer company_id;
}
