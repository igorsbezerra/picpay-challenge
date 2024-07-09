package dev.igor.picpay.service;

import dev.igor.picpay.controller.dto.TransferDto;
import dev.igor.picpay.entity.Transfer;
import dev.igor.picpay.entity.Wallet;
import dev.igor.picpay.exceptions.InsufficientBalanceException;
import dev.igor.picpay.exceptions.TransferNotAllowedForWalletTypeException;
import dev.igor.picpay.exceptions.TransferNotAuthorizedException;
import dev.igor.picpay.exceptions.WalletNotFoundException;
import dev.igor.picpay.repository.TransferRepository;
import dev.igor.picpay.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class TransferService {
    private final TransferRepository transferRepository;
    private final AuthorizationService authorizationService;
    private final NotificationService notificationService;
    private final WalletRepository walletRepository;

    public TransferService(TransferRepository repository, TransferRepository repository1, AuthorizationService authorizationService, NotificationService notificationService, WalletRepository walletRepository) {
        this.transferRepository = repository1;
        this.authorizationService = authorizationService;
        this.notificationService = notificationService;
        this.walletRepository = walletRepository;
    }

    @Transactional
    public Transfer transfer(TransferDto dto) {
        Wallet sender = walletRepository.findById(dto.payer())
                .orElseThrow(() -> new WalletNotFoundException(dto.payer()));
        Wallet receiver = walletRepository.findById(dto.payee())
                .orElseThrow(() -> new WalletNotFoundException(dto.payee()));
        validateTransfer(dto, sender);
        sender.debit(dto.value());
        receiver.credit(dto.value());

        var transfer = new Transfer(sender, receiver, dto.value());
        transferRepository.save(transfer);
        walletRepository.save(sender);
        walletRepository.save(receiver);
        CompletableFuture.runAsync(() -> notificationService.sendNotification(transfer));

        return transfer;
    }

    private void validateTransfer(TransferDto dto, Wallet sender) {
        if (!sender.isTransferAllowedForWalletType()) {
            throw new TransferNotAllowedForWalletTypeException();
        }
        if (!sender.isBalancerEqualOrGreaterThan(dto.value())) {
            throw new InsufficientBalanceException();
        }
        if (!authorizationService.isAuthorized(dto)) {
            throw new TransferNotAuthorizedException();
        }
    }
}
