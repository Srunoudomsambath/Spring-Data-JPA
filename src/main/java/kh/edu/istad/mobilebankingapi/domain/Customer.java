package kh.edu.istad.mobilebankingapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

// Step 1 Mark @Entity above java class OR POJO
@Entity
@Table(name="customers")
@Getter
@Setter
@NoArgsConstructor

public class Customer {
        @Id   // Primary Key
        @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto generate value of primary key
        private Integer id;

        @Column(name = "full_name", nullable = false)
        private String fullName;

        @Column(name = "gender", nullable = false)
        private String gender;

        @Column(name = "email", nullable = false, unique = true)
        private String email;

        @Column(name = "phone_number", unique = true, length = 20)
        private String phoneNumber;

        @Column(name = "remark")
        private String remark;

        @Column(name = "is_deleted", nullable = false)
        private Boolean isDeleted = false;


        @Column(nullable = false)
        private LocalDate dob;

        @Column(length = 100)
        private String address;
        @Column(length = 50)
        private String cityOrProvince;
        @Column(length = 50)
        private String country;
        @Column(length = 50)
        private String zipCode;

        @Column(length = 50)
        private String employmentType;
        @Column(length = 50)
        private String position;
        @Column(length = 50)
        private String companyName;
        @Column(length = 50)
        private String mainSourceOfIncome;
        @Column(length = 50)
        private BigDecimal monthlyIncomeRange;


        //one customer has many accounts
        // TODO mappedBy meaning tell hibernate that we already make a relationship
        //  "if not use this it will show 3 tables" bidirectional and we use in this class cause it plural accounts
        @OneToMany(mappedBy = "customer")
        private List<Account> accounts;

        //
        @OneToOne(mappedBy = "customer",cascade = CascadeType.ALL)
//        @JoinColumn(unique = true)// kyc is unique
        @PrimaryKeyJoinColumn
        private KYC kyc;

        //many customers has only one segment
        @ManyToOne
        @JoinColumn(name = "segment")
        private Segment segment;


}
