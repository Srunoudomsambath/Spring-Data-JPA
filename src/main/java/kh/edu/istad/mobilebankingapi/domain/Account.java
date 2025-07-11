package kh.edu.istad.mobilebankingapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "account_number",nullable = false,unique = true,length = 20)
    private String accountNumber;
//    @Column(name = "account_type",nullable = false,length = 50)
//    private String accountType;
    @Column(name = "account_currency",nullable = false,length = 20)
    private String accountCurrency;
    @Column(name = "balance",nullable = false)
    private BigDecimal balance;
    @Column(name = "is_deleted",nullable = false)
    private Boolean isDeleted = false;



    //Many accounts have only one customer
    @ManyToOne
    @JoinColumn(name = "cust_id") // TODO to rename the relationship column name
    private Customer customer;

    //Many accounts have only one account_type (Saving, Current)
    @ManyToOne
    @JoinColumn(name = "account_type_id", nullable = false)
    private AccountType accountType;

    // One account can sent many transactions as sender or receiver
    @OneToMany(mappedBy = "sender")
    private List<Transaction> sentTransactions;

    @OneToMany(mappedBy = "receiver")
    private List<Transaction> receivedTransactions;

    //One transaction has one sender or receiver account



}
