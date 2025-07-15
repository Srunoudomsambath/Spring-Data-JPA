package kh.edu.istad.mobilebankingapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "segment")
@Getter
@Setter
@NoArgsConstructor
public class Segment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;// Gold Silver Regular
    @Column(nullable = false)
    private Boolean isDeleted;
    private String description;

    // One segment using by many customers
    @OneToMany(mappedBy = "segment")
    List<Customer> customers;
}
