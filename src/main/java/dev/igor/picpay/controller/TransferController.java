package dev.igor.picpay.controller;

import dev.igor.picpay.controller.dto.TransferDto;
import dev.igor.picpay.entity.Transfer;
import dev.igor.picpay.service.TransferService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransferController {
    private final TransferService service;

    public TransferController(TransferService service) {
        this.service = service;
    }

    @PostMapping("/transfer")
    public ResponseEntity<Transfer> transfer(@RequestBody @Valid TransferDto dto) {
        var resp = service.transfer(dto);
        return ResponseEntity.ok(resp);
    }
}
