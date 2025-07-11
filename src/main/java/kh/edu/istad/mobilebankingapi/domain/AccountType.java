package kh.edu.istad.mobilebankingapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "account_type")
@Getter
@Setter
@NoArgsConstructor
// Can be saving or current acc_type
public class AccountType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    //One account type can be use by many account (
    @OneToMany(mappedBy = "accountType",cascade = CascadeType.ALL)
    private List<Account> accounts;
}
