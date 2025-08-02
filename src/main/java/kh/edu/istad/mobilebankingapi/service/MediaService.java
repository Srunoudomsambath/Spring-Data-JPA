package kh.edu.istad.mobilebankingapi.service;
import kh.edu.istad.mobilebankingapi.domain.Media;
import kh.edu.istad.mobilebankingapi.dto.MediaResponse;
import org.springframework.web.multipart.MultipartFile;

public interface MediaService {

    MediaResponse upload(MultipartFile file);
}
