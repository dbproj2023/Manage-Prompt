package com.dbproj.manageprompt.controller;

import com.dbproj.manageprompt.dto.ClientResponseDto;
import com.dbproj.manageprompt.dto.ClientUpdateRequestDto;
import com.dbproj.manageprompt.dto.IdResponseDto;
import com.dbproj.manageprompt.service.ClientService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/client")
public class ClientController {

    private final ClientService clientService;

    // 발주처 상세 조회
    @GetMapping("/{client_id}")
    public ClientResponseDto findOne(@PathVariable(value = "client_id") long clientId) {
        return clientService.findOne(clientId);
    }

    // 발주처 담당자 정보 수정
    @PatchMapping("/update/{client_id}")
//    public IdResponseDto update(@PathVariable(value = "client_id") long clientId, @RequestBody @Valid ClientUpdateRequestDto requestDto) {
    public IdResponseDto update(@PathVariable(value = "client_id") long clientId, ClientUpdateRequestDto requestDto) {
        long responseId = clientService.update(clientId, requestDto);
        return new IdResponseDto(responseId);
    }
}
