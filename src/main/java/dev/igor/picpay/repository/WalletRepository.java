package dev.igor.picpay.repository;

import dev.igor.picpay.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByDocumentOrEmail(String document, String email);
}
