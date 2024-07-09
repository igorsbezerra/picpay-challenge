package dev.igor.picpay.controller;

import dev.igor.picpay.controller.dto.CreateWalletDto;
import dev.igor.picpay.entity.Wallet;
import dev.igor.picpay.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletController {
    private final WalletService service;

    public WalletController(WalletService service) {
        this.service = service;
    }

    @PostMapping("/wallets")
    public ResponseEntity<Wallet> createWallet(@RequestBody @Valid CreateWalletDto dto) {
        return ResponseEntity.status(201).body(service.createWallet(dto));
    }
}
