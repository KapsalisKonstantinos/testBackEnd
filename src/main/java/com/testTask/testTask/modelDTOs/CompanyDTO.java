package com.testTask.testTask.modelDTOs;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CompanyDTO {
    private Integer id;
    private String name;
}
