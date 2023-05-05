package com.dbproj.manageprompt.dto;

import com.dbproj.manageprompt.entity.ClientInfoEntity;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ClientRequestDto {
    private String clientName;
    private String clientEmpName;
    private String clientEmpPh;
    private String clientEmpEmail;

    public ClientInfoEntity toEntity() {
        ClientInfoEntity clientInfo = ClientInfoEntity.builder()
                .clientName(clientName)
                .clientEmpName(clientEmpName)
                .clientEmpPh(clientEmpPh)
                .clientEmpEmail(clientEmpEmail)
                .build();

        return clientInfo;
    }
}
