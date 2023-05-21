package com.dbproj.manageprompt.dto;

import com.dbproj.manageprompt.entity.AccessInfoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccessInfoRequestDto {
    private Integer accessGrade;
    private String discrete;

    public static AccessInfoRequestDto toDto(AccessInfoEntity accessInfoEntity) {
        AccessInfoRequestDto accessInfoRequestDto = new AccessInfoRequestDto();
        accessInfoRequestDto.setAccessGrade(accessInfoEntity.getAccessGrade());
        accessInfoRequestDto.setDiscrete(accessInfoEntity.getDiscrete());
        return accessInfoRequestDto;
    }
}
