package dev.igor.picpay.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_wallet")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "full_name")
    private String fullName;
    @Column(unique = true)
    private String document;
    @Column(unique = true)
    private String email;
    private String password;
    private BigDecimal balance = BigDecimal.ZERO;
    @ManyToOne
    @JoinColumn(name = "wallet_type_id")
    private WalletType walletType;

    public Wallet() {
    }

    public Wallet(String fullName, String document, String email, String password, WalletType walletType) {
        this.fullName = fullName;
        this.document = document;
        this.email = email;
        this.password = password;
        this.walletType = walletType;
    }

    public boolean isTransferAllowedForWalletType() {
        return this.walletType.equals(WalletType.Enum.USER.get());
    }

    public boolean isBalancerEqualOrGreaterThan(BigDecimal value) {
        return this.balance.doubleValue() >= value.doubleValue();
    }

    public void debit(BigDecimal value) {
        this.balance = this.balance.subtract(value);
    }

    public void credit(BigDecimal value) {
        this.balance = this.balance.add(value);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public WalletType getWalletType() {
        return walletType;
    }

    public void setWalletType(WalletType walletType) {
        this.walletType = walletType;
    }
}
