package kh.edu.istad.mobilebankingapi.service.impl;

import kh.edu.istad.mobilebankingapi.domain.Media;
import kh.edu.istad.mobilebankingapi.dto.MediaResponse;
import kh.edu.istad.mobilebankingapi.repository.MediaRepository;
import kh.edu.istad.mobilebankingapi.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;



@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {

    @Value("${media.server-path}")
    private String serverPath;

    @Value("${media.base-uri}")
    private String baseUri;

    private final MediaRepository mediaRepository;

    @Override
    public MediaResponse upload(MultipartFile file) {

        String name = UUID.randomUUID().toString();

        // Find last index of dot (.)
        int lastIndex = Objects.requireNonNull(file.getOriginalFilename())
                .lastIndexOf('.');
        // Extract extension
        String extension = file.getOriginalFilename()
                .substring(lastIndex + 1);

        // Create path object
        Path path = Paths.get(serverPath + String.format("%s.%s", name, extension));

        try {
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Media upload failed");
        }

        Media media = new Media();
        media.setName(name);
        media.setExtension(extension);
        media.setMimeType(file.getContentType());
        media.setIsDeleted(false);

        media = mediaRepository.save(media);

        return MediaResponse.builder()
                .name(media.getName())
                .mimeType(media.getMimeType())
                .size(file.getSize())
                .uri(baseUri + String.format("%s.%s", name, extension))
                .build();
    }

    @Override
    public List<MediaResponse> uploadMultiple(List<MultipartFile> files) {

        List<MediaResponse> responses = new ArrayList<>();
        for (MultipartFile file : files) {
            responses.add(upload(file)); // reuse single upload logic
        }
        return responses;
    }

}