package com.dbproj.manageprompt.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectUpdateRequestDto {
    @NotBlank
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start_date;

    @NotBlank
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end_date;

    @NotBlank
    private Integer budget;
}
