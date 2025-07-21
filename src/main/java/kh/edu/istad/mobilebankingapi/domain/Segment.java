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
    @Column(nullable = false, unique = true, length = 100)
    private String segment;// Gold Silver Regular
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(nullable = false)
    private Boolean isDeleted;

    // One segment using by many customers
    @OneToMany(mappedBy = "segment", fetch = FetchType.EAGER)
    private List<Customer> customers;
}
