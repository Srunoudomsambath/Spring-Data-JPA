package kh.edu.istad.mobilebankingapi.repository;

import kh.edu.istad.mobilebankingapi.domain.Segment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SegmentRepository extends JpaRepository<Segment,Integer> {
}
