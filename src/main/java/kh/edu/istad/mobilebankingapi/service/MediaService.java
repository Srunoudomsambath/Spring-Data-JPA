package kh.edu.istad.mobilebankingapi.service;
import kh.edu.istad.mobilebankingapi.domain.Media;
import kh.edu.istad.mobilebankingapi.dto.MediaResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MediaService {

    MediaResponse upload(MultipartFile file);
    List<MediaResponse> uploadMultiple(List<MultipartFile> files);
    Resource downloadByName(String filename);
    void deleteByName(String filename);


}
