package kh.edu.istad.mobilebankingapi.service.impl;

import kh.edu.istad.mobilebankingapi.domain.Media;
import kh.edu.istad.mobilebankingapi.dto.MediaResponse;
import kh.edu.istad.mobilebankingapi.repository.MediaRepository;
import kh.edu.istad.mobilebankingapi.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.MalformedURLException;
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
        // Validate file
        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File is empty or missing");
        }
        // Generate unique name
        String name = UUID.randomUUID().toString();

        // Extract extension from original filename
        String originalFilename = Objects.requireNonNull(file.getOriginalFilename());
        int lastIndex = originalFilename.lastIndexOf('.');
        if (lastIndex == -1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid file extension");
        }

        String extension = originalFilename.substring(lastIndex + 1);
        String filename = name + "." + extension;

        // Save to local storage
        Path path = Paths.get(serverPath).resolve(filename);
        try {
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Media upload failed");
        }

        // Save metadata in DB
        Media media = new Media();
        media.setName(name);
        media.setFilename(filename); // ✅ set filename for querying
        media.setExtension(extension);
        media.setMimeType(file.getContentType());
        media.setIsDeleted(false);

        media = mediaRepository.save(media);

        return MediaResponse.builder()
                .name(media.getName())
                .mimeType(media.getMimeType())
                .size(file.getSize())
                .uri(baseUri + filename)
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

    @Override
    public Resource downloadByName(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Filename cannot be empty");
        }

        Media media = mediaRepository.findByFilenameAndIsDeletedFalse(filename)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found: " + filename));

        try {
            Path filePath = Paths.get(serverPath).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found or not readable: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error accessing file: " + filename);
        }
    }

    @Override
    public void deleteByName(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Filename cannot be empty");
        }

        if (filename.contains("..") || filename.contains("/") || filename.contains("\\")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid filename");
        }

        Media media = mediaRepository.findByFilenameAndIsDeletedFalse(filename)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found: " + filename));

        // Soft delete: mark as deleted in DB
        media.setIsDeleted(true);
        mediaRepository.save(media);

        // Optional: physically delete file from disk
        Path filePath = Paths.get(serverPath).resolve(filename).normalize();
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            // Log the error, but don't block delete operation
            System.err.println("Failed to delete file from disk: " + filename);
        }
    }
}