package dev.chanchhaya.course.features.file;

import dev.chanchhaya.course.features.file.dto.FileResponse;
import dev.chanchhaya.course.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Value("${file-server.server-path}")
    private String serverPath;

    @Value("${file-server.base-uri}")
    private String baseUri;


    @Override
    public List<FileResponse> uploadMultiple(List<MultipartFile> files) throws IOException {

        List<FileResponse> responses = new ArrayList<>();

        for (MultipartFile file : files) {
            responses.add(upload(file));
        }

        return responses;
    }


    @Override
    public FileResponse upload(MultipartFile file) throws IOException {

        String newName = FileUtil.generateFileName(file.getOriginalFilename());
        String extension = FileUtil.extractExtension(file.getOriginalFilename());

        Path path = Path.of(serverPath + newName);
        Files.copy(file.getInputStream(), path);

        return FileResponse.builder()
                .name(newName)
                .size(file.getSize())
                .extension(extension)
                .contentType(file.getContentType())
                .uri(baseUri + newName)
                .build();
    }

}
