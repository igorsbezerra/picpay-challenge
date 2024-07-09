package dev.igor.picpay.controller.dto;

import dev.igor.picpay.entity.Wallet;
import dev.igor.picpay.entity.WalletType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateWalletDto(
        @NotBlank String fullName,
        @NotBlank String document,
        @NotBlank String email,
        @NotBlank String password,
        @NotNull WalletType.Enum walletType
) {
    public Wallet toWallet() {
        return new Wallet(fullName, document, email, password, walletType.get());
    }
}
