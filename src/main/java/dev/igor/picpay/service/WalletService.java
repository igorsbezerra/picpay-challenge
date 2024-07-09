package dev.igor.picpay.service;

import dev.igor.picpay.controller.dto.CreateWalletDto;
import dev.igor.picpay.entity.Wallet;
import dev.igor.picpay.exceptions.WalletDataAlreadyExistsException;
import dev.igor.picpay.repository.WalletRepository;
import org.springframework.stereotype.Service;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet createWallet(CreateWalletDto dto) {
        var wallet = walletRepository.findByDocumentOrEmail(dto.document(), dto.email());
        if (wallet.isPresent()) {
            throw new WalletDataAlreadyExistsException("'document' or 'e-mail' already exists");
        }
        return walletRepository.save(dto.toWallet());
    }
}
