package kh.edu.istad.mobilebankingapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "transaction_type")
@Getter
@Setter
@NoArgsConstructor
public class TransactionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;


    //One transactionType(TRANSFER, PAYMENT) can be used by many transactions
    @OneToMany(mappedBy = "transactionType",cascade = CascadeType.ALL)
    private List<Transaction> transactions;
}
