package kh.edu.istad.mobilebankingapi.repository;

import kh.edu.istad.mobilebankingapi.domain.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MediaRepository extends JpaRepository<Media, Integer> {
    Optional<Media> findByFilenameAndIsDeletedFalse(String filename);
}
