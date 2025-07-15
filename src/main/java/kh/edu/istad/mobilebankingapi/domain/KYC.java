package kh.edu.istad.mobilebankingapi.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class KYC {
    // Know your customer
    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
    private Integer id;
    @Column(nullable = false)
    private String nationalCardId;
    @Column(nullable = false)
    private Boolean isVerified;
    @Column(nullable = false)
    private Boolean isDeleted;


    @OneToOne
    //shared primary key
    @MapsId
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
