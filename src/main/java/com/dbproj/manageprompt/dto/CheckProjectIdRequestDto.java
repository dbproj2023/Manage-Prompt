package com.dbproj.manageprompt.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CheckProjectIdRequestDto {
    @NotBlank
    private String proId;
}
