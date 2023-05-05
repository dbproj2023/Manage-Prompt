package com.dbproj.manageprompt.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectUpdateRequestDto {
    @NotBlank
    private Date startDate;

    @NotBlank
    private Date endDate;

    @NotBlank
    private Integer budget;
}
