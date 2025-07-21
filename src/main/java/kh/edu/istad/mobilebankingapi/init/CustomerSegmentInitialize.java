package kh.edu.istad.mobilebankingapi.init;


import jakarta.annotation.PostConstruct;
import kh.edu.istad.mobilebankingapi.domain.Segment;
import kh.edu.istad.mobilebankingapi.repository.SegmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomerSegmentInitialize {

    private final SegmentRepository segmentRepository;

    @PostConstruct
    public void init() {

        if (segmentRepository.count() == 0) {
            Segment segmentRegular = new Segment();
            segmentRegular.setSegment("REGULAR");
            segmentRegular.setDescription("REGULAR");
            segmentRegular.setIsDeleted(false);

            Segment segmentSilver = new Segment();
            segmentSilver.setSegment("SILVER");
            segmentSilver.setDescription("SILVER");
            segmentSilver.setIsDeleted(false);

            Segment segmentGold = new Segment();
            segmentGold.setSegment("GOLD");
            segmentGold.setDescription("GOLD");
            segmentGold.setIsDeleted(false);

            segmentRepository.saveAll(
                    List.of(segmentRegular, segmentSilver, segmentGold)
            );
        }

    }
}
