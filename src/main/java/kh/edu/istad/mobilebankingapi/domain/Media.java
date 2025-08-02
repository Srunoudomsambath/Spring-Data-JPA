package kh.edu.istad.mobilebankingapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "medias")
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(unique = true, nullable = false,length =160)
    private String name;

    @Column(nullable = false, length = 20)
    private String extension;

    @Column(nullable = false,length =160)
    private String mimeType;
    @Column(nullable = false)
    private Boolean isDeleted;

}
