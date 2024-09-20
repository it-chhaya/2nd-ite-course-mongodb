package dev.chanchhaya.course.features.file;

import dev.chanchhaya.course.features.file.dto.FileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    List<FileResponse> uploadMultiple(List<MultipartFile> files) throws IOException;

    FileResponse upload(MultipartFile file) throws IOException;

}
