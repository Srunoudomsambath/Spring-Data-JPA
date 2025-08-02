package kh.edu.istad.mobilebankingapi.controller;


import kh.edu.istad.mobilebankingapi.dto.MediaResponse;
import kh.edu.istad.mobilebankingapi.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/medias")
@RequiredArgsConstructor
public class MediaController {
    private final MediaService mediaService;

    @PostMapping
    public MediaResponse upload(@RequestPart MultipartFile file) {
        return mediaService.upload(file);
    }

    @PostMapping("/upload-multiple")
    public ResponseEntity<List<MediaResponse>> uploadMultipleFiles(@RequestParam("files") List<MultipartFile> files) {
        return  ResponseEntity.ok(mediaService.uploadMultiple(files));
    }

    @GetMapping("/media/{filename:.+}")
     public ResponseEntity<Resource> download(@PathVariable String filename) {
        Resource resource = mediaService.downloadByName(filename);

        return  ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+resource.getFilename()+"\"")
                .body(resource);
    }
    @DeleteMapping("media/{filename:.+}")
    public ResponseEntity<Void> deleteByFilename(@PathVariable String filename) {
        mediaService.deleteByName(filename);
        return  ResponseEntity.noContent().build();
    }
}
